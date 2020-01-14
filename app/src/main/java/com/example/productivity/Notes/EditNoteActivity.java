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

        title = findViewById(R.id.title);
        body = findViewById(R.id.body);

        Intent intent = getIntent();
        type = (int) intent.getExtras().get(ToDoActivity.editType);

        if (type == ToDoActivity.editTypeNote) {
            note = (Note) intent.getExtras().get(ToDoActivity.noteEditExtra);
            title.setText(note.getTitle());
            body.setText(note.getBody());
        } else if (type == ToDoActivity.editTypeTodo) {
            todo = (Todo) intent.getExtras().get(ToDoActivity.todoEditExtra);
            title.setText(todo.getTitle());
            body.setText(todo.getBody());
        }
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent();

        if (type == ToDoActivity.editTypeNote) {
            result.putExtra(ToDoActivity.noteReturnExtra, new Note(title.getText().toString(), body.getText().toString()));
        } else if (type == ToDoActivity.editTypeTodo) {
            result.putExtra(ToDoActivity.todoReturnExtra, new Todo(title.getText().toString(), body.getText().toString()));
        }

        setResult(RESULT_OK, result);

        super.onBackPressed();
    }
}
