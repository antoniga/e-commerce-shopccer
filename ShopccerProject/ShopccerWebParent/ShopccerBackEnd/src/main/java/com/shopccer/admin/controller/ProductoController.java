package com.shopccer.admin.controller;


import com.shopccer.admin.exception.ProductoNotFoundException;
import com.shopccer.admin.service.MarcaService;
import com.shopccer.admin.service.ProductoService;
import com.shopccer.admin.service.SuperficieService;
import com.shopccer.admin.service.impl.ProductoServiceImpl;
import com.shopccer.admin.utils.FileLoadUtil;
import com.shopccer.common.entity.Marca;
import com.shopccer.common.entity.Producto;
import com.shopccer.common.entity.Superficie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private SuperficieService superficieService;

    @GetMapping("/productos")
    public String listFirstPage(Model model){

        return listByPage(1, model, "idProducto", "asc", null, null, null);
    }

    @GetMapping("/productos/pagina/{numeroPagina}")
    public String listByPage(@PathVariable(name="numeroPagina") Integer numeroPagina, Model model,
                             @Param("campoOrden") String campoOrden, @Param("dirOrden") String dirOrden,
                             @Param("palabraClave") String palabraClave, @Param("marcaId") Integer marcaId,
                             @Param("superficieId") Integer superficieId) {

        System.out.println("------- Superficie seleccionada: " + superficieId);
        Page<Producto> pagina = productoService.listByPage(numeroPagina, campoOrden, dirOrden, palabraClave,marcaId,superficieId);
        List<Producto> listaProductos = pagina.getContent();


        List<Marca> listaMarcas = marcaService.listAll();
        List<Superficie> listaSuperficies = superficieService.listAll();

        long startCount = (numeroPagina -1) * ProductoServiceImpl.PROD_POR_PAG + 1;
        long endCount = startCount + ProductoServiceImpl.PROD_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        String dirOrdenContrario = ("asc").equals(dirOrden) ? "desc" : "asc";

        if(marcaId != null){
            model.addAttribute("marcaId", marcaId);
        }

        if(superficieId != null){
            model.addAttribute("superficieId", superficieId);
        }

        model.addAttribute("listaProductos",listaProductos);
        model.addAttribute("paginaActual",numeroPagina);
        model.addAttribute("paginasTotales",pagina.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("productosTotales",pagina.getTotalElements());
        model.addAttribute("campoOrden",campoOrden);
        model.addAttribute("dirOrden",dirOrden);
        model.addAttribute("dirOrdenContrario",dirOrdenContrario);
        model.addAttribute("palabraClave",palabraClave);
        model.addAttribute("listaMarcas",listaMarcas);
        model.addAttribute("listaSuperficies",listaSuperficies);

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
        model.addAttribute("cantidadFotosDetallesExistentes", 0);
        model.addAttribute("tituloPagina", "Crear nuevo producto");
        return "productos/producto_form";
    }

    @PostMapping("/productos/save")
    public String saveProducto(Producto producto, RedirectAttributes redirectAttributes,
                               @RequestParam("productoImgPrincipal") MultipartFile multipartFile,
                               @RequestParam("fotoDetalle") MultipartFile [] fotosDetalle,
                               @RequestParam(name = "fotosDetallesNombre", required = false) String [] fotosDetallesNombre
                               ) throws IOException {


        Integer stockTotal = producto.getTallaStock().values().stream().mapToInt(Integer::intValue).sum();

        if(stockTotal > 0){
            producto.setInStock(true);
        }else {
            producto.setInStock(false);
        }

        setNombreFotoPrincipal(multipartFile, producto);
        setExistingsFotosDetalle(fotosDetallesNombre, producto);
        setNewFotosDetalle(fotosDetalle, producto);

        Producto savedProducto = productoService.save(producto);

        saveUploadedImages(multipartFile, fotosDetalle, savedProducto);

        deleteFotosDetalleRemovedOnForm(producto);



        productoService.save(producto);


        redirectAttributes.addFlashAttribute("msg", "El producto ha sido añadido correctamente");

        return "redirect:/productos";
    }

    private void deleteFotosDetalleRemovedOnForm(Producto producto) {

        String dirFotosDetalle = "../fotos-productos/" + producto.getIdProducto() + "/detalles";
        Path dirPath = Paths.get(dirFotosDetalle);

        try {
            Files.list(dirPath).forEach(file ->{
                String filename = file.toFile().getName();

                if(!producto.contieneFotoDetalle(filename)){
                    try{
                        Files.delete(file);
                        LOGGER.info("Borrada foto detalle: " + filename);
                    }catch (IOException e){
                        LOGGER.error("No se puede eliminar la foto detalle : "+ filename);
                    }
                }
            } );
        }catch (IOException e){
            LOGGER.error("No se puede listar el directorio: " + dirPath);
        }

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

    private void setNewFotosDetalle(MultipartFile [] fotosDetalle, Producto producto){
        if(fotosDetalle.length > 0) {
            List<String> listaFotosDetalleNueva = new ArrayList<>();
            for (MultipartFile multipartFile : fotosDetalle) {
                if (!multipartFile.isEmpty()) {
                    String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    if(!producto.contieneFotoDetalle(nombreArchivo)){
                        listaFotosDetalleNueva.add(nombreArchivo);
                    }
                }
            }
            List<String> listaFotosDetalleExistentes = producto.getFotosDetalles();
            if(listaFotosDetalleExistentes != null){
                listaFotosDetalleExistentes.addAll(listaFotosDetalleNueva);
                producto.setFotosDetalles(listaFotosDetalleExistentes);
            }else{
                producto.setFotosDetalles(listaFotosDetalleNueva);
            }
        }
    }

    private void setExistingsFotosDetalle(String [] fotosDetallesNombre, Producto producto ){
        if(fotosDetallesNombre == null || fotosDetallesNombre.length == 0) return;

        List<String> fotosDetalle = new ArrayList<>();

        for (String fd: fotosDetallesNombre) {
           fotosDetalle.add(fd);
        }
        producto.setFotosDetalles(fotosDetalle);
    }

    private void setNombreFotoPrincipal(MultipartFile multipartFile, Producto producto){

        if(!multipartFile.isEmpty()) {
            String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            producto.setFotoPrincipal(nombreArchivo);
        }

    }

    @GetMapping("productos/edit/{idProducto}")
    public String editProducto(@PathVariable(name = "idProducto") Integer idProducto, Model model,
                               RedirectAttributes redirectAttributes){

        try {
            Producto producto = productoService.findById(idProducto);
            List<Marca> listaMarcas = marcaService.listAll();
            List<Superficie> listaSuperficies = superficieService.listAll();

            Integer cantidadFotosDetallesExistentes = producto.getFotosDetalles().size();

            model.addAttribute("producto",producto);
            model.addAttribute("listaMarcas", listaMarcas);
            model.addAttribute("listaSuperficies", listaSuperficies);
            model.addAttribute("cantidadFotosDetallesExistentes", cantidadFotosDetallesExistentes);
            model.addAttribute("tituloPagina", "Editar producto (Id: "+ idProducto+") ");

            return "productos/producto_form" ;
        } catch (ProductoNotFoundException e) {

            redirectAttributes.addFlashAttribute("msg",e.getMessage());
            return "redirect:/productos";
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