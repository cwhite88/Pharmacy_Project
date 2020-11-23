package com.example.final_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Activity_thinnerSurvey extends AppCompatActivity {

    Button goBackButton;
    Button onSubmit;
    ProgressDialog progressDialog;
    EditText text_thinner, text_medication, text_doses, text_BorB, text_pain, text_emergency, text_questions;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thinner_survey);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        onSubmit = findViewById(R.id.onSubmit);
        goBackButton = findViewById(R.id.goBackButton);
        text_thinner = findViewById(R.id.text_thinner); // first
        text_medication = findViewById(R.id.text_medication);     // second
        text_doses = findViewById(R.id.text_doses);     // third
        text_BorB = findViewById(R.id.text_BorB); // fourth
        text_pain = findViewById(R.id.text_pain);         // fifth
        text_emergency = findViewById(R.id.text_emergency);       // sixth
        text_questions = findViewById(R.id.text_questions);       // seventh

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        queue = Volley.newRequestQueue(getApplicationContext());

        onSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //---------------------------------------------------------------------------------//


                SharedPreferences pref = getApplicationContext().getSharedPreferences("myprefs", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("thinnervoucher", true);
                editor.commit();

                //---------------------------------------------------------------------------------//


                if (text_thinner.getText().toString().trim().length() > 0 &&
                        text_medication.getText().toString().trim().length() > 0 &&
                        text_doses.getText().toString().trim().length() > 0 &&
                        text_BorB.getText().toString().trim().length() > 0 &&
                        text_pain.getText().toString().trim().length() > 0 &&
                        text_emergency.getText().toString().trim().length() > 0 &&
                        text_questions.getText().toString().trim().length() > 0) {

                    postData(text_thinner.getText().toString().trim(),
                            text_medication.getText().toString().trim(),
                            text_doses.getText().toString().trim(),
                            text_BorB.getText().toString().trim(),
                            text_pain.getText().toString().trim(),
                            text_emergency.getText().toString().trim(),
                            text_questions.getText().toString().trim());
                } else {
                    Snackbar.make(view, "Required Fields Missing", Snackbar.LENGTH_LONG).show();
                }

                //---------------------------------------------------------------------------------//


                startActivity(new Intent(getApplicationContext(), Activity_thinnerVoucher.class));
            }
        });

    }


    public void postData(final String first, final String second, final String third, final String fourth, final String fifth, final String sixth, final String seventh) {

        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants2.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Response: " + response);
                        if (response.length() > 0) {
                            Snackbar.make(onSubmit, "Successfully Posted", Snackbar.LENGTH_LONG).show();
                            text_thinner.setText(null);
                            text_medication.setText(null);
                            text_doses.setText(null);
                            text_BorB.setText(null);
                            text_pain.setText(null);
                            text_emergency.setText(null);
                            text_questions.setText(null);
                        } else {
                            Snackbar.make(onSubmit, "Try Again", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(onSubmit, "Error while Posting Data", Snackbar.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants2.firstField, first);
                params.put(Constants2.secondField, second);
                params.put(Constants2.thirdField, third);
                params.put(Constants2.fourthField, fourth);
                params.put(Constants2.fifthField, fifth);
                params.put(Constants2.sixthField, sixth);
                params.put(Constants2.seventhField, seventh);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}
