package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteCategoria;
import com.shopccer.site.repository.AjusteRepository;
import com.shopccer.site.service.AjusteService;
import com.shopccer.site.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AjusteServiceImpl implements AjusteService {

    @Autowired
    private AjusteRepository ajusteRepository;


    @Override
    public List<Ajuste> getAjustesGenerales() {

        return ajusteRepository.findByCategoria(AjusteCategoria.GENERAL);
    }

    @Override
    public EmailUtil getAjustesEmail() {
        List<Ajuste> ajustes = ajusteRepository.findByCategoria(AjusteCategoria.MAIL_SERVER);
        ajustes.addAll(ajusteRepository.findByCategoria(AjusteCategoria.MAIL_TEMPLATE));

        return new EmailUtil(ajustes);
    }
}
