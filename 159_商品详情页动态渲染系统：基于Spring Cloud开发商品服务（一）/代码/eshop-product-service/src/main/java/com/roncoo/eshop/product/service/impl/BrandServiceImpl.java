package com.roncoo.eshop.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.product.mapper.BrandMapper;
import com.roncoo.eshop.product.model.Brand;
import com.roncoo.eshop.product.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;
	
	public void add(Brand brand) {
		brandMapper.add(brand); 
	}

	public void update(Brand brand) {
		brandMapper.update(brand); 
	}

	public void delete(Long id) {
		brandMapper.delete(id); 
	}

	public Brand findById(Long id) {
		return brandMapper.findById(id);
	}

}
