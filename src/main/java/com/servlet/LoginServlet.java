package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DB.DBconnect;
import com.entity.User;
import com.dao.UserDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {

			String em = req.getParameter("email");
			String ps = req.getParameter("password");
			User u = new User();
			HttpSession session = req.getSession();

			if ("admin@gmail.com".equals(em) && "1234".equals(ps)) {

				session.setAttribute("userobj", u);
				u.setRole("admin");
				resp.sendRedirect("admin.jsp");

			} else {

				UserDao dao = new UserDao(DBconnect.getConn());
				User user =dao.login(em,ps);
				if(user!=null) {
					session.setAttribute("userobj",user);
					resp.sendRedirect("home.jsp");
					

					
					
				}
				else {
					session.setAttribute("succMsg","Invalid Email and Password");
					resp.sendRedirect("login.jsp");
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
