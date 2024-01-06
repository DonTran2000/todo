package todo.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDAO;
import todo.entity.TodoValueObject;

@WebServlet("/todo/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		TodoDAO dao = new TodoDAO();
		TodoValueObject vo = new TodoValueObject();

		try {
			dao.getConnection();

			vo = dao.detail(id); // 1
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}

		String filename = vo.getFilename(); // 2

		if (filename == null || "".equals(filename)) { // 3
			request.setAttribute("message", "ファイルは添付されていません");
			request.getRequestDispatcher("/todo/search?id=" + id).forward(request, response);
			return;
		}

		File downloadFile = new File("/Users/trandon/" + filename); // 4
		FileInputStream fis = new FileInputStream(downloadFile);
		try (BufferedInputStream buf = new BufferedInputStream(fis)) {
			filename = URLEncoder.encode(filename, "utf-8"); // 5

			response.setContentType("application/octet-stream; charset=\"utf-8\""); // 6
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\""); // 7

			ServletOutputStream out = response.getOutputStream(); // 8

			int length = 0;
			byte[] buffer = new byte[1024];
			while ((length = buf.read(buffer)) >= 0) {
				out.write(buffer, 0, length); // 9
			}
			out.close(); // 10
			out.flush(); // 11
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
