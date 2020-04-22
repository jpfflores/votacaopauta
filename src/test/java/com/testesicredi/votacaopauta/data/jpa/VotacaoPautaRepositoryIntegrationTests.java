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

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.testesicredi.votacaopauta.VotacaoPautaApplication;
import com.testesicredi.votacaopauta.model.Pauta;
import com.testesicredi.votacaopauta.model.Voto;
import com.testesicredi.votacaopauta.repository.PautaRepository;
import com.testesicredi.votacaopauta.repository.VotoRepository;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * Integration tests for {@link PautaRepository}.
 * 
 * @author Oliver Gierke
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
public class VotacaoPautaRepositoryIntegrationTests {

	@Autowired private PautaRepository pautaRepository;
	@Autowired private VotoRepository votoRepository;


    @Test
    public void testCriarPauta() {

        Pauta pauta = new Pauta();
        pauta.setObjetivo("Assunto1");
        pautaRepository.save(pauta);
        assertTrue(pautaRepository.existsById(pauta.getId()));
    }	
    @Test
    public void testBuscarPauta() {

        Pauta pauta = new Pauta();
        pauta.setObjetivo("Assunto1");
        pautaRepository.save(pauta);
        Pauta pauta2 = pautaRepository.findById(pauta.getId()).get();
        assertNotNull(pauta2);
        assertEquals(pauta.getId(),pauta2.getId());
        assertEquals(pauta.getObjetivo(), pauta2.getObjetivo());
    }

    @Test
    public void testVotar() {

        Voto voto = new Voto();
        voto.setCpf("99999999900");
        voto.setConteudo("SIM");
        voto.setIdPauta(new Long(1));
        votoRepository.save(voto);
        
        assertTrue(votoRepository.existsById(voto.getId()));
    }	
    @Test
    public void testBuscarVoto() {

        Voto voto = new Voto();
        voto.setCpf("99999999900");
        voto.setConteudo("SIM");
        voto.setIdPauta(new Long(1));
        votoRepository.save(voto);
        Voto voto2 = votoRepository.findById(voto.getId()).get();
        assertNotNull(voto2);
        assertEquals(voto.getId(),voto2.getId());
        assertEquals(voto.getConteudo(), voto2.getConteudo());
    }

}


