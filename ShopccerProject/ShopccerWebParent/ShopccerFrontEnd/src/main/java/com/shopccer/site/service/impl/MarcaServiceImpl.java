package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Marca;
import com.shopccer.site.repository.MarcaRepository;
import com.shopccer.site.service.MarcaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> findAllEnable() {
        return marcaRepository.findAllEnabled();
    }

    @Override
    public Marca findByIdEnable(Integer idMarca) {
        return marcaRepository.findByIdEnabled(idMarca);
    }
}
