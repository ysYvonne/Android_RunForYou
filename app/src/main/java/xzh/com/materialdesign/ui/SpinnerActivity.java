package xzh.com.materialdesign.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import xzh.com.materialdesign.R;

/**
 * Created by Flora on 2017/4/21.
 */

public class SpinnerActivity extends AppCompatActivity {

    private Spinner spinner;
    private List<String> data;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_change);

        spinner = (Spinner)findViewById(R.id.school_spinner);

        data = new ArrayList<String>();
        data.add("北京交通大学");
        data.add("清华大学");
        data.add("北京理工大学");
        data.add("北京大学");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }
}
