package kr.kjy.janban;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText newUsernameEditText;
    private EditText newPasswordEditText;
    private Button signUpSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        newUsernameEditText = findViewById(R.id.NewUsername);
        newPasswordEditText = findViewById(R.id.NewPassword);
        signUpSubmitButton = findViewById(R.id.buttonSignUpSubmit);

        signUpSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = newUsernameEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (isValidSignUp(newUsername, newPassword)) {
                    // 회원 가입 작업 수행 (예: 새 사용자를 데이터베이스에 저장)
                    showToast("회원 가입 성공!");
                    finish(); // 회원 가입 액티비티 닫기
                } else {
                    // 오류 메시지 표시
                    showToast("회원 가입 실패. 사용자 이름이 이미 존재하거나 잘못된 데이터입니다.");
                }
            }
        });
    }

    private boolean isValidSignUp(String newUsername, String newPassword) {
        // 이 로직을 실제로 유효성 검사 논리로 교체하세요 (예: 사용자 이름이 이미 존재하는지 확인)
        // 이 예에서는 필드가 비어 있지 않으면 회원 가입이 성공했다고 가정합니다.
        return !newUsername.isEmpty() && !newPassword.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
