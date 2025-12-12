package com.ecommerce.app.service;

import com.ecommerce.app.model.Category;
import com.ecommerce.app.payload.CategoryDTO;
import com.ecommerce.app.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
     CategoryResponse getAllCategories();
     CategoryDTO createCategory(CategoryDTO categoryDTO);
     String deleteCategory(long categoryId);
     Category updateCategory(long categoryId, Category category);
}
