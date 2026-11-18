package com.amogh.dishdiscover.service;

// import java.io.IOException;

import com.amogh.dishdiscover.dao.UserDAO;
import com.amogh.dishdiscover.exception.UserDAOException;
import com.amogh.dishdiscover.exception.UserServiceException;
import com.amogh.dishdiscover.model.User;
import com.amogh.dishdiscover.util.PasswordUtil;

public class UserService {
    private UserDAO userDAO;
    public UserService(){
        userDAO = new UserDAO();
    }
    // REGISTER
    // check if user exists
        // yes:
            // throw exception -> take them to login page.
        // no:
            // Hash the password
            // send to saveDAO
    // public void register(String username, String email, String password) throws UserServiceException{
    //     try{
    //         User existingUser;
    //         existingUser = userDAO.findByUsername(username);
    //         if(existingUser != null){
    //             throw new UserServiceException("User already exists");
    //         }
    //         else{
    //             password = PasswordUtil.hashPassword(password);
    //             existingUser = new User(username, email, password);

    //             userDAO.save(existingUser);
    //         }
    //     }
    //     catch( Exception e){
    //         throw new UserServiceException("unable to register", e);
    //     }
    // }

    
    public void register(String username, String email, String password)
            throws UserServiceException {
        try {
            System.out.println("Checking username...");
            User existingUser = userDAO.findByUsername(username);

            if (existingUser != null) {
                throw new UserServiceException("User already exists");
            }

            System.out.println("Hashing password...");
            String passwordHash = PasswordUtil.hashPassword(password);

            User newUser = new User(username, email, passwordHash);

            System.out.println("Saving user...");
            userDAO.save(newUser);

            System.out.println("Registration successful!");

        } catch (UserDAOException e) {
            e.printStackTrace();
            throw new UserServiceException("Unable to register user", e);
        }
    }

    
        // auth
            // check if user exists
            // yes:
                // verify user password
                // create a session.
            // no:
                // throw exception -> take them to register page
    public User authenticate(String username, String password) throws UserServiceException{ //login
        try{
            User user;
            user = userDAO.findByUsername(username);
            if(user == null){
                throw new UserServiceException("User doesn't exist");
            }

            if(PasswordUtil.verifyPassword(password, user.getPasswordHash())){
                return user;
            }
            else{
                throw new UserServiceException("Password or Username is incorrect");
            }

        }
        catch( Exception e){
            throw new UserServiceException("unable to login", e);
        }
    }
    
    public void updateProfile(User user, String username, String email) throws UserServiceException {
        if (username == null || !username.trim().matches("[A-Za-z0-9_.-]{3,30}"))
            throw new UserServiceException("Username must be 3–30 characters and use letters, numbers, dots, underscores or hyphens.");
        if (email == null || !email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
            throw new UserServiceException("Enter a valid email address.");
        username = username.trim(); email = email.trim();
        try {
            if (userDAO.usernameTakenByOther(username, user.getId())) throw new UserServiceException("That username is already in use.");
            if (userDAO.emailTakenByOther(email, user.getId())) throw new UserServiceException("That email address is already in use.");
            userDAO.updateProfile(user.getId(), username, email);
            user.setUsername(username); user.setEmail(email);
        } catch (UserDAOException e) { throw new UserServiceException("Unable to update your profile right now.", e); }
    }

    public void changePassword(User user, String currentPassword, String newPassword) throws UserServiceException {
        if (currentPassword == null || !PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash()))
            throw new UserServiceException("Your current password is incorrect.");
        if (newPassword == null || newPassword.length() < 8)
            throw new UserServiceException("Your new password must be at least 8 characters long.");
        try {
            String newHash = PasswordUtil.hashPassword(newPassword);
            userDAO.updatePassword(user.getId(), newHash);
            user.setPasswordHash(newHash);
        } catch (UserDAOException e) { throw new UserServiceException("Unable to change your password right now.", e); }
    }

}
