package com.testesicredi.votacaopauta.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.testesicredi.votacaopauta.model.Pauta;
import com.testesicredi.votacaopauta.repository.PautaRepository;



@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PautaService {

 
}
