package in.co.ryas.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.ryas.proj4.bean.BaseBean;
import in.co.ryas.proj4.bean.UserBean;
import in.co.ryas.proj4.util.DataUtility;
import in.co.ryas.proj4.util.DataValidator;
import in.co.ryas.proj4.util.ServletUtility;


/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 *
 * 
 *
 */
public abstract class BaseCtl extends HttpServlet {

	public static final String OP_SAVE = "SAVE";
	public static final String OP_CANCEL = "CANCEL";
	public static final String OP_DELETE = "DELETE";
	public static final String OP_LIST = "LIST";
	public static final String OP_SEARCH = "SEARCH";
	public static final String OP_VIEW = "VIEW";
	public static final String OP_NEXT = "NEXT";
	public static final String OP_PREVIOUS = "PREVOIUS";
	public static final String OP_NEW = "NEW";
	public static final String OP_GO = "GO";
	public static final String OP_BACK = "BACK";
	public static final String OP_LOG_OUT = "LOGOUT";
	public static final String OP_RESET = "RESET";
	public static final String OP_UPDATE = "UPDATE";
	public static final String OP_SING_IN = "SINGIN";
	public static final String OP_SING_UP = "SINGUP";


	/**
	 * Success message key constant
	 */
	public static final String MSG_SUCCESS = "success";

	/**
	 * Error message key constant
	 */
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
	}

	/**
	 * Populates bean object from request parameters
	 *
	 * @param request
	 * @return
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	/**
	 * Populates Generic attributes in DTO
	 *
	 * @param dto
	 * @param request
	 * @return
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		} else {

			modifiedBy = userbean.getLogin();

			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}

		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Bctl service");

		// Load the preloaded data required to display at HTML form
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Bctl servi op" + op);
		// Check if operation is not DELETE, VIEW, CANCEL, RESET and NULL then
		// perform input data validation

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("Bctl 5 operation");
			// Check validation, If fail then send back to page with error
			// messages


			if (!validate(request)) {	// call setBean method for show inserted data
				System.out.println("Bctl validate ");
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		super.service(request, response);
	}

	/**
	 * Returns the VIEW page of this Controller
	 *
	 * @return
	 */
	protected abstract String getView();

}
