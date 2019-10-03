package com.arjun.mynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> news;
    List<String> link;
    List<String> content;
    ListView list;
    ArrayAdapter<String> arrayAdapter;

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try{

                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){

                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
                return result;

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonObject=new JSONObject(result);
                String newNews=jsonObject.getString("articles");
                JSONArray arr=new JSONArray(newNews);
                for(int i=0;i<arr.length();i++){

                    JSONObject jsonPart=arr.getJSONObject(i);
                    news.add(jsonPart.getString("title"));
                    link.add(jsonPart.getString("url"));
                    content.add(jsonPart.getString("content"));

                }

            }
            catch (JSONException e) {

                e.printStackTrace();
            }


            list.setAdapter(arrayAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent=new Intent(getApplicationContext(), Main3Activity.class);
                    intent.putExtra("SUMMARY", content.get(i));
                    intent.putExtra("LINK", link.get(i));
                    startActivity(intent);
                }
            });


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("MyNews by RyuuKenshi");

        list=(ListView)findViewById(R.id.list);

        news=new ArrayList<String>();
        link=new ArrayList<String>();
        content=new ArrayList<String>();

        DownloadTask task=new DownloadTask();
        task.execute("https://newsapi.org/v2/top-headlines?country=in&apiKey=a28127e7ed904f67ab8d630b8634c9f5");

        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, news);


    }
}
