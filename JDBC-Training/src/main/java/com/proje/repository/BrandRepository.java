package com.proje.repository;

import java.util.List;

import com.proje.model.Brand;

public interface BrandRepository {

	Brand saveBrand(int id);
	
	List<Brand> findBrands();
}
