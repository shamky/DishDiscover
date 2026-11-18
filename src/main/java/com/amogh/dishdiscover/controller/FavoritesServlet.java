package com.amogh.dishdiscover.controller;

import com.amogh.dishdiscover.exception.FavoriteServiceException;
import com.amogh.dishdiscover.model.Recipe;
import com.amogh.dishdiscover.model.User;
import com.amogh.dishdiscover.service.FavoriteService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {
  private FavoriteService favoriteService;

  @Override
  public void init() {
    favoriteService = new FavoriteService();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User user = getLoggedInUser(request, response);
    if (user == null)
      return;

    try {
      List<Recipe> favorites = favoriteService.getFavorites(user.getId());
      request.setAttribute("favorites", favorites);
      request.getRequestDispatcher("/favorites.jsp").forward(request, response);
    }
    catch (FavoriteServiceException e) {
      getServletContext().log(
        "Unable to load favorites",
        e
      );
      response.sendError(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
        "Unable to load your favorites right now."
      );
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    User user = getLoggedInUser(request, response);
    if (user == null)
      return;

    String action = request.getParameter("action");
    String mealId = request.getParameter("mealId");
    try {
      if ("add".equals(action)) {
        favoriteService.addFavorite(user.getId(), mealId);
      } else if ("remove".equals(action)) {
        favoriteService.removeFavorite(user.getId(), mealId);
      } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown favorites action.");
        return;
      }
      String destination = "add".equals(action) ? "/recipeDetails?id=" + mealId : "/favorites";
      response.sendRedirect(request.getContextPath() + destination);
    } catch (FavoriteServiceException e) {
      getServletContext().log("Unable to update favorites", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Unable to update favorites right now.");
    }
  }

  private User getLoggedInUser(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    HttpSession session = request.getSession(false);
    if (session == null || !(session.getAttribute("user") instanceof User)) {
      response.sendRedirect(request.getContextPath() + "/login");
      return null;
    }
    return (User) session.getAttribute("user");
  }
}
