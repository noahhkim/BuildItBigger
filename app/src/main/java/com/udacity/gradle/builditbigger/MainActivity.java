package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this);
        endpointsAsyncTask.execute();
    }

    class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
        private MyApi myApiService = null;
        private Context mContext;
        private String mResult;

        public EndpointsAsyncTask(Context context) {
            mContext = context;
        }


        @Override
        protected String doInBackground(String... params) {
            if (myApiService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("https://builditbigger-172105.appspot.com/_ah/api/");
                myApiService = builder.build();
            }

            try {
                return myApiService.tellJoke(new MyBean()).execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mResult = result;
            sendJokeToJokeActivity();
        }

        private void sendJokeToJokeActivity() {
            Intent intent = new Intent(mContext, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY, mResult);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}
