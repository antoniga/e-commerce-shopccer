package com.shopccer.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopccer.common.entity.Rol;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer>{

}
