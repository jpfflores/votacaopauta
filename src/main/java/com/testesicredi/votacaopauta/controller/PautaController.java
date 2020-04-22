package com.testesicredi.votacaopauta.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testesicredi.votacaopauta.model.Pauta;
import com.testesicredi.votacaopauta.model.ResultadoVotacao;
import com.testesicredi.votacaopauta.repository.PautaRepository;
import com.testesicredi.votacaopauta.repository.VotoRepository;

@RestController
@RequestMapping("/pautas")
public class PautaController {
		
		@Autowired
		private PautaRepository pautaRepository;
		private VotoRepository votoRepository;
		
		@RequestMapping(method = RequestMethod.POST, value = "/gerar-pauta")
		public Pauta gerarPauta(@Validated @RequestBody Pauta pauta) {
			return this.pautaRepository.save(pauta);
		}

		@RequestMapping(method = RequestMethod.POST, value = "/ativar-pauta")
		public String ativarPauta(@RequestParam Long id, @RequestParam Long tempo) {
			try {
				LocalDateTime dataEncerramento = LocalDateTime.now(); //now
				dataEncerramento = dataEncerramento.plusMinutes(tempo); 
				if(this.pautaRepository.existsById(id)) {
					this.pautaRepository.ativaPauta(dataEncerramento, id);
					return String.format("Sucesso. Ativado até %s", dataEncerramento.toLocalTime());

				} else {
					return "Pauta inexistente";
				}
				
			} catch ( Exception ex){
				return "Falha no ativamento";
			}
			
		}
		
		@RequestMapping(method = RequestMethod.GET, value = "/resultado")
		public String resultadoVotacao(@RequestParam Long id) {
			
			if(this.pautaRepository.existsById(id)) {
				ResultadoVotacao resultado = votoRepository.resultadoVotacao(id);
				if(ObjectUtils.allNotNull(resultado)) {
					return String.format("Contagem SIM: %s e Não: %s", resultado.getVotoSim().toString(), resultado.getVotoNao().toString());
				} else {
					return "Erro na contagem";
				}
			} else {
				return "Pauta inexistente";
			}
		}		
}
