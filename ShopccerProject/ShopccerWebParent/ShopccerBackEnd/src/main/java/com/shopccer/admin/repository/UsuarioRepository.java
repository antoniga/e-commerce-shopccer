package com.shopccer.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopccer.common.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

}
