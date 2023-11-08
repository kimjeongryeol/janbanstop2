package kr.kjy.janban;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class UserUiActivity extends AppCompatActivity {
    private TextView dateTextView1;
    private TextView dateTextView2;
    private TextView dateTextView3;
    private TextView menuText;
    private TextView menuText2;
    private TextView menuText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ui);

        dateTextView1 = findViewById(R.id.dateTextView1);
        dateTextView2 = findViewById(R.id.dateTextView2);
        dateTextView3 = findViewById(R.id.dateTextView3);
        menuText = findViewById(R.id.menuText1);
        menuText2 = findViewById(R.id.menuText2); // Add menuText2 reference
        menuText3 = findViewById(R.id.menuText3); // Add menuText3 reference

        // Allow network requests in the main thread (for demonstration purposes only)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get the current date and time
        Date currentDate = new Date();

        if (currentDate != null) {
            dateTextView1.setText(formatDate(currentDate, "yyyy-MM-dd")); // Current date
            dateTextView2.setText(formatDate(getPreviousDate(currentDate, 1), "yyyy-MM-dd")); // Yesterday
            dateTextView3.setText(formatDate(getPreviousDate(currentDate, 2), "yyyy-MM-dd")); // Day before yesterday
        } else {
            dateTextView1.setText("Unable to retrieve date and time");
        }

        // Replace with the URL of your PHP script
        String scriptUrl = "http://tina908.dothome.co.kr/receive.php";

        // Replace with the date you want to retrieve the menu for
        String date = formatDate(currentDate, "yyyy-MM-dd");
        new RetrieveMenuTask(menuText).execute(scriptUrl, formatDate(getPreviousDate(currentDate, 0), "yyyy-MM-dd"));
// Execute the network request asynchronously for menuText2 with the previous date
        // Execute the network request asynchronously for menuText2 with the previous date
        new RetrieveMenuTask(menuText2).execute(scriptUrl, formatDate(getPreviousDate(currentDate, 1), "yyyy-MM-dd"));
// Execute the network request asynchronously for menuText3 with the date before yesterday
        new RetrieveMenuTask(menuText3).execute(scriptUrl, formatDate(getPreviousDate(currentDate, 2), "yyyy-MM-dd"));

    }

    private Date getPreviousDate(Date currentDate, int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
        return calendar.getTime();
    }

    private String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public class RetrieveMenuTask extends AsyncTask<String, Void, String> {
        private TextView menuTextView;

        public RetrieveMenuTask(TextView menuTextView) {
            this.menuTextView = menuTextView;
        }
        @Override
        protected String doInBackground(String... params) {
            String scriptUrl = params[0];
            String date = params[1];
            String menuData = null;

            try {
                URL menuUrl = new URL(scriptUrl + "?date=" + date);
                HttpURLConnection connection = (HttpURLConnection) menuUrl.openConnection();

                // Read the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                // Parse the JSON response
                JSONObject json = new JSONObject(response.toString());
                menuData = json.optString("menu");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MyApp", "Error in network request: " + e.getMessage());
            }

            return menuData;
        }

        protected void onPostExecute(String menuData) {
            if ("Menu not found".equals(menuData)) {

                menuTextView.setText("Menu not found");
            } else if (menuData != null && !menuData.isEmpty()) {
                // Handle the case when the menu data is available
                menuTextView.setText(menuData);
            } else {
                // Handle other error cases
                menuTextView.setText("An error occurred");
            }
        }
    }
}
