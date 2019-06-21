package diploma.storytime.stolbysassistant.utils;


import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class HTTPRequest {
    public static JSONObject login(String email) {
        String urlParameters = "login=" + email;
        String outputLine = "";
        try {
            outputLine = new AsyncRequest().execute("login", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject respond = new JSONObject();
        try {
            respond = (JSONObject) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (errorCheck(respond)) {
            return respond;
        } else {
            return null;
        }

    }

    static public boolean signup(String name, String email, String password, String salt) {
        String urlParameters = "name=" + name + "&email=" + email + "&password=" + password + "&salt=" + salt;
        String outputLine = "";
        try {
            outputLine = new AsyncRequest().execute("signup", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject respond = new JSONObject();

        try {
            respond = (JSONObject) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return errorCheck(respond);
    }

    public static void getFriends(MainActivity activity, User user) {
        String urlParameters = "name=" + user.getName();
        String outputLine = "";
        if (user.getFriendCount() == 0) {
            //TODO: допили реквест
            if (HTTPRequest.getFriendsCount(activity, user)) {
                return;
            }
        }
        try {
            outputLine = new AsyncRequest().execute("friends", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (outputLine.startsWith("ava")) {
            outputLine = outputLine.substring(4);
        }
        JSONParser parser = new JSONParser();
        JSONArray respond = new JSONArray();
        try {
            respond = (JSONArray) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Friend> friends = ReadJSON.readFriends(respond);
        activity.getUser().setFriends(friends);
    }

    static private boolean getFriendsCount(MainActivity activity, User user) {
        String urlParameters = "name=" + user.getName();
        String outputLine = "";
        try {
            outputLine = new AsyncRequest().execute("friendcount", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject respond = new JSONObject();
        try {
            respond = (JSONObject) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (errorCheck(respond)) {
            String number = (String) respond.get("count");
            if (Integer.parseInt(number) <= 0) {
                return false;
            } else {
                user.setFriendCount(Integer.parseInt(number));
                return true;
            }
        } else {
            Toast.makeText(activity, R.string.duplicate_friend, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    static public boolean addFriend(MainActivity activity, String name, String friendKey) {
        String urlParameters = "name=" + name + "&friendKey=" + friendKey;
        String outputLine = "";
        try {
            outputLine = new AsyncRequest().execute("newfriend", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject respond = new JSONObject();
        try {
            respond = (JSONObject) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (errorCheck(respond)) {
            return true;
        } else {
            Toast.makeText(activity, R.string.duplicate_friend, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    static public void setOffline(User user) {
        String urlParameters = "name=" + user.getName();
        try {
            new AsyncRequest().execute("offline", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public void deleteFriend(MainActivity activity, User user, String friendName) {
        String urlParameters = "name=" + user.getName() + "&friend=" + friendName;
        String outputLine = "";

        try {
            outputLine = new AsyncRequest().execute("deletefriend", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject respond = new JSONObject();
        try {
            respond = (JSONObject) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (errorCheck(respond)) {
            Toast.makeText(activity, R.string.friend_deleted, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(activity, R.string.exception, Toast.LENGTH_LONG).show();
        }
    }

    static public void updateFriendsCoordinates(MainActivity activity, User user) {
        String urlParameters = "name=" + user.getName();
        String outputLine = "";
        try {
            outputLine = new AsyncRequest().execute("coordinates", urlParameters).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONArray respond = new JSONArray();
        try {
            respond = (JSONArray) parser.parse(outputLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activity.getUser().setFriends(ReadJSON.readFriends(respond));
    }

    static private boolean errorCheck(JSONObject respond) {
        return respond.get("error") == null;
    }

}
