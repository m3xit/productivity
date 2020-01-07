package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.widget.EditText;

public class ToDoActivity extends AppCompatActivity {

    private EditText todoText;
    private static String key = "com.example.productivity.todoString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("To Do");
        setContentView(R.layout.activity_to_do);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(key, 0);
        String todoString = settings.getString(key, "");

        todoText = findViewById(R.id.todo_text);
        String input = "";

        if (todoString != "null") {
            input = todoString;
            todoString = "";
        }

        for(String s : input.split("\n")) {
            if (s.length() > 0 && !s.substring(0, 1).equals("\u2022")) {
                System.out.println(s.substring(0, 1));
                todoString += "\u2022 " + s + "\n";
            } else {
                todoString += s + "\n";
            }
        }

        todoString = todoString.substring(0, todoString.length()-1);

        if (!todoString.endsWith("\u2022 ")) {
            todoString += "\n\u2022 ";
        }

        todoText.setText(todoString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String todoString = todoText.getText().toString();

        SharedPreferences settings = getApplicationContext().getSharedPreferences(key, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, todoString);
        editor.apply();
    }
}
