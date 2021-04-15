package example.tukcu.tukcu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tukcu.tukcu.R;

import java.util.HashMap;

public class SpeakersAndTopicsFragment extends Fragment {
    private EditText speakerName, speakerChurch,speakerPhoneNumber,theTopic;
    private Button submit;
    private FirebaseAuth mAuth;
    private DatabaseReference speakersAndTopicsRef;
    private FirebaseUser onlineCuMember;
    private String onlineUserID;
    private ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.speakers_and_topics_fragment, container,false);

        speakerName = RootView.findViewById(R.id.speakerName);
        speakerChurch = RootView.findViewById(R.id.speakerChurch);
        speakerPhoneNumber = RootView.findViewById(R.id.speakerPhoneNumber);
        theTopic = RootView.findViewById(R.id.suggestedTopic);
        submit = RootView.findViewById(R.id.submit);


        mAuth = FirebaseAuth.getInstance();
        onlineCuMember = mAuth.getCurrentUser();
        onlineUserID = onlineCuMember.getUid();
        speakersAndTopicsRef = FirebaseDatabase.getInstance().getReference().child("Speakers and Topics");
        progressDialog = new ProgressDialog(getContext());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = speakerName.getText().toString().trim();
                String church = speakerChurch.getText().toString().trim();
                String phone = speakerPhoneNumber.getText().toString().trim();
                String topic = theTopic.getText().toString().trim();

                if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(church)) && (!TextUtils.isEmpty(phone)) && (!TextUtils.isEmpty(topic))){

                    progressDialog.setTitle("Recording..");
                    progressDialog.setMessage("please wait as we record all your suggestions...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("Speaker Name", name);
                    dataMap.put("Church or Ministry", church);
                    dataMap.put("Phone Number", phone);
                    dataMap.put("Topic", topic);

                    speakersAndTopicsRef.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "All Your suggestions have been recorded successfully. Thank you!", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                                speakerName.setText("");
                                speakerChurch.setText("");
                                speakerPhoneNumber.setText("");
                                theTopic.setText("");
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(getContext(), "Failed to record details" + error, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }

                if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(church)) && (!TextUtils.isEmpty(phone)) && (TextUtils.isEmpty(topic))){

                    progressDialog.setTitle("Recording..");
                    progressDialog.setMessage("please wait as we record your suggestions...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("Speaker Name", name);
                    dataMap.put("Church or Ministry", church);
                    dataMap.put("Phone Number", phone);

                    speakersAndTopicsRef.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "Your suggestions have been recorded successfully. Thank you!", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                speakerName.setText("");
                                speakerChurch.setText("");
                                speakerPhoneNumber.setText("");

                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(getContext(), "Failed to record details" + error, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }

                if (!(TextUtils.isEmpty(name)) && TextUtils.isEmpty(church) && TextUtils.isEmpty(phone)){
                    speakerChurch.setError("Required!");
                    speakerPhoneNumber.setError("Required!");
                    Toast.makeText(getContext(), "Please enter speaker's church or ministry and his/her phone number", Toast.LENGTH_LONG).show();

                }

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(church) && TextUtils.isEmpty(phone) && (!TextUtils.isEmpty(topic))){

                    progressDialog.setTitle("Recording..");
                    progressDialog.setMessage("please wait as we record your topic suggestion(s)...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    HashMap<String, String> Map = new HashMap<>();
                    Map.put("Topic", topic);
                    speakersAndTopicsRef.push().setValue(Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "Your topic suggestion has been recorded. Thank you!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                theTopic.setText("");
                            }else {
                                String topicError = task.getException().toString();
                                Toast.makeText(getContext(), "Failed to record topic" + topicError, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(church) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(topic)){

                    speakerName.setError("speaker's name is required");
                    speakerChurch.setError("speaker's church or ministry is required");
                    speakerPhoneNumber.setError("speaker's phone no. is required");
                    theTopic.setError("topic is required");
                    Toast.makeText(getContext(), "Please fill the details above.", Toast.LENGTH_LONG).show();
                }


            }



        });


        return RootView;
    }
}
