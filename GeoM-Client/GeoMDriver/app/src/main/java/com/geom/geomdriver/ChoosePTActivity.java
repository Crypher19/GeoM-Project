package com.geom.geomdriver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.geom.geomdriver.classes.layout_classes.ListViewDivider;

import com.geom.geomdriver.classes.layout_classes.PublicTransportListAdapter;
import com.geom.geomdriver.classes.SharedData;

public class ChoosePTActivity extends AppCompatActivity {
    public SharedData s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pt);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            toolbar.setTitle("Scegli mezzo");
        setSupportActionBar(toolbar);

        //pulsante logout
        Button logout_btn = (Button) findViewById(R.id.btn_logout);
        if(logout_btn != null) {
            logout_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),
                            R.style.AppCompatAlertDialogStyleLight);
                    builder.setTitle(Html.fromHtml("<b>"+ getString(R.string.warning_title) +"</b>"));
                    builder.setMessage(getString(R.string.warning_message));
                    builder.setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            s.sendCoord = false; // termino l'invio di coordinate
                            //Log.i("sMESSAGE CAMBIATO", "s.sendCoord = " + s.sendCoord);
                            Intent i = new Intent(ChoosePTActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.undo_string), null);
                    builder.show();
                }
            });
        }

        s = getIntent().getBundleExtra("bundle").getParcelable("SharedData");

        //RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pt_recycler_view);
        if(recyclerView != null) {
            //divider
            recyclerView.addItemDecoration(new ListViewDivider(this, ListViewDivider.VERTICAL_LIST));
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //adapter
            PublicTransportListAdapter publicTransportListAdapter
                    = new PublicTransportListAdapter(s);
            recyclerView.setAdapter(publicTransportListAdapter);
        }
    }

}
