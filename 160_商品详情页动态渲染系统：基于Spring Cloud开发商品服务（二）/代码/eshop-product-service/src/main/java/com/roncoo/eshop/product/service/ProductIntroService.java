package com.roncoo.eshop.product.service;

import com.roncoo.eshop.product.model.ProductIntro;

public interface ProductIntroService {
	
	public void add(ProductIntro productIntro);
	
	public void update(ProductIntro productIntro);
	
	public void delete(Long id);
	
	public ProductIntro findById(Long id);
	
}
