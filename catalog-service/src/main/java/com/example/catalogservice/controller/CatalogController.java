package com.example.catalogservice.controller;

import com.example.catalogservice.controller.dto.catalog.CatalogResponseDto;
import com.example.catalogservice.domain.catalog.Catalog;
import com.example.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogResponseDto>> getCatalogs() {
        List<Catalog> catalogs = catalogService.getAllCatalogs();

        List<CatalogResponseDto> result = new ArrayList<>();
        catalogs.forEach(v -> {
            result.add(new ModelMapper().map(v, CatalogResponseDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
