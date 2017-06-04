package edu.nju.ar.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nju.ar.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/register")
	public void loginAjax(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		PrintWriter out;
		out = response.getWriter();
		out.write(userService.register(name, password)+"");
        out.close();
		
	}

}