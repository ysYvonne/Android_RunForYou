package xzh.com.materialdesign.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import xzh.com.materialdesign.R;

public class ContactActivity extends AppCompatActivity {
    ImageButton navBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        navBack=(ImageButton)findViewById(R.id.contactBack);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
