package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository toyProductRepository;

    public ProductServiceImpl(ProductRepository toyProductRepository) {
        this.toyProductRepository = toyProductRepository;
    }

    @Override
    public Product register(Product product) {
       return toyProductRepository.save(product);
    }

    @Override
    public Optional<Product> getProduct(Long id) {
       return toyProductRepository.findById(id);
    }

    @Override
    public Iterable<Product> getProducts() {
        return toyProductRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> foundProduct = toyProductRepository.findById(id);

        foundProduct.get().setName(product.getName());
        foundProduct.get().setMaker(product.getMaker());
        foundProduct.get().setPrice(product.getPrice());
        foundProduct.get().setImg(product.getImg());

        return foundProduct.get();
    }

    @Override
    public void delete(Long id) {
        toyProductRepository.deleteById(id);
    }

}
