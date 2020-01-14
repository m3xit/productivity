package com.example.productivity.Notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.productivity.R;
import com.example.productivity.stuff.GridSpacingItemDecoration;
import com.example.productivity.stuff.HorizontalSpaceItemDecoration;
import com.example.productivity.stuff.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity implements NotesAdapter.ItemClickListener, TodoAdapter.ItemClickListener {

    private RecyclerView notesView;
    private RecyclerView todoView;

    private NotesAdapter notesAdapter;
    private List<Note> noteList;

    private TodoAdapter todoAdapter;
    private List<Todo> todoList;

    private static String key = "com.example.productivity.ToDoActivity.todoString";

    static String editType = "com.example.productivity.ToDoActivity.editType";
    static int editTypeNote = 0;
    static int editTypeTodo = 1;

    static String noteEditExtra = "com.example.productivity.ToDoActivity.noteEdit";
    static String noteReturnExtra = "com.example.productivity.ToDoActivity.noteReturn";
    private int requestCodeEditNote = 1;

    static String todoEditExtra = "com.example.productivity.ToDoActivity.todoEdit";
    static String todoReturnExtra = "com.example.productivity.ToDoActivity.todoReturn";
    private int requestCodeEditTodo = 2;

    private int noteEdited = 0;
    private int todoEdited = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.todo);
        setContentView(R.layout.activity_notes);

        todoList = new ArrayList<>();
        todoList.add(new Todo("Title1", "Body1Body1Body1Body1Body1"));
        todoList.add(new Todo("Title2", "Body1Body1Body1Body1Body2"));
        todoList.add(new Todo("Title3", "Body1Body1Body1Body1Body3"));
        todoList.add(new Todo("Title4", "Body1Body1Body1Body1Body4"));
        todoList.add(new Todo("Title5", "Body1Body1Body1Body1Body5"));
        todoList.add(new Todo("Title6", "Body1Body1Body1Body1Body6"));
        todoList.add(new Todo("Title7", "Body1Body1Body1Body1Body7"));
        todoList.add(new Todo("Title8", "Body1Body1Body1Body1Body8"));
        todoList.add(new Todo("Title9", "Body1Body1Body1Body1Body9"));
        todoList.add(new Todo("Title10", "Body1Body1Body1Body1Body10"));

        todoView = findViewById(R.id.todo);
        todoView.setHasFixedSize(true);

        todoAdapter = new TodoAdapter(todoList);
        todoAdapter.setClickListener(this);
        todoView.setLayoutManager(new LinearLayoutManager(this));
        todoView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        todoView.setAdapter(todoAdapter);

        //SharedPreferences settings = getApplicationContext().getSharedPreferences(key, 0);
        //String todoString = settings.getString(key, "");



        noteList = new ArrayList<>();
        noteList.add(new Note("Test1", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test2", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test3", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test4", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test5", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test6", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test7", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test8", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test9", "BodyBodyBodyBodyBodyBodyBody"));
        noteList.add(new Note("Test10", "BodyBodyBodyBodyBodyBodyBody"));

        notesView = findViewById(R.id.notes);
        notesView.setHasFixedSize(true);

        notesAdapter = new NotesAdapter(noteList);
        notesAdapter.setClickListener(this);
        notesView.setLayoutManager(new GridLayoutManager(this, 2));
        notesView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        notesView.setAdapter(notesAdapter);
    }

//        SharedPreferences settings = getApplicationContext().getSharedPreferences(key, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(key, todoString);
//        editor.apply();

    @Override
    public void onItemClick(View view, int position) {
        if (view.getId() == R.id.notes) {
            Intent intent = new Intent(this, EditNoteActivity.class);
            noteEdited = position;
            intent.putExtra(editType, editTypeNote);
            intent.putExtra(noteEditExtra, noteList.get(noteEdited));
            startActivityForResult(intent, requestCodeEditNote);
        } else if (view.getId() == R.id.todoImageView) {
            Intent intent = new Intent(this, EditNoteActivity.class);
            todoEdited = position;
            intent.putExtra(editType, editTypeTodo);
            intent.putExtra(todoEditExtra, todoList.get(todoEdited));
            startActivityForResult(intent, requestCodeEditTodo);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeEditNote) {
            if (resultCode == RESULT_OK) {
                noteList.remove(noteEdited);
                noteList.add(0, (Note) data.getExtras().get(noteReturnExtra));
                notesAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == requestCodeEditTodo) {
            if (resultCode == RESULT_OK) {
                todoList.set(todoEdited, (Todo) data.getExtras().get(todoReturnExtra));
                todoAdapter.notifyItemChanged(todoEdited);
            }
        }
    }
}
