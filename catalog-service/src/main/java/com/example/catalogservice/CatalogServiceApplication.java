package com.example.catalogservice;

import com.example.catalogservice.domain.catalog.Catalog;
import com.example.catalogservice.domain.catalog.CatalogRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class CatalogServiceApplication {

    private final CatalogRepository catalogRepository;

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {

        Catalog catalog = catalogRepository.findByProductId(1L).orElse(null);

        if(catalog == null) {
            Catalog catalog1 = Catalog.builder()
                    .productId(1L)
                    .productName("Berlin")
                    .stock(100)
                    .unitPrice(1500)
                    .build();

            Catalog catalog2 = Catalog.builder()
                    .productId(2L)
                    .productName("Tokyo")
                    .stock(100)
                    .unitPrice(900)
                    .build();

            Catalog catalog3 = Catalog.builder()
                    .productId(3L)
                    .productName("Stockholm")
                    .stock(100)
                    .unitPrice(1200)
                    .build();

            catalogRepository.saveAll(List.of(catalog1, catalog2, catalog3));
        }
    }

}
