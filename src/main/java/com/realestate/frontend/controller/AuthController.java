package com.realestate.frontend.controller;

import com.realestate.frontend.model.AuthResponse;
import com.realestate.frontend.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        return "auth/login";
    }
    
    @PostMapping("/auth/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Attempting login for username: " + username);
            AuthResponse response = authService.login(username, password).block();
            
            System.out.println("Login response: " + (response != null ? "received" : "null"));
            if (response != null) {
                System.out.println("Token: " + (response.getToken() != null ? "exists" : "null"));
                System.out.println("User: " + (response.getUser() != null ? response.getUser().getUsername() : "null"));
            }
            
            if (response != null && response.getToken() != null && !response.getToken().isEmpty()) {
                // Store token and user info in session
                session.setAttribute("token", response.getToken());
                session.setAttribute("user", response.getUser());
                session.setAttribute("authenticated", true);
                
                System.out.println("Login successful, redirecting to dashboard");
                return "redirect:/dashboard";
            } else {
                System.out.println("Login failed - invalid response");
                redirectAttributes.addAttribute("error", "true");
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.out.println("Login exception: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addAttribute("error", "true");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
    
    @PostMapping("/auth/register")
    public String register(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            AuthResponse response = authService.register(username, email, password, fullName).block();
            
            if (response != null && response.getToken() != null && !response.getToken().isEmpty()) {
                // Store token and user info in session
                session.setAttribute("token", response.getToken());
                session.setAttribute("user", response.getUser());
                session.setAttribute("authenticated", true);
                
                redirectAttributes.addFlashAttribute("success", "Đăng ký thành công!");
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("error", "Đăng ký không thành công. Vui lòng thử lại!");
                return "redirect:/register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            return "redirect:/register";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("authenticated") == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", session.getAttribute("user"));
        return "dashboard/index";
    }
}