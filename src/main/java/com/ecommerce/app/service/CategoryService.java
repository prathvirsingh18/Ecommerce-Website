package com.ecommerce.app.service;

import com.ecommerce.app.model.Category;
import com.ecommerce.app.payload.CategoryDTO;
import com.ecommerce.app.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
     CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
     CategoryDTO createCategory(CategoryDTO categoryDTO);
     CategoryDTO deleteCategory(long categoryId);
     CategoryDTO updateCategory(CategoryDTO categoryDTo, long categoryId);
}
