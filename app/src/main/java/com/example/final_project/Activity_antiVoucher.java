package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_antiVoucher extends AppCompatActivity {
//edit
    Button ButtonHome;
    Button ButtonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_voucher);



        ButtonReset = findViewById(R.id.button_reset);
        ButtonHome = findViewById(R.id.backToHome);

        ButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        ButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myprefs", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("antivoucher");
                editor.remove("thinnervoucher");  
                editor.commit();


            }
        });

    }
}