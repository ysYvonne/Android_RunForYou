package xzh.com.materialdesign.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.materialdesign.R;


/**
 * Created by dz on 2017/5/10.
 */

public class ReceiveDetailActivity extends AppCompatActivity {
    ImageButton navBack;
    private static final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_transport_detail);
        navBack=(ImageButton)findViewById(R.id.nav_back);
        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton modify = (ImageButton) findViewById(R.id.order_transport_detail_change);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {

        RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .session(3)
                .threshold(3)
                .ratingBarColor(R.color.base_color_text_black)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {
                        Log.i(TAG,"Feedback:" + feedback);
                    }
                })
                .build();


        ratingDialog.show();
    }
}
