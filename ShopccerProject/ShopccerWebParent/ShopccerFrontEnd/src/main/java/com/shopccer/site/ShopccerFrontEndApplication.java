package com.shopccer.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopccer.common.entity"})
public class ShopccerFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopccerFrontEndApplication.class, args);
	}

}
