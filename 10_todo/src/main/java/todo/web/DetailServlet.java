package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDAO;
import todo.entity.TodoValueObject;


@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		TodoDAO dao = new TodoDAO();
		
		String paramId = request.getParameter("id");
		
		TodoValueObject vo;
		try {
			dao.getConnection();
			
			int id = Integer.parseInt(paramId);
			vo = dao.detail(id);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}
		
		request.setAttribute("vo", vo);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
