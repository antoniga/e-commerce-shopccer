package com.shopccer.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopccer.common.entity", "com.shopccer.admin.user"})
public class ShopccerBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopccerBackEndApplication.class, args);
	}

}
