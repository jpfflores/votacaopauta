package com.testesicredi.votacaopauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.ObjectUtils;

import com.testesicredi.votacaopauta.model.Voto;
import com.testesicredi.votacaopauta.repository.*;

@RestController
@RequestMapping("/voto")
public class VotoController {
	
	@Autowired
	private VotoRepository voto;
	
	@Autowired
	private PautaRepository pauta;
	
	@RequestMapping(method = RequestMethod.POST, value = "/votar")
	public String votar(@RequestParam Long idPauta, @RequestParam String cpf, @RequestParam String conteudo) {
		
		if (this.pauta.existsById(idPauta)) {
			
			if( this.pauta.prontaParaSerVotada(idPauta) == 1){
				if(this.voto.votouNestaPauta(idPauta, cpf) == 0  ) {
					this.voto.save(new Voto(idPauta, cpf, conteudo));
					return "Voto computado";
				} else {
					return "Associado ja votou";
				}
			} else {
				return "Pauta nao ativada";
			}
		} else {
			return "Pauta Inexistente";
		}
	}

}
