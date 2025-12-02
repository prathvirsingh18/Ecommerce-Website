package com.ecommerce.app.service;

import com.ecommerce.app.model.Category;

import java.util.List;

public interface CategoryService {
     List<Category> getAllCategories();
     void createCategory(Category category);
}
