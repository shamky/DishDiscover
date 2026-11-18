package com.amogh.dishdiscover.controller;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

import com.amogh.dishdiscover.exception.UserServiceException;
import com.amogh.dishdiscover.service.UserService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init() {
    userService = new UserService();
  }

  // @Override
  // public void doGet(HttpServletRequest request, HttpServletResponse response)
  // throws ServletException, IOException{
  // response.sendRedirect( request.getContextPath() + "/register");
  // }
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/register.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    try {

      userService.register(username, email, password);
      response.sendRedirect(request.getContextPath() + "/login");

    } catch (UserServiceException e) {
      getServletContext().log("Registration failed", e);

      request.setAttribute("error", e.getMessage());
      request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
  }
}