package com.testesicredi.votacaopauta.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testesicredi.votacaopauta.model.Pauta;
import com.testesicredi.votacaopauta.repository.PautaRepository;

@RestController
@RequestMapping("/pautas")
public class PautaController {
		
		@Autowired
		private PautaRepository pautaRepository;
		
		@RequestMapping(method = RequestMethod.POST, value = "/gerar-pauta")
		public Pauta gerarPauta(@Validated @RequestBody Pauta pauta) {
			return this.pautaRepository.save(pauta);
		}

		@RequestMapping(method = RequestMethod.POST, value = "/ativar-pauta")
		public String ativarPauta(@RequestParam Long id, @RequestParam Long tempo) {
			LocalDateTime dataEncerramento = LocalDateTime.now(); //now
			dataEncerramento = dataEncerramento.plusMinutes(tempo); 
			if(this.pautaRepository.existsById(id)) {
				this.pautaRepository.ativaPauta(dataEncerramento, id);
			}
			return "error";
		}
		
		@RequestMapping(method = RequestMethod.GET, value = "/resultado")
		public String resultadoVotacao(@RequestParam Long id) {
			
			if(this.pautaRepository.existsById(id)) {

			}
			return "error";
		}		
}
