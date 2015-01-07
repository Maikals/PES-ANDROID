package com.pes.maikals.quiosquer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ServiceLayer.ApiService;


public class Login extends Activity {
    private boolean logged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logged = false;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        Log.d("com.pes.maikals.quiosquer", "on Resume logged  " + logged);
        if (logged) finish();
    }

    public void loginAttempt(View view) {
        Log.d("com.pes.maikals.subscriptor", "loginAttempt() cridat");
        EditText emailEdit = (EditText) findViewById(R.id.email);
        EditText passwordEdit = (EditText) findViewById(R.id.password);
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        int code = ApiService.login(email, password);
        if (code == 401) Toast.makeText(getApplicationContext(), "Usuari Incorrecte", Toast.LENGTH_LONG).show();
        else if (code == 200) {
            logged = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
