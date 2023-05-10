package com.shopccer.site.service;

import com.shopccer.common.entity.Superficie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SuperficieService {

    List<Superficie> findAllEnable();

    Superficie findByIdEnable(Integer idSuperficie);
}
