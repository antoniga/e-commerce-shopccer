package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Superficie;
import com.shopccer.site.repository.SuperficieRepository;
import com.shopccer.site.service.SuperficieService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SuperficieServiceImpl implements SuperficieService {

    @Autowired
    private SuperficieRepository superficieRepository;

    @Override
    public List<Superficie> findAllEnable() {
        return superficieRepository.findAllEnabled();
    }

    @Override
    public Superficie findByIdEnable(Integer idSuperficie) {
        return superficieRepository.findByIdEnabled(idSuperficie);
    }
}
