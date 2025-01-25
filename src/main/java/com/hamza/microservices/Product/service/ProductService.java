package com.hamza.microservices.Product.service;

import com.hamza.microservices.Product.dto.ProductRequest;
import com.hamza.microservices.Product.dto.ProductResponse;
import com.hamza.microservices.Product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.hamza.microservices.Product.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // c'est une annotation Lombock qui permet de faire
//l'injection de dependance via un constructeur
//@Slf4j
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .Id(productRequest.Id())
                .Name(productRequest.Name())
                .Description(productRequest.Description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return new ProductResponse(product.getId(), product.getName()
                , product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName()
                , product.getDescription(), product.getPrice()))
                .toList();
    }
}
