package com.shopccer.admin.controller;

import com.shopccer.admin.service.MarcaService;
import com.shopccer.admin.service.ProductoService;
import com.shopccer.admin.service.SuperficieService;
import com.shopccer.common.entity.Marca;
import com.shopccer.common.entity.Producto;
import com.shopccer.common.entity.Superficie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private SuperficieService superficieService;

    @GetMapping("/productos")
    public String listFirstPage(Model model){

        List<Producto> listaProductos = productoService.listAll();

        model.addAttribute("listaProductos", listaProductos);

        return "productos/productos";
    }

    @GetMapping("/productos/nuevo")
    public String addMarca(Model model) {
        List<Marca> listaMarcas = marcaService.listAll();
        List<Superficie> listaSuperficies = superficieService.listAll();

        Producto producto = new Producto();
        producto.setActivo(true);
        producto.setInStock(true);

        model.addAttribute("producto", producto);
        model.addAttribute("listaMarcas", listaMarcas);
        model.addAttribute("listaSuperficies", listaSuperficies);
        model.addAttribute("tituloPagina", "Crear nuevo producto");
        return "productos/producto_form";
    }
}