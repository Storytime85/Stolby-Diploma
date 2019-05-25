package diploma.storytime.stolbysassistant.utils;


import android.os.AsyncTask;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.fragments.LoginFragment;
import diploma.storytime.stolbysassistant.fragments.MainFragment;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class HTTPRequest {

    public static void login(MainActivity activity, String email, String password) {
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
            String emailString = (String) respond.get("email");
            String nameString = (String) respond.get("name");
            long friends = (long) respond.get("count");
            String salt = (String) respond.get("salt");
            String encryptedPassword = (String) respond.get("password");
            String friendKey = (String) respond.get("friendKey");
            if (PasswordUtils.verifyUserPassword(password, encryptedPassword, salt)) {
                activity.setUser(new User(emailString, nameString, (int) friends, friendKey));
                activity.setUserInfo();
                activity.switchMenuItems(true);
                FragmentChanger.changeFragment(new MainFragment(), activity);
                Toast.makeText(activity, R.string.success_login, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, R.string.failed_login, Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(activity, R.string.failed_login, Toast.LENGTH_LONG).show();
        }
    }
//    POST login [mail, password] [name, mail]
//    POST signin [name, mail, password]
//    POST friends [name]   array:[friendName, online]
//    POST coordinates [name]   array:[friendName, latitude, longitude]
//    POST newcoordinates [name, latitude, longitude]

    static public void signup(MainActivity activity, String name, String email, String password, String salt) {
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
        if (errorCheck(respond)) {
            Toast.makeText(activity, R.string.success_registration, Toast.LENGTH_LONG).show();
            FragmentChanger.changeFragment(new LoginFragment(), activity);
        } else {
            Toast.makeText(activity, R.string.failed_registration, Toast.LENGTH_LONG).show();
        }

    }

    public static void getFriends(MainActivity activity, User user) {
        String urlParameters = "name=" + "Storytime";
        String outputLine = "";
        try {
            outputLine = new AsyncRequest().execute("friends", urlParameters).get();
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

    }

    static public void addFriend(MainActivity activity, String name, String friendKey) {

    }

//    public static String getFriendKey(MainActivity activity, String name){
//        String urlParameters = "name=" + "name";
//        String outputLine = "";
//        try {
//            outputLine = new AsyncRequest().execute("friendkey", urlParameters).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        JSONParser parser = new JSONParser();
//        JSONObject respond = new JSONObject();
//        try {
//            respond= (JSONObject) parser.parse(outputLine);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    static private boolean errorCheck(JSONObject respond) {
        return respond.get("error") == null;
    }

    static class AsyncRequest extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... arg) {
            return postRequest(arg[0], arg[1]);

        }

        private String postRequest(String getPart, String urlParameters) {
            StringBuilder response = new StringBuilder();

            try {

                URL url = new URL("http://stolbyassistant.site/" + getPart + ".php");
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                int postDataLength = postData.length;
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setInstanceFollowRedirects(false);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("charset", "utf-8");
                con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                con.setRequestProperty("Accept", "application/json");
                con.setUseCaches(false);
                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.write(postData);
                }
                if (con.getResponseCode() < 299) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                        String responseLine = null;

                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    return "{\"error\": \"error\"}";
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString();
        }
    }
}
