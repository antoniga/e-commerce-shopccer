package com.shopccer.admin.service.impl;

import com.shopccer.admin.repository.AjusteRepository;
import com.shopccer.admin.service.AjusteService;
import com.shopccer.common.entity.Ajuste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AjusteServiceImpl implements AjusteService {

    @Autowired
    private AjusteRepository ajusteRepository;


    @Override
    public List<Ajuste> listAllAjustes() {
        return (List<Ajuste>) ajusteRepository.findAll();
    }
}
