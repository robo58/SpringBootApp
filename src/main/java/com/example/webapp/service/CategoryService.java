package com.example.webapp.service;

import java.util.List;

import com.example.webapp.model.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    void saveCategory(Category category);

    Category getCategorybyId(long id);

    void deleteCategoryById(long id);

    
}
