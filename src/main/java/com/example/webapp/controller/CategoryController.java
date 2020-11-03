package com.example.webapp.controller;


import com.example.webapp.model.Category;
import com.example.webapp.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categoryIndex(Model model){
        model.addAttribute("categories", this.categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/newCategory")
    public String showNewCategoryForm(Model model)
    {   
        //create model to bind data
        Category category = new Category();
        model.addAttribute("category", category);
        return "new_category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category category)
    {
        //save category to database
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable(value="id") long id,Model model)
    {

        //get Category from service
        Category category = this.categoryService.getCategorybyId(id);
        //set user as model for population of the update form
        model.addAttribute("category", category);
        return "new_category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value="id") long id)
    {

        // call delete Category by id
        this.categoryService.deleteCategoryById(id);
        
        return "redirect:/categories";
    }

}
