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

public class MemberRegistrationActivity extends AppCompatActivity {

    private EditText memberEmail,memberPass;
    private Button proceed, back;
    private Toolbar toolbarmembreg;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_member_registration);

        //casting views
        memberEmail = findViewById(R.id.memberRegistrationEmail);
        memberPass = findViewById(R.id.memberRegistrationPassword);
        proceed = findViewById(R.id.memberRegistrationButton);
        back = findViewById(R.id.memberRegistrationBackButton);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(MemberRegistrationActivity.this);

        //toolbar
        toolbarmembreg = findViewById(R.id.toolbarmembreg);
        setSupportActionBar(toolbarmembreg);
        getSupportActionBar().setTitle("TUK CU Registration");

        //setting onclick listeners
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String email = memberEmail.getText().toString().trim();
               String password = memberPass.getText().toString().trim();

               if (TextUtils.isEmpty(email)){
                   memberEmail.setError("Your email is required.");
                   return;
               }if (TextUtils.isEmpty(password)){
                   memberPass.setError("Your password is required.");
                   return;
                }
               else {
                   dialog.setTitle("Recording Details...");
                   dialog.setMessage("Please wait as your details get recorded...");
                   dialog.setCanceledOnTouchOutside(false);
                   dialog.show();

                   mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){
                              Toast.makeText(MemberRegistrationActivity.this, "Your details have been recorded successfully", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(MemberRegistrationActivity.this, MemberCompleteRegistrationActivity.class);
                              startActivity(intent);
                              finish();
                              dialog.dismiss();
                          }else {
                              Toast.makeText(MemberRegistrationActivity.this, "Failed to record your data, please check your internet connection and try again." , Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(MemberRegistrationActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
