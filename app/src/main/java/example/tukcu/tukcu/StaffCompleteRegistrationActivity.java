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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tukcu.tukcu.R;

import java.util.HashMap;

public class StaffCompleteRegistrationActivity extends AppCompatActivity {

    private EditText title,sName, occupation,phone;
    private Button staffCompleRegBtn, back;
    private Toolbar toolbarstaff;
    private FirebaseAuth mAuth;
    private DatabaseReference staffRef;
    private FirebaseUser onlineStaff;
    private String onlineUserID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_staff_complete_registration);

        //casting views
        title = findViewById(R.id.staffFirstName);
        sName = findViewById(R.id.staffSurName);
        occupation = findViewById(R.id.staffOccupation);
        phone = findViewById(R.id.staffPhoneNumber);
        staffCompleRegBtn = findViewById(R.id.staffCompleteRegistrationButton);
        back = findViewById(R.id.staffCompleteRegistrationBackButton);
        mAuth = FirebaseAuth.getInstance();
        onlineStaff = mAuth.getCurrentUser();
        onlineUserID = onlineStaff.getUid();
        staffRef = FirebaseDatabase.getInstance().getReference().child("Staff").child(onlineUserID);
        progressDialog = new ProgressDialog(StaffCompleteRegistrationActivity.this);

        //toolbar
        toolbarstaff = findViewById(R.id.toolbarstaff);
        setSupportActionBar(toolbarstaff);
        getSupportActionBar().setTitle("TUK CU Staff Registration");

        //setting onclick listener.
        staffCompleRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String staffTitle = title.getText().toString().trim();
                final String surname = sName.getText().toString().trim();
                String staffOccupation = occupation.getText().toString().trim();
                String staffPhoneNumber = phone.getText().toString().trim();

                if (TextUtils.isEmpty(staffTitle)){
                    title.setError("Your title is required");
                    return;
                }
                if (TextUtils.isEmpty(surname)){
                    sName.setError("Your Surname is required");
                    return;
                }
                if (TextUtils.isEmpty(staffOccupation)){
                    occupation.setError("Your occupation is required");
                    return;
                }
                if (TextUtils.isEmpty(staffPhoneNumber)){
                    phone.setError("Your phone number is required.");
                }
                else {
                    progressDialog.setTitle("Registering..");
                    progressDialog.setMessage("please wait as we register you...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("First Name", staffTitle);
                    dataMap.put("Surname", surname);
                    dataMap.put("Occupation", staffOccupation);
                    dataMap.put("Phone number", staffPhoneNumber);
                    dataMap.put("ID", onlineUserID);

                    staffRef.setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(StaffCompleteRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent registrationIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                registrationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(registrationIntent);
                                Toast.makeText(StaffCompleteRegistrationActivity.this, "Welcome "+staffTitle + "  "+ surname, Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(StaffCompleteRegistrationActivity.this, "Registration Failed, please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffCompleteRegistrationActivity.this, StaffRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
