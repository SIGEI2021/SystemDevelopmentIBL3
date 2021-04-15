package example.tukcu.tukcu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tukcu.tukcu.R;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPass;
    private Button login;
    private TextView question,forgotPassword;
    private Toolbar toolbarLogin;
    private TextView textViewLoginToolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //casting the views
        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        question = findViewById(R.id.dontHaveAnAccount);
        forgotPassword = findViewById(R.id.forgotPassword);
        textViewLoginToolbar = findViewById(R.id.textViewLoginToolbar);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        //toolbar
        toolbarLogin = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbarLogin);
        getSupportActionBar().setTitle("TUK CU Login");

        //setting Onclick listeners
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Your email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    loginEmail.setError("Your password is required");
                    return;
                }
                else {
                    dialog.setTitle("log in.. ");
                    dialog.setMessage("please wait as we log you in...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String userEmail = mAuth.getCurrentUser().getEmail();
                                Toast.makeText(LoginActivity.this, "Logged in successfully as " +userEmail, Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            }else {
                                String e  = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "login failed! please check your internet connection or your credentials and try again  " + e, Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                        }
                    });
            }
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        textViewLoginToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
