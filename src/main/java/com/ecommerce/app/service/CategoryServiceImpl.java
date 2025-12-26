package com.ecommerce.app.service;

import com.ecommerce.app.exception.APIException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.payload.CategoryDTO;
import com.ecommerce.app.payload.CategoryResponse;
import com.ecommerce.app.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Without Pagination
//    @Override
//    public CategoryResponse getAllCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        if(categories.isEmpty()) throw new APIException("No Category found");
//        List<CategoryDTO> categoryDTOS = categories.stream().
//                map(category -> modelMapper.map(category, CategoryDTO.class))
//                .toList();
//        CategoryResponse categoryResponse = new CategoryResponse();
//        categoryResponse.setContent(categoryDTOS);
//        return categoryResponse;
//    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //sort logic
        //sortBy is categoryId and it can be any field , which we will pass from postman
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //pagination logic
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Category> categoriePage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoriePage.getContent();
        if(categories.isEmpty()) throw new APIException("No Category found");
        List<CategoryDTO> categoryDTOS = categories.stream().
                map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoriePage.getNumber());
        categoryResponse.setPageSize(categoriePage.getSize());
        categoryResponse.setTotalElements(categoriePage.getTotalElements());
        categoryResponse.setLastPage(categoriePage.isLast());
        categoryResponse.setTotalPages(categoriePage.getTotalPages());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFound = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFound != null)
            throw new APIException("Category with the name "+ category.getCategoryName()+ " already exists");
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(long categoryId) {
        //optimised way
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
//        List<Category> categories = categoryRepository.findAll();
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
////        if(category==null) return "category with categoryId: "+ categoryId+ " not found";
//        categoryRepository.delete(category);
//        return "category with categoryId: " + categoryId + " deleted successfully";
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, long categoryId) {
        //optimised way
       Category savedCategory = categoryRepository.findById(categoryId)
                        .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        savedCategory.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(savedCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);

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
