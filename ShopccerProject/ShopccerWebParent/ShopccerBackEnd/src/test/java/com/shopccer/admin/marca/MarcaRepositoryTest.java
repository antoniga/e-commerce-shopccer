package com.shopccer.admin.marca;

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

import com.shopccer.admin.repository.MarcaRepository;
import com.shopccer.common.entity.Marca;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class MarcaRepositoryTest {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@Test
	@Order(1)
	public void testCreateMarca() {
		Marca marca = new Marca("Nike", "nike.png", true);
		
		marcaRepository.save(marca);
		
		assertThat(marca.getIdMarca()).isPositive();
		
	}
	
	@Test
	@Order(2)
	public void testListAllMarcas() {
		
		List<Marca> listMarcas = (List<Marca>) marcaRepository.findAll();
		
		listMarcas.forEach(m -> System.out.println(m.getNombre()));
		
		assertThat(listMarcas).hasSize(4);
		
	}
	
	@Test
	@Order(3)
	public void testGetMarcaById(){
		Marca marca = marcaRepository.findById(1).get();
		
		assertThat(marca.getNombre()).isEqualTo("Adidas");
	}
	
	@Test
	@Order(4)
	public void testGetByNombre() {
		
		Marca marca = marcaRepository.findByNombre("Nike");
		System.out.println("------> " + marca.getNombre() + " <------");
		assertThat(("Nike").equals(marca.getNombre()));
	}

}
