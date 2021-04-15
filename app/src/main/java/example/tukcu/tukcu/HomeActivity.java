package example.tukcu.tukcu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tukcu.tukcu.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    private TextView mnavHeaderName,mNavHeaderEmail;

    private FirebaseAuth mAuth;
    private FirebaseUser onlineUser;
    private  String onlineUserID;
    private DatabaseReference memberRef;

    private boolean doubleBackToExitPressedOnce;

    private String type = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity( intent);
                finish();
                return true;

                default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TUK Christian Union");

        drawerLayout = findViewById(R.id.drawer_layoutZ);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState ==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutTUKCUFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_aboutTukCu);
        }

        mnavHeaderName =navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_name);
        mNavHeaderEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_email);
        mAuth = FirebaseAuth.getInstance();
        onlineUser = mAuth.getCurrentUser();
        onlineUserID = onlineUser.getUid();

        memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child(onlineUserID);




        String userEmail = onlineUser.getEmail();
        mNavHeaderEmail.setText(userEmail);
        getMemberInformation();

    }

    private void getMemberInformation(){
        memberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChild("First Name")){
                    String firstName = dataSnapshot.child("First Name").getValue().toString();
                    String surName = dataSnapshot.child("Surname").getValue().toString();
                    mnavHeaderName.setText(firstName + " " + surName);
                }

                else {
                    Toast.makeText(HomeActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nav_aboutTukCu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutTUKCUFragment()).commit();
               getSupportActionBar().setTitle("About TUK CU");

                break;
            case R.id.nav_leaders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LeadersFragment()).commit();
               getSupportActionBar().setTitle("TUK CU Leaders");
                break;

            case R.id.nav_subCom:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubcomsFragment()).commit();
               getSupportActionBar().setTitle("TUK CU Sub committees");
                break;

            case R.id.nav_BSCellGroups:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BSCellGroupsFragment()).commit();
               getSupportActionBar().setTitle("TUK CU BS Cell Groups");
                break;

            case R.id.nav_newsEvents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsEventsFragment()).commit();
               getSupportActionBar().setTitle("TUK CU News & Events");
                break;

            case R.id.nav_speakersAndTopics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpeakersAndTopicsFragment()).commit();
                getSupportActionBar().setTitle("Suggest Speaker/Topic");
                break;

            case R.id.nav_documents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DocumentsFragment()).commit();
               getSupportActionBar().setTitle("TUK CU constitution");
                break;

            case R.id.nav_profile:
                ProfileFragment details = new ProfileFragment();
                details.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
               getSupportActionBar().setTitle("My TUK CU Profile");
                break;

            case R.id.nav_contacts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactsFragment()).commit();
              getSupportActionBar().setTitle("TUK CU Contacts");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back  again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }
}
