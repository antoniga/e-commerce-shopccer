package com.shopccer.site.controller;

import com.shopccer.common.entity.Superficie;
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



        return "productos_por_marca";
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



        return "productos_por_superficie";
    }
}
