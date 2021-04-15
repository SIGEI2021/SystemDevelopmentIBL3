package example.tukcu.tukcu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tukcu.tukcu.R;

public class AssociateRegistrationActivity extends AppCompatActivity {

    private EditText associateEmail,associatePass;
    private Button proceed, back;
    private Toolbar toolbarassociateReg;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_registration);

        //casting views
        associateEmail = findViewById(R.id.associateRegistrationEmail);
        associatePass = findViewById(R.id.associateRegistrationPassword);
        proceed = findViewById(R.id.associateRegistrationButton);
        back = findViewById(R.id.associateRegistrationBackButton);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(AssociateRegistrationActivity.this);

        //toolbar
        toolbarassociateReg = findViewById(R.id.toolbarassociateReg);
        setSupportActionBar(toolbarassociateReg);
        getSupportActionBar().setTitle("TUK CU Registration");

        //setting onclick listeners
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = associateEmail.getText().toString().trim();
                String password = associatePass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    associateEmail.setError("Your email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    associatePass.setError("Your password is required");
                    return;
                }
                else {
                    dialog.setTitle("Recording your details...");
                    dialog.setMessage("Please wait as we record your details...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){
                              Toast.makeText(AssociateRegistrationActivity.this, "Your details have been recorded successfully", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(AssociateRegistrationActivity.this, AssociateCompleteRegistrationActivity.class);
                              startActivity(intent);
                              finish();
                              dialog.dismiss();
                          }
                          else {
                              Toast.makeText(AssociateRegistrationActivity.this, "Failed to record your details,check your internet connection and try again.", Toast.LENGTH_LONG).show();
                              dialog.dismiss();
                          }
                        }
                    });

            }
          }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssociateRegistrationActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
