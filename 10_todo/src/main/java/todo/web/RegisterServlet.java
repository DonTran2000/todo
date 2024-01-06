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


@WebServlet("/todo/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String task = request.getParameter("task");
		String inputLimitDate = request.getParameter("limit");
		String userid = request.getParameter("userid");
		int status = Integer.parseInt(request.getParameter("status"));

		System.out.println(request.getParameter("title"));
		
		response.setContentType("text/html;charset=utf-8");
		
		TodoValueObject vo = new TodoValueObject();
		vo.setId(id);
		vo.setTitle(title);
		vo.setTask(task);
		vo.setInputLimitdate(inputLimitDate);
		vo.setUserid(userid);
		vo.setStatus(status);
		
		
		boolean checkResult = vo.valueCheck();
		
		if ( !checkResult ) {
			request.setAttribute("errorMessages", vo.getErrorMessages());
			
			request.setAttribute("vo", vo);
			
			RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
			rd.forward(request, response);
			return;
		}

		TodoDAO dao = new TodoDAO();
		try {
			dao.getConnection();

			if (id == 0) {
				dao.registerInsert(vo);
				setMessage(request, "タスク[ " + id + " ]の新規登録処理が完了しました。");
			} else {
				dao.registerUpdate(vo);
				setMessage(request, "タスク[ " + id + " ]の更新処理が完了しました。");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/todo/search");
		rd.forward(request, response);
	}

	protected void setMessage(HttpServletRequest request, String message) {
		// TODO Auto-generated method stub
		request.setAttribute("message", message);
	}

}
