package kr.kjy.janban;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.Username); // Replace with the actual IDs in your XML layout
        passwordEditText = findViewById(R.id.Password);
        loginButton = findViewById(R.id.login_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidCredentials(username, password)) {
                    // Perform login action (e.g., navigate to the main activity)
                    navigateToMainActivity();
                } else {
                    // Display an error message
                    showToast("Invalid username or password");
                }
            }
        });

        // Initialize the Sign Up button and set a click listener
        signUpButton = findViewById(R.id.SignUp_btn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUpActivity();
            }
        });
    }

    // You can implement your own login validation logic here
    private boolean isValidCredentials(String username, String password) {
        // Replace this with your own authentication logic (e.g., calling a web service)
        // For this example, we'll consider "admin" as a valid username and "password" as a valid password.
        return username.equals("admin") && password.equals("password");
    }

    private void navigateToMainActivity() {
        // Replace this with the code to start your main activity
        // For example, you can use an Intent to navigate to the main activity.
        // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // startActivity(intent);
    }

    private void navigateToSignUpActivity() {
        // Start the Sign Up activity
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
