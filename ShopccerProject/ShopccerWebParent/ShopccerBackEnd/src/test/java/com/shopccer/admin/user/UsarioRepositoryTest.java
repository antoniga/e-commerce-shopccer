package com.shopccer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import com.shopccer.admin.repository.UsuarioRepository;
import com.shopccer.common.entity.Rol;
import com.shopccer.common.entity.Usuario;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class UsarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	@Test
	@Order(1)
	public void testCreateUser() {

		rolRepository.save(new Rol("Admin"));

		Usuario usuarioAgs = new Usuario("ags0389@gmail", "pwd2023", "Antonio", "García Sánchez", null, true,
				rolRepository.findById(1).get());

		Usuario savedUsuario = usuarioRepository.save(usuarioAgs);

		assertThat(savedUsuario.getIdUsuario()).isPositive();
	}

	@Test
	@Order(2)
	public void testListAllUsers() {

		List<Usuario> listUsuarios = (List<Usuario>) usuarioRepository.findAll();

		listUsuarios.forEach(usuario -> System.out.println(usuario.toString()));

		assertThat(listUsuarios).hasSize(1);
	}

	@Test
	@Order(3)
	public void testGetUserById() {

		Usuario usuarioId = usuarioRepository.findById(1).get();

		assertThat(usuarioId.getNombre()).isEqualTo("Antonio");
	}

}
