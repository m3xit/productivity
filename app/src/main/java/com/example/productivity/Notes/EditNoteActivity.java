package com.example.productivity.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.productivity.R;

public class EditNoteActivity extends AppCompatActivity {

    private EditText title, body;

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        note = (Note) getIntent().getExtras().get(ToDoActivity.noteEditExtra);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);

        title.setText(note.getTitle());
        body.setText(note.getBody());
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent();
        result.putExtra(ToDoActivity.noteReturnExtra, new Note(title.getText().toString(), body.getText().toString()));
        setResult(RESULT_OK, result);

        super.onBackPressed();
    }
}
