package com.shopccer.admin.superficie;

import com.shopccer.admin.repository.SuperficieRepository;
import com.shopccer.common.entity.Producto;
import com.shopccer.common.entity.Superficie;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SuperficieRepositoryTest {

    @Autowired
    private SuperficieRepository superficieRepository;

    @Test
    @Order(1)
    public void testCreateSuperficie(){

        Superficie superficie = new Superficie("Cesped artificial","artificial.png",true);

        superficieRepository.save(superficie);

        assertThat(superficie.getIdSuperficie()).isPositive();
    }

    @Test
    @Order(2)
    public void testListAllSuperficies(){

        Iterable<Superficie> listSuperficies = superficieRepository.findAll();

        listSuperficies.forEach(superficie -> System.out.println(superficie.getNombre()));

        assertThat(listSuperficies).hasSize(1);

    }

    @Test
    @Order(3)
    public void testGetSuperficieById(){

        Optional<Superficie> byId = superficieRepository.findById(1);

        assertThat(byId.get().getNombre()).isEqualTo("Cesped artificial");
    }

    @Test
    @Order(4)
    public void testGetByNombre(){
        Superficie superficie = superficieRepository.findByNombre("Cesped artificial");
        System.out.println("--->"+superficie.getNombre()+"<---");
        assertThat(("Cesped articial").equals(superficie.getNombre()));
    }

    @Test
    @Order(5)
    public void testDesactivaSuperficie(){
        superficieRepository.updateSuperficieActiva(1,false);
    }

    @Test
    @Order(6)
    public void testActivaSuperficie(){
        superficieRepository.updateSuperficieActiva(1,true);
    }

    @Test
    @Order(7)
    public void testListProductosSuperficies(){

        List<Producto> productos = superficieRepository.findByNombre("Botas de cesped artificial").getProductos();

        productos.forEach(producto -> System.out.println(producto.getNombre()));

        assertThat(productos.size()).isGreaterThan(0);
    }

}
