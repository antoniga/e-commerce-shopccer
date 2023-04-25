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
import java.util.List;
import java.util.Optional;

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
    public void testCreateProductAdidasKaiser(){

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

    @Test
    @Order(2)
    public void testCreateProductMizunoMorelia(){

        Marca mizuno = entityManager.find(Marca.class,5);
        Superficie naturalSeco = entityManager.find(Superficie.class,2);

        Producto producto= new Producto();
        producto.setNombre("Mizuno Morelia II Pro");
        producto.setDescripcion("Perfecciona tu técnica con una bota que ofrece la sensación de ir descalzo gracias" +
                " a su diseño con numerosas mejoras. Esta bota de fútbol de piel de canguro auténtica consigue que " +
                "tengas la sensación de ir descalzo combinada con una gran durabilidad, que se mantendrá en el tiempo" +
                " mientras tú alcanzas velocidades que no creías posibles.");
        producto.setMarca(mizuno);
        producto.setSuperficie(naturalSeco);
        producto.setPrecio(109.99);
        producto.setCratedTime(new Date());
        producto.setUpdatedTime(new Date());

        Producto savedProducto = productoRepository.save(producto);

        assertThat(producto.getIdProducto()).isPositive();
    }

    @Test
    @Order(3)
    public void testListAllProductos() {

        List<Producto> listProductos = (List<Producto>) productoRepository.findAll();

        listProductos.forEach(p -> System.out.println(p.getNombre() +
                "[ -- ]"+ p.getDescripcion()));

        assertThat(listProductos).hasSize(2);

    }

    @Test
    @Order(4)
    public void testGetProductoById(){
        Producto producto = productoRepository.findById(1).get();

        assertThat("Adidas Kaiser 5 Liga").isEqualTo(producto.getNombre());

    }

    @Test
    @Order(5)
    public void testUpdateProducto(){
        Producto producto = productoRepository.findById(1).get();

        producto.setPrecio(65.79);
        producto.setUpdatedTime(new Date());

        productoRepository.save(producto);

        System.out.println(productoRepository.findById(1).get().getPrecio());

        assertThat(65.79).isEqualTo(productoRepository.findById(1).get().getPrecio());


    }

    @Test
    @Order(6)
    public void testDeleteProducto(){

        productoRepository.deleteById(2);
        Optional<Producto> producto = productoRepository.findById(2);

        assertThat(!producto.isPresent());
    }
}
