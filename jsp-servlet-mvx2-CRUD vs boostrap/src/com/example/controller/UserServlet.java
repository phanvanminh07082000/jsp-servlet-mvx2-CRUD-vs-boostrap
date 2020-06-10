package com.example.controller;

import com.example.da.UserDAO;
import com.example.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    @Override
    public  void init() throws ServletException{
        userDAO = new UserDAO();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getContextPath();
        try{
            switch (action){
                case "/insert":
                    insertUser(request,response);
                case "/new":
                    newUserForm(request,response);
                default:
                    listUser(request,response);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }
    private void insertUser(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        //STEP2: instance of model
        UserDAO.insertUser(user);
        //STEP3: redirect to view
        response.sendRedirect("list-user");
    }
    private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        //step2
        List<User> listUser = userDAO.selectAllUsers();
        //step3
        request.setAttribute("listUser",listUser);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request,response);
    }
    private void newUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward();
    }
}
