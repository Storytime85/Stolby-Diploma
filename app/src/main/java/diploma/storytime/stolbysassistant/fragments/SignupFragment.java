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

import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.HTTPRequest;
import diploma.storytime.stolbysassistant.utils.PasswordUtils;
import diploma.storytime.stolbysassistant.views.MainActivity;

public class SignupFragment extends Fragment {
    private MainActivity activity;

    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;
    private Button signupButton;
    private TextView loginLink;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_signup, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        nameText = activity.findViewById(R.id.input_name);
        emailText = activity.findViewById(R.id.input_email);
        passwordText = activity.findViewById(R.id.input_password);
        signupButton = activity.findViewById(R.id.btn_signup);
        loginLink = activity.findViewById(R.id.link_login);

        signupButton.setOnClickListener(v -> signup());

        loginLink.setOnClickListener(v -> {
            FragmentChanger.changeFragment(new LoginFragment(),activity);
        });
    }

    private void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        String salt = PasswordUtils.getSalt(30);
        String securedPassword = PasswordUtils.generateSecurePassword(password, salt);
        boolean respond = HTTPRequest.signup(name, email, securedPassword, salt);
        if (respond) {
            Toast.makeText(activity, R.string.success_registration, Toast.LENGTH_LONG).show();
            FragmentChanger.changeFragment(new LoginFragment(), activity);
        } else {
            Toast.makeText(activity, R.string.failed_registration, Toast.LENGTH_LONG).show();
        }
    }

    private void onSignupFailed() {
        Toast.makeText(activity.getBaseContext(), R.string.signup_failed, Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError(getString(R.string.at_least));
            valid = false;
        } else {
            nameText.setError(null);
        }

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
