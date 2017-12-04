package com.gsk.weatherapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    TextView t1;
    RelativeLayout relativeLayout;
    EditText e1;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=(TextView)findViewById(R.id.textView);
        t1.setVisibility(View.INVISIBLE);
        e1=(EditText)findViewById(R.id.editText);
        relativeLayout=(RelativeLayout)findViewById(R.id.activity_main);
    }
    class getResultTask extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... Params)
        {
            String result = "";

            URL url ;
            HttpURLConnection connection = null;
            try {

                url = new URL(Params[0]);
                connection = (HttpURLConnection)url.openConnection();

                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader (in));
                StringBuilder builder = new StringBuilder();
                while ((result = reader.readLine()) != null) {
                    builder.append(result + "\n");
                }
                Thread.sleep(4000);
                return builder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
               // Toast.makeText(getApplicationContext(), "doInBackground is Runned Successfully",Toast.LENGTH_LONG).show();

            }
            return null;
        }

        @Override
        protected void onPreExecute()

        {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(" Please Wait ");
            dialog.setMessage( e1.getText ().toString ()+ " Climate is Loading");
            dialog.show();
            //Toast.makeText(getApplicationContext(), "OnPreExecut is running Successfully",Toast.LENGTH_LONG).show();

        }


        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            String main = "";
            String description = "";
            String message = "";

            try {


                String result = "";



                JSONObject server_data = new JSONObject(s);
                String data = server_data.getString("weather");
                JSONArray array = new JSONArray(data);
                JSONObject weather_data = array.getJSONObject(0);
                main = weather_data.getString("main");
                description = weather_data.getString("description");
                message = main + ":" + description;


                t1.setText(message);
                if (main.equals("Drizzle")) {
                    relativeLayout.setBackgroundResource(R.drawable.drizzle);
                    Toast.makeText(getApplicationContext(), "LIGHT DRIZZLE  PRESENT IN  SKY",Toast.LENGTH_LONG).show();

                }
                else if (main.equals("Clouds")) {
                    relativeLayout.setBackgroundResource(R.drawable.cloud);
                    Toast.makeText(getApplicationContext(), "HEAVY CLOUDS PRESENT IN  SKY",Toast.LENGTH_LONG).show();


                }
                else if (main.equals("Clear"))
                {
                    relativeLayout.setBackgroundResource(R.drawable.clear);
                    Toast.makeText(getApplicationContext(), "CLEAR SUN IN THE SKY",Toast.LENGTH_LONG).show();

                }

                else if (main.equals("Haze"))
                {
                    relativeLayout.setBackgroundResource(R.drawable.haze);
                    Toast.makeText(getApplicationContext(), "HAZE PRESENT IN SKY",Toast.LENGTH_LONG).show();


                } else if (main.equals("Mist"))
                {
                    relativeLayout.setBackgroundResource(R.drawable.mist);
                    Toast.makeText(getApplicationContext(), "HEAVY MIST PRESENT",Toast.LENGTH_LONG).show();


                }
                else if (main.equals("Smoke"))
                {
                    relativeLayout.setBackgroundResource(R.drawable.smoke);
                    Toast.makeText(getApplicationContext(), "HEAVY SMOKE PRESENT",Toast.LENGTH_LONG).show();


                }
                else if (main.equals("Rain"))
                {
                    relativeLayout.setBackgroundResource(R.drawable.raindrop);
                    Toast.makeText(getApplicationContext(), "HEAVY RAIN PRESENT IN  SKY",Toast.LENGTH_LONG).show();


                }
                else
                {
                    relativeLayout.setBackgroundResource(R.drawable.bgs );
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            t1.setVisibility(View.VISIBLE);
           //Toast.makeText(getApplicationContext(), "OnPost Method is Performed",Toast.LENGTH_LONG).show();


        }
    }
    public  void Check_Weather(View view)
    {

        getResultTask task = new getResultTask ();

       // task.execute ("http://api.openweathermap.org/data/2.5/weather?zip="+e1.getText ().toString ()+"&appid=00ae1968c9fe0bc196247d280ae0eb11");

        //task.execute ("http://api.openweathermap.org/data/2.5/group?id="+e1.getText ().toString ()+"&units=metric&appid=00ae1968c9fe0bc196247d280ae0eb11");


        task.execute ("http://api.openweathermap.org/data/2.5/weather?q="+e1.getText().toString()+"&appid=00ae1968c9fe0bc196247d280ae0eb11");
       // String str1 ="http://api.openweathermap.org/data/2.5/weather?q=";
        //String str2 = e1.getText().toString();
        //String str3 ="&appid=00ae1968c9fe0bc196247d280ae0eb11" ;
       // String ConctString =str1+str2+str3;
        //Toast.makeText(getApplicationContext(), ConctString,Toast.LENGTH_LONG).show();




    }

}


