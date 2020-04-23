/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.testesicredi.votacaopauta.data.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.testesicredi.votacaopauta.VotacaoPautaApplication;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.testesicredi.votacaopauta.model.Pauta;

@RunWith(SpringJUnit4ClassRunner.class)

@WebAppConfiguration
@EnableWebMvc
@ActiveProfiles("scratch")

public class VotacaoPautaApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	protected String gerarPauta() throws Exception {

		String uri = "/pautas/gerar-pauta";
		Pauta pauta = new Pauta();
		pauta.setObjetivo("Assunto1");

		String inputJson = "{ \"objetivo\" : \"Assunto1\" }";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		return content;
	}

	public Pauta gerarPautaAtiva() throws Exception {
		String content = gerarPauta();
		Pauta pauta = (Pauta) mapFromJson(content, Pauta.class);
		String uri = "/pautas/ativar-pauta";
		String inputJson = String.format("{ \"id\": %d , \"tempo\" : 50 }", pauta.getId());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertThat(content, containsString("Sucesso. Ativado"));
		return pauta;
	}

	@Test

	public void criarPauta() throws Exception {
		String uri = "http://localhost:8080/pautas/gerar-pauta";

		Pauta pauta = new Pauta();
		pauta.setObjetivo("Assunto1");

		// String inputJson = mapToJson(pauta);
		String inputJson = "{ \"objetivo\" : \"Assunto1\" }";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		Pauta pauta2 = (Pauta) mapFromJson(content, Pauta.class);
		assertEquals(pauta.getId(), pauta2.getId());
	}

	@Test
	public void ativarPauta() throws Exception {
		String content = gerarPauta();
		Pauta pauta = (Pauta) mapFromJson(content, Pauta.class);
		String uri = "/pautas/ativar-pauta";
		String inputJson = String.format("{ \"id\": %d , \"tempo\" : 50 }", pauta.getId());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertThat(content, containsString("Sucesso. Ativado"));
	}

	@Test
	public void votarSucesso() throws Exception {

		Pauta pauta = gerarPautaAtiva();

		// Votar
		String uri = "/voto/votar";

		String inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999900\" , \"conteudo\" : \"SIM\" }",
				pauta.getId());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Voto computado");

	}

	@Test
	public void votarPautaInexistente() throws Exception {
		Pauta pauta = gerarPautaAtiva();

		// Votar
		String uri = "/voto/votar";
		String inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999900\" , \"conteudo\" : \"SIM\" }",
				pauta.getId() + 10);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Pauta inexistente");
	}

	@Test
	public void votarPautaNaoAtiva() throws Exception {
		String content = gerarPauta();
		Pauta pauta = (Pauta) mapFromJson(content, Pauta.class); // Votar
		String uri = "/voto/votar";
		String inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999900\" , \"conteudo\" : \"SIM\" }",
				pauta.getId());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Pauta inexistente");
	}

	@Test
	public void votarMaisUmaVez() throws Exception {
		Pauta pauta = gerarPautaAtiva();

		// Votar
		String uri = "/voto/votar";
		String inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999900\" , \"conteudo\" : \"SIM\" }",
				pauta.getId());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn(); // Segundo voto
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		assertEquals(content, "Associado ja votou");

	}

	@Test
	public void resultado() throws Exception {
		Pauta pauta = gerarPautaAtiva();

		// Votar
		String uri = "/voto/votar";
		String inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999900\" , \"conteudo\" : \"SIM\" }",
				pauta.getId());
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn(); // Segundo voto
		inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999901\" , \"conteudo\" : \"SIM\" }",
				pauta.getId());
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn(); // Terceiro voto
		inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999902\" , \"conteudo\" : \"SIM\" }",
				pauta.getId());
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn(); // Quarto voto
		inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999903\" , \"conteudo\" : \"NAO\" }",
				pauta.getId());
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn(); // Quinto voto
		inputJson = String.format("{ \"id\": %d , \"tempo\" : \"99999999904\" , \"conteudo\" : \"NAO\" }",
				pauta.getId());
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn(); // Sexto voto
		inputJson = String.format("{ \"id\": %d , \"tempo\" : \"9999999995\" , \"conteudo\" : \"NAO\" }",
				pauta.getId());
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		uri = "/pautas/resultado";
		inputJson = String.format("{ \"id\": %d }", pauta.getId());
		mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Contagem SIM: 3 e Não: 3");
	}

}