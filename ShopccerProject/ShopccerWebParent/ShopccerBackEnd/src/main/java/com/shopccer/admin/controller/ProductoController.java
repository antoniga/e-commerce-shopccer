package com.shopccer.admin.controller;


import com.shopccer.admin.exception.ProductoNotFoundException;
import com.shopccer.admin.service.MarcaService;
import com.shopccer.admin.service.ProductoService;
import com.shopccer.admin.service.SuperficieService;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.common.entity.Marca;
import com.shopccer.common.entity.Producto;
import com.shopccer.common.entity.Superficie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
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
    public String addProducto(Model model) {
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
    public String saveProducto(Producto producto, @RequestParam("productoImgPrincipal") MultipartFile multipartFile,
                               @RequestParam("fotoDetalle") MultipartFile [] fotosDetalle,
                               RedirectAttributes redirectAttributes) throws IOException {

        Integer stockTotal = producto.getTallaStock().values().stream().mapToInt(Integer::intValue).sum();

        if(stockTotal > 0){
            producto.setInStock(true);
        }else {
            producto.setInStock(false);
        }

        setNombreFotoPrincipal(multipartFile, producto);
        setNombreFotosDetalle(fotosDetalle, producto);

        Producto savedProducto = productoService.save(producto);

        saveUploadedImages(multipartFile, fotosDetalle, savedProducto);



        productoService.save(producto);


        redirectAttributes.addFlashAttribute("msg", "El producto ha sido añadido correctamente");

        return "redirect:/productos";
    }

    private void saveUploadedImages(MultipartFile multipartFile, MultipartFile [] fotosDetalle, Producto savedProducto) throws IOException {

        if(!multipartFile.isEmpty()) {
            String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String dirSubida = "../fotos-productos/" + savedProducto.getIdProducto();
            FileLoadUtil.cleanDir(dirSubida);
            FileLoadUtil.saveFile(dirSubida, nombreArchivo, multipartFile);
        }

        if(fotosDetalle.length > 0) {
            String dirSubidaDetalle = "../fotos-productos/" + savedProducto.getIdProducto() + "/detalles";

            for (MultipartFile multipartFileDetalle : fotosDetalle) {
                if (multipartFileDetalle.isEmpty()) continue;
                String nombreArchivoDetalle = StringUtils.cleanPath(multipartFileDetalle.getOriginalFilename());
                FileLoadUtil.saveFile(dirSubidaDetalle, nombreArchivoDetalle, multipartFileDetalle);
            }
        }
    }

    private void setNombreFotosDetalle(MultipartFile [] fotosDetalle, Producto producto){
        if(fotosDetalle.length > 0) {
            List<String> listaFotosDetalle = new ArrayList<>();
            for (MultipartFile multipartFile : fotosDetalle) {
                if (!multipartFile.isEmpty()) {
                    String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    listaFotosDetalle.add(nombreArchivo);
                }
            }
            producto.setFotosDetalles(listaFotosDetalle);
        }
    }

    private void setNombreFotoPrincipal(MultipartFile multipartFile, Producto producto){

        if(!multipartFile.isEmpty()) {
            String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            producto.setFotoPrincipal(nombreArchivo);
        }

    }

    @GetMapping("/productos/delete/{idProducto}")
    public String deleteProducto(@PathVariable(name = "idProducto") Integer idProducto, Model model,
                              RedirectAttributes redirectAttributes) {

        try {
            productoService.deleteById(idProducto);
            String productoDirFotosDetalle = "../fotos-productos/" + idProducto + "/detalles";
            String productoDirFotos = "../fotos-productos/" + idProducto;

            FileLoadUtil.cleanDir(productoDirFotosDetalle);
            FileLoadUtil.cleanDir(productoDirFotos);

            redirectAttributes.addFlashAttribute("msg", "El producto con id: " + idProducto + " ha sido eliminada.");
        } catch (ProductoNotFoundException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:/productos";
    }

    @GetMapping("/productos/{idProducto}/activo/{bool}")
    public String updateProductoActivo(@PathVariable(name = "idProducto") Integer idProducto,
                                    @PathVariable(name = "bool") Boolean activo, RedirectAttributes redirectAttributes) {

        productoService.updateProductoActivo(idProducto, activo);
        String msgActivo = activo ? "activado" : "desactivado";
        String msg = "El producto con id: " + idProducto + " ha sido " + msgActivo;

        redirectAttributes.addFlashAttribute("msg", msg);

        return "redirect:/productos";
    }
}