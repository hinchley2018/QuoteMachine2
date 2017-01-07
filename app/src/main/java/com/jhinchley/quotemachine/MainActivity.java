package com.jhinchley.quotemachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncResponse{
    EditText RecipeView;
    String Recipe;
    TextView textView,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get reference to defined view in layout file

        RecipeView=(EditText)findViewById(R.id.Recipe);
        textView = (TextView) findViewById(R.id.textArea);
        textView2 = (TextView) findViewById(R.id.textArea2);
    }

    public void send_data_to_server(View v){

        //get the values from the views

        Recipe=RecipeView.getText().toString();
        //create a JSONObject so I can post data...
        JSONObject post_dict = new JSONObject();
        //Toast.makeText(getApplication(),"F:"+FirstName+",L:"+LastName+",A:"+Age,Toast.LENGTH_SHORT).show();
        try {

            post_dict.put("recipe",Recipe);
            Log.i("My JSONObject",post_dict.toString());
            //Toast.makeText(getApplication(),"My Json:"+post_dict.toString(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (post_dict.length()>0){
            String urlStr = "http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en";
            new SendJsonDataToServer(this).execute(urlStr,String.valueOf(post_dict));
        }
    }

    //this overrides the implemented method from asyncTask
    @Override
    public void processResponse(String JSONResponse){
        //process the JSONReponse from the server
        Log.w("Response mainActivity",JSONResponse);
        JSONObject jsonObject = new JSONObject();
        String quote="";
        String author="";
        try {
            jsonObject = new JSONObject(JSONResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            quote = jsonObject.getString("quoteText");
            author = jsonObject.getString("quoteAuthor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),quote,Toast.LENGTH_SHORT).show();
        textView.setText(quote);
        textView2.setText("Author: "+author);

    }
}
