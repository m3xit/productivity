package com.example.productivity.Notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.productivity.R;

public class EditNoteActivity extends AppCompatActivity {

    private EditText title, body;

    private int type = -1;

    private Note note;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit);

        setTitle(R.string.edit_notes);

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);

        Intent intent = getIntent();
        type = (int) intent.getExtras().get(NotesActivity.editType);

        if (type == NotesActivity.editTypeNote) {
            note = (Note) intent.getExtras().get(NotesActivity.noteEditExtra);
            title.setText(note.getTitle());
            body.setText(note.getBody());
        } else if (type == NotesActivity.editTypeTodo) {
            todo = (Todo) intent.getExtras().get(NotesActivity.todoEditExtra);
            title.setText(todo.getTitle());
            body.setText(todo.getBody());
        }
    }

    @Override
    public void onBackPressed() {
        result();

        super.onBackPressed();
    }

    private void result() {
        Intent result = new Intent();

        if (type == NotesActivity.editTypeNote) {
            result.putExtra(NotesActivity.noteReturnExtra, new Note(title.getText().toString(), body.getText().toString()));
        } else if (type == NotesActivity.editTypeTodo) {
            result.putExtra(NotesActivity.todoReturnExtra, new Todo(title.getText().toString(), body.getText().toString()));
        }

        setResult(RESULT_OK, result);
    }
}
