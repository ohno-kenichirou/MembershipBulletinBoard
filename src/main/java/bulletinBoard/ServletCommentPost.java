package bulletinBoard;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletCommentPost
 */
@WebServlet("/ServletCommentPost")
public class ServletCommentPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCommentPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		request.setAttribute("sendThreadId", Integer.parseInt(request.getParameter("threadId")));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/commentPost.jsp");
		dispatcher.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		int threadId = Integer.parseInt(request.getParameter("threadId"));
		request.setAttribute("sendThreadId", threadId);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/commentPost.jsp");
		dispatcher.forward(request, response);	
		
	}

}
