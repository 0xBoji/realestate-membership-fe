package com.realestate.frontend.controller;

import com.realestate.frontend.model.Membership;
import com.realestate.frontend.service.MembershipService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/memberships")
public class MembershipController {
    
    @Autowired
    private MembershipService membershipService;
    
    @GetMapping
    public String listMemberships(Model model) {
        try {
            List<Membership> memberships = membershipService.getAllMemberships().block();
            model.addAttribute("memberships", memberships != null ? memberships : List.of());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("memberships", List.of());
        }
        
        return "memberships/list";
    }
    
    @GetMapping("/{id}")
    public String membershipDetail(@PathVariable Long id, Model model) {
        try {
            Membership membership = membershipService.getMembershipById(id).block();
            
            if (membership != null && membership.getId() != null) {
                model.addAttribute("membership", membership);
                return "memberships/detail";
            } else {
                return "redirect:/memberships";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/memberships";
        }
    }
    
    @PostMapping("/{id}/subscribe")
    public String subscribeMembership(@PathVariable Long id,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        
        if (token == null) {
            return "redirect:/login";
        }
        
        try {
            String result = membershipService.subscribeMembership(token, id).block();
            
            if (result != null && !result.contains("Error")) {
                redirectAttributes.addFlashAttribute("success", "Đăng ký gói thành viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Đăng ký gói thành viên không thành công!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi đăng ký gói thành viên!");
        }
        
        return "redirect:/memberships";
    }
    
    @GetMapping("/my-membership")
    public String myMembership(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        
        if (token == null) {
            return "redirect:/login";
        }
        
        try {
            Object currentMembership = membershipService.getMyMembership(token).block();
            model.addAttribute("currentMembership", currentMembership);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("currentMembership", null);
        }
        
        return "memberships/my-membership";
    }
    
    @GetMapping("/history")
    public String membershipHistory(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        
        if (token == null) {
            return "redirect:/login";
        }
        
        try {
            List<Object> history = membershipService.getMembershipHistory(token).block();
            model.addAttribute("history", history != null ? history : List.of());
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("history", List.of());
        }
        
        return "memberships/history";
    }
}