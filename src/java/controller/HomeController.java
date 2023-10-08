/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.CategoryServiceDAO;
import Database.PostDAO;
import Database.ServiceDAO;
import Database.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.CategoryService;
import model.Post;
import model.Service;
import model.Slider;

/**
 *
 * @author Admin
 */
public class HomeController extends HttpServlet {

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
            out.println("<title>Servlet HomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);

        SliderDAO sliderDAO = new SliderDAO();
        List<Slider> listSlide = sliderDAO.getAllSlideActive();
        request.setAttribute("slider", listSlide);

        PostDAO postDAO = new PostDAO();
        List<Post> listPost = postDAO.getAllPosts();

        List<Post> latestPosts = new ArrayList<>();
        if (listPost.size() < 3) {
            for (int i = listPost.size() - 1; i >= 0; i--) {
                latestPosts.add(listPost.get(i));
            }
        } else {
            for (int i = listPost.size() - 1; i >= listPost.size() - 3; i--) {
                latestPosts.add(listPost.get(i));
            }
        }
        request.setAttribute("last3post", latestPosts);

        Post hotest = postDAO.getHotestPost();
        request.setAttribute("hotest", hotest);
        List<Post> get3hotpost = postDAO.get3HotestPost();
        request.setAttribute("hot3pot", get3hotpost);

        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        List<CategoryService> listCategoryService = categoryServiceDAO.getAllCategoryServices();
        request.setAttribute("category", listCategoryService);

        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> listService = serviceDAO.getAllServices();
        request.setAttribute("services", listService);

        request.getRequestDispatcher("index.jsp").forward(request, response);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
