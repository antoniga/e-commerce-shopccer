package com.shopccer.admin.service;

import com.shopccer.admin.utils.GeneralUtil;
import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteUtil;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AjusteService {

    List<Ajuste> listAllAjustes();
    GeneralUtil getAjustesGenerales();

    void saveAll(Iterable<Ajuste> ajustes);

    List<Ajuste> getAjustesMailServer();
}
