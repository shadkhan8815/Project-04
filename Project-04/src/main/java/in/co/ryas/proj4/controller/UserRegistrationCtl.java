package in.co.ryas.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.ryas.proj4.bean.BaseBean;
import in.co.ryas.proj4.bean.RoleBean;
import in.co.ryas.proj4.bean.UserBean;
import in.co.ryas.proj4.model.UserModel;
import in.co.ryas.proj4.util.DataUtility;
import in.co.ryas.proj4.util.DataValidator;
import in.co.ryas.proj4.util.ServletUtility;

@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
 
		    boolean isValid = true ;
		    
		    if (DataValidator.isNull(request.getParameter("firstName"))) {
		    request.setAttribute("firstName", "First Name Required");
		    isValid = false ;
		    
		    } else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "First Name Contains Alphabet");
			isValid = false ;
			}
		    
		    
		    if (DataValidator.isNull(request.getParameter("lastName"))) {
		    	request.setAttribute("lastName", "Last Name Required");
		    	isValid = false ;
		    	
		    } else if (!DataValidator.isName(request.getParameter("lastName"))) {
		    	request.setAttribute("lastName", "Last Name Contains Alphabet");
		    	isValid = false ;
		    }
		    
		    
		    
		    if (DataValidator.isNull(request.getParameter("login"))) {
		    	request.setAttribute("login", " E-mail Must Required");
		    	isValid = false ;
		    	
		    } 
//		    else if (!DataValidator.isEmail(request.getParameter("login"))) {
//		    	request.setAttribute("login", "E-mail Contains A,a,1,@,. ");
//		    	isValid = false ;
//		    }
//		    
//		    
		    
		    if (DataValidator.isNull(request.getParameter("dob"))) {
		    	request.setAttribute("dob", "Dob Required");
		    	isValid = false ;
		    	
		    }
		    else if (!DataValidator.isAge(request.getParameter("dob"))) {
		    	request.setAttribute("dob", "Minimum Age 18 years");
		    	isValid = false ;
		    }
		    
		    
		    
		    if (DataValidator.isNull(request.getParameter("gender"))) {
		    	request.setAttribute("gender", "Gender Required");
		    	isValid = false ;
		    	
		    } else if (!DataValidator.isName(request.getParameter("gender"))) {
		    	request.setAttribute("gender", "Gender Contains Alphabet (Male/Female)");
		    	isValid = false ;
		    }
		    
		    
		    
		    if (DataValidator.isNull(request.getParameter("mobileNo"))) {
		    	request.setAttribute("mobileNo", "Mobile_No Required");
		    	isValid = false ;
		    	
		    }
//		    else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
//		    	request.setAttribute("mobileNo", "Mobile_No Contain Numerics(123)");
//		    	isValid = false ;
//		    }
//		    
		    
		    
		    if (DataValidator.isNull(request.getParameter("password"))) {
		    	request.setAttribute("password", "Password Must Required");
		    	isValid = false ;
		    	
		    } 
//		    else if (!DataValidator.isPassword(request.getParameter("password"))) {
//		    	request.setAttribute("password", "Password Contains A,1,*,#,@,");
//		    	isValid = false ;
//		    }
		    
		    
		    
		    if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
		    	request.setAttribute("confirmPassword", "Confirm Password Required");
		    	isValid = false ;
		    	
		    } else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
		    	request.setAttribute("confirmPassword", "Passwords Must be Same");
		    	isValid = false ;
		    }
		    
	       return isValid ;
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		      UserBean bean = new UserBean();
		      
		      bean.setRoleId(RoleBean.STUDENT);
		      
		      bean.setId(DataUtility.getLong(request.getParameter("id")));
		      bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		      bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		      bean.setLogin(DataUtility.getString(request.getParameter("login")));
		      bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		      bean.setGender(DataUtility.getString(request.getParameter("gender")));
		      bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		      bean.setPassword(DataUtility.getString(request.getParameter("password")));
		      bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
			
		      
		      populateDTO(bean, request);
		      
		      return bean;	      
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		             UserBean bean = new UserBean();
		             UserModel model = new UserModel();
		             
		             bean = (UserBean) populateBean(request);
		             
		             if (OP_SING_UP.equalsIgnoreCase(op)) {
		            	 
		     			try {
		     				
		     				model.add(bean);
		     				ServletUtility.setBean(bean, request);
		     				ServletUtility.setSuccessMessage("user register successfully", request);
		     				
		     			} catch (Exception e) {
		     				e.printStackTrace();
		     			}
		     		}
               ServletUtility.forward(getView(), request, response);
	}
	

	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}