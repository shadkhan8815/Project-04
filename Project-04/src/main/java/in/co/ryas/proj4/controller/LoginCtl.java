package in.co.ryas.proj4.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.ryas.proj4.Exception.ApplicationException;
import in.co.ryas.proj4.bean.BaseBean;
import in.co.ryas.proj4.bean.RoleBean;
import in.co.ryas.proj4.bean.UserBean;
import in.co.ryas.proj4.model.RoleModel;
import in.co.ryas.proj4.model.UserModel;
import in.co.ryas.proj4.util.DataUtility;
import in.co.ryas.proj4.util.DataValidator;
import in.co.ryas.proj4.util.ServletUtility;

 
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
  	
		boolean isValid = true ;
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		if (OP_SING_IN.equalsIgnoreCase(op)|| OP_LOG_OUT.equalsIgnoreCase(op)) {
			
			isValid = true ;
			
			return isValid ;
			
		}
		
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
	protected BaseBean populateBean(HttpServletRequest request) {
 
		UserBean bean = new UserBean();
		
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		
		return bean ;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		HttpSession session = request.getSession();
		
		if(OP_LOG_OUT.equalsIgnoreCase(op)|| op != null) {
			
			session.invalidate();
			
			ServletUtility.setSuccessMessage("User Logout Successfully", request);
			
		}
		
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserBean bean = new UserBean();
		RoleBean rolebean = new RoleBean();
		
		UserModel model = new UserModel();
		RoleModel rolemodel = new RoleModel();
		
		bean = (UserBean)populateBean(request);
		
		HttpSession session = request.getSession();
		
	String op =	DataUtility.getString(request.getParameter("operation"));
		
	   if (OP_SING_IN.equalsIgnoreCase(op)) {
		   
		   try {
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
		     
			if (bean != null) {
				
				session.setAttribute("user", bean);
				
				rolebean = rolemodel.findByPk(bean.getRoleId());
				
				session.setAttribute("role", rolebean.getName());
				
				ServletUtility.redirect(ORSView.WELCOME_CTL,request,response);
			}
			else {
				ServletUtility.setErrorMessage("Invalid Login id OR Password", request);
				
				ServletUtility.forward(getView(), request, response);
			}
			
		   } catch (Exception e) {
 			e.printStackTrace();
		}
		
	 }
		
		if (OP_SING_UP.equalsIgnoreCase(op)) {
			
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
		}
		System.out.println("LoginCtl post method ");
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
