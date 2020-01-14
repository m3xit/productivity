package com.example.productivity.Notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.productivity.MainActivity;
import com.example.productivity.R;
import com.example.productivity.stuff.GridSpacingItemDecoration;
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

    static String editType = "com.example.productivity.ToDoActivity.editType";
    static int editTypeNote = 0;
    static int editTypeTodo = 1;

    private final String noteKey = "com.example.productivity.ToDoActivity.noteKey";
    static String noteEditExtra = "com.example.productivity.ToDoActivity.noteEdit";
    static String noteReturnExtra = "com.example.productivity.ToDoActivity.noteReturn";
    private int requestCodeEditNote = 1;

    private final String todoKey = "com.example.productivity.ToDoActivity.todoKey";
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

        readData();

        initializeAdapters();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        int requestCode = 0;

        if (view.getId() == R.id.notes) {

            requestCode = requestCodeEditNote;
            noteEdited = position;
            intent.putExtra(editType, editTypeNote);
            intent.putExtra(noteEditExtra, noteList.get(noteEdited));

        } else if (view.getId() == R.id.todoImageView) {

            requestCode = requestCodeEditTodo;
            todoEdited = position;
            intent.putExtra(editType, editTypeTodo);
            if (position == todoList.size()) {
                todoList.add(new Todo("", ""));
                intent.putExtra(todoEditExtra, todoList.get(position));
            } else {
                intent.putExtra(todoEditExtra, todoList.get(todoEdited));
            }
        }

        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == requestCodeEditNote) {

                noteList.remove(noteEdited);
                noteList.add(0, (Note) data.getExtras().get(noteReturnExtra));
                notesAdapter.notifyDataSetChanged();
                writeNotes();

            } else if (requestCode == requestCodeEditTodo) {

                todoList.set(todoEdited, (Todo) data.getExtras().get(todoReturnExtra));
                todoAdapter.notifyItemChanged(todoEdited);
                writeTodos();
            }
        }
    }

    private void initializeAdapters() {
        todoView = findViewById(R.id.todo);
        todoView.setHasFixedSize(true);
        todoAdapter = new TodoAdapter(todoList);
        todoAdapter.setClickListener(this);
        todoView.setLayoutManager(new LinearLayoutManager(this));
        todoView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        todoView.setAdapter(todoAdapter);

        notesView = findViewById(R.id.notes);
        notesView.setHasFixedSize(true);
        notesAdapter = new NotesAdapter(noteList);
        notesAdapter.setClickListener(this);
        notesView.setLayoutManager(new GridLayoutManager(this, 2));
        notesView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        notesView.setAdapter(notesAdapter);
    }

    private void readData() {
        todoList = (ArrayList<Todo>) MainActivity.store.read(todoKey);

        if (todoList == null) {
            todoList = new ArrayList<>();
        }

        noteList = (ArrayList<Note>) MainActivity.store.read(noteKey);

        if (noteList == null) {
            noteList = new ArrayList<>();
        }
    }

    private void writeTodos() {
        MainActivity.store.write(todoKey, todoList);
    }

    private void writeNotes() {
        MainActivity.store.write(noteKey, noteList);

    }
}
