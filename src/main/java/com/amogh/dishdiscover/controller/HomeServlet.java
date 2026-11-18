package com.amogh.dishdiscover.controller;

import com.amogh.dishdiscover.exception.RecipeServiceException;
import com.amogh.dishdiscover.model.Recipe;
import com.amogh.dishdiscover.model.User;
import com.amogh.dishdiscover.service.RecipeService;
import com.amogh.dishdiscover.service.FavoriteService;
import com.amogh.dishdiscover.exception.FavoriteServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = { "/home", "" })
public class HomeServlet extends HttpServlet {
  private RecipeService recipeService;
  private FavoriteService favoriteService;

  @Override
  public void init() {
    recipeService = new RecipeService();
    favoriteService = new FavoriteService();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    User user = session != null && session.getAttribute("user") instanceof User
        ? (User) session.getAttribute("user")
        : null;
    try {
      // loading up the required preloaded sections-
      request.setAttribute("discoverRecipes", recipeService.getDiscoverRecipes(8));
      request.setAttribute("categories", recipeService.getCategories());

      if (user != null) { // user exist = load.
        try {
          List<Recipe> favorites = favoriteService.getFavorites(user.getId());
          request.setAttribute("favoritePreview", favorites.size() > 4
              ? favorites.subList(0, 4)
              : favorites);

        } catch (FavoriteServiceException e) {
          getServletContext().log("Unable to load favorite preview", e);
          request.setAttribute("favoritePreview", Collections.emptyList());
        }
      }

    } catch (RecipeServiceException e) {
      getServletContext().log("Unable to load homepage recipes", e);
      request.setAttribute("homeError", "We couldn't load recipes right now. Please try again in a moment.");
    }
    request.getRequestDispatcher("/home.jsp").forward(request, response);
  }
}
