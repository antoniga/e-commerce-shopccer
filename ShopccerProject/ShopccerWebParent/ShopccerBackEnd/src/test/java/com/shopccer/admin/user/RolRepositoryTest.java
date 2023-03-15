package com.shopccer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopccer.common.entity.Rol;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RolRepositoryTest {

	@Autowired
	RolRepository rolRepository;

	@Test
	public void testCreateFirstRol() {
		Rol rolAdmin = new Rol("Admin");
		Rol savedRol = rolRepository.save(rolAdmin);
		assertThat(savedRol.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRol() {
		Rol rolClient = new Rol("Cliente");
		Rol savedRol = rolRepository.save(rolClient);
		assertThat(savedRol.getId()).isGreaterThan(0);
	}

}
