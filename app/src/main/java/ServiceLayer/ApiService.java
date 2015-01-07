package ServiceLayer;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import Domini.Val;


/**
 * Created by maikals on 04/01/15.
 */

public class ApiService {

    static public Val getVal(String id) {
        Val v = null;
        Log.d("com.pes.maikals.subscriptor", "mail i pass aconseguits cridat " + id);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://pes.ivy-corp.com/api/vals/check?ticketId="+id);
        Log.d("com.pes.maikals.subscriptor", "intentem connexió");
        try {
            // Execute HTTP Post Request

            HttpResponse response = httpclient.execute(httpget);
            Log.d("com.pes.maikals.subscriptor", "post executat");
            String result = EntityUtils.toString(response.getEntity());
            Log.d("com.pes.maikals.subscriptor", result);
            JSONObject jsonObj = new JSONObject(result);
            // CONVERT RESPONSE STRING TO JSON ARRAY
            jsonObj = jsonObj.getJSONObject("val");
            String nomSubscripcio = jsonObj.getString("nomSubscripcio");
            String data = jsonObj.getString("data");

            int dia, mes, any;
            dia = mes = any = 0;
            int cont = 0;
            Log.d("com.pes.maikals.quiosquer", data);
            for (int i = 0; i < data.length(); ++i) {
                if (data.charAt(i) == '/') {
                    ++cont;
                }
                else if (cont == 0) mes = mes*10 + data.charAt(i)-'0';
                else if (cont == 1) dia = dia*10 + data.charAt(i)-'0';
                else if (cont == 2) any = any*10 + data.charAt(i)-'0';

            }
            Log.d("com.pes.maikals.quiosquer", dia + " / " + mes + " / " + any);
            data = (dia >= 10?dia:"0"+dia) + "/" + (mes >= 10?mes:"0"+mes) + "/" + any;
            v = new Val(nomSubscripcio,data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    static public boolean marcarVal(String codi) {
        //http://pes.ivy-corp.com/api/vals/tick?ticketId=
        boolean marcat = false;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://pes.ivy-corp.com/api/vals/tick?ticketId="+codi);
        Log.d("com.pes.maikals.subscriptor", "intentem connexió");
        try {
            // Execute HTTP Post Request

            HttpResponse response = httpclient.execute(httpget);
            Log.d("com.pes.maikals.subscriptor", "post executat");
            String result = EntityUtils.toString(response.getEntity());
            Log.d("com.pes.maikals.subscriptor", result);
            if (result.equals("200")) marcat = true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marcat;
    }

    static public int login(String email, String password) {
        Log.d("com.pes.maikals.subscriptor", "mail i pass aconseguits cridat " + email + " " + password);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://pes.ivy-corp.com/api/checkauth");
        Log.d("com.pes.maikals.subscriptor", "intentem connexió");

        int stat = 0;
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request

            HttpResponse response = httpclient.execute(httppost);
            Log.d("com.pes.maikals.subscriptor", "post executat");
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();
            JSONTokener tokener = new JSONTokener(json);
            stat = Integer.parseInt(tokener.next(3));
        } catch (UnsupportedEncodingException e) {
            Log.d("com.pes.maikals.subscriptor", "error 1");

            e.printStackTrace();
        } catch (IOException e) {
            Log.d("com.pes.maikals.subscriptor", "error 2");

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stat;
    }
}
