package com.example.prateek.springbootexample;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prateek on 30-01-2017.
 */

public class AddDataAsyncTask extends AsyncTask<String,Void,String> {


    private static final String SERVER_URL = "http://192.168.1.4/android/data.php";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    String name;
    String occ;
    String comp;
    String phone;
    String work;
    String hang;
    String skype;
    String goog;
    String fb;
    String blog;

    protected String doInBackground(String... params){

        HttpURLConnection httpURLConnection = null;
        name = params[0];
        occ = params[1];
        comp = params[2];
        phone = params[3];
        work = params[4];
        hang = params[5];
        skype = params[6];
        goog = params[7];
        fb = params[8];
        blog = params[9];

        try {
            URL url = new URL(SERVER_URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("name",params[0])
                    .appendQueryParameter("occupation",params[1])
                    .appendQueryParameter("company",params[2])
                    .appendQueryParameter("phone",params[3])
                    .appendQueryParameter("work",params[4])
                    .appendQueryParameter("hangout",params[5])
                    .appendQueryParameter("skype",params[6])
                    .appendQueryParameter("google",params[7])
                    .appendQueryParameter("facebook",params[8])
                    .appendQueryParameter("blog",params[9]);
            String query = builder.build().getEncodedQuery();
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

            bufferedWriter.write(query);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            httpURLConnection.connect();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            int response_code = httpURLConnection.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    result.append(line);
                }

                return (result.toString());
            }else {
                return ("unsucessfull");
            }
        }catch (IOException e){
            e.printStackTrace();
            return "exception";
        }finally {
            httpURLConnection.disconnect();
        }
    }

    @Override
    public void onPostExecute(String result){
        super.onPostExecute(result);
    }

}
