package com.roncoo.eshop.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.product.mapper.ProductMapper;
import com.roncoo.eshop.product.model.Product;
import com.roncoo.eshop.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	public void add(Product product) {
		productMapper.add(product); 
	}

	public void update(Product product) {
		productMapper.update(product); 
	}

	public void delete(Long id) {
		productMapper.delete(id); 
	}

	public Product findById(Long id) {
		return productMapper.findById(id);
	}

}
