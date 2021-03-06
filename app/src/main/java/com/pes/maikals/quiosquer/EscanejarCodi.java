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

import Domini.Val;
import ServiceLayer.ApiService;

//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;


public class EscanejarCodi extends Activity implements View.OnClickListener {
    //public int SCANNER_REQUEST_CODE = 123;
    private Button scanBtn;
    private boolean escanejat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanejar_codi);
        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);
        escanejat = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_escanejar_codi, menu);
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
    public void onClick(View view) {
        if(view.getId()==R.id.scan_button){
            Intent in = new Intent("com.google.zxing.client.android.SCAN");
            in.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(in, 0);
            //- See more at: http://techiedreams.com/android-zxing-barcode-scanner-integration/#sthash.vh810gy0.dpuf
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (escanejat) finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            // Handle successful scan
            String contents = intent.getStringExtra("SCAN_RESULT");
            Val v = ApiService.getVal(contents);
            if (v != null) {
                escanejat = true;
                Intent intent2 = new Intent(this, ValInfo.class);
                intent2.putExtra("id", contents);
                startActivity(intent2);
            }
            else Toast.makeText(getApplicationContext(), "Codi de val incorrecte o caducat", Toast.LENGTH_LONG).show();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void entrarCodiManual(View view) {
        Intent intent = new Intent(this, EntradaCodiManual.class);
        startActivity(intent);
    }
}
