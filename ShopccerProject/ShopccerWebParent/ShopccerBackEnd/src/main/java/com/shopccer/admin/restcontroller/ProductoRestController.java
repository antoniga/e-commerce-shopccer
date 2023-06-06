package com.shopccer.admin.restcontroller;

import com.shopccer.admin.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/productos/checknombre")
    public String checkNombreUnico(Integer idProducto, String nombre) {
        return productoService.isNombreUnique(idProducto, nombre) ? "ok" : "duplicado";
    }
}
