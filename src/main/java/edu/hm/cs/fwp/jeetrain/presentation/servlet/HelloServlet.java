/* HelloServlet.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple demo implementation of a {@code Servlet}.
 * 
 * @author theism
 * @version %PR% %PRT% %PO%
 * @since release 1.0 11.04.2013 22:25:56
 */
@WebServlet(urlPatterns = { "/helloServlet" })
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 590153928180563245L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/plain");
//		PrintWriter writer = new PrintWriter(response.getOutputStream());
//		writer.println("Hello out there!");
//		writer.flush();
//		writer.close();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/helloWorld.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name != null) {
			Model model = new Model();
			model.setName(name);
			request.setAttribute("model", model);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/helloWorld.jsp");
		dispatcher.forward(request, response);
	}
}
