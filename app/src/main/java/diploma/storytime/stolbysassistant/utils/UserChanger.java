package diploma.storytime.stolbysassistant.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.fragments.LoginFragment;
import diploma.storytime.stolbysassistant.fragments.MainFragment;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class UserChanger {
    static public void signupUser(String name, String password, String email, MainActivity activity){
        String query = "SELECT * FROM users u WHERE u.email=" + email + ";";
        try {
            Statement stmt = SQLConnection.connect().createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                Toast.makeText(activity, R.string.failed_registration,Toast.LENGTH_LONG ).show();
            } else {
                stmt.executeQuery("INSERT INTO users (name, email, password) VALUES (" +
                        name + "," + email + "," + password + ");");
                FragmentChanger.changeFragment(new LoginFragment(), activity);
                Toast.makeText(activity, R.string.success_registration,Toast.LENGTH_LONG ).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void loginUser(String password, String email, MainActivity activity) {
        String query = "SELECT * FROM users u WHERE u.email=" + email + ", u.password=" +
                password + ";";
        try {
            Statement stmt = SQLConnection.connect().createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                ParseUser.getCurrentUser().setUsername(rs.getString("name"));
                ParseUser.getCurrentUser().setEmail(rs.getString("email"));
                ParseUser.getCurrentUser().signUpInBackground(e -> {
                    if (e == null) {
                        setUserInfo(activity.getEmailTextView(), activity.getUserNameTextView(), activity.getImageView());
                        FragmentChanger.changeFragment(new MainFragment(), activity);
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(activity, R.string.failed_signup, Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void setUserInfo(TextView emailText, TextView  userNameTextView, ImageView image){
        emailText.setText(ParseUser.getCurrentUser().getEmail());
        userNameTextView.setText(ParseUser.getCurrentUser().getUsername());
        image.setVisibility(View.VISIBLE);
    }

    static public void deleteUserInfo(TextView emailText, TextView  userNameTextView,
                                       ImageView image){
        emailText.setText("");
        userNameTextView.setText("");
        image.setVisibility(View.INVISIBLE);
    }


}
