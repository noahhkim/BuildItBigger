package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.JokeSource;
import com.example.android.jokedisplay.JokeActivity;
import com.example.noah.myapplication.backend.myApi.MyApi;
import com.example.noah.myapplication.backend.myApi.model.MyBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));
        new EndpointsAsyncTask(this).execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void startJokeActivity(View view) {
        Intent intent = new Intent(this, JokeActivity.class);
        JokeSource jokeSource = new JokeSource();
        String lameJoke = jokeSource.tellAJoke();
        intent.putExtra(JokeActivity.JOKE_KEY, lameJoke);
        startActivity(intent);
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context mContext;

        public EndpointsAsyncTask(Context context) {
            mContext = context;
        }


        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("https://builditbigger-172105.appspot.com//_ah/api/");

                // end options for devappserver
                myApiService = builder.build();
            }

//            mContext = params[0].first;
//            String name = params[0].second;
//
//            try {
//                return myApiService.sayHi(name).execute().getData();
//            } catch (IOException e) {
//                return e.getMessage();
//            }

            try {
                return myApiService.tellJoke(new MyBean()).execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
        }
    }
}
