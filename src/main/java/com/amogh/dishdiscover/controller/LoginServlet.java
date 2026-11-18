package com.amogh.dishdiscover.controller;

import java.io.IOException;

import com.amogh.dishdiscover.exception.UserServiceException;
import com.amogh.dishdiscover.service.UserService;
import com.amogh.dishdiscover.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  private UserService userService;
  // private

  @Override
  public void init() throws ServletException {
    userService = new UserService();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);

    if (session != null && session.getAttribute("user") != null) {
      response.sendRedirect(request.getContextPath() + "/home");
    } else {
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    try {
      User user = userService.authenticate(username, password);
      HttpSession oldSession = request.getSession(false);
      if (oldSession != null)
        oldSession.invalidate(); //shutting down any previous sessions related to a request.
      HttpSession session = request.getSession(true); // associating new session to the req.
      session.setAttribute("user", user);
      response.sendRedirect(request.getContextPath() + "/home");
    } catch (UserServiceException e) {
      request.setAttribute("error", e.getMessage());
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
  }
}
