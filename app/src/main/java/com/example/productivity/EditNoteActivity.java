package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        note = (Note) getIntent().getExtras().get(ToDoActivity.noteEditExtra);
    }
}
