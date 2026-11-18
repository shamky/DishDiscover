package com.amogh.dishdiscover.service;

import com.amogh.dishdiscover.dao.FavoriteDAO;
import com.amogh.dishdiscover.exception.FavoriteDAOException;
import com.amogh.dishdiscover.exception.FavoriteServiceException;
import com.amogh.dishdiscover.exception.RecipeServiceException;
import com.amogh.dishdiscover.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteService {
  private final FavoriteDAO favoriteDAO;
  private final RecipeService recipeService;

  public FavoriteService() {
    favoriteDAO = new FavoriteDAO();
    recipeService = new RecipeService();
  }

  public void addFavorite(int userId, String mealId) throws FavoriteServiceException {
    validateMealId(mealId);
    try {
      favoriteDAO.add(userId, mealId);
    } catch (FavoriteDAOException e) {
      throw new FavoriteServiceException("Unable to save this recipe to favorites.", e);
    }
  }

  public void removeFavorite(int userId, String mealId) throws FavoriteServiceException {
    validateMealId(mealId);
    try {
      favoriteDAO.remove(userId, mealId);
    } catch (FavoriteDAOException e) {
      throw new FavoriteServiceException("Unable to remove this recipe from favorites.", e);
    }
  }

  public List<Recipe> getFavorites(int userId) throws FavoriteServiceException {
    try {
      List<String> ids = favoriteDAO.findMealIdsByUserId(userId);
      List<Recipe> recipes = new ArrayList<>();
      for (String id : ids) {
        try {
          Recipe recipe = recipeService.getRecipeById(id);
          if (recipe != null) {
            recipes.add(recipe);
          }
        } catch (RecipeServiceException e) {
          // Keep the page usable if one saved recipe is temporarily unavailable from
          // TheMealDB.
          System.err.println("Unable to load saved recipe " + id + ": " + e.getMessage());
        }
      }
      return recipes;
    } catch (FavoriteDAOException e) {
      throw new FavoriteServiceException("Unable to load your favorites.", e);
    }
  }

  private void validateMealId(String mealId) throws FavoriteServiceException {
    if (mealId == null || !mealId.matches("\\d{1,12}")) {
      throw new FavoriteServiceException("Invalid recipe ID.");
    }
  }
}
