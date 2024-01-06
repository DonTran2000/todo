package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDAO;

@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		TodoDAO dao = new TodoDAO();

		String paramId = request.getParameter("id");
		System.out.println(paramId);
		
		
		try {
			dao.getConnection();
			int id = Integer.parseInt(paramId);
			int result = dao.delete(id);
			System.out.println(result);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}

		setMessage(request, "タスク[ " + paramId + " ]の削除処理が完了しました。");

		RequestDispatcher rd = request.getRequestDispatcher("/todo/search");
		rd.forward(request, response);
	}

	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}

}
