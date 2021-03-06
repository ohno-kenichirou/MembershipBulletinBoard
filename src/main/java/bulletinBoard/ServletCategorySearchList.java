package bulletinBoard;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletCategorySearchList
 */
@WebServlet("/ServletCategorySearchList")
public class ServletCategorySearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCategorySearchList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("ThreadSearchInfo");
		}

		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			page = 1;
			session.removeAttribute("CategorySearchInfo");
		}
		
		CategorySearchInfo categorySearch = (CategorySearchInfo)session.getAttribute("CategorySearchInfo");
		if (categorySearch != null) {
			String searchWord = categorySearch.getSearchWord();
			String match = categorySearch.getMatch();
			
			if (searchWord != null && match != null) {
				ArrayList<CategoryNameDisp> categoryList = new CategoryListDAO().searchCategory(categorySearch, page);
				session.setAttribute("CategoryList", categoryList);
				session.setAttribute("CategoryPagination", new CategoryListDAO().searchCategoryCount(categorySearch));
			} else {
				CategoryListDAO categoryDao = new CategoryListDAO();
				ArrayList<CategoryNameDisp> categoryList = categoryDao.searchAndSetList(page);
				session.setAttribute("CategoryList", categoryList);
				session.setAttribute("CategoryPagination", new CategoryListDAO().searchAndSetListCount());
			}
		} else {
			CategoryListDAO categoryDao = new CategoryListDAO();
			ArrayList<CategoryNameDisp> categoryList = categoryDao.searchAndSetList(page);
			session.setAttribute("CategoryList", categoryList);
			session.setAttribute("CategoryPagination", new CategoryListDAO().searchAndSetListCount());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categorySearchList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			page = 1;
		}
		
		String btn = request.getParameter("update");
		if (btn == null) {
			doGet(request, response);
			return;
		}
		if (btn.equals("search")) {
			String searchName = request.getParameter("searchWord");
			String selectMatch = request.getParameter("match");
			CategorySearchInfo categorySearch = new CategorySearchInfo(searchName, selectMatch);
			ArrayList<CategoryNameDisp> categoryList = new CategoryListDAO().searchCategory(categorySearch, page);
			session.setAttribute("CategoryList", categoryList);
			session.setAttribute("CategorySearchInfo", categorySearch);
			session.setAttribute("CategoryPagination", new CategoryListDAO().searchCategoryCount(categorySearch));
			request.setAttribute("sendCategoryList", categoryList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/categorySearchList.jsp");
			dispatcher.forward(request, response);
		} else if (btn.equals("add")) {
			response.sendRedirect("ServletCategoryAdd");
		} else {
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			String categoryName = request.getParameter("categoryName");
			String categoryKana = request.getParameter("categoryKana");
			CategoryInfo info = new CategoryInfo(categoryId, categoryName, categoryKana);
			if (btn.equals("modify")) {
				session.setAttribute("CategoryModify", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletCategoryModify");
			} else if (btn.equals("delete")) {
				session.setAttribute("CategoryDel", info);
				session.setMaxInactiveInterval(60 * 60 * 24);
				response.sendRedirect("ServletCategoryDelConfirm");
			}
		}
		
	}

}
