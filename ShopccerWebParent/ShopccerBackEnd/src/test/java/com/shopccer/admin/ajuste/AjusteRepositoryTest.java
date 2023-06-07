package com.shopccer.admin.ajuste;

import com.shopccer.admin.repository.AjusteRepository;
import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteCategoria;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AjusteRepositoryTest {
    
    @Autowired
    private AjusteRepository ajusteRepository;

    @Test
    @Order(1)
    public void testCreateAjusteGeneral() {

        Ajuste siteName = new Ajuste("SITE_NAME","Shopccer", AjusteCategoria.GENERAL);
        Ajuste savedAjuste = ajusteRepository.save(siteName);

        assertThat(savedAjuste).isNotNull();

    }
}
