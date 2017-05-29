package xzh.com.materialdesign.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;

public class ContactActivity extends AppCompatActivity {
    @InjectView(R.id.contactBack)
    ImageButton navBack;

    @InjectView(R.id.contact_cancel)
    ImageButton cancel;


    protected void onCreate(Bundle savedInstanceState) {
        Log.v("dz","ContactActivity onCreate");
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        setContentView(R.layout.activity_contact_us);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
