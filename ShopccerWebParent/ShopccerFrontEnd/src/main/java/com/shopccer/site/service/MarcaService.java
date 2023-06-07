package com.shopccer.site.service;

import com.shopccer.common.entity.Marca;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarcaService {

    List<Marca> findAllEnable();

    Marca findByIdEnable(Integer idMarca);


}
