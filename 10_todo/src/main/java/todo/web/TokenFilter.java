package todo.web;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/todo/*")
public class TokenFilter implements Filter {

	public TokenFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		
		HttpServletRequest httpreq = (HttpServletRequest) request; // 1

		if (!tokenCheck(httpreq)) { // 2
			throw new ServletException("トークンチェック例外です。");
		}

		createToken(httpreq); // 3

		chain.doFilter(request, response);
	}

	protected boolean tokenCheck(HttpServletRequest request) {

		String method = request.getMethod(); // 4
		if (!"POST".equals(method))
			return true;

		String reqToken = request.getParameter("token");
		if (reqToken == null || reqToken.isEmpty()) {
			return false;
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}

		String sessionToken = (String) session.getAttribute("token");

		return (reqToken.equals(sessionToken)); // 5
	}

	private void createToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = generateToken();
		session.setAttribute("token", token);
	}

	private String generateToken() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 64; i++) { // 6
			char c = (char) (r.nextInt(64) + 32);
			sb.append(c);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");	//7
		} catch (NoSuchAlgorithmException e) {
			return sb.toString();
		}
		md.reset();
		md.update(sb.toString().getBytes());
		byte[] hash = md.digest(); // 8

		StringBuilder hex = new StringBuilder();
		for (int i = 0; i < hash.length; i++) { // 9
			hex.append(Integer.toHexString((hash[i] >> 4) & 0x0F));
			hex.append(Integer.toHexString(hash[i] & 0x0F));
		}
		return hex.toString();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
