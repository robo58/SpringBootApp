package com.example.webapp.service;

import java.util.List;
import java.util.Optional;

import com.example.webapp.model.Category;
import com.example.webapp.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategorybyId(long id) {
        Optional<Category> optional = this.categoryRepository.findById(id);
        Category category = null;
        if(optional.isPresent()){
            category = optional.get();
        }else{
            throw new RuntimeException("Category not found with id :: "+id);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(long id) {
        this.categoryRepository.deleteById(id);
    }

}
