package com.shopccer.site.itemcarro;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Producto;
import com.shopccer.site.repository.ItemCarroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ItemCarroTest {

    @Autowired
    private ItemCarroRepository itemCarroRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testSaveItem(){

        Integer idCliente = 1;
        Integer idProducto = 1;

        Cliente cliente = testEntityManager.find(Cliente.class, idCliente);
        Producto producto = testEntityManager.find(Producto.class, idProducto);

        ItemCarro newItem = new ItemCarro();
        newItem.setCliente(cliente);
        newItem.setProducto(producto);
        newItem.setCantidad(1);

        ItemCarro savedItem = itemCarroRepository.save(newItem);

        assertThat(savedItem.getIdItemCarro()).isGreaterThan(0);

    }
}
