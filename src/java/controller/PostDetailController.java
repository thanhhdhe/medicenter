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
public class PostDetailController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            int ID= Integer.parseInt(request.getParameter("ID"));
            PostDAO postDAO = new PostDAO();
            Post post = postDAO.getPostByID(ID);
            request.setAttribute("title", post.getTitle());
            request.setAttribute("author", post.getAuthorID());
            request.setAttribute("thumbnail", post.getThumbnail());
            request.setAttribute("update-date", post.getCreatedDate());
            request.setAttribute("category", post.getCategoryPost());
            request.setAttribute("post-detail", post.getContent());

            request.getRequestDispatcher("./view/post-detail.jsp").forward(request, response);
//            out.print("dcm");s
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
        int ID = Integer.parseInt(request.getParameter("ID"));
        Post post = postDAO.getPostByID(0);

        out.print("<div class=\"row p-3 mb-2\">\n"
                + "                        <div class=\"col-md-3\">\n"
                + "                            <img src=\"" + postDAO.getAvatarByUserID(post.getAuthorID()) + "\" alt=\"img\" class=\"w-100 h-100 object-contain\" />\n"
                + "                        </div>\n"
                + "                        <div class=\"col-md-3\">\n"
                + "                            <img src=\"" + post.getThumbnail() + "\" alt=\"img\" class=\"w-100 h-100 object-contain\" />\n"
                + "                        </div>\n"
                + "                        <div class=\"col-md-6\">\n"
                + "                            <h3>" + post.getTitle() + "</h3>\n"
                + "                            <p class=\"truncate\">\n"
                + "                                " + post.getBriefInfo() + "\n"
                + "                            </p>\n"
                + "                        </div>\n"
                + "                        <div class=\"info-aside col-md-3\">\n"
                + "                            <br />\n"
                + "                            <p>\n" + post.getContent()
                + "                            </p>\n"
                + "                        </div>\n"
                + "                    </div>");

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
