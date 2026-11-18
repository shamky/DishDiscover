package com.amogh.dishdiscover.controller;

import com.amogh.dishdiscover.exception.UserServiceException;
import com.amogh.dishdiscover.model.User;
import com.amogh.dishdiscover.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
  private UserService userService;

  @Override
  public void init() {
    userService = new UserService();
  }

  private User requireUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession(false);
    if (session == null || !(session.getAttribute("user") instanceof User)) {
      response.sendRedirect(request.getContextPath() + "/login");
      return null;
    }
    return (User) session.getAttribute("user");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = requireUser(request, response);
    if (user == null)
      return;
    request.getRequestDispatcher("/profile.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = requireUser(request, response);
    if (user == null)
      return;
    String action = request.getParameter("action");
    try {
      if ("profile".equals(action)) {
        userService.updateProfile(user, request.getParameter("username"), request.getParameter("email"));
        request.setAttribute("success", "Your profile has been updated.");
      } else if ("password".equals(action)) {
        userService.changePassword(user, request.getParameter("currentPassword"), request.getParameter("newPassword"));
        request.setAttribute("success", "Your password has been changed.");
      } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown profile action.");
        return;
      }
    } catch (UserServiceException e) {
      request.setAttribute("error", e.getMessage());
    }
    request.getRequestDispatcher("/profile.jsp").forward(request, response);
  }
}
