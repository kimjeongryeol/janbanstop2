package kr.kjy.janban;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserUiActivity extends AppCompatActivity {

    private TextView dateTextView1;
    private TextView dateTextView2;
    private TextView dateTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ui);

        dateTextView1 = findViewById(R.id.dateTextView1);
        dateTextView2 = findViewById(R.id.dateTextView2);
        dateTextView3 = findViewById(R.id.dateTextView3);

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
            dateTextView1.setText("Unable to retrieve date and time.");
        }
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
}
