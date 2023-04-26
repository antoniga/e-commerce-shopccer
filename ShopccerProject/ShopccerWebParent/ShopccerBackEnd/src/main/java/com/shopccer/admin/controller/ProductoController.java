package com.shopccer.admin.controller;

import com.shopccer.admin.service.ProductoService;
import com.shopccer.common.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public String listFirstPage(Model model){

        List<Producto> listaProductos = productoService.listAll();

        model.addAttribute("listaProductos", listaProductos);

        return "productos/productos";
    }
}
