package com.ecommerce.app.service;

import com.ecommerce.app.exception.APIException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        if(categoryRepository.findAll().isEmpty()) throw new APIException("No Category found");
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category categoryFound = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFound == null)
            categoryRepository.save(category);
        else throw new APIException("Category with the name "+ category.getCategoryName()+ " already exists");
    }

    @Override
    public String deleteCategory(long categoryId) {
        //optimised way
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return "category with categoryId: " + categoryId + " deleted successfully";
//        List<Category> categories = categoryRepository.findAll();
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
////        if(category==null) return "category with categoryId: "+ categoryId+ " not found";
//        categoryRepository.delete(category);
//        return "category with categoryId: " + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(long categoryId, Category category) {
        //optimised way
       Category savedCategory = categoryRepository.findById(categoryId)
                        .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
       savedCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(savedCategory);
        return savedCategory;

//        Category savedCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
//        savedCategory.setCategoryName(category.getCategoryName());
//        categoryRepository.save(savedCategory);
//        return savedCategory;


        /// //////////////////////////////////////////////////////////
//        List<Category> categories = categoryRepository.findAll();
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//        if (optionalCategory.isPresent()) {
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory = categoryRepository.save(existingCategory);
//            return savedCategory;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }


//    using list as a datasource
//    private List<Category> categories = new ArrayList<>();
//    @Override
//    public List<Category> getAllCategories() {
//        return categories;
//    }
//
//    @Override
//    public void createCategory(Category category) {
//        categories.add(category);
//    }
//
//    @Override
//    public String deleteCategory(long categoryId) {
//        Category category = categories.stream()
//                        .filter(c -> c.getCategoryId().equals(categoryId))
//                                .findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
////        if(category==null) return "category with categoryId: "+ categoryId+ " not found";
//        categories.remove(category);
//        return "category with categoryId: "+ categoryId+ " deleted successfully";
//    }
//
//    @Override
//    public Category updateCategory(long categoryId, Category category) {
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//        if(optionalCategory.isPresent()){
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            return existingCategory;
//        } else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
//        }
//    }
    }
