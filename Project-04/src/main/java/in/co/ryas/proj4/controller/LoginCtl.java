package in.co.ryas.proj4.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.ryas.proj4.util.DataValidator;
import in.co.ryas.proj4.util.ServletUtility;

 
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
  	
		boolean isValid = true ;
		
		System.out.println("Email :" + request.getParameter("login"));
		if (DataValidator.isNull(request.getParameter("login"))) {
			        System.out.println("Email is Required");
			        request.setAttribute("login", "Email is Required");
			        isValid = false ;
		}
		
		System.out.println("password :" + request.getParameter("password"));
		if(DataValidator.isNull(request.getParameter("password"))) {
			         System.out.println("Password is Required");
			         request.setAttribute("password", "Password is Required");
			         isValid = false ;
		}
		
		return isValid ;
	
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
