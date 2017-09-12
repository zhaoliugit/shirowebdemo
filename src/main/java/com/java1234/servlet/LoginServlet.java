package com.java1234.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("login doGet");
		req.getRequestDispatcher("login.jsp").forward(req, resp); 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("login doPost");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		try {
			subject.login(token);
			Session session = subject.getSession();
			System.out.println("sessionid:"+session.getId());
			System.out.println("sessionHost:"+session.getHost());
			System.out.println("sessionTimeout:"+session.getTimeout());
			session.setAttribute("info", "i love it");
			resp.sendRedirect("success.jsp");
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("errorinfo", "用户名或者密码错误");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
	
	

}
