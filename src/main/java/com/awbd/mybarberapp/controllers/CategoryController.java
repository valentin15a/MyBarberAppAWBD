package com.awbd.mybarberapp.controllers;

import com.awbd.mybarberapp.dtos.CategoryDTO;
import com.awbd.mybarberapp.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("")
    public String categoryList(Model model) {
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "categoryList";
    }

}