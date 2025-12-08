package com.ecommerce.app.controller;

import com.ecommerce.app.model.Category;
import com.ecommerce.app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Method;
import java.util.List;

@RestController
//@RequestMapping("/api)// so for below we can remove api from starting
public class CategoryController {

//    private Long id = 1L;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
//or we can do
    @RequestMapping(value="/api/public/categories", method = RequestMethod.GET)
//    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    //@Valid is used to validate request and it throws MethodArgumentNotValidException
    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
//        category.setCategoryId(id++);
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId){
            String status = categoryService.deleteCategory(categoryId);
            //below are the three ways in which we can use response entity
            //return new ResponseEntity<>(status, HttpStatus.OK);
//            return ResponseEntity.ok(status);
            return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable long categoryId){
            com.ecommerce.app.model.Category savedCategory = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.status(HttpStatus.OK).body("Category with category id : "+ categoryId+ " is updated");
    }
}
