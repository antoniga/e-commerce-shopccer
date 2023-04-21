package com.shopccer.admin.service.impl;

import com.shopccer.admin.exception.SuperficieNotFoundException;
import com.shopccer.admin.repository.SuperficieRepository;
import com.shopccer.admin.service.SuperficieService;
import com.shopccer.common.entity.Superficie;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class SuperficieServiceImpl implements SuperficieService {

    public static final Integer SUP_POR_PAG = 4;

    @Autowired
    private SuperficieRepository superficieRepository;

    @Override
    public List<Superficie> listAll() {
        return (List<Superficie>) superficieRepository.findAll();
    }

    @Override
    public Superficie save(Superficie superficie) {
        return superficieRepository.save(superficie);
    }

    @Override
    public Superficie findById(Integer id) throws SuperficieNotFoundException {
        return superficieRepository.findById(id).orElseThrow(() -> new SuperficieNotFoundException("No existe ninguna superficie con ese id: "+id));
    }

    @Override
    public Boolean isNombreUnique(Integer id, String nombre) {

//        Buscamos la superficie por el nombre
        Superficie superficieNombre = superficieRepository.findByNombre(nombre);

//        Si no existe, es null, por lo que el nombre es unico en bd
        if (superficieNombre == null) return true;

//        Si existe el nombre, miramos si es nueva superficie o edicion
//        comprobando si el id es null (nueva superficie)
        boolean isNuevaSuperficie = (id==null);

//        si es nueva
        if (isNuevaSuperficie){
//            y ya ese existe ese nombre, false
            if (superficieNombre != null){
                return false;
            }
        }else {
            if(superficieNombre.getIdSuperficie() != id){
                return false;
            }
        }

        return true;
    }

    @Override
    public void deleteById(Integer id) throws SuperficieNotFoundException {

        if (superficieRepository.findById(id).isPresent()){
            superficieRepository.deleteById(id);
        } else {
            throw new SuperficieNotFoundException("No existe ninguna superficie con ese id: "+id);
        }

    }

    @Override
    public void updateSuperficieActiva(Integer id, Boolean activo) {

        superficieRepository.updateSuperficieActiva(id,activo);

    }

    @Override
    public Page<Superficie> listByPage(Integer numeroPagina, String campoOrden, String dirOrden, String palabraClave) {

        Sort sort = Sort.by(campoOrden);

        sort = ("asc").equals(dirOrden) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(numeroPagina - 1, SUP_POR_PAG, sort);

        if (palabraClave != null) {

            return superficieRepository.findAll(palabraClave, pageable);
        }

        return superficieRepository.findAll(pageable);
    }
}
