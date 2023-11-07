package kr.kjy.janban;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;


public class UserProfileActivity extends AppCompatActivity {
    private TextView tv_id;
    private TextView tv_name;
    private TextView tv_UID;
    private TextView tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.user_profile);


        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        tv_name = findViewById(R.id.tv_name);
        tv_UID = findViewById(R.id.tv_UID);


        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userUID = intent.getStringExtra("userUID");


        tv_id.setText(userID);
        tv_pass.setText(userPass);
        tv_name.setText(userName);
        tv_UID.setText(userUID);

    }
}