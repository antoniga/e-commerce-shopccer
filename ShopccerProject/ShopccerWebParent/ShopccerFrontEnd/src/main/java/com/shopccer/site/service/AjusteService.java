package com.shopccer.site.service;

import com.shopccer.common.entity.Ajuste;
import com.shopccer.site.util.EmailUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AjusteService {

    List<Ajuste> getAjustesGenerales();
    EmailUtil getAjustesEmail();
}
