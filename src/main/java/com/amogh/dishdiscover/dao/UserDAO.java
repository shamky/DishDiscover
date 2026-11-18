package com.amogh.dishdiscover.dao;

import com.amogh.dishdiscover.exception.UserDAOException;
import com.amogh.dishdiscover.model.User;
import com.amogh.dishdiscover.util.DBConnection;
import java.sql.*;

public class UserDAO {
  public void save(User user) throws UserDAOException {
    String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getPasswordHash());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new UserDAOException("Unable to save user", e);
    }
  }

  public User findByUsername(String username) throws UserDAOException {
    String sql = "SELECT id, username, email, password FROM users WHERE username = ?";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next())
          return mapUser(rs);
      }
    } catch (SQLException e) {
      throw new UserDAOException("Unable to find user", e);
    }
    return null;
  }

  public boolean usernameTakenByOther(String username, int userId) throws UserDAOException {
    return exists("SELECT id FROM users WHERE username = ? AND id <> ?", username, userId);
  }

  public boolean emailTakenByOther(String email, int userId) throws UserDAOException {
    return exists("SELECT id FROM users WHERE email = ? AND id <> ?", email, userId);
  }

  private boolean exists(String sql, String value, int userId) throws UserDAOException {
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, value);
      ps.setInt(2, userId);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      throw new UserDAOException("Unable to validate account details", e);
    }
  }

  public void updateProfile(int id, String username, String email) throws UserDAOException {
    String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username);
      ps.setString(2, email);
      ps.setInt(3, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new UserDAOException("Unable to update profile", e);
    }
  }

  public void updatePassword(int id, String passwordHash) throws UserDAOException {
    String sql = "UPDATE users SET password = ? WHERE id = ?";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, passwordHash);
      ps.setInt(2, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new UserDAOException("Unable to update password", e);
    }
  }

  private User mapUser(ResultSet rs) throws SQLException {
    User user = new User();
    user.setId(rs.getInt("id"));
    user.setUsername(rs.getString("username"));
    user.setEmail(rs.getString("email"));
    user.setPasswordHash(rs.getString("password"));
    return user;
  }
}
