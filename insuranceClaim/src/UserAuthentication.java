import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.tomcat.util.buf.Base64;


/**
 * Servlet implementation class ServerValidation
 */
@WebServlet("/UserAuthentication")
public class UserAuthentication extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String uName = "test";
	private String uPwd = "test";

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long captcha = null;
		boolean resultC = true;

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String captchaStr = request.getParameter("captcha");
		
		try {
			if(!captchaStr.equals("")) {
				captcha = Long.parseLong(captchaStr);
			}
        } catch (NumberFormatException e) {
        	resultC = false;
        	System.out.println("IndexOutOfBoundsException: " + e.getMessage());
        }
		

		String error = "";

		boolean result = true;

		//AppWorks Logical Error Introduce
		//if (userName == "") {
		if (userName.equals("")) {
			error += "Please enter User Name.<br/>";
			result = false;
		}
		
		
		if (password.equals("")) {
			error += "Please enter Password.<br/>";
			result = false;
		}
		
		if (captcha == null || resultC == false || captcha != 4) {
			error += "Please enter correct Captcha Value.<br/>";
			result = false;
		} else {
			if (userName.equals(uName) && password.equals(uPwd) && captcha == 4) {
				// Create a session object if it is already not  created.
				HttpSession session = request.getSession(true);
				session.setAttribute("sessUname", uName);
				session.setAttribute("sessUpwd", uPwd);
				
				//System.out.println(session.getAttribute("sessUname"));
				//System.out.println(session.getAttribute("sessUpwd"));
				result = true;
			} else {
				error += "User Name or Password is incorrect.<br/>";
				result = false;
			}
		}
		
		
		if (result) {
			response.sendRedirect("home.jsp");
		} else {
			request.setAttribute("errorMessage", error);
			request.getRequestDispatcher("/insuranceClaim.jsp").forward(request, response);
		}
	}	
	

}