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

package com.testesicredi.votacaopauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.testesicredi.votacaopauta.model.ResultadoVotacao;
import com.testesicredi.votacaopauta.model.Voto;


@RepositoryRestResource(collectionResourceRel = "voto", path = "votos")
public interface VotoRepository extends JpaRepository<Voto, Long> {

	@Query(value = "Select count(*) from VOTO pt where idPauta = ?1 and cpf = ?2")
	Long votouNestaPauta(Long id, String cpf);
  
	@Query(value = "Select sum(case when conteudo = 'SIM' then 1 else 0 end) as SIM, sum(case when conteudo = 'SIM' then 0 else 1 end) as NAO from VOTO where idPauta= ?1")
	ResultadoVotacao resultadoVotacao(Long id);
	
}
