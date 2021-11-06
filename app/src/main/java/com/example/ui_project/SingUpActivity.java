package com.example.ui_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingUpActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth; //파이어베이스 인증
    private DatabaseReference databaseReference; //실시간 데이터베이스
    private EditText email, pwd; //회원가입 Email, Pwd 변수
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Yanudo");

        //입력필드 ID 가져오기
        email = findViewById(R.id.registerEmail);
        pwd = findViewById(R.id.registerPWD);
        btnRegister = findViewById(R.id.registerButton);

        //회원가입 시작
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //입력한 이메일, PWD를 String 변환
                String strEmail = email.getText().toString();
                String strPWD = pwd.getText().toString();

                //Firebase 인증 시작
                //입력받은 Email, PWD를 매개변수로 사용용
               firebaseAuth.createUserWithEmailAndPassword(strEmail, strPWD).addOnCompleteListener(SingUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ //회원가입이 성공했을 때
                            //현재 입력한 회원정보를 유저정보로 가져오기
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPWD);

                            databaseReference.child("UserAcoount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SingUpActivity.this, "회원가입 성공!", Toast.LENGTH_LONG);

                        }
                        else{
                            Toast.makeText(SingUpActivity.this, "회원가입 실패!", Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });
    }
}