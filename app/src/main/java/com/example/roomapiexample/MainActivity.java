package com.example.roomapiexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    TextView textViewID;
    EditText editTextName;
    EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewID = findViewById(R.id.tVID);
        editTextName = findViewById(R.id.eTName);
        editTextEmail = findViewById(R.id.eTEmail);

        db = Room.databaseBuilder(this, AppDatabase.class, "User").build();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void saveButton(View v){
        //Room API calls should be in a separate thread
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.name = editTextName.getText().toString();
                user.email = editTextEmail.getText().toString();
                UserDao userDao = db.userDao();
                userDao.insertOne(user);
            }
        });
    }

    public void searchButton(View v){
        //Room API calls should be in a separate thread
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = db.userDao();
                User user = userDao.findByName(editTextName.getText().toString());
                //UI work should be done on a UI Thread
                textViewID.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewID.setText(user.uid+"");
                    }
                });
                //UI work should be done on a UI Thread
                editTextEmail.post(new Runnable() {
                    @Override
                    public void run() {
                        editTextEmail.setText(user.email);
                    }
                });
            }
        });
    }
}