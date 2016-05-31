package com.geom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import classes.SharedData;

public class SettingsActivity extends AppCompatActivity {

    SharedData s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        if(getIntent().getBundleExtra("bundle").containsKey("SharedData")){//quando arrivo da InfoActivity l'Intent non contiene SharedData
            s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");
        }

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new CustomPreferenceFragment()).commit();
    }

    public static class CustomPreferenceFragment extends PreferenceFragment {
        private boolean _return;
        private SharedData s;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            s = getActivity().getIntent().getBundleExtra("bundle").getParcelable("SharedData");

            Preference resetPref = findPreference("resetAll");
            resetPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        public boolean onPreferenceClick(Preference preference) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                                    R.style.AppCompatAlertDialogStyleLight);
                            builder.setTitle("Ripristinare le impostazioni predefinite?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                //riporto impostazioni al valore di default
                                public void onClick(DialogInterface dialog, int id) {
                                    String snackbarContent;

                                    if(s.removeAllFavs()){//elimino i preferiti
                                        //wifiOnly
                                        CheckBoxPreference wifiOnly = (CheckBoxPreference) findPreference("wifiOnly");
                                        if(wifiOnly.isChecked()){
                                            wifiOnly.setChecked(false);

                                            //cambio valori
                                            //...
                                        }

                                        //notifico esito dell'operazione
                                        snackbarContent = "Impostazioni ripristinate";
                                        _return = true;
                                    } else {
                                        snackbarContent = "ERRORE durante il ripristino delle impostazioni";
                                        _return = false;
                                    }

                                    Snackbar.make(getActivity().findViewById(R.id.activity_settings), snackbarContent, Snackbar.LENGTH_LONG).show();
                                }
                            });

                    builder.setNegativeButton("ANNULLA", null);
                    builder.show();

                    return _return;
                }
            });

            //vado a InfoActivity
            Preference prefInfo = findPreference("info");
            prefInfo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(getActivity(), InfoActivity.class);
                    startActivity(i);
                    return _return;
                }
            });
        }
    }

    public void goBack(){
        Intent i = new Intent(SettingsActivity.this, HomeActivity.class);
        Bundle b = new Bundle();

        s.goToHomeActivity = false;

        b.putParcelable("SharedData", s);
        i.putExtra("bundle", b);
        setResult(RESULT_OK);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        goBack();
    }
}