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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tukcu.tukcu.R;

public class StaffRegistrationActivity extends AppCompatActivity {

    private EditText staffEmail, staffPass;
    private Button  proceed, back;
    private Toolbar toolbarstaffReg;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_staff_registration);

        //casting the views
        staffEmail = findViewById(R.id.staffRegistrationEmail);
        staffPass = findViewById(R.id.staffRegistrationPassword);
        proceed = findViewById(R.id.staffRegistrationButton);
        back = findViewById(R.id.staffRegistrationBackButton);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(StaffRegistrationActivity.this);

        //toolbar
        toolbarstaffReg = findViewById(R.id.toolbarstaffReg);
        setSupportActionBar(toolbarstaffReg);
        getSupportActionBar().setTitle("TUK CU Registration");

        //setting onclick listeners
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = staffEmail.getText().toString().trim();
                String password = staffPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    staffEmail.setError("Your email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    staffPass.setError("Your password is required.");
                    return;
                }
                else {
                    dialog.setTitle("Recording your details");
                    dialog.setMessage("Please wait as we record your details");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(StaffRegistrationActivity.this, "Your details have been recorded successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StaffRegistrationActivity.this, StaffCompleteRegistrationActivity.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(StaffRegistrationActivity.this, "Failed to record your details, please check your internet connection and try again.", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(StaffRegistrationActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
