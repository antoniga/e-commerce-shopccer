package com.shopccer.admin.producto;

import com.shopccer.admin.repository.ProductoRepository;
import com.shopccer.common.entity.Marca;
import com.shopccer.common.entity.Producto;
import com.shopccer.common.entity.Superficie;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    public void testCreateProduct(){

        Marca adidas = entityManager.find(Marca.class,1);
        Superficie naturalSeco = entityManager.find(Superficie.class,2);

        Producto producto= new Producto();
        producto.setNombre("Adidas Kaiser 5 Liga");
        producto.setDescripcion("Ideales para los campos de suelo duro, " +
                "estas adidas Kaiser 5 Liga football boots presentan una parte " +
                "superior de piel granulada, forro de secado rápido y plantilla de " +
                "EVA que absorbe los impactos.");
        producto.setMarca(adidas);
        producto.setSuperficie(naturalSeco);
        producto.setPrecio(89.99);
        producto.setCratedTime(new Date());
        producto.setUpdatedTime(new Date());

        Producto savedProducto = productoRepository.save(producto);

        assertThat(producto.getIdProducto()).isPositive();
    }
}
