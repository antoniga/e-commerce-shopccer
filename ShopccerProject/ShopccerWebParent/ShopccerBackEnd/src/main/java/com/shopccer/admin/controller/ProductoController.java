package com.shopccer.admin.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<Integer,Integer> tallaStock = new HashMap<>();
        tallaStock.put(36,0);
        tallaStock.put(37,0);
        tallaStock.put(38,0);
        tallaStock.put(39,0);
        tallaStock.put(40,0);
        tallaStock.put(41,0);
        tallaStock.put(42,0);
        tallaStock.put(43,0);
        tallaStock.put(44,0);
        producto.setTallaStock(tallaStock);

        model.addAttribute("producto", producto);
        model.addAttribute("listaMarcas", listaMarcas);
        model.addAttribute("listaSuperficies", listaSuperficies);
        model.addAttribute("tituloPagina", "Crear nuevo producto");
        return "productos/producto_form";
    }

    @PostMapping("/productos/save")
    public String saveProducto(Producto producto){


        System.out.println(producto);

        return "redirect:/productos";
    }
}