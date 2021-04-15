package example.tukcu.tukcu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tukcu.tukcu.R;

public class NavigationHeader extends Fragment {
    private TextView userName,userEmail;
    private FirebaseAuth mAuth;
    private FirebaseUser onlineUser;
    private  String onlineUserID;
    private DatabaseReference memberRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView =  inflater.inflate(R.layout.nav_header,container,false);

        userName = RootView.findViewById(R.id.nav_header_user_name);
        userEmail = RootView.findViewById(R.id.nav_header_user_email);
        mAuth = FirebaseAuth.getInstance();
        onlineUser = mAuth.getCurrentUser();
        onlineUserID = onlineUser.getUid();
        memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child(onlineUserID);

        retrieveUserInformation();

        return RootView;
    }

    private void retrieveUserInformation() {
        memberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("First Name"))){
                     String retrieveFrstName = dataSnapshot.child("First Name").getValue().toString();
                     String retrieveSurname = dataSnapshot.child("Surname").getValue().toString();
                    String retrieveUserPhoneNumber = dataSnapshot.child("Phone Number").getValue().toString();

                      userName.setText(retrieveFrstName + "  " + retrieveSurname );
                      userEmail.setText(retrieveUserPhoneNumber);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
