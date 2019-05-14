package diploma.storytime.stolbysassistant.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.parse.ParseUser;

//import butterknife.ButterKnife;
//import butterknife.BindView;
import diploma.storytime.stolbysassistant.R;
import diploma.storytime.stolbysassistant.utils.FragmentChanger;
import diploma.storytime.stolbysassistant.utils.UserChanger;
import diploma.storytime.stolbysassistant.views.MainActivity;

import static android.app.Activity.RESULT_OK;

public class SignupFragment extends Fragment {
    private static final String TAG = "SignupActivity";
    private MainActivity activity;

    //@BindView(R.id.input_name)
    EditText nameText;

    //@BindView(R.id.input_email)
    EditText emailText;

    //@BindView(R.id.input_password)
    EditText passwordText;

    //@BindView(R.id.btn_signup)
    Button signupButton;

    //@BindView(R.id.link_login)
    TextView loginLink;

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

        //ButterKnife.bind(activity);
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

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        UserChanger.signupUser(name,password,email,activity);
        new android.os.Handler().postDelayed(
                () -> {
                    // On complete call either onSignupSuccess or onSignupFailed
                    // depending on success
                    onSignupSuccess();
                    // onSignupFailed();
                    progressDialog.dismiss();
                }, 3000);
    }


    private void onSignupSuccess() {
        signupButton.setEnabled(true);
        activity.setResult(RESULT_OK, null);
        //activity.finish();
    }

    private void onSignupFailed() {
        Toast.makeText(activity.getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

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
