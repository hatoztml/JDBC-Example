package com.proje.service.impl;

import java.util.List;

import com.proje.model.Brand;
import com.proje.repository.BrandRepository;
import com.proje.repository.impl.BrandRepositoryImpl;
import com.proje.service.BrandService;

public class BrandServiceImpl implements BrandService{

	private BrandRepository brandRepository = new BrandRepositoryImpl();

	public Brand saveBrand(int id) {
		
		return brandRepository.saveBrand(id);
	}

	public List<Brand> findBrands() {
		
		return brandRepository.findBrands();
	}
}
