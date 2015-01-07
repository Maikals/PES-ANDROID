package com.pes.maikals.quiosquer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Domini.Val;
import ServiceLayer.ApiService;


public class EntradaCodiManual extends Activity {
    private boolean escanejat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_codi_manual);
        escanejat = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entrada_codi_manual, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (escanejat) finish();
    }

    public void codiEntrat(View view) {
        EditText edit = (EditText) findViewById(R.id.editText);
        String codi = edit.getText().toString();
        Log.d("entrada manual ", codi.length() + " " + codi);
        if (codi.length() > 0) {
            Val v = ApiService.getVal(codi);
            if (v != null) {
                escanejat = true;
                Intent intent = new Intent(this, ValInfo.class);
                intent.putExtra("id", codi);
                startActivity(intent);
            }
            else Toast.makeText(getApplicationContext(), "Codi de val incorrecte o caducat", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Entrar codi", Toast.LENGTH_LONG).show();
        }
    }
}
