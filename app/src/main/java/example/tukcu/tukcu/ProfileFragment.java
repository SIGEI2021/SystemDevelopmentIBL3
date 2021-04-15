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

public class ProfileFragment extends Fragment {

    private TextView userNames, userSubcomm, userCellGROUP,userPhoneNumber;
    private FirebaseAuth mAuth;
    private FirebaseUser onlineUser;
    private  String onlineUserID;
    private DatabaseReference memberRef, staffRef, associateRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView =  inflater.inflate(R.layout.fragment_profile, container,false);


        userNames = RootView.findViewById(R.id.userNames);
        userSubcomm = RootView.findViewById(R.id.userSubcom);
        userCellGROUP = RootView.findViewById(R.id.userCellGROUP);
        userPhoneNumber = RootView.findViewById(R.id.userPhoneNumber);
        mAuth = FirebaseAuth.getInstance();
        onlineUser = mAuth.getCurrentUser();
        onlineUserID = onlineUser.getUid();
        memberRef = FirebaseDatabase.getInstance().getReference().child("Members").child(onlineUserID);
        staffRef = FirebaseDatabase.getInstance().getReference().child("Associates").child(onlineUserID);
        associateRef = FirebaseDatabase.getInstance().getReference().child("Staff").child(onlineUserID);

        retrieveMembersInformation();


        return RootView;

    }

    private void retrieveMembersInformation() {
        memberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("First Name"))){
                    String retrieveFrstName = dataSnapshot.child("First Name").getValue().toString();
                    String retrieveSurname = dataSnapshot.child("Surname").getValue().toString();
                    String retrieveSubCom = dataSnapshot.child("Subcom").getValue().toString();
                    String retrieveUserCellGroup = dataSnapshot.child("Cell Group").getValue().toString();
                    String retrieveUserPhoneNumber = dataSnapshot.child("Phone Number").getValue().toString();

                    userNames.setText(retrieveFrstName + "  " + retrieveSurname );
                    userSubcomm.setText("Subcom: " + retrieveSubCom);
                    userCellGROUP.setText("Cell Group: " +retrieveUserCellGroup);
                    userPhoneNumber.setText("Phone Number: " +retrieveUserPhoneNumber );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
