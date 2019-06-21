package diploma.storytime.stolbysassistant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.simple.JSONObject;

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.HTTPRequest;
import diploma.storytime.stolbysassistant.utils.PasswordUtils;
import diploma.storytime.stolbysassistant.utils.User;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class LoginFragment extends Fragment {
    private MainActivity activity;

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_login, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        emailText = activity.findViewById(R.id.input_email);
        passwordText = activity.findViewById(R.id.input_password);
        loginButton = activity.findViewById(R.id.btn_login);
        signupLink = activity.findViewById(R.id.link_signup);

        loginButton.setOnClickListener(v -> login());

        signupLink.setOnClickListener(v -> {
            FragmentChanger.changeFragment(new SignupFragment(), activity);
        });
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        JSONObject respond = HTTPRequest.login(email);

        if (respond != null) {
            String emailString = (String) respond.get("email");
            String nameString = (String) respond.get("name");
            String friends = (String) respond.get("count");
            String salt = (String) respond.get("salt");
            String encryptedPassword = (String) respond.get("password");
            String friendKey = (String) respond.get("friendKey");
            if (PasswordUtils.verifyUserPassword(password, encryptedPassword, salt)) {
                activity.setUser(new User(nameString, emailString, Integer.parseInt(friends), friendKey));
                activity.setUserInfo();
                activity.setMenuState(false);
                activity.invalidateOptionsMenu();
                FragmentChanger.changeFragment(new MainFragment(), activity);
                Toast.makeText(activity, R.string.success_login, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, R.string.failed_login, Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(activity, R.string.failed_login, Toast.LENGTH_LONG).show();
        }
    }

    private void onLoginFailed() {
        Toast.makeText(activity.getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getString(R.string.invalid_email));
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError(getString(R.string.between_characters));
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}