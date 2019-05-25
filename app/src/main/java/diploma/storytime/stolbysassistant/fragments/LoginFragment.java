package diploma.storytime.stolbysassistant.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import diploma.storytime.stolbysassistant.views.MainActivity;

import static android.app.Activity.RESULT_OK;

//import diploma.storytime.stolbysassistant.utils.UserChanger;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class LoginFragment extends Fragment {
    private MainActivity activity;
    private static final int REQUEST_SIGNUP = 0;

    //@BindView(R.id.input_email)
    EditText emailText;

    //@BindView(R.id.input_password)
    EditText passwordText;

    //@BindView(R.id.btn_login)
    Button loginButton;

    //@BindView(R.id.link_signup)
    TextView signupLink;


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
        //ButterKnife.bind(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        emailText = activity.findViewById(R.id.input_email);
        passwordText = activity.findViewById(R.id.input_password);
        loginButton = activity.findViewById(R.id.btn_login);
        signupLink = activity.findViewById(R.id.link_signup);

        loginButton.setOnClickListener(v -> login());

        signupLink.setOnClickListener(v -> {
            // Start the Signup activity
            FragmentChanger.changeFragment(new SignupFragment(), activity);
        });
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        new android.os.Handler().postDelayed(
                () -> {
                    // On complete call either onLoginSuccess or onLoginFailed
                    onLoginSuccess();
                    // onLoginFailed();
                    progressDialog.dismiss();
                }, 3000);
        HTTPRequest.login(activity, email, password);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                // activity.finish();
            }
        }
    }


    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        //activity.finish();
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
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}