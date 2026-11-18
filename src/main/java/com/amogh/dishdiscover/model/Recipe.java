package com.amogh.dishdiscover.model;

import com.google.gson.annotations.SerializedName;

// Store info of each meal
public class Recipe {
  @SerializedName("idMeal")
  private int id;

  @SerializedName("strMeal")
  private String name;

  @SerializedName("strCategory")
  private String category;

  @SerializedName("strArea")
  private String area;

  @SerializedName("strInstructions")
  private String instructions;

  @SerializedName("strMealThumb")
  private String thumbnail;

  public Recipe() {
    //
  }

  public Recipe(int id, String name, String category, String area, String instructions, String thumbnail) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.area = area;
    this.instructions = instructions;
    this.thumbnail = thumbnail;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getArea() {
    return area;
  }

  public String getInstructions() {
    return instructions;
  }

  public String getThumbnail() {
    return thumbnail;
  }

}