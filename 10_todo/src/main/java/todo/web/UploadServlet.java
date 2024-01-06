package todo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import todo.dao.TodoDAO;
import todo.entity.TodoValueObject;

@WebServlet(urlPatterns = { "/todo/upload" }) // 1
@MultipartConfig(location = "/Users/trandon/") // 2
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Part part = request.getPart("uploadfile"); // 3

		String filename = null;

		for (String cd : part.getHeader("Content-Disposition").split(";")) { // 4
			cd = cd.trim();

			if (cd.startsWith("filename")) {
				filename = cd.substring(cd.indexOf("=") + 1).replace("\"", "").trim();
				break;
			}
		}

		String idStr = request.getParameter("id");
		log("idStr:" + idStr);
		int id = Integer.parseInt(idStr);

		String message = null;
		if (filename != null) {
			
			filename = filename.replace("\\", "/");
			
			int pos = filename.lastIndexOf("/");
			
			if (pos >= 0 ) {
				filename =filename.substring(pos+1);
			}
			
			part.write(filename); // 5

			TodoValueObject vo = new TodoValueObject();
			vo.setId(id);
			vo.setFilename(filename);

			TodoDAO dao = new TodoDAO();
			try {
				dao.getConnection();
				dao.updateUploadInfo(vo);
			} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				dao.closeConnection();
			}
			message = "[ " + filename + " ]のアップロードが完了しました。";
		} else {
			message = "アップロードが失敗しました。";
		}
		
		request.setAttribute("message", message);

		request.getRequestDispatcher("/todo/detail?id=" + id).forward(request, response);
	}

}
