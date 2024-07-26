package com.example.catalogservice.service;

import com.example.catalogservice.domain.catalog.Catalog;
import com.example.catalogservice.domain.catalog.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
