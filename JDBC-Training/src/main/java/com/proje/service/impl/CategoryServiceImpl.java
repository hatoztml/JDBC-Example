package com.proje.service.impl;

import java.util.List;

import com.proje.model.Category;
import com.proje.repository.CAtegoryRepository;
import com.proje.repository.impl.CategoryRepositoryImpl;
import com.proje.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	private CAtegoryRepository categoryRepository = new CategoryRepositoryImpl();

	public Category findCategoryById(int id) {
		return categoryRepository.findCategoryById(id);
	}

	public List<Category> findCategories() {
		return categoryRepository.findCategories();
	}
	
}
