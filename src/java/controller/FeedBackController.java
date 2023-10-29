/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.FeedBackDAO;
import Database.ServiceDAO;
import Database.StaffDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.FeedBack;
import model.Mail;
import model.MedicalExamination;
import model.Service;
import model.Staff;
import model.User;

/**
 *
 * @author pc
 */
public class FeedBackController extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        //get Role account
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByStaffEmail(role);
        UserDAO userdao = new UserDAO();
        User Arole = userdao.getUser(role);
        // check login
        if (staff == null && Arole == null) {
            // check user is no login
            request.getRequestDispatcher("/view/404-forbidden.jsp").forward(request, response);
        } else {
            String roleaccount = "";
            if (staff == null) {
                roleaccount = "";
            } else {
                roleaccount = staff.getRole();
            }
            // check role of account
            if (roleaccount.equals("manager".trim())) {
                FeedBackDAO dao = new FeedBackDAO();
                //get service for fillter
                ServiceDAO servicedao = new ServiceDAO();
                List<Service> serviceList = servicedao.getAllServices();
                request.setAttribute("servicelistfill", serviceList);
                // set event for servlet
                String event = request.getParameter("event");
                // if event null  appear information
                if (event == null) {

                    //get total page
                    int endP = dao.getTotalFeedback();
                    int endPage = endP / 10;
                    //paging
                    if (endP % 10 != 0) {
                        endPage++; // if endP not divide by 10 so that endPage + 1
                    }
                    String index = request.getParameter("index");
                    if (index == null) {
                        List<FeedBack> feedbacks = dao.getPageFeedBacks(1);
                        request.setAttribute("endP", endPage);
                        request.setAttribute("feedbacks", feedbacks);
                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    } else {
                        int page = Integer.parseInt(index);
                        List<FeedBack> feedbacks = dao.getPageFeedBacks(page);
                        request.setAttribute("endP", endPage);
                        request.setAttribute("feedbacks", feedbacks);
                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    }
                } else if (event.equals("showdetailfeedback")) {
                    //create feedback by id
                    String FDid = request.getParameter("FDid");
                    FeedBack feedback = dao.getFeedbackDetail(Integer.parseInt(FDid));
                    ServiceDAO serviceDAO = new ServiceDAO();

                    Service ser = serviceDAO.getServiceByID(feedback.getServiceName());
                    request.setAttribute("FDid", FDid);
                    request.setAttribute("ser", ser);
                    request.setAttribute("feedbackdetail", feedback);
                    request.getRequestDispatcher("/view/feedback-detail.jsp").forward(request, response);
                } // update status
                else if (event.equals("updatestatus")) {
                    // get infor for update
                    String Fstatus = request.getParameter("Fstatus");
                    String UID = request.getParameter("UID");
                    if (Fstatus.equals("Resolved")) {
                        // Get user's email 
                        UserDAO userDao = new UserDAO();
                        String email = userDao.getUserByID(Integer.parseInt(UID)).getEmail();
                        System.out.println(email);
                        // Send an email with a feedback request
                        Mail.sendEmail(email, "THANK TO USE SERVICE", "Thank you for using our service");

                    }
                    dao.updateStatus(Fstatus, Integer.parseInt(UID));

                    //fillter for status
                } else if (event.equals("fillterstatus")) {

                    //get infor
                    String Fillstatus = request.getParameter("fillstatus");
                    //get total page
                    int endP = dao.getTotalFeedbackFill(Fillstatus, "FStatus");
                    int endPage = endP / 10;
                    //paging
                    if (endP % 10 != 0) {
                        endPage++; // if endP not divide by 10 so that endPage + 1
                    }
                    String index = request.getParameter("index");
                    if (index == null) {
                        //get search
                        String search = request.getParameter("search");
                        List<FeedBack> feedbacks = dao.getPageFeedBackByFill(1, Fillstatus, "FStatus");
                        System.out.println(feedbacks.get(0).getFeedbackID());
                        // if search not null new list list contains feedback have search end send for jsp
                        if (search != null) {

                            List<FeedBack> newfeedbacks = new ArrayList<>();
                            for (FeedBack f : feedbacks) {
                                // get name user
                                User userser = userdao.getUserByID(f.getUserID());
                                if (userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                        || userser.getLastName().toLowerCase().contains(search.toLowerCase())) {
                                    newfeedbacks.add(f);
                                }
                            }
                            request.setAttribute("feedbacks", newfeedbacks);
                            int newpage = newfeedbacks.size() / 10;

                            if (newfeedbacks.size() % 10 != 0) {
                                newpage++; // if endP not divide by 10 so that endPage + 1
                            }

                            request.setAttribute("endP", newpage);
                        } else {
                            request.setAttribute("feedbacks", feedbacks);
                            request.setAttribute("endP", endPage);
                        }
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "fillstatus");
                        //set fill for url paging
                        request.setAttribute("fill", Fillstatus);

                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    } else {
                        //get search
                        String search = request.getParameter("search");
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "fillstatus");
                        //set fill for url paging
                        request.setAttribute("fill", Fillstatus);
                        int page = Integer.parseInt(index);

                        List<FeedBack> feedbacks = dao.getPageFeedBackByFill(page, Fillstatus, "FStatus");
                        // if search not null new list list contains feedback have search end send for jsp
                        if (search != null) {
                            List<FeedBack> newfeedbacks = new ArrayList<>();
                            for (FeedBack f : feedbacks) {
                                // get name user
                                User userser = userdao.getUserByID(f.getUserID());
                                if (userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                        || userser.getLastName().toLowerCase().contains(search.toLowerCase())) {
                                    newfeedbacks.add(f);
                                }
                            }
                            request.setAttribute("feedbacks", newfeedbacks);
                            int newpage = newfeedbacks.size() / 10;

                            if (newfeedbacks.size() % 10 != 0) {
                                newpage++; // if endP not divide by 10 so that endPage + 1
                            }

                            request.setAttribute("endP", newpage);
                        } else {
                            request.setAttribute("feedbacks", feedbacks);
                            request.setAttribute("endP", endPage);
                        }

                        request.setAttribute("feedbacks", feedbacks);
                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    }
                    //fillter for rate star
                } else if (event.equals("fillterrate")) {
                    //get infor
                    String Fillrate = request.getParameter("fillrate");
                    //get total page
                    int endP = dao.getTotalFeedbackFill(Fillrate, "RatedStar");
                    int endPage = endP / 10;
                    //paging
                    if (endP % 10 != 0) {
                        endPage++; // if endP not divide by 10 so that endPage + 1
                    }
                    String index = request.getParameter("index");

                    if (index == null) {
                        //get search
                        String search = request.getParameter("search");
                        List<FeedBack> feedbacks = dao.getPageFeedBackByFill(1, Fillrate, "RatedStar");
                        // if search not null new list list contains feedback have search end send for jsp
                        if (search != null) {
                            List<FeedBack> nfeedbacks = dao.getPageFeedBackByFill(-1, Fillrate, "RatedStar");
                            List<FeedBack> newfeedbacks = new ArrayList<>();
                            List<FeedBack> countnewfeedbacks = new ArrayList<>();
                            int count = 0;
                            for (FeedBack f : nfeedbacks) {
                                // get name user
                                User userser = userdao.getUserByID(f.getUserID());
                                if ((userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                        || userser.getLastName().toLowerCase().contains(search.toLowerCase()))) {
                                    countnewfeedbacks.add(f);
                                }
                            }
                            for (FeedBack f : nfeedbacks) {
                                // get name user
                                if (count < 10) {
                                    User userser = userdao.getUserByID(f.getUserID());
                                    if ((userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                            || userser.getLastName().toLowerCase().contains(search.toLowerCase()))) {

                                        newfeedbacks.add(f);
                                        count++;
                                    }
                                } else {
                                    break;
                                }
                            }
                            request.setAttribute("feedbacks", newfeedbacks);
                            System.out.println(countnewfeedbacks.size());
                            int newpage = countnewfeedbacks.size() / 10;
                            System.out.println(newpage);
                            if (countnewfeedbacks.size() % 10 != 0) {
                                newpage++; // if endP not divide by 10 so that endPage + 1
                            }
                            System.out.println(newpage);
                            request.setAttribute("search", search);
                            request.setAttribute("endP", newpage);
                        } else {
                            request.setAttribute("feedbacks", feedbacks);
                            request.setAttribute("endP", endPage);
                        }
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "fillrate");
                        //set fill for url paging
                        request.setAttribute("fill", Fillrate);

                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    } else {
                        //get search
                        String search = request.getParameter("search");
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "fillrate");
                        //set fill for url paging
                        request.setAttribute("fill", Fillrate);
                        int page = Integer.parseInt(index);
                        List<FeedBack> feedbacks = dao.getPageFeedBackByFill(page, Fillrate, "RatedStar");
                        // if search not null new list list contains feedback have search end send for jsp
                        if (search != null) {
                            List<FeedBack> nfeedbacks = dao.getPageFeedBackByFill(-1, Fillrate, "RatedStar");
                            //new List to count page
                            List<FeedBack> countnewfeedbacks = new ArrayList<>();
                            //new List to search
                            List<FeedBack> newfeedbacks = new ArrayList<>();
                            // count page
                            int startIndex = (Integer.parseInt(index) - 1) * 10;
                            int endIndex = startIndex + 10;
                            int count = 0;
                            for (FeedBack f : nfeedbacks) {
                                // get name user
                                User userser = userdao.getUserByID(f.getUserID());
                                if (userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                        || userser.getLastName().toLowerCase().contains(search.toLowerCase())) {
                                    countnewfeedbacks.add(f);
                                }
                            }
                            for (int i = startIndex; i < endIndex; i++) {
                                if (i < nfeedbacks.size()) {
                                    FeedBack f = nfeedbacks.get(i);
                                    User userser = userdao.getUserByID(f.getUserID());
                                    if (userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                            || userser.getLastName().toLowerCase().contains(search.toLowerCase())) {
                                        newfeedbacks.add(f);
                                        count++;
                                    }
                                } else {
                                    break;
                                }
                            }
                            request.setAttribute("feedbacks", newfeedbacks);
                            int newpage = countnewfeedbacks.size() / 10;

                            if (countnewfeedbacks.size() % 10 != 0) {
                                newpage++; // if endP not divide by 10 so that endPage + 1
                            }
                            request.setAttribute("search", search);
                            request.setAttribute("endP", newpage);
                        } else {
                            request.setAttribute("feedbacks", feedbacks);
                            request.setAttribute("endP", endPage);
                        }

                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    }
                } // fillter for service 
                else if (event.equals("fillterservice")) {
                    //get infor
                    String Fillservice = request.getParameter("fillservice");
                    //get total page
                    int endP = dao.getTotalFeedbackFillSer(Fillservice);
                    int endPage = endP / 10;
                    //paging
                    if (endP % 10 != 0) {
                        endPage++; // if endP not divide by 10 so that endPage + 1
                    }
                    //get search
                    String search = request.getParameter("search");
                    String index = request.getParameter("index");
                    if (index == null) {
                        List<FeedBack> feedbacks = dao.getPageFeedBackByFillSer(1, Fillservice);

                        // if search not null new list list contains feedback have search end send for jsp
                        if (search != null) {
                            List<FeedBack> nfeedbacks = dao.getPageFeedBackByFillSer(-1, Fillservice);
                            //new List to search 
                            List<FeedBack> newfeedbacks = new ArrayList<>();
                            //new list to count page
                            List<FeedBack> countnewfeedbacks = new ArrayList<>();
                            int count = 0;
                            for (FeedBack f : nfeedbacks) {
                                // get name user
                                User userser = userdao.getUserByID(f.getUserID());
                                if ((userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                        || userser.getLastName().toLowerCase().contains(search.toLowerCase()))) {
                                    countnewfeedbacks.add(f);
                                }
                            }
                            for (FeedBack f : nfeedbacks) {
                                // get name user
                                if (count < 10) {
                                    User userser = userdao.getUserByID(f.getUserID());
                                    if ((userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                            || userser.getLastName().toLowerCase().contains(search.toLowerCase()))) {

                                        newfeedbacks.add(f);
                                        count++;
                                    }
                                } else {
                                    break;
                                }
                            }
                            request.setAttribute("feedbacks", newfeedbacks);
                            System.out.println(countnewfeedbacks.size());
                            int newpage = countnewfeedbacks.size() / 10;
                            System.out.println(newpage);
                            if (countnewfeedbacks.size() % 10 != 0) {
                                newpage++; // if endP not divide by 10 so that endPage + 1
                            }
                            System.out.println(newpage);
                            request.setAttribute("search", search);
                            request.setAttribute("endP", newpage);
                        } else {
                            request.setAttribute("feedbacks", feedbacks);
                            request.setAttribute("endP", endPage);
                        }
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "fillservice");
                        //set fill for url paging
                        request.setAttribute("fill", Fillservice);

                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    } else {
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "fillservice");
                        //set fill for url paging
                        request.setAttribute("fill", Fillservice);
                        int page = Integer.parseInt(index);
                        List<FeedBack> feedbacks = dao.getPageFeedBackByFillSer(page, Fillservice);
                        // if search not null new list list contains feedback have search end send for jsp
                        if (search != null) {
                            List<FeedBack> nfeedbacks = dao.getPageFeedBackByFillSer(-1, Fillservice);
                            //new List to count page
                            List<FeedBack> countnewfeedbacks = new ArrayList<>();
                            //new List to search
                            List<FeedBack> newfeedbacks = new ArrayList<>();
                            // count page
                            int startIndex = (Integer.parseInt(index) - 1) * 10;
                            int endIndex = startIndex + 10;
                            int count = 0;
                            for (FeedBack f : nfeedbacks) {
                                // get name user
                                User userser = userdao.getUserByID(f.getUserID());
                                if (userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                        || userser.getLastName().toLowerCase().contains(search.toLowerCase())) {
                                    countnewfeedbacks.add(f);
                                }
                            }
                            for (int i = startIndex; i < endIndex; i++) {
                                if (i < nfeedbacks.size()) {
                                    FeedBack f = nfeedbacks.get(i);
                                    User userser = userdao.getUserByID(f.getUserID());
                                    if (userser.getFirstName().toLowerCase().contains(search.toLowerCase())
                                            || userser.getLastName().toLowerCase().contains(search.toLowerCase())) {
                                        newfeedbacks.add(f);
                                        count++;
                                    }
                                } else {
                                    break;
                                }
                            }
                            request.setAttribute("feedbacks", newfeedbacks);
                            int newpage = countnewfeedbacks.size() / 10;

                            if (countnewfeedbacks.size() % 10 != 0) {
                                newpage++; // if endP not divide by 10 so that endPage + 1
                            }
                            request.setAttribute("search", search);
                            request.setAttribute("endP", newpage);
                        } else {
                            request.setAttribute("feedbacks", feedbacks);
                            request.setAttribute("endP", endPage);
                        }

                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    }
                } // search
                else if (event.equals("searchfeedback")) {
                    //get infor
                    String SearchN = request.getParameter("search");
                    //get total page
                    int endP = dao.getTotalFeedbackSearch(SearchN);
                    int endPage = endP / 10;
                    //paging
                    if (endP % 10 != 0) {
                        endPage++; // if endP not divide by 10 so that endPage + 1
                    }
                    String index = request.getParameter("index");
                    if (index == null) {
                        List<FeedBack> feedbacks = dao.getPageFeedBacksSearch(SearchN, SearchN, 1);
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "search");
                        //set fill for url paging
                        request.setAttribute("fill", SearchN);
                        request.setAttribute("endP", endPage);
                        request.setAttribute("feedbacks", feedbacks);
                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    } else {
                        // set event for paging
                        request.setAttribute("fillevent", event);
                        //set name of fill parameter url paging                  
                        request.setAttribute("fillparameter", "search");
                        //set fill for url paging
                        request.setAttribute("fill", SearchN);
                        int page = Integer.parseInt(index);
                        List<FeedBack> feedbacks = dao.getPageFeedBacksSearch(SearchN, SearchN, page);
                        request.setAttribute("endP", endPage);
                        request.setAttribute("feedbacks", feedbacks);
                        request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                    }
                } // sort
                else if (event.equals("sort")) {
                    //get total page
                    int endP = dao.getTotalFeedback();
                    int endPage = endP / 10;
                    //paging
                    if (endP % 10 != 0) {
                        endPage++; // if endP not divide by 10 so that endPage + 1
                    }

                    //get type of sort
                    String sortby = request.getParameter("sortby");
                    if (sortby.equals("fullname")) {
                        String index = request.getParameter("index");
                        if (index == null) {
                            List<FeedBack> feedbacks = dao.getSortByName(1);
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        } else {
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            int page = Integer.parseInt(index);
                            List<FeedBack> feedbacks = dao.getSortByName(page);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        }
                    } //sort by service name 
                    else if (sortby.equals("servicename")) {
                        String index = request.getParameter("index");
                        if (index == null) {
                            List<FeedBack> feedbacks = dao.getSortByService(1);
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        } else {
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            int page = Integer.parseInt(index);
                            List<FeedBack> feedbacks = dao.getSortByService(page);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        }
                    } // sort by rate star
                    else if (sortby.equals("ratestar")) {
                        String index = request.getParameter("index");
                        if (index == null) {
                            List<FeedBack> feedbacks = dao.getSortByRate(1);
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        } else {
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            int page = Integer.parseInt(index);
                            List<FeedBack> feedbacks = dao.getSortByRate(page);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        }
                    } // sort by status
                    else if (sortby.equals("status")) {
                        String index = request.getParameter("index");
                        if (index == null) {
                            List<FeedBack> feedbacks = dao.getSortByStatus(1);
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        } else {
                            // set event for paging
                            request.setAttribute("fillevent", event);
                            //set name of fill parameter url paging                  
                            request.setAttribute("fillparameter", "sortby");
                            //set fill for url paging
                            request.setAttribute("fill", sortby);
                            int page = Integer.parseInt(index);
                            List<FeedBack> feedbacks = dao.getSortByStatus(page);
                            request.setAttribute("endP", endPage);
                            request.setAttribute("feedbacks", feedbacks);
                            request.getRequestDispatcher("/view/feedback-list-manager.jsp").forward(request, response);
                        }
                    }
                }

            } else if (roleaccount.equals("doctor".trim()) || roleaccount.equals("nurse".trim())) {
                // check user is no login
                request.getRequestDispatcher("/view/403-forbidden.jsp").forward(request, response);
            } else {
                // Get the 'action' parameter from the request
                String action = request.getParameter("action");
                // Determine the servlet action based on the 'action' parameter
                if (action.equals("accessfeedback")) {
                    // Check if the user is logged in

                    String email = (String) session.getAttribute("email");
                    // Display a login required message as a popup overlay

                    // Get user information
                    User user = userdao.getUser(email);
                    System.out.println(user.getEmail());
                    // Get medical examinations awaiting feedback
                    System.out.println(user.getUserID());
                    FeedBackDAO dao = new FeedBackDAO();
                    // Set the 'medical' attribute for rendering in FeedBack.jsp
                    List<MedicalExamination> medical = dao.getMedicalExamination(user.getUserID());
                    request.setAttribute("medical", medical);
                    request.getRequestDispatcher("/view/FeedBack.jsp").forward(request, response);

                } else if (action.equals("sendfeedback")) {
                    // Get user information

                    String email = (String) session.getAttribute("email");

                    User user = userdao.getUser(email);
                    System.out.println(user.getEmail());
                    // Get feedback details from the request
                    System.out.println(user.getUserID());
                    FeedBackDAO dao = new FeedBackDAO();
                    String ratestar = request.getParameter("rate");
                    System.out.println(ratestar);
                    if (ratestar == null) {
                        System.out.println("asdas");
                        request.setAttribute("notify", "You have to choose the number of stars");
                        request.getRequestDispatcher("/feedback?action=accessfeedback").forward(request, response);
                    } else {
                        String content = request.getParameter("content");
                        String medicalID = request.getParameter("medical");

                        int medicalId = Integer.parseInt(medicalID);
                        if (content.length() >= 6 && content.length() <= 2000) {
                            int rate = Integer.parseInt(ratestar);
                            // Insert the feedback into the database
                            dao.InsertFeedBack(user.getUserID(), medicalId, content, rate);
                            out.println("<!DOCTYPE html>\n"
                                    + "<html>\n"
                                    + "<head>\n"
                                    + "    <meta charset=\"UTF-8\">\n"
                                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                    + "    <style>\n"
                                    + "        body {\n"
                                    + "            background-color: #f2f2f2;\n"
                                    + "            font-family: Arial, sans-serif;\n"
                                    + "            display: flex;\n"
                                    + "            justify-content: center;\n"
                                    + "            align-items: center;\n"
                                    + "            height: 100vh;\n"
                                    + "            margin: 0;\n"
                                    + "        }\n"
                                    + "        \n"
                                    + "        .container {\n"
                                    + "            text-align: center;\n"
                                    + "            background-color: #ffffff;\n"
                                    + "            border-radius: 10px;\n"
                                    + "            padding: 20px;\n"
                                    + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                                    + "        }\n"
                                    + "        \n"
                                    + "        h1 {\n"
                                    + "            font-size: 36px;\n"
                                    + "            color: #333;\n"
                                    + "        }\n"
                                    + "        \n"
                                    + "        p {\n"
                                    + "            font-size: 18px;\n"
                                    + "            color: #666;\n"
                                    + "        }\n"
                                    + "    </style>\n"
                                    + "</head>\n"
                                    + "<body>\n"
                                    + "    <div class=\"container\">\n"
                                    + "        <h1>Thank you</h1>\n"
                                    + "        <p>FeedBack have been sent.</p>\n"
                                    + " <button style=\" background-color: blue; border: 0px; border-radius: 5px;padding: 10px;\"> "
                                    + " <a style=\"color: white; padding: 10px;text-decoration: none;\" href=\"http://localhost:9999/ChildrenCare/\">HOME</a> </button>"
                                    + "    </div>\n"
                                    + "</body>\n"
                                    + "</html>");
                        } else {
                            // notification
                            String notify = "";
                            if (content.length() <= 6 || content.length() >= 2000) {
                                notify = "You must enter content with a length greater than 6 characters and less than 2000 characters";
                            }
                            request.setAttribute("notify", notify);
                            request.getRequestDispatcher("/feedback?action=accessfeedback").forward(request, response);
                        }
                    }

                } else {
                    // Get user's email from the session

                    String email = (String) session.getAttribute("email");
                    // Send an email with a feedback request
                    Mail.sendEmail(email, "THANK TO USE SERVICE", "Thank you for using our service\n"
                            + "Please give us feedback about the service by clicking on feedback in the header on the homepage on the website");
                    request.getRequestDispatcher("/view/FeedBack.jsp").forward(request, response);

                }
            }
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
        processRequest(request, response);
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
