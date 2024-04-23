package com.igo.catalogDeProducts;

import com.igo.catalogDeProducts.models.Category;
import com.igo.catalogDeProducts.respositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CatalogDeProductsApplication {

	public static void main(String[] args) {

		SpringApplication.run(CatalogDeProductsApplication.class, args);
	}

//	@Bean
//	CommandLineRunner initDatabase(CategoryRepository categoryRepository){
//		return args -> {
//			categoryRepository.deleteAll();
//
//			Category c = new Category();
//			c.setName("Eletronicos");
//
//			categoryRepository.save(c);
//		};
//	}
}
