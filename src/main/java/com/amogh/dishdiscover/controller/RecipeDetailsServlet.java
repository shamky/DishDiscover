package com.amogh.dishdiscover.controller;

import com.amogh.dishdiscover.exception.RecipeServiceException;
import com.amogh.dishdiscover.model.Recipe;
import com.amogh.dishdiscover.service.RecipeService;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/recipeDetails")

public class RecipeDetailsServlet extends HttpServlet {

  private RecipeService recipeService;

  @Override
  public void init() {
    recipeService = new RecipeService();
  }

  public void doGet(
      HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    String id = request.getParameter("id");
    try {
      Recipe recipe = recipeService.getRecipeById(id);
      request.setAttribute("recipe", recipe);

      request.getRequestDispatcher("/recipe.jsp").forward(request, response);
    } catch (RecipeServiceException e) {
      response.getWriter().println("unable to load recipe detail");

    }
  }
}