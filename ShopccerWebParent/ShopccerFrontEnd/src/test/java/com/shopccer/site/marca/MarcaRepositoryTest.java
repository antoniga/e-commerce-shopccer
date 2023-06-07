package com.shopccer.site.marca;

import com.shopccer.common.entity.Marca;
import com.shopccer.site.repository.MarcaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarcaRepositoryTest {

    @Autowired
    MarcaRepository marcaRepository;

    @Test
    @Order(1)
    public void testListEnabled() {

        List<Marca> allEnabled = marcaRepository.findAllEnabled();

        allEnabled.forEach(marca -> System.out.println("nombre ----> " + marca.getNombre()));

        assertThat(allEnabled.size()).isEqualTo(3);
    }

    @Test
    @Order(2)
    public void testMarcaIdEnabled() {

        Marca byIdEnabled = marcaRepository.findByIdEnabled(1);


        assertThat("Adidas").isEqualTo(byIdEnabled.getNombre());
    }
}
