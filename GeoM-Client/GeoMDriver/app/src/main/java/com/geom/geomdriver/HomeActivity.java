package com.geom.geomdriver;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {
    private SharedData s;

    private EditText inputName, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);
        Button btnSignUp = (Button) findViewById(R.id.btn_signup);
        if(btnSignUp != null) {
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitForm();
                }
            });
        }

        s = new SharedData();
    }

    private void submitForm() {
        boolean checkUser;
        boolean checkPsw;

        //campo username vuoto
        if(!inputName.getText().toString().isEmpty()) {
            inputLayoutName.setErrorEnabled(false);
            checkUser = true;
        } else {
            inputLayoutName.setErrorEnabled(true);
            inputLayoutName.setError(getString(R.string.err_msg_name_empty));
            requestFocus(inputName);
            checkUser = false;
        }

        //campo password vuoto
        if(!inputPassword.getText().toString().isEmpty()) {
            inputLayoutPassword.setErrorEnabled(false);
            checkPsw = true;
        } else {
            inputLayoutPassword.setErrorEnabled(true);
            inputLayoutPassword.setError(getString(R.string.err_msg_password_empty));
            requestFocus(inputPassword);
            checkPsw = false;
        }

        //username e password non vuote
        if(checkUser && checkPsw) {
            //get username and password from input text
            s.username = inputName.getText().toString();
            s.password = inputPassword.getText().toString();

            View v = findViewById(R.id.activity_home);

            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String msgResp = (String) msg.obj; // object of String
                    if ("-1".equals(msgResp)) {
                        //refresh textview
                        inputLayoutName.setError(getString(R.string.err_msg_wrong_name));
                        requestFocus(inputName);
                    }
                    else if ("-2".equals(msgResp)) {
                        inputLayoutPassword.setError(getString(R.string.err_msg_wrong_password));
                        requestFocus(inputPassword);
                    }
                }
            };

            TransportThread tt = new TransportThread(s, v, handler);
            tt.start();

            String response = "ok";
            /*Stirng response = t.getResponse();*/

            /*if(!response.toLowerCase().isEmpty()) {

                switch (response.toLowerCase()) {
                    case "-1"://username errato
                        inputLayoutName.setError(getString(R.string.err_msg_wrong_name));
                        requestFocus(inputName);
                        break;

                    case "-2"://password errato
                        inputLayoutPassword.setError(getString(R.string.err_msg_wrong_password));
                        requestFocus(inputPassword);
                        break;

                    case "ok"://credenziali corrette
                        Intent i = new Intent(HomeActivity.this, ChoosePTActivity.class);
                        startActivity(i);
                        finish();
                        break;

                    default://errore generico
                        inputLayoutPassword.setError(getString(R.string.err_msg_undefined));
                        break;
                }
            }*/
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
