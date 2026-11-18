package com.amogh.dishdiscover.service;

import com.amogh.dishdiscover.api.MealDBClient;
import com.amogh.dishdiscover.model.*;
import com.amogh.dishdiscover.exception.RecipeServiceException;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class RecipeService {

    private final MealDBClient client;
    private final Gson gson;

    public RecipeService() {

        client = new MealDBClient();
        gson = new Gson();
    }

public List<Recipe> searchRecipes(String keyword) throws RecipeServiceException {

    try {
        String json = client.searchRecipies(keyword);   // uses the mealDBClient
        MealResponse response = gson.fromJson(json, MealResponse.class); // JSON_String to Objects
        return response.getMeals();
    } 
    catch(IOException | InterruptedException e) {
        throw new RecipeServiceException("Unable to fetch recipes", e);
    }
}
public List<Recipe> getDiscoverRecipes(int count) throws RecipeServiceException {
    Map<Integer, Recipe> unique = new LinkedHashMap<>();
    try {
        for (int i = 0; i < count * 2 && unique.size() < count; i++) {
            String json = client.getRandomRecipe();
            MealResponse response = gson.fromJson(json, MealResponse.class);
            if (response != null && response.getMeals() != null) {
                for (Recipe recipe : response.getMeals()) {
                    unique.putIfAbsent(recipe.getId(), recipe);
                }
            }
        }
        return new ArrayList<>(unique.values());
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RecipeServiceException("Unable to load discover recipes", e);
    } catch (IOException e) {
        throw new RecipeServiceException("Unable to load discover recipes", e);
    }
}

public List<String> getCategories() throws RecipeServiceException {
    try {
        String json = client.getCategories();
        com.google.gson.JsonObject root = com.google.gson.JsonParser.parseString(json).getAsJsonObject();
        List<String> categories = new ArrayList<>();
        com.google.gson.JsonArray items = root.getAsJsonArray("categories");
        if (items != null) {
            for (com.google.gson.JsonElement item : items) {
                categories.add(item.getAsJsonObject().get("strCategory").getAsString());
            }
        }
        return categories;
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RecipeServiceException("Unable to load categories", e);
    } catch (IOException e) {
        throw new RecipeServiceException("Unable to load categories", e);
    }
}

public List<Recipe> filterByCategory(String category) throws RecipeServiceException {
    try {
        String json = client.filterByCategory(category);
        MealResponse response = gson.fromJson(json, MealResponse.class);
        List<Recipe> recipes = response == null || response.getMeals() == null
                ? new ArrayList<>()
                : response.getMeals();

        // The category endpoint already provides each recipe's ID, name, and image.
        // no need to  make a separate detail API request for every recipe; details load
        // only after the user opens an individual recipe.
        for (Recipe recipe : recipes) {
            if (recipe.getCategory() == null) {
                recipe.setCategory(category);
            }
        }
        return recipes;
    } catch (IOException e) {
        throw new RecipeServiceException("Unable to filter recipes", e);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RecipeServiceException("Unable to filter recipes", e);
    }
}

public Recipe getRecipeById(String id) throws RecipeServiceException{
    try{
        String json = client.getRecipeById(id);
        MealResponse response = gson.fromJson(json, MealResponse.class); // MealResponse will have a list which 
                                                                                    // will contain only 1 object

        if (response.getMeals() == null || response.getMeals().isEmpty()) {
            throw new RecipeServiceException("Recipe not found");
        }
        
        return response.getMeals().get(0);  // sends the first and only object in the list to the caller.
    }
    catch(IOException | InterruptedException e){
        throw new RecipeServiceException("Unable to fetch recipe", e);
    }
}

}