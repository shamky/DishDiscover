// talk to MealDB to get the json.
package com.amogh.dishdiscover.api;

import java.io.IOException;

// web imports
import java.net.URI;
import java.net.http.*;

public class MealDBClient {
  private static final String SEARCH_API_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
  private static final String DETAILED_API_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";
  private static final String RANDOM_API_URL = "https://www.themealdb.com/api/json/v1/1/random.php";
  private static final String CATEGORIES_API_URL = "https://www.themealdb.com/api/json/v1/1/categories.php";
  private static final String FILTER_CATEGORY_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";

  public String searchRecipies(String keyword) throws IOException, InterruptedException {
    String url = SEARCH_API_URL + java.net.URLEncoder.encode(keyword, java.nio.charset.StandardCharsets.UTF_8);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }

  public String getRandomRecipe() throws IOException, InterruptedException {
    return get(RANDOM_API_URL);
  }

  public String getCategories() throws IOException, InterruptedException {
    return get(CATEGORIES_API_URL);
  }

  public String filterByCategory(String category) throws IOException, InterruptedException {
    return get(FILTER_CATEGORY_URL + java.net.URLEncoder.encode(category, java.nio.charset.StandardCharsets.UTF_8));
  }

  private String get(String url) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

  public String getRecipeById(String id) throws IOException, InterruptedException {
    String url = DETAILED_API_URL + id;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }

}
