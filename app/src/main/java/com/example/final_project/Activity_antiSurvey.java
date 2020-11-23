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

public class Activity_antiSurvey extends AppCompatActivity {

    Button goBackButton;
    Button onSubmit;
    ProgressDialog progressDialog;
    EditText text_finish, text_ifNo, text_proB, text_proBans, text_alc, text_over, text_ques;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_survey);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        onSubmit = findViewById(R.id.onSubmit);
        goBackButton = findViewById(R.id.goBackButton);
        text_finish = findViewById(R.id.text_finish); // first
        text_ifNo = findViewById(R.id.text_ifNo);     // second
        text_proB = findViewById(R.id.text_proB);     // third
        text_proBans = findViewById(R.id.text_proBans); // fourth
        text_alc = findViewById(R.id.text_alc);         // fifth
        text_over = findViewById(R.id.text_over);       // sixth
        text_ques = findViewById(R.id.text_ques);       // seventh

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
                editor.putBoolean("antivoucher", true);
                editor.commit();

                //---------------------------------------------------------------------------------//


                if (text_finish.getText().toString().trim().length() > 0 &&
                        text_ifNo.getText().toString().trim().length() > 0 &&
                        text_proB.getText().toString().trim().length() > 0 &&
                        text_proBans.getText().toString().trim().length() > 0 &&
                        text_alc.getText().toString().trim().length() > 0 &&
                        text_over.getText().toString().trim().length() > 0 &&
                        text_ques.getText().toString().trim().length() > 0) {

                    postData(text_finish.getText().toString().trim(),
                            text_ifNo.getText().toString().trim(),
                            text_proB.getText().toString().trim(),
                            text_proBans.getText().toString().trim(),
                            text_alc.getText().toString().trim(),
                            text_over.getText().toString().trim(),
                            text_ques.getText().toString().trim());
                } else {
                    Snackbar.make(view, "Required Fields Missing", Snackbar.LENGTH_LONG).show();
                }

                //---------------------------------------------------------------------------------//


                startActivity(new Intent(getApplicationContext(), Activity_antiVoucher.class));
            }
        });

    }


    public void postData(final String first, final String second, final String third, final String fourth, final String fifth, final String sixth, final String seventh) {

        progressDialog.show();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Response: " + response);
                        if (response.length() > 0) {
                            Snackbar.make(onSubmit, "Successfully Posted", Snackbar.LENGTH_LONG).show();
                            text_finish.setText(null);
                            text_ifNo.setText(null);
                            text_proB.setText(null);
                            text_proBans.setText(null);
                            text_alc.setText(null);
                            text_over.setText(null);
                            text_ques.setText(null);
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
                params.put(Constants.firstField, first);
                params.put(Constants.secondField, second);
                params.put(Constants.thirdField, third);
                params.put(Constants.fourthField, fourth);
                params.put(Constants.fifthField, fifth);
                params.put(Constants.sixthField, sixth);
                params.put(Constants.seventhField, seventh);
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
