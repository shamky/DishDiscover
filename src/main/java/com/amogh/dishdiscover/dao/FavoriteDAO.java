package com.amogh.dishdiscover.dao;

import com.amogh.dishdiscover.exception.FavoriteDAOException;
import com.amogh.dishdiscover.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

  public void add(int userId, String mealId) throws FavoriteDAOException {
    String sql = "INSERT IGNORE INTO favorites (user_id, meal_id) VALUES (?, ?)"; // can be used to like prevent dups
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, userId);
      ps.setString(2, mealId);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new FavoriteDAOException("Unable to add favorite", e);
    }
  }

  public void remove(int userId, String mealId) throws FavoriteDAOException {
    String sql = "DELETE FROM favorites WHERE user_id = ? AND meal_id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, userId);
      ps.setString(2, mealId);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new FavoriteDAOException("Unable to remove favorite", e);
    }
  }

  public List<String> findMealIdsByUserId(int userId) throws FavoriteDAOException {
    String sql = "SELECT meal_id FROM favorites WHERE user_id = ? ORDER BY created_at DESC";
    List<String> mealIds = new ArrayList<>();
    
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
      
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          mealIds.add(rs.getString("meal_id"));
        }
      }
      return mealIds;
    }
    catch (SQLException e) {
      throw new FavoriteDAOException("Unable to load favorites", e);
    }
  }

}