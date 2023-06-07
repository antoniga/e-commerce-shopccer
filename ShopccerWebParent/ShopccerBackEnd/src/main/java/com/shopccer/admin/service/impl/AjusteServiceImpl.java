package com.shopccer.admin.service.impl;

import com.shopccer.admin.repository.AjusteRepository;
import com.shopccer.admin.service.AjusteService;
import com.shopccer.admin.utils.GeneralUtil;
import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AjusteServiceImpl implements AjusteService {

    @Autowired
    private AjusteRepository ajusteRepository;


    @Override
    public List<Ajuste> listAllAjustes() {
        return (List<Ajuste>) ajusteRepository.findAll();
    }

    @Override
    public GeneralUtil getAjustesGenerales() {

        List<Ajuste> ajustes = new ArrayList<>();

        List<Ajuste> ajustesGenerales = ajusteRepository.findByCategoria(AjusteCategoria.GENERAL);

        ajustes.addAll(ajustesGenerales);

        return new GeneralUtil(ajustes);
    }

    @Override
    public void saveAll(Iterable<Ajuste> ajustes) {
        ajusteRepository.saveAll(ajustes);
    }

    @Override
    public List<Ajuste> getAjustesMailServer() {
        return ajusteRepository.findByCategoria(AjusteCategoria.MAIL_SERVER);
    }

    @Override
    public List<Ajuste> getAjustesMailTemplates() {
        return ajusteRepository.findByCategoria(AjusteCategoria.MAIL_TEMPLATE);
    }

    @Override
    public List<Ajuste> getAjustesPagos() {
        return ajusteRepository.findByCategoria(AjusteCategoria.PAGO);
    }


}
