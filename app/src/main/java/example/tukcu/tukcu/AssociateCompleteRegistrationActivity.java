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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tukcu.tukcu.R;

import java.util.HashMap;

public class AssociateCompleteRegistrationActivity extends AppCompatActivity {

    private EditText fName,sName,gender,course,yearOfCompletion,phone;
    private Button ascompleteRegButton, back;
    private Toolbar toolbarAssociate;
    private FirebaseAuth mAuth;
    private DatabaseReference associateRef;
    private FirebaseUser onlineAssociate;
    private String onlineUserID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_complete_registration);

        //casting views
        fName = findViewById(R.id.associateFirstName);
        sName = findViewById(R.id.associateSurName);
        gender = findViewById(R.id.associateGender);
        course = findViewById(R.id.associateCourse);
        yearOfCompletion = findViewById(R.id.associateYearOfCompletion);
        phone = findViewById(R.id.associatePhoneNumber);
        ascompleteRegButton = findViewById(R.id.associateCompleteRegistrationButton);
        back = findViewById(R.id.associateCompleteRegistrationBackButton);
        mAuth = FirebaseAuth.getInstance();
        onlineAssociate = mAuth.getCurrentUser();
        onlineUserID = onlineAssociate.getUid();
        associateRef = FirebaseDatabase.getInstance().getReference().child("Associates").child(onlineUserID);
        progressDialog = new ProgressDialog(AssociateCompleteRegistrationActivity.this);

        //toolbar
        toolbarAssociate = findViewById(R.id.toolbarAssociate);
        setSupportActionBar(toolbarAssociate);
        getSupportActionBar().setTitle("TUK CU associate Registration");

        //setting onclick listeners
        ascompleteRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstName = fName.getText().toString().trim();
                final String secondName = sName.getText().toString().trim();
                String Gender = gender.getText().toString().trim();
                String Course = course.getText().toString().trim();
                String AssociateYOC = yearOfCompletion.getText().toString().trim();
                String phoneNumber = phone.getText().toString();

                if (TextUtils.isEmpty(firstName)){
                    fName.setError("Your First Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(secondName)){
                    sName.setError("Your Surname is required.");
                    return;
                }
                if (TextUtils.isEmpty(Gender)){
                    gender.setError("Your Gender is required.");
                    return;
                }
                if (TextUtils.isEmpty(Course)){
                    course.setError("The course you did is required.");
                    return;
                }
                if (TextUtils.isEmpty(AssociateYOC)){
                    yearOfCompletion.setError("Please enter your year of completion");
                }
                if (TextUtils.isEmpty(phoneNumber)){
                    phone.setError("Your phone number is required");
                    return;
                }
                else {
                    progressDialog.setTitle("Registering..");
                    progressDialog.setMessage("please wait as we register you...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("First Name", firstName);
                    dataMap.put("Surname", secondName);
                    dataMap.put("Gender", Gender);
                    dataMap.put("Course", Course);
                    dataMap.put("Year of Completion", AssociateYOC);
                    dataMap.put("Phone Number", phoneNumber);
                    dataMap.put("ID", onlineUserID);

                    associateRef.setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AssociateCompleteRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent registrationIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                registrationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(registrationIntent);
                                Toast.makeText(AssociateCompleteRegistrationActivity.this, "Welcome "+firstName +"  "+ secondName, Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(AssociateCompleteRegistrationActivity.this, "Registration Failed, Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(AssociateCompleteRegistrationActivity.this, AssociateRegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
