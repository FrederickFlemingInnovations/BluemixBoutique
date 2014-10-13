package example;


import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doLogin(req, res);

		return;
	}

	/**
	 * @see HttpServlet#doPost(Htt
	 * pServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doLogin(req, res);
		return;
	}
	
	
	private void doLogin(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String userID = isValidUsername(req.getParameter("uid"));
		if(userID == null){
			res.sendRedirect("index.jsp?error=invalidUserName");
			return;
		}
		HttpSession session = req.getSession(true);
		session.setAttribute("userid", userID);
		res.sendRedirect("index.jsp");
		return;
	}

	public static String isValidUsername(String x) {
	    return (x != null && Pattern.matches("^[a-zA-Z0-9:]*", x)) ? x : null;
	}
	
	
	public static void main(String[] args){
		System.out.println(isValidUsername("uid:0"));
	}
}
