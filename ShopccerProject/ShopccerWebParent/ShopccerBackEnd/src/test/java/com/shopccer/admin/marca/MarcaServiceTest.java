package com.shopccer.admin.marca;

import com.shopccer.admin.repository.MarcaRepository;
import com.shopccer.admin.service.MarcaService;
import com.shopccer.admin.service.MarcaServiceImpl;
import com.shopccer.common.entity.Marca;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MarcaServiceTest {

    @MockBean
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService = new MarcaServiceImpl();

    @Test
    @Order(1)
    public void testIsNombreUniqueExisteNuevaMarca(){

        Integer id= null;
        String nombre="Nike";

        Marca marca = new Marca(id,nombre,null,true);

        Mockito.when(marcaRepository.findByNombre(nombre)).thenReturn(marca);

        Assert.isTrue(!marcaService.isNombreUnique(id, nombre),"Ya existe");

    }

    @Test
    @Order(2)
    public void testIsNombreUniqueNoExisteNuevaMarca(){

        Integer id= null;
        String nombre="Nike";

        Marca marca = new Marca(id,nombre,null,true);

        Mockito.when(marcaRepository.findByNombre(nombre)).thenReturn(marca);

        Assert.isTrue(marcaService.isNombreUnique(id, "Adidas"),"Es único");

    }
}
