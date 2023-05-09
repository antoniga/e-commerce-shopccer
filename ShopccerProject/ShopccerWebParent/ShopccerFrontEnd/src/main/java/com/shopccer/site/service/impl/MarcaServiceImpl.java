package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Marca;
import com.shopccer.site.service.MarcaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class MarcaServiceImpl implements MarcaService {
    @Override
    public List<Marca> findAllEnable() {
        return null;
    }
}
