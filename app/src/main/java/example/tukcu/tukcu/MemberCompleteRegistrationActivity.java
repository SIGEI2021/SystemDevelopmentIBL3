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

public class MemberCompleteRegistrationActivity extends AppCompatActivity {

    private EditText fName, sName,gender,  course,yearOfStudy, subcom,cellgroup,phone;
    private Button completeRegButton,back;
    private Toolbar toolbarmember;
    private FirebaseAuth mAuth;
    private DatabaseReference memberRef;
    private FirebaseUser onlineMember;
    private String onlineUserID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_member_complete_registration);

        //casting views
        fName = findViewById(R.id.memberFirstName);
        sName = findViewById(R.id.memberSurName);
        gender = findViewById(R.id.memberGender);
        course = findViewById(R.id.memberCourse);
        yearOfStudy = findViewById(R.id.memberYearOfStudy);
        subcom = findViewById(R.id.memberSubCom);
        cellgroup = findViewById(R.id.memberCellGroup);
        phone = findViewById(R.id.memberPhoneNumber);
        completeRegButton = findViewById(R.id.memberCompleteRegistrationButton);
        back = findViewById(R.id.memberCompleteRegistrationBackButton);
        mAuth = FirebaseAuth.getInstance();
       onlineMember = mAuth.getCurrentUser();
       onlineUserID = onlineMember.getUid();
        memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child(onlineUserID);
        progressDialog = new ProgressDialog(MemberCompleteRegistrationActivity.this);


        //toolbar
        toolbarmember = findViewById(R.id.toolbarmember);
        setSupportActionBar(toolbarmember);
        getSupportActionBar().setTitle("TUK CU Member Registration");

        // setting onclick listeners
        completeRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memberfname = fName.getText().toString().trim();
                final String membersname = sName.getText().toString().trim();
                String memberGender = gender.getText().toString().trim();
                String membercourse = course.getText().toString().trim();
                String memberYOS = yearOfStudy.getText().toString().trim();
                String memberSubcom = subcom.getText().toString().trim();
                String memberCellgroup = cellgroup.getText().toString().trim();
                String memberphone = phone.getText().toString().trim();

                if (TextUtils.isEmpty(memberfname)){
                    fName.setError("Your first name is required");
                    return;
                }
                if (TextUtils.isEmpty(membersname)){
                    sName.setError("Your Surname is required");
                }
                if (TextUtils.isEmpty(memberGender)){
                    gender.setError("Your Gender is required");
                    return;
                }
                if (TextUtils.isEmpty(membercourse)){
                    course.setError("Your course is required");
                    return;
                }
                if (TextUtils.isEmpty(memberYOS)){
                    yearOfStudy.setError("Your year of study is required");
                    return;
                }
                if (TextUtils.isEmpty(memberphone)){
                    phone.setError("Your phone number is required");
                }
                else {
                    progressDialog.setTitle("Registering..");
                    progressDialog.setMessage("please wait as we register you...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("First Name", memberfname);
                    dataMap.put("Surname", membersname);
                    dataMap.put("Gender", memberGender);
                    dataMap.put("Course", membercourse);
                    dataMap.put("Year of Study", memberYOS);
                    dataMap.put("Subcom", memberSubcom);
                    dataMap.put("Cell Group", memberCellgroup);
                    dataMap.put("Phone Number", memberphone);
                    dataMap.put("ID", onlineUserID);

                    memberRef.setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MemberCompleteRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent registrationIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                registrationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(registrationIntent);
                                Toast.makeText(MemberCompleteRegistrationActivity.this, "Welcome "+memberfname +"  "+ membersname, Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(MemberCompleteRegistrationActivity.this, "Registration Failed, please check your internet connection and try again" , Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(MemberCompleteRegistrationActivity.this, MemberRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
