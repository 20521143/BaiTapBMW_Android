package com.camoi.baitapadroidSqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    //reference to button and other controls on the layout

    Button btn_signUp, btn_cancel;
    EditText et_userName, et_mail, et_passWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_signUp = findViewById(R.id.btn_signUp);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_userName = findViewById(R.id.et_userName);
        et_mail = findViewById(R.id.et_mail);
        et_passWord = findViewById(R.id.et_passWord);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            UserModel userModel;
            @Override
            public void onClick(View v) {
                try {
                    userModel = new UserModel(-1, et_userName.getText().toString(), et_passWord.getText().toString(), et_mail.getText().toString());
                    Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();

                    userModel = new UserModel(-1, "Error", "Error", "Error");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(userModel);
                Toast.makeText(MainActivity.this, "Success: " + success , Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });




    }
}