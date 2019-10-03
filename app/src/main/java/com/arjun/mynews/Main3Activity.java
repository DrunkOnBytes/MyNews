package com.arjun.mynews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main3Activity extends Activity {

    String link;

    public void showWeb(View view){

        Intent intent=new Intent(getApplicationContext(), Main2Activity.class);
        intent.putExtra("URL", link);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TextView text=(TextView)findViewById(R.id.summary);

        Intent intent=getIntent();
        String summ=intent.getStringExtra("SUMMARY");
        link=intent.getStringExtra("LINK");
        text.setText(summ);

    }
}
