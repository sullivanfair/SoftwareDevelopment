package com.example.volleytrials;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class is a singleton that stores important data for the current user
 */
public class UserData {
    private static UserData instance;
    private static String globalName;
    private static String globalPassword;
    private static String globalID;
    private JSONArray array; //of Listings
    private String email;
    private ArrayList<String> notifications;

    /**
     * Initializes all variables
     */
    private UserData(){
        globalName = "";
        globalPassword = "";
        globalID = "";
        array = null;
        email = "";
        notifications = new ArrayList<String>();
    }

    /**
     * Insures that only one copy of UserData can exist at a time
     * @return the instance that is being used
     */
    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    /**
     * Returns the username of the current user
     * @return current user's username
     */
    public static String getName() {
        return globalName;
    }
    /**
     * Stores the username of the current user
     * @param value current user's username
     */
    public static void setName(String value) {
        globalName = value;
    }
    /**
     * Returns the password of the current user
     * @return current user's password
     */
    public static String getPassword() {
        return globalPassword;
    }
    /**
     * Stores the password of the current user
     * @param value current user's password
     */
    public static void setPassword(String value) {
        globalPassword = value;
    }
    /**
     * Returns the ID of the current user
     * @return current user's ID
     */
    public static String getID() {
        return globalID;
    }
    /**
     * Stores the ID of the current user
     * @param value current user's ID
     */
    public static void setID(String value) {
        globalID = value;
    }
    /**
     * Returns the data array of the current user
     * Has recommended roommates if Tenant and userListings if landlord
     * @return current user's data array
     */
    public JSONArray getArray() {
        return array;
    }
    /**
     * Stores the data array of the current user
     * @param value current user's important data array depending on User type
     */
    public void setArray(JSONArray value) {
        array = value;
    }
    /**
     * Returns the email of the current user
     * @return current user's email
     */
    public String getEmail() {return email;}
    /**
     * Stores the email of the current user
     * @param value current user's email
     */
    public void setEmail(String value) {email = value;}
    /**
     * Returns the ArrayList storing the notifications of the current user
     * @return current user's notifications sotred in an ArrayList
     */
    public ArrayList<String> getNotifications() {return notifications;}
}
