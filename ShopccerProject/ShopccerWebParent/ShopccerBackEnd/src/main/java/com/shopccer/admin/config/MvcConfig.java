package com.shopccer.admin.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String nombreDirUsario = "fotos-usuarios";
		Path dirFotosUsuario = Paths.get(nombreDirUsario);

		String pathFotosUsuario = dirFotosUsuario.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + nombreDirUsario + "/**").addResourceLocations("file:/" + pathFotosUsuario + "/");
		
		String nombreDirMarcas = "../fotos-marcas";
		Path dirFotosMarcas = Paths.get(nombreDirMarcas);

		String pathFotosMarcas = dirFotosMarcas.toFile().getAbsolutePath();

		registry.addResourceHandler("/fotos-marcas/**").addResourceLocations("file:/" + pathFotosMarcas + "/");

		String nombreDirSuperficies = "../fotos-superficies";
		Path dirFotosSuperficies = Paths.get(nombreDirSuperficies);

		String pathFotosSuperficies = dirFotosSuperficies.toFile().getAbsolutePath();

		registry.addResourceHandler("/fotos-superficies/**").addResourceLocations("file:/" + pathFotosSuperficies + "/");

	}
	
	

}
