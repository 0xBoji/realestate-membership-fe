package com.realestate.frontend.controller;

import com.realestate.frontend.model.PageResponse;
import com.realestate.frontend.model.Property;
import com.realestate.frontend.service.CategoryService;
import com.realestate.frontend.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/properties")
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public String listProperties(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "12") int size,
                                @RequestParam(defaultValue = "createdAt") String sortBy,
                                @RequestParam(defaultValue = "desc") String sortDir,
                                Model model) {
        try {
            PageResponse<Property> properties = propertyService.getAllProperties(page, size, sortBy, sortDir).block();
            
            model.addAttribute("properties", properties != null ? properties : new PageResponse<>());
            model.addAttribute("categories", categoryService.getAllCategories().block());
            model.addAttribute("currentPage", page);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortDir", sortDir);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("properties", new PageResponse<>());
            model.addAttribute("categories", java.util.List.of());
        }
        
        return "properties/list";
    }
    
    @GetMapping("/search")
    public String searchProperties(@RequestParam(required = false) String city,
                                  @RequestParam(required = false) String district,
                                  @RequestParam(required = false) String propertyType,
                                  @RequestParam(required = false) String listingType,
                                  @RequestParam(required = false) String minPrice,
                                  @RequestParam(required = false) String maxPrice,
                                  @RequestParam(required = false) Long categoryId,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  Model model) {
        try {
            PageResponse<Property> properties = propertyService.searchProperties(
                city, district, propertyType, listingType, minPrice, maxPrice, categoryId, page, size
            ).block();
            
            model.addAttribute("properties", properties != null ? properties : new PageResponse<>());
            model.addAttribute("categories", categoryService.getAllCategories().block());
            model.addAttribute("currentPage", page);
            
            // Add search parameters to model for form persistence
            model.addAttribute("city", city);
            model.addAttribute("district", district);
            model.addAttribute("propertyType", propertyType);
            model.addAttribute("listingType", listingType);
            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);
            model.addAttribute("categoryId", categoryId);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("properties", new PageResponse<>());
            model.addAttribute("categories", java.util.List.of());
        }
        
        return "properties/search";
    }
    
    @GetMapping("/{id}")
    public String propertyDetail(@PathVariable Long id, Model model) {
        try {
            Property property = propertyService.getPropertyById(id).block();
            
            if (property != null && property.getId() != null) {
                model.addAttribute("property", property);
                return "properties/detail";
            } else {
                return "redirect:/properties";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/properties";
        }
    }
}