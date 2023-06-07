package com.shopccer.admin.service;

import com.shopccer.admin.exception.SuperficieNotFoundException;
import com.shopccer.common.entity.Superficie;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SuperficieService {

    public List<Superficie> listAll();

    public Superficie save(Superficie superficie);

    public Superficie findById(Integer id) throws SuperficieNotFoundException;

    public Boolean isNombreUnique(Integer id, String nombre);

    public void deleteById(Integer id) throws SuperficieNotFoundException;

    public void updateSuperficieActiva(Integer id, Boolean activo);

    public Page<Superficie> listByPage(Integer numeroPagina, String campoOrden, String dirOrden, String palabraClave);
}
