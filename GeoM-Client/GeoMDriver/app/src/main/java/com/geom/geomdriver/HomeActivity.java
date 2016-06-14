package com.geom.geomdriver;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;

import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    private EditText inputName, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword;
    private Button btnSignUp;

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
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {

        //username empty
        if (!inputName.getText().toString().isEmpty()) {
            inputLayoutName.setErrorEnabled(false);
            //password empty
            if(!inputPassword.getText().toString().isEmpty()){
                inputLayoutPassword.setErrorEnabled(false);

                //get username and password from input text
                String username = inputName.getText().toString();
                String passsword = inputPassword.getText().toString();
                /*
                * Thread t = new Thread(username, password);
                * t.start();
                *
                * try{
                *   t.stop();
                * } catch(IOException e){
                *   e.printStackTrace();
                * }
                * */

                String response = "ok";
                /*Stirng response = t.getResponse();*/

                if(!response.trim().toLowerCase().isEmpty()) {

                    switch(response.trim().toLowerCase()){
                        case "-1"://username errato
                            inputLayoutName.setError(getString(R.string.err_msg_name));
                            requestFocus(inputName);
                            break;

                        case "-2"://password errato
                            inputLayoutPassword.setError(getString(R.string.err_msg_password));
                            requestFocus(inputPassword);
                            break;

                        case "ok"://credenziali corrette
                            Intent i = new Intent(HomeActivity.this, ChoosePTActivity.class);
                            startActivity(i);
                            finish();
                            break;

                        default://errore generico
                            inputLayoutPassword.setError(getString(R.string.err_msg_password));
                            break;
                    }
                } else{//errore generico
                    inputLayoutPassword.setError(getString(R.string.err_msg_password));
                }

            } else{
                inputLayoutPassword.setError(getString(R.string.err_msg_password_empty));
                requestFocus(inputPassword);
            }
        } else{
            inputLayoutName.setError(getString(R.string.err_msg_name_empty));
            requestFocus(inputName);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    requestFocus(inputName);
                    break;
                case R.id.input_password:
                    requestFocus(inputPassword);
                    break;
            }
        }
    }
}
