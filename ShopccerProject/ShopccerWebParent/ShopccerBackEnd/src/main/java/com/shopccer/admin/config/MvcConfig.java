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

		exposeDirectory("fotos-usuarios", registry);
		exposeDirectory("../fotos-marcas", registry);
		exposeDirectory("../fotos-superficies", registry);
		exposeDirectory("../fotos-productos", registry);

	}

	private void exposeDirectory(String pathpattern, ResourceHandlerRegistry registry){

		Path path = Paths.get(pathpattern);
		String absoluePath = path.toFile().getAbsolutePath();

		String logicalPath = pathpattern.replace("../","") + "/**";

		registry.addResourceHandler(logicalPath).addResourceLocations("file:/"+absoluePath+"/");
	}
	
	

}
