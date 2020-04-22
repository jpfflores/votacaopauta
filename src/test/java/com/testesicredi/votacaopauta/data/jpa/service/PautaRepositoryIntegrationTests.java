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

package com.testesicredi.votacaopauta.data.jpa.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.testesicredi.votacaopauta.VotacaoPautaApplication;
import com.testesicredi.votacaopauta.model.Pauta;
import com.testesicredi.votacaopauta.repository.PautaRepository;
import com.testesicredi.votacaopauta.repository.VotoRepository;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
//@SpringApplicationConfiguration(classes = VotacaoPautaApplication.class)
public class PautaRepositoryIntegrationTests {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;

	@Autowired private PautaRepository pautaRepository;
	@Autowired private VotoRepository votoRepository;


/*
    @Test
    public void testBuscarPauta() {

        //Pauta pauta = new Pauta("admin", "admin", "admin@gmail.com");
        //employeeRepository.save(employee);
        //Pauta pauta2 = new Pauta("admin", "admin", "admin@gmail.com");
        //assertNotNull(pauta);
        //assertEquals(employee2.getFirstName(), employee.getFirstName());
       // assertEquals(employee2.getLastName(), employee.getLastName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee("admin", "admin", "admin@gmail.com");
        employeeRepository.save(employee);
        employeeRepository.delete(employee);
    }

    @Test
    public void findAllEmployees() {
        Employee employee = new Employee("admin", "admin", "admin@gmail.com");
        employeeRepository.save(employee);
        assertNotNull(employeeRepository.findAll());
    }

    @Test
    public void deletByEmployeeIdTest() {
        Employee employee = new Employee("admin", "admin", "admin@gmail.com");
        Employee emp = employeeRepository.save(employee);
        employeeRepository.deleteById(emp.getId());
    }
    
    */
}


