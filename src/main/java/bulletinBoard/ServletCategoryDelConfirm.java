/*
	処理内容:	カテゴリー削除確認サーブレット
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
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
 * Servlet implementation class ServletCategoryDelConfirm
 */
@WebServlet("/ServletCategoryDelConfirm")
public class ServletCategoryDelConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCategoryDelConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryDelConfirm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		UserInfo user = (UserInfo)session.getAttribute("User");
		CategoryInfo category = (CategoryInfo)session.getAttribute("CategoryDel");
		if (category == null) {
			doGet(request, response);
			return;
		}
		CategoryCommonDAO dao = new CategoryDelDAO(user, category);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categoryDelConfirm.jsp");
		if (dao.delCategory()) {
			request.setAttribute("message", "カテゴリーが削除されました。");
			session.removeAttribute("CategoryDel");
			dispatcher = request.getRequestDispatcher("ServletCategorySearchList");
		} else {
			request.setAttribute("message", "[システムエラー]処理に失敗しました。");
		}
		dispatcher.forward(request, response);
	}

}
