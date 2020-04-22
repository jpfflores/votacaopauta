package com.testesicredi.votacaopauta.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
@SqlResultSetMapping(
	    name="VotacaoResult",
	    entities={ @EntityResult(entityClass=com.testesicredi.votacaopauta.model.ResultadoVotacao.class,
		        fields={
		        		@FieldResult(name="totalSim", column="SIM"),
		        		@FieldResult(name="totalNao", column="NAO")
		        		}
	    			)
	    		}
	    )

@NamedNativeQuery(name = "ResultadoVotacao",
query = "Select sum(case when conteudo = 'SIM' then 1 else 0 end) as SIM, sum(case when conteudo = 'SIM' then 0 else 1 end) as NAO from VOTO where id Pauta= ?1",
resultSetMapping = "VotacaoResult")



@Entity
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ResultadoVotacao {

	Long votoSim;
	Long votoNao;


	
}
