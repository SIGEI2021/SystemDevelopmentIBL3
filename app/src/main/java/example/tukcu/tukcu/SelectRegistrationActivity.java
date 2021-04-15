package example.tukcu.tukcu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tukcu.tukcu.R;

public class SelectRegistrationActivity extends AppCompatActivity {

    private Button member,associate,staff;
    private TextView question;
    private Toolbar toolbarselect;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_registration);

        //typecasting the Views
        member = findViewById(R.id.member_btn);
        associate = findViewById(R.id.associate_btn);
        staff = findViewById(R.id.staff_btn);
        question = findViewById(R.id.alreadyHaveAnAccount);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent HomeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            HomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(HomeIntent);
            finish();
            Toast.makeText(SelectRegistrationActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();

        }

        //toolbar
        toolbarselect = findViewById(R.id.toolbarselect);
        setSupportActionBar(toolbarselect);
        getSupportActionBar().setTitle("TUK Christian Union");

        //setting onClick listeners on the buttons

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegistrationActivity.this, MemberRegistrationActivity.class);
                startActivity(intent);
            }
        });

        associate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegistrationActivity.this, AssociateRegistrationActivity.class);
                startActivity(intent);
            }
        });

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegistrationActivity.this, StaffRegistrationActivity.class);
                startActivity(intent);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
