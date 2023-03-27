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
		String nombreDir = "fotos-usuarios";
		Path dirFotosUsuario = Paths.get(nombreDir);

		String pathFotosUsuario = dirFotosUsuario.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + nombreDir + "/**").addResourceLocations("file:/" + pathFotosUsuario + "/");
	}
	
	

}
