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

import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

  private RecipeService recipeService;

  @Override
  public void init() {
    recipeService = new RecipeService();
  }

  @Override
  protected void doGet(
      HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    String keyword = request.getParameter("keyword");
    try {

      List<Recipe> recipes = recipeService.searchRecipes(keyword);
      request.setAttribute("recipes", recipes);
      request.setAttribute("keyword", keyword);

      request.getRequestDispatcher("/recipes.jsp").forward(request, response);
    } catch (RecipeServiceException e) {

      response.getWriter().println("Unable to load recipes.");
    }
  }
}