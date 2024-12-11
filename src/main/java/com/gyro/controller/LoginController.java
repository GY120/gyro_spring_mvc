package com.gyro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.gyro.model.User;

@Controller
public class LoginController {
	

    @RequestMapping(value = "/logining", method = RequestMethod.POST)
    public String addContact(@Valid User user, BindingResult result, ModelMap model, 
                             @RequestParam(value = "lang", required = false) String lang, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            model.addAttribute("lang", lang);  
            return "login";  
        } else {
            model.addAttribute("user", user);  
            model.addAttribute("lang", lang); 

            // 登录逻辑
            if ("gyro".equals(user.getUsername()) && "1234".equals(user.getPassword())) {
                // 登录成功，设置 session 属性
                request.getSession().setAttribute("username", user.getUsername());
                model.addAttribute("username", user.getUsername());
                //throw new RuntimeException("用户未登录，无法访问！");
                return "redirect:/main"; 
            } else {
                model.addAttribute("errorMessage", "用户名或密码错误！");
                return "login";  

               
            }
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showContacts(ModelMap model, 
                               @RequestParam(value = "lang", required = false) String lang) {
        model.addAttribute("user", new User());  
        model.addAttribute("lang", lang);  
        return "login";  
    }
    
    
}
