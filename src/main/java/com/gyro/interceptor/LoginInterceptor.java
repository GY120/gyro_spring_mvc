package com.gyro.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

    // 在请求处理之前调用（controller方法调用之前）
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    // 只拦截 /main 请求
	    if (request.getRequestURI().equals("/MVC/main")) {
	        // 检查是否已经登录
	        String username = (String) request.getSession().getAttribute("username");
	        if (username == null) {
	            // 如果没有登录，重定向到登录页面
	            response.sendRedirect("/MVC/login");
	            return false;  // 停止请求处理
	            
	        }
	    }
	    // 其他请求放行
	    return true;
	}

    // 在请求处理之后调用，但是在视图渲染之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyCustomInterceptor - postHandle");
    }

    // 在整个请求处理完毕之后调用（视图渲染之后）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyCustomInterceptor - afterCompletion");
    }
}
