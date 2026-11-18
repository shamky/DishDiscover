package com.amogh.dishdiscover.model;

import java.util.List;

// creates an object which contains a list of meals.
public class MealResponse {
  // same name as in the ThemealDB, no need annotation for gson.
  private List<Recipe> meals;

  public List<Recipe> getMeals() {
    return meals;
  }
}