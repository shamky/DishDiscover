package com.amogh.dishdiscover.controller;

import com.amogh.dishdiscover.exception.RecipeServiceException;
import com.amogh.dishdiscover.service.RecipeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
  private RecipeService recipeService;

  @Override
  public void init() {
    recipeService = new RecipeService();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String category = request.getParameter("name");
    if (category == null || category.isBlank() || category.length() > 80) {
      response.sendError(400, "Choose a valid category.");
      return;
    }
    try {
      request.setAttribute("recipes", recipeService.filterByCategory(category));
      request.setAttribute("keyword", category);
      request.setAttribute("categoryMode", Boolean.TRUE);
      request.getRequestDispatcher("/recipes.jsp").forward(request, response);
    } catch (RecipeServiceException e) {
      getServletContext().log("Unable to load category " + category, e);
      response.sendError(502, "Unable to load this category right now.");
    }
  }
}
