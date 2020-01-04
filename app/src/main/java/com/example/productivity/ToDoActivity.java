package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.widget.EditText;

public class ToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("To Do");
        setContentView(R.layout.activity_to_do);

        EditText todoText = findViewById(R.id.todo_text);
        String todoString = "";
        String input = "App programmieren\nCookie spazieren\nMasterarbeit";
        for(String s : input.split("\n")) {
//            SpannableString string = new SpannableString(s);
//            string.setSpan(new BulletSpan(), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            todoString += "\u2022 " + s + "\n";
        }

        todoString += "\u2022 ";
        todoText.setText(todoString);
    }
}
