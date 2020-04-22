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

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.testesicredi.votacaopauta.model.Pauta;

@Repository
//@RepositoryRestResource(collectionResourceRel = "pauta", path = "pautas")
public interface PautaRepository extends JpaRepository<Pauta, Long> {

	@Modifying
	@Transactional
    @Query(value = "Update PAUTA pt set pt.dataEncerramento = ?1 WHERE pt.id = ?2")
	void ativaPauta(LocalDateTime encerramento, Long id);
	
    @Query(value = "Select count(*) from PAUTA pt where id = ?1 and pt.dataEncerramento is not null and pt.dataEncerramento > current_timestamp")
	Long prontaParaSerVotada(Long id);
  
    
}
