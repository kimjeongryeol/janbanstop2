package kr.kjy.janban;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button btn_register;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        et_id = findViewById(R.id.NewUsername);
        et_pass = findViewById(R.id.NewPassword);
        btn_register = findViewById(R.id.buttonSignUpSubmit);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Log.d(TAG, "buttonSignUpSubmit clicked");

                if (userID.isEmpty() || userPass.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform the network request in an AsyncTask
                try {
                    new OkhttpClient_Post().execute("http://3.35.21.200/SignUp.php",userID,userPass).get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
//
//    private class NetworkRequestTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            String userID = params[0];
//            String userPass = params[1];
//
//            try {
//                URL url = new URL("http://3.35.21.200/SignUp.php");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoOutput(true);
//                Log.d( "서버 응답:1 " ,"응답");
//
//                // POST 데이터를 위한 맵 생성
//                Map<String, String> postData = new HashMap<>();
//                postData.put("userID", userID);
//                postData.put("userPassword", userPass);
//                Log.d( "응답:1 " ,"응답");
//                // POST 데이터 작성
//                StringBuilder postDataStringBuilder = new StringBuilder();
//                for (Map.Entry<String, String> entry : postData.entrySet()) {
//                    if (postDataStringBuilder.length() != 0) {
//                        postDataStringBuilder.append('&');
//                    }
//                    postDataStringBuilder.append(entry.getKey());
//                    postDataStringBuilder.append('=');
//                    postDataStringBuilder.append(entry.getValue());
//                }
//                String postDataString = postDataStringBuilder.toString();
//                Log.d( "응답:2 " ,"응답");
//
//                // 연결에 POST 데이터 작성
//                try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
//                    byte[] postDataBytes = postDataString.getBytes("UTF-8");
//                    os.write(postDataBytes);
//                }Log.d( "응답:3 " ,"응답");
//
//                int responseCode = connection.getResponseCode();
//                Log.d( "응답:4 " ,"응답");
//                Log.d("Response Code", String.valueOf(responseCode));
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    // 응답 처리
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    String line;
//                    StringBuilder response = new StringBuilder();
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//                    reader.close();
//
//                    String responseString = response.toString();
//                    Log.d( "응답 5 " ,"응답");
//                    return responseString;
//                } else {
//                    return null; // 요청 실패
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null; // 요청 실패
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String responseString) {
//            if (responseString != null) {
//                // 서버 응답은 responseString에 있습니다
//                Log.d(TAG, "서버 응답:3 " + responseString);
//                // 필요한 경우 사용자에게 응답을 표시할 수도 있습니다
//                // 예를 들어, Toast.makeText(SignUpActivity.this, "응답: " + responseString, Toast.LENGTH_SHORT).show();
//            } else {
//                // 등록 실패
//                Log.e(TAG, "네트워크 요청 실패");
//                Toast.makeText(SignUpActivity.this, "등록 실패", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public class OkhttpClient_Post extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();
        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String strUrl = params[0];
            Log.d("ddd1","ddd");
            RequestBody formBody = new FormBody.Builder()
                    .add("userID", params[1])
                    .add("password", params[2])
                    .build();

            /**
             * new OkhttpClient_Post().execute(url,my message).get();
             * 첫번째 파라미터로 api 통신할 url 입력하고, 두번째 파라미터로 url 에 보낼 메세지값을 입력한다.
             * api 받는 서버에서 어떤형식으로 받아올지 는 내가 add 에 입력한 값으로.
             * 내가 "message" / 메세지 ! ////라고 보내면 서버에서 message 값을 받으면 메세지 ! 값이 받아져온다.
             */
            Log.d("ddd2","ddd");
            Request request = new Request.Builder().url(strUrl)
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
                Log.d("ddd3","ddd");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

    }
}
