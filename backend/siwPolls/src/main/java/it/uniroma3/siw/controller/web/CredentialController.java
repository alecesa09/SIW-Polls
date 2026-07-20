package it.uniroma3.siw.controller.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class CredentialController {
	
	@Value("${app.frontend.url}")
	private String frontendUrl;
	
	@GetMapping("/login")
	public String showLoginForm(HttpServletRequest request, Principal principal, @RequestParam(required = false) String returnUrl) {
	    if (principal != null) {
	        return "redirect:" + frontendUrl;
	    }
	    if (returnUrl != null && !returnUrl.isEmpty()) {
	        request.getSession().setAttribute("url_pre_login", frontendUrl + returnUrl);
	    } 
	    else {
	        String referer = request.getHeader("Referer");
	        if (referer != null && !referer.contains("/login")) {
	            request.getSession().setAttribute("url_pre_login", referer);
	        }
	    }
	    
	    return "utente/login"; 
	}
	
	@GetMapping("/")
    public String react() {
        return "redirect:" + frontendUrl; 
    }
    
    @GetMapping("/error")
    public String error() {
    	return "/error";
    }
}
