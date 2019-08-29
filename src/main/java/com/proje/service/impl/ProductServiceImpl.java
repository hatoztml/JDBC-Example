package com.proje.service.impl;

import java.util.List;

import com.proje.model.Product;
import com.proje.repository.ProductRepository;
import com.proje.repository.impl.ProductRepositoryImpl;
import com.proje.service.ProductService;

public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository = new ProductRepositoryImpl();

	public Product saveProduct(Product product) {
		return productRepository.saveProduct(product);
	}

	public boolean SaveBatchProduct(List<Product> products) {
		return productRepository.SaveBatchProduct(products);
	}

	public Product updateProduct(Product product) {
		return productRepository.updateProduct(product);
	}

	public boolean removeProduct(int id) {
		return productRepository.removeProduct(id);
	}

	public Product findProductById(int id) {
		return productRepository.findProductById(id);
	}

	public List<Product> findProducts() {
		return productRepository.findProducts();
	}
	
}
