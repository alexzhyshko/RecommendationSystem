package io.github.zhyshko;

import io.github.zhyshko.facade.IndexerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"io.github.zhyshko.mapper"})
public class RecommendationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendationSystemApplication.class, args);
	}

}
