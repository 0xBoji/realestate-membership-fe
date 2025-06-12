package com.realestate.frontend.controller;

import com.realestate.frontend.model.Property;
import com.realestate.frontend.service.CategoryService;
import com.realestate.frontend.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        try {
            // Get featured properties
            List<Property> featuredProperties = propertyService.getFeaturedProperties(6).block();
            model.addAttribute("featuredProperties", featuredProperties != null ? featuredProperties : List.of());
            
            // Get categories
            model.addAttribute("categories", categoryService.getAllCategories().block());
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("featuredProperties", List.of());
            model.addAttribute("categories", List.of());
        }
        
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}