package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import Database.PostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Post;

/**
 *
 * @author Admin
 */
public class PostController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String event = request.getParameter("event");
        if (event.equals("post-list")) {
            request.getRequestDispatcher("./view/post-list.jsp").forward(request, response);
        } else if (event.equals("post-list-userchoose")) {
            renderPostListByOption(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String txtSearch = request.getParameter("postTitle");
        request.setAttribute("postTitle", txtSearch);
        String event = request.getParameter("event");
        if (event.equals("post-list")) {
            request.getRequestDispatcher("./view/post-list.jsp").forward(request, response);
        } else if (event.equals("post-list-userchoose")) {
            renderPostListByOption(request, response);
        }
    }

    protected void renderPostListByOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        PostDAO postDAO = new PostDAO();
        String postTitle = request.getParameter("postTitle");
        String postCategory = request.getParameter("postCategory");
        int page = Integer.parseInt(request.getParameter("page"));
        List<Post> postList;
        if (postCategory.isEmpty()) {
            postList = postDAO.getPostedPagedPostsBySearch((page - 1) * 6, 6, postTitle);

        } else {
            postList = postDAO.getSortedPagedPostsByUserChoice((page - 1) * 6, 6, postTitle, postCategory);
        }
        String paginationHtml = "";
        int numOfPage = postDAO.getCountOfPostsUserChoose(postTitle, postCategory) / 6;
        if (postDAO.getCountOfPostsUserChoose(postTitle, postCategory) % 6 != 0) {
            numOfPage += 1;
        }
        for (int i = 1; i <= numOfPage; i++) {
            paginationHtml += "<button class=\"pagination-btn ms-2 " + (i == page ? "active" : "inactive")
                    + "\" data-page=\"" + i + "\" onclick=\"loadPagePosts(" + i + ")\">" + i + "</button>";
        }
        // Thêm giá trị phân trang vào header của phản hồi
        response.addHeader("pagination", paginationHtml);
        out.print("<div class=\"container py-5\">\n"
                + "                        <div class=\"row g-5\">");
        for (Post post : postList) {
            out.print("                     <div class=\"col-xl-4 col-lg-6\">\n"
                    + "                        <div class=\"bg-light rounded overflow-hidden\">\n"
                    + "                            <img class=\"img-fluid w-100\" src=\"" + post.getThumbnail() + "\" alt=\"\">\n"
                    + "                            <div class=\"p-4\">\n"
                    + "                                 <a class=\"h3 d-block mb-3\" href=\"/ChildrenCare/postDetail?ID=" + post.getPostID() + "\">" + post.getTitle() + "</a>\n"
                    + "                                <p class=\"m-0\">" + post.getBriefInfo() + "</p>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"d-flex justify-content-between border-top p-4\">\n"
                    + "                                <div class=\"d-flex align-items-center\">\n"
                            + "                                           <img class=\"rounded-circle me-2 avatar\" src=\""+postDAO.getAvatarByUserID(post.getAuthorID())+"\" id=\"avatar\" alt=\"\">\n" 
                    + "                                    <small>" + postDAO.getNameByUserID(post.getAuthorID()) + "</small>\n"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </div>");
        }
        out.print("</div>\n"
                + "                    </div>");
        out.flush();
        out.close();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static void main(String[] args) {
    }
}
