package com.geom.geomdriver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.geom.geomdriver.classes.Connectivity;
import com.geom.geomdriver.classes.PublicTransport;
import com.geom.geomdriver.classes.SharedData;
import com.geom.geomdriver.classes.StaticHandler;
import com.geom.geomdriver.classes.threads.TransportThread;

public class HomeActivity extends AppCompatActivity {
    private SharedData s;
    TransportThread tt;
    Handler handler;

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

        //client connesso ad internet
        if(Connectivity.isConnected(HomeActivity.this)){
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

                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.obj instanceof String) {
                            String option = (String) msg.obj; // object of String

                            switch(option) {
                                case "-1":
                                    //refresh textview
                                    inputLayoutName.setError(getString(R.string.err_msg_wrong_name));
                                    requestFocus(inputName);
                                    break;
                                case "-2":
                                    //refresh textview
                                    inputLayoutPassword.setError(getString(R.string.err_msg_wrong_password));
                                    requestFocus(inputPassword);
                                    break;
                                case "OK":

                                    //finish();
                                    Log.i("sMESSAGE", "FINE SWITCH CASE OK");
                                    break;
                                default: //errore generico
                                    inputLayoutPassword.setError(getString(R.string.err_msg_undefined));
                                    break;
                            }
                        }
                        else if (msg.obj instanceof SharedData) {
                            SharedData newsd = (SharedData) msg.obj; // object of PublicTransport
                            tt.setSharedData(newsd);

                            if (newsd.refreshOnly) {
                                newsd.refreshOnly = false;
                            } else {
                                // sveglio il thread, dato che è stato scelto un mezzo
                                synchronized (StaticHandler.lock) {
                                    StaticHandler.lock.notify();
                                }
                            }
                        }

                    }
                };

                StaticHandler.setHandler(handler);
                //s.handler = handler;

                View v = findViewById(R.id.activity_home);

                tt = new TransportThread(s, v, handler);
                tt.start();

                /*Stirng response = tt.getResponse();*/

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
        } else{//client non connesso
            showAlertDialog(getString(R.string.internet_error_title),
                    getString(R.string.internet_error_message));
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showAlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,
                R.style.AppCompatAlertDialogStyleLight);
        if(title != null && !title.isEmpty())
            builder.setTitle(Html.fromHtml("<b>" + title + "<b>"));
        if(message != null && !message.isEmpty())
            builder.setMessage(message);

        builder.setPositiveButton(Html.fromHtml("<b>" + getString(R.string.ok_string) + "<b>"), null);
        builder.show();
    }
}
