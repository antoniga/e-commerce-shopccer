package com.shopccer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopccer.admin.repository.RolRepository;
import com.shopccer.common.entity.Rol;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class RolRepositoryTest {

	@Autowired
	RolRepository rolRepository;

	@Test
	@Order(1)
	public void testCreateAdminRol() {
		Rol rolAdmin = new Rol("Admin");
		Rol savedRol = rolRepository.save(rolAdmin);
		assertThat(savedRol.getIdRol()).isPositive();
	}

	@Test
	@Order(2)
	public void testCreateClienteRol() {
		Rol rolClient = new Rol("Cliente");
		Rol savedRol = rolRepository.save(rolClient);
		assertThat(savedRol.getIdRol()).isPositive();
	}

}
