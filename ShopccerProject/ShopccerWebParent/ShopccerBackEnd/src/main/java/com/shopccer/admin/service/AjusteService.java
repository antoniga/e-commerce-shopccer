package com.shopccer.admin.service;

import com.shopccer.common.entity.Ajuste;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AjusteService {

    List<Ajuste> listAllAjustes();
}
