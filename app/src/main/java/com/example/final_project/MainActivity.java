package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static Button voucherAnti;
    public static Button voucherThinner;


    Button buttonAnti;
    Button buttonThinner;


    public boolean flag = false;
    public static final String MyPreferences = "myprefs";
    public static final String value = "key";

    //---------------------------------------------------------------------------------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonThinner = findViewById(R.id.thinner_survey);
        buttonAnti = findViewById(R.id.buttonAnti);

        voucherThinner = findViewById(R.id.thinner_voucher);
        voucherAnti = findViewById(R.id.voucher_button);



        //---------------------------------------------------------------------------------//


        buttonAnti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_antiSurvey.class));
            }
        });

        buttonThinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_thinnerSurvey.class));
            }
        });

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myprefs", 0);
        boolean antivoucher = prefs.getBoolean("antivoucher", false);
        if(antivoucher) {
            voucherAnti.setVisibility(View.VISIBLE);
        }else{
            voucherAnti.setVisibility(View.INVISIBLE);
    }

        boolean thinnervoucher = prefs.getBoolean("thinnervoucher", false);
        if(thinnervoucher) {
            voucherThinner.setVisibility(View.VISIBLE);
        }else{
            voucherThinner.setVisibility(View.INVISIBLE);
        }



        //---------------------------------------------------------------------------------//

        voucherAnti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_antiVoucher.class));
            }
        });

        voucherThinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Activity_thinnerVoucher.class));
            }
        });
    }
}

