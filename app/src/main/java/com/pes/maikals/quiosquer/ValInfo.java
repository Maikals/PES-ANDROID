package com.pes.maikals.quiosquer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import Domini.Val;
import ServiceLayer.ApiService;


public class ValInfo extends Activity {
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_val_info);
        id = null;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Val v = ApiService.getVal(id);
        TextView nomPublicacio = (TextView) findViewById(R.id.publicacio);
        nomPublicacio.setText(v.getNomSubscripcio());
        TextView data = (TextView) findViewById(R.id.data);
        data.setText(v.getData());
        Button confirmar = (Button) findViewById(R.id.confirmar);
        confirmar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClick();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_val_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void longClick() {
        //TODO
        boolean confirmat = ApiService.marcarVal(id);
        if (confirmat) Toast.makeText(getApplicationContext(), "Val confirmat", Toast.LENGTH_LONG).show();
        else Toast.makeText(getApplicationContext(), "Codi de val incorrecte o caducat", Toast.LENGTH_LONG).show();
        finish();
    }

    public void clicat(View view) {
        Toast.makeText(getApplicationContext(), "Fes una pulsació llarga per confirmar", Toast.LENGTH_LONG).show();
    }
}
