package registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistraionServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		Connection connect = null;
		RequestDispatcher dispatcher = null;
		
//		server-side validation
		if(fname == null || fname.equals("")) {
			request.setAttribute("status", "emptyFname");
			dispatcher = request.getRequestDispatcher("registration.jsp");	
			dispatcher.forward(request,response);
		}
		else if(lname == null || lname.equals("")) {
			request.setAttribute("status", "emptyLname");
			dispatcher = request.getRequestDispatcher("registration.jsp");	
			dispatcher.forward(request,response);
		}
		else if(email == null || email.equals("")) {
			request.setAttribute("status", "emptyEmail");
			dispatcher = request.getRequestDispatcher("registration.jsp");	
			dispatcher.forward(request,response);
		}
		else if(phone == null || phone.equals("") || phone.length() != 10) {
			request.setAttribute("status", "invalidPhone");
			dispatcher = request.getRequestDispatcher("registration.jsp");	
			dispatcher.forward(request,response);
		}
		else if(password == null || password.equals("")) {
			request.setAttribute("status", "emptyPassword");
			dispatcher = request.getRequestDispatcher("registration.jsp");	
			dispatcher.forward(request,response);
		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/iotbay?useSSL=false","root","Ds180507.");
			PreparedStatement prep = connect.prepareStatement("insert into users(firstName,lastName,email,upassword,phone) values(?,?,?,?,?)");
			prep.setString(1,fname);
			prep.setString(2,lname);
			prep.setString(3,email);
			prep.setString(4,password);
			prep.setString(5,phone);
			
			int rowCount = prep.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
