import java.io.*;
//import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import org.apache.tomcat.util.buf.Base64;


/**
 * Servlet implementation class ServerValidation
 */
@WebServlet("/UserAuthentication")
public class UserAuthentication extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String uName = "test";
	private String uPwd = "test";
	public String name1;
	
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
		
		name1 = this.readJsonDataScript();
		
		if (result) {
			//response.sendRedirect("http://localhost:6060/insuranceClaim/home.jsp?data="+name1);
		    response.sendRedirect(request.getContextPath() + "/home.jsp?data=" + name1);

		} else {
			request.setAttribute("errorMessage", error);
			request.getRequestDispatcher("/insuranceClaim.jsp").forward(request, response);
		}
	}	
	

	
	/**
	 * 
	 */
	public String readJsonDataScript() throws IOException {
		// TODO Auto-generated method stub
		// Json data read
		
		JSONParser parser = new JSONParser();
		try {
			/*
		    Object obj = parser.parse(new FileReader("D:\\ExternalJAR\\kimJson.txt"));
		    //JSONObject jsonObject = (JSONObject) obj;
		    //JSONArray jsonarray = (JSONArray) obj;
			*/
			
		    JSONArray a = (JSONArray) parser.parse(new FileReader("D:\\ExternalJAR\\kimJson.txt"));
		    
		    Boolean i = true;
		    for (Object o : a)
		    {
			   if(i) {
			      JSONObject gitCommitData = (JSONObject) o;
	
			      /*
			      String name = (String) person.get("sha");
			      System.out.println(name);
	
			      String city = (String) person.get("url");
			      System.out.println(city);
	
			      String job = (String) person.get("comments_url");
			      System.out.println(job);
			      */
			      
			      JSONObject msgData = (JSONObject) gitCommitData.get("commit");
			      name1 = (String) msgData.get("message");
			      System.out.println(name1);
			      i = false;
		       }
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return name1;
	}	
	
	
	
}