package com.example.testhttpsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog.Builder;
import android.view.View;
import android.widget.Button;

import com.example.testretrofituselibrary.MainController;
import com.example.testretrofituselibrary.ServiceCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements ServiceCallback {

    private static final String CLASS_TAG = MainActivity.class.getSimpleName();

    public Button metodouno,metododos,metodotres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metodouno=findViewById(R.id.button);
        metododos=findViewById(R.id.button2);
        metodotres=findViewById(R.id.button3);

        metodouno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchTask().execute();
            }
        });

        metododos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urls="https://jsonplaceholder.typicode.com/posts";

//                AsyncHttpClient client = new AsyncHttpClient();
//                client.get(urls, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        Log.e(TAG,"Response body "+responseBody.toString());
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                    }
//
//
//                });

                new JSONAsyncTask().execute(urls);
                Log.e(CLASS_TAG, "resp: " + urls);
            }
        });

        metodotres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MainController.makeGetPetition(MainActivity.this);
            }
        });


    }

    @Override
    public void handleData(Object result) {
        Log.e(CLASS_TAG, "resp: " + result);

    }

    @Override
    public void handleIndividualData(Object result) {
        Log.e(CLASS_TAG, "resp individual: " + result);

    }

    private class FetchTask extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Show progressbar
            setProgressBarIndeterminateVisibility(true);
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL("https://www.tanelikorri.com/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Log the server response code
                int responseCode = connection.getResponseCode();
                Log.i(CLASS_TAG, "Server responded with: " + responseCode);

                // And if the code was HTTP_OK then parse the contents
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    // Convert request content to string
                    InputStream is = connection.getInputStream();
                    String content = convertInputStream(is, "UTF-8");
                    is.close();

                    return content;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        private String convertInputStream(InputStream is, String encoding) {
            Scanner scanner = new Scanner(is, encoding).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Hide progressbar
        setProgressBarIndeterminateVisibility(false);

        if (result != null) {

            // Create a dialog
            Log.i(CLASS_TAG, "Server responded with 3: " + result);
            Builder builder = new Builder(MainActivity.this);
            builder.setMessage(result.substring(0, 200) + "...");
            builder.setPositiveButton("OK", null);

            // and show it
            builder.create().show();
        }
    }
}

    private class JSONAsyncTask extends AsyncTask<String,Void,String>{

        String data;
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show progressbar
            setProgressBarIndeterminateVisibility(true);
        }
        @Override
        protected String doInBackground(String... url) {

            String stringUrl = url[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(CLASS_TAG,"response "+s);
            try {
                JSONArray data=new JSONArray(s);
                Log.e(CLASS_TAG,"response JSON "+data.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
