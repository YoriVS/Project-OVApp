package org.example.ovapp.handler;

import org.example.ovapp.user.SavedRoute;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginHandler {

    public static boolean login(String username, String password) {
        JSONArray userInfos = JSONHandler.loadJsonArray("/JSON/userInfo.json");
        boolean pass = false;

        String hashedUsername = DatatypeConverter.printHexBinary(HashingHandler.Creating_SHA2_Hash(username));
        String hashedPassword = DatatypeConverter.printHexBinary(HashingHandler.Creating_SHA2_Hash(password));

        for (Object user : userInfos) {
            JSONObject userInfo = (JSONObject) user;
            JSONObject login = (JSONObject) userInfo.get("login");

            if (login.get("username").equals(hashedUsername) && login.get("password").equals(hashedPassword)) {
                pass = true;
            }
        }

        return pass;
    }

    public static void register(String username, String password) {
        JSONArray userInfos = JSONHandler.loadJsonArray("/JSON/userInfo.json");
        JSONArray loginInfo = new JSONArray();
        JSONObject user = new JSONObject();
        JSONObject login = new JSONObject();

        login.put("username", DatatypeConverter.printHexBinary(HashingHandler.Creating_SHA2_Hash(username)));
        login.put("password", DatatypeConverter.printHexBinary(HashingHandler.Creating_SHA2_Hash(password)));
        user.put("login", login);
        user.put("savedRoute", new ArrayList<SavedRoute>());

        userInfos.add(user);

        JSONHandler.saveJsonArray("/JSON/userInfo.json", userInfos);
    }

}
