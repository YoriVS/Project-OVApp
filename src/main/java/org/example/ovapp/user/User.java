package org.example.ovapp.user;

import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private ArrayList<SavedRoute> savedRoutes;

    User(String name, String password) {
        this.name = name;
        this.password = password;
        savedRoutes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<SavedRoute> getSavedRoutes() {
        return savedRoutes;
    }

    public void setSavedRoutes(ArrayList<SavedRoute> savedRoutes) {
        this.savedRoutes = savedRoutes;
    }


}
