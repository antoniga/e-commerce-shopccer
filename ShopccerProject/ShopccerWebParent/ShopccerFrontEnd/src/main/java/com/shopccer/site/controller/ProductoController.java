package com.shopccer.site.controller;

import com.shopccer.common.entity.Superficie;
import com.shopccer.site.exception.ProductoNotFoundException;
import com.shopccer.site.service.SuperficieService;
import com.shopccer.site.service.impl.ProductoServiceImpl;
import com.shopccer.common.entity.Marca;
import com.shopccer.common.entity.Producto;
import com.shopccer.site.service.MarcaService;
import com.shopccer.site.service.ProductoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private SuperficieService superficieService;
    
    @GetMapping("/marcas/{idMarca}")
    public String viewMarcasFirstPage(@PathVariable("idMarca") Integer idMarca, Model model){
    	
    	return viewMarcasByPage(idMarca, 1, model);
    }

    @GetMapping("/marcas/{idMarca}/pagina/{numeroPagina}")
    public String viewMarcasByPage(@PathVariable("idMarca") Integer idMarca, 
    		@PathVariable("numeroPagina") Integer numeroPagina, Model model){

        Marca marca = marcaService.findByIdEnable(idMarca);

        if(marca == null) return "error/404";
        
        Page<Producto> pagina = productoService.listByMarca(idMarca, numeroPagina);
        List<Producto> listaProductos = pagina.getContent();

        long startCount = (numeroPagina -1) * ProductoServiceImpl.PRODUCTOS_POR_PAG + 1;
        long endCount = startCount + ProductoServiceImpl.PRODUCTOS_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        model.addAttribute("listaProductos",listaProductos);
        model.addAttribute("paginaActual",numeroPagina);
        model.addAttribute("paginasTotales",pagina.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("productosTotales",pagina.getTotalElements());
        model.addAttribute("tituloPagina",marca.getNombre());
        model.addAttribute("idMarca",idMarca);



        return "producto/productos_por_marca";
    }

    @GetMapping("/superficies/{idSuperficie}")
    public String viewSuperficiesFirstPage(@PathVariable("idSuperficie") Integer idSuperficie, Model model){

        return viewSuperficiesByPage(idSuperficie, 1, model);
    }

    @GetMapping("/superficies/{idSuperficie}/pagina/{numeroPagina}")
    public String viewSuperficiesByPage(@PathVariable("idSuperficie") Integer idSuperficie,
                                   @PathVariable("numeroPagina") Integer numeroPagina, Model model){

        Superficie superficie = superficieService.findByIdEnable(idSuperficie);

        if(superficie == null) return "error/404";

        Page<Producto> pagina = productoService.listBySuperficie(idSuperficie, numeroPagina);
        List<Producto> listaProductos = pagina.getContent();

        long startCount = (numeroPagina -1) * ProductoServiceImpl.PRODUCTOS_POR_PAG + 1;
        long endCount = startCount + ProductoServiceImpl.PRODUCTOS_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        model.addAttribute("listaProductos",listaProductos);
        model.addAttribute("paginaActual",numeroPagina);
        model.addAttribute("paginasTotales",pagina.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("productosTotales",pagina.getTotalElements());
        model.addAttribute("tituloPagina",superficie.getNombre());
        model.addAttribute("idSuperficie",idSuperficie);



        return "producto/productos_por_superficie";
    }

    @GetMapping("/{idProducto}")
    public String viewDetallesProducto(@PathVariable("idProducto") Integer idProducto, Model model){

        try {
            Producto producto = productoService.findById(idProducto);
            model.addAttribute("producto", producto);
            model.addAttribute("tituloPagina", producto.getNombre());

            return "producto/producto_detalle";
        } catch (ProductoNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String searchFirstPage(String palabraClave,Model model){

        return searchByPage(palabraClave,1,model);
    }

    @GetMapping("/search/pagina/{numeroPagina}")
    public String searchByPage(String palabraClave, @PathVariable("numeroPagina") Integer numeroPagina,
                               Model model){

        Page<Producto> pagina = productoService.searchBypalabraClave(palabraClave, numeroPagina);
        List<Producto> listaProductos = pagina.getContent();

        long startCount = (numeroPagina -1) * ProductoServiceImpl.PRODUCTOS_POR_PAG + 1;
        long endCount = startCount + ProductoServiceImpl.PRODUCTOS_POR_PAG - 1;

        if (endCount > pagina.getTotalElements()) {
            endCount = pagina.getTotalElements();
        }

        model.addAttribute("paginaActual",numeroPagina);
        model.addAttribute("paginasTotales",pagina.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("productosTotales",pagina.getTotalElements());
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("listaProductos", listaProductos);
        model.addAttribute("tituloPagina", palabraClave + " - Resultado búsqueda");
        return "producto/search_result";
    }
}
