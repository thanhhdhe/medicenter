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
        processRequest(request, response);
    }

    protected void renderPostListByOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        PostDAO postDAO = new PostDAO();
        String postTitle = (request.getParameter("postTitle") + "").equals("null") ? "" : (request.getParameter("postTitle") + "");
        String postCategory = (request.getParameter("postCategory") + "").equals("null") ? "" : (request.getParameter("postCategory") + "");
        int page = (request.getParameter("page") + "").equals("page") ? 1 : Integer.parseInt(request.getParameter("page") + "");
        List<Post> postList = postDAO.getSortedPagedPostsByUserChoice((page - 1) * 5, 5, postTitle, postCategory);

        String paginationHtml = "";
        for (int i = 1; i <= (postList.size() / 5 + 1) * 2 / 2; i++) {
            paginationHtml += "<button class=\"pagination-btn ms-2 " + (i == page ? "active" : "inactive")
                    + "\" data-page=\"" + i + "\" onclick=\"loadPagePosts(" + i + ")\">" + i + "</button>";
        }
        // Thêm giá trị phân trang vào header của phản hồi
        response.addHeader("pagination", paginationHtml);

        for (Post post : postList) {
            out.print("<div class=\"row p-3 mb-2\">\n"
                    + "                        <div class=\"col-md-3\">\n"
                    + "                            <img src=\"" + post.getThumbnail() + "\" alt=\"ìmg\" class=\"w-100 h-100 object-contain\" />\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-md-6\">\n"
                    + "                            <h3>" + post.getTitle() + "</h3>\n"
                    + "                            <p class=\"truncate\">\n"
                    + "                                " + post.getBriefInfo() + "\n"
                    + "                            </p>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"info-aside col-md-3\">\n"
                    + "                            <div class=\"price-wrap\">");
            out.print("</div>\n"
                    + "                            <br />\n"
                    + "                            <p>\n"
                    + "                                <a href=\"#\" class=\"btn btn-primary btn-block\"> Details </a>\n"
                    + "                            </p>\n"
                    + "                        </div>\n"
                    + "                    </div>");
        }
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
