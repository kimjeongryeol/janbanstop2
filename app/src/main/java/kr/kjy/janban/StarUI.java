package kr.kjy.janban;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StarUI extends AppCompatActivity {
    private RatingBar ratingBar1;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ddd1", "onPostExecute executed");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ui);
        ratingBar1 = findViewById(R.id.ratingBar1);
        new FetchRatingTask().execute();
    }

    private class FetchRatingTask extends AsyncTask<Void, Void, Float> {
        @Override
        protected Float doInBackground(Void... params) {
            String phpUrl = "http://tina908.dothome.co.kr/Star.php";

            try {
                URL url = new URL(phpUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                Log.d("ddd", "HTTP Response Code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    try {
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        float rating = (float) jsonResponse.getDouble("rating");
                        Log.d("ddd", "Rating: " + rating);
                        return rating;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return -1.0f;
                    }
                } else {
                    return -1.0f;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MyApp", "Error in network request: " + e.getMessage());
                return -1.0f;
            }
        }

        protected void onPostExecute(Float rating) {
            super.onPostExecute(rating);

            if (rating >= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ratingBar1.setRating(rating);
                        ratingBar1.setIsIndicator(true);

                        // After the rating is set, start UserUiActivity or initiate RetrieveMenuTask
                        startUserUiActivity();
                    }
                });
            } else {
                // Handle the case when rating retrieval fails
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Handle the error
                    }
                });
            }
        }
    }

    // Start UserUiActivity or initiate RetrieveMenuTask
    private void startUserUiActivity() {
        startActivity(new Intent(StarUI.this, UserUiActivity.class));
    }
}

