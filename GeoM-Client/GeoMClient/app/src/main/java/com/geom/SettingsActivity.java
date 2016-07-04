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
import android.util.Log;
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
                            builder.setMessage(getString(R.string.pref_question));
                            builder.setPositiveButton(getString(R.string.ok_string),
                                    new DialogInterface.OnClickListener() {

                                //riporto impostazioni al valore di default
                                public void onClick(DialogInterface dialog, int id) {
                                    String snackbarContent;

                                    if(s.removeAllFavs()){//elimino i preferiti
                                        //wifiOnly
                                        CheckBoxPreference wifiOnly = (CheckBoxPreference) findPreference("wifiOnly");
                                        if(wifiOnly.isChecked()){
                                            wifiOnly.setChecked(false);
                                        }

                                        //aggiorno il valore di wifiOnly
                                        s.wifiOnly = wifiOnly.isChecked();

                                        //notifico esito dell'operazione
                                        snackbarContent = getString(R.string.pref_restored);
                                        _return = true;
                                    } else {
                                        snackbarContent = getString(R.string.pref_not_restored);
                                        _return = false;
                                    }

                                    Snackbar.make(getActivity().findViewById(R.id.activity_settings), snackbarContent, Snackbar.LENGTH_LONG).show();
                                }
                            });

                    builder.setNegativeButton(getString(R.string.annulla_string), null);
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

            //solo wifi (si-no)
            final CheckBoxPreference prefWifiOnly = (CheckBoxPreference) findPreference("wifiOnly");
            prefWifiOnly.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    //cambio valore
                    prefWifiOnly.setChecked((Boolean) newValue);
                    //Log.i("GUI_LOG", "newvalue: " + newValue.toString());
                    //aggiorno in SharedData
                    s.wifiOnly = (Boolean) newValue;
                    return false;
                }
            });
        }
    }

    public void goBack(){

        Intent i;
        Bundle b = new Bundle();

        if(s.goToChoosePTActivity){//sono arrivato in SettingsActivity tramite popup
            i = new Intent(SettingsActivity.this, ChoosePTActivity.class);
            s.goToChoosePTActivity = false;
        } else{//sono arrivato in SettingsActivity da HomeActivity
            i = new Intent(SettingsActivity.this, MainActivity.class);
            s.goToHomeActivity = false;
        }

        b.putParcelable("SharedData", s);
        i.putExtra("bundle", b);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        goBack();
    }
}