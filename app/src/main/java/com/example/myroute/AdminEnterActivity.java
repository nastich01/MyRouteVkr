package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminEnterActivity extends AppCompatActivity {

    EditText loginBox;
    EditText passwordBox;
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enter);

        loginBox = findViewById(R.id.login);
        passwordBox = findViewById(R.id.password);
        enterButton = findViewById(R.id.enterButton);
    }

    public void enter(View view){
        String login = loginBox.getText().toString();
        String password = passwordBox.getText().toString();
        System.out.println(login+password);
        if ((login.length()==0)||(password.length()==0)){
            Toast.makeText(getApplicationContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
        } else if ((login.equals("admin"))&&(password.equals("111"))){
            Intent intent = new Intent(this, AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Неверный логин и/или пароль!", Toast.LENGTH_SHORT).show();
        }

    }
}