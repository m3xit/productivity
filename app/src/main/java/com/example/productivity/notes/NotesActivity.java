package com.example.productivity.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;

import com.example.productivity.MainActivity;
import com.example.productivity.R;
import com.example.productivity.stuff.GridSpacingItemDecoration;
import com.example.productivity.stuff.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.ItemClickListener,
        TodoAdapter.ItemClickListener, TodoAdapter.TextWatcher, TodoAdapter.OnCheckedChangeListener,
        DoneAdapter.ItemClickListener, DoneAdapter.TextWatcher, DoneAdapter.OnCheckedChangeListener {

    private RecyclerView notesView;
    private RecyclerView todoView;
    private RecyclerView doneView;

    private NotesAdapter notesAdapter;
    private List<Note> noteList;

    private TodoAdapter todoAdapter;
    private List<Todo> todoList;
    private DoneAdapter doneAdapter;
    private List<Todo> doneList;

    static String editType = "com.example.productivity.NotesActivity.editType";
    static int editTypeNote = 0;
    static int editTypeTodo = 1;

    private final String noteKey = "com.example.productivity.NotesActivity.noteKey";
    static String noteEditExtra = "com.example.productivity.NotesActivity.noteEdit";
    static String noteReturnExtra = "com.example.productivity.NotesActivity.noteReturn";
    private int requestCodeEditNote = 1;

    private final String doneKey = "com.example.productivity.NotesActivity.doneKey";
    private final String todoKey = "com.example.productivity.NotesActivity.todoKey";
    static String todoEditExtra = "com.example.productivity.NotesActivity.todoEdit";
    static String todoReturnExtra = "com.example.productivity.NotesActivity.todoReturn";
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
        if (view.getId() == R.id.notes) {

            Intent intent = new Intent(this, EditNoteActivity.class);

            noteEdited = position;
            intent.putExtra(editType, editTypeNote);
            if (position == noteList.size()) {
                noteList.add(new Note("", ""));
                intent.putExtra(noteEditExtra, noteList.get(noteEdited));
            } else {
                intent.putExtra(noteEditExtra, noteList.get(noteEdited));
            }

            startActivityForResult(intent, requestCodeEditNote);

        } else if (view.getId() == R.id.todoImageView) {

            Intent intent = new Intent(this, EditNoteActivity.class);

            todoEdited = position;
            intent.putExtra(editType, editTypeTodo);
            if (position == todoList.size()) {
                todoList.add(new Todo("", ""));
                intent.putExtra(noteEditExtra, todoList.get(todoEdited));
            } else {
                intent.putExtra(noteEditExtra, todoList.get(todoEdited));
            }
//            if (position == todoList.size()) {
//                todoList.add(new Todo("", ""));
//                intent.putExtra(todoEditExtra, todoList.get(todoEdited));
//            } else {
//                intent.putExtra(todoEditExtra, todoList.get(todoEdited));
//            }

            startActivityForResult(intent, requestCodeEditTodo);

        } else if (view.getId() == DoneAdapter.ID_CANCEL) {
            doneList.remove(position);

            doneAdapter.notifyDataSetChanged();

            writeDone();
        }
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
        todoAdapter.setTextWatcher(this);
        todoAdapter.setCheckListener(this);
        todoView.setLayoutManager(new LinearLayoutManager(this));
        todoView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        todoView.setAdapter(todoAdapter);

        doneView = findViewById(R.id.done);
        doneView.setHasFixedSize(true);
        doneAdapter = new DoneAdapter(doneList);
        doneAdapter.setClickListener(this);
        doneAdapter.setTextWatcher(this);
        doneAdapter.setCheckListener(this);
        doneView.setLayoutManager(new LinearLayoutManager(this));
        doneView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        doneView.setAdapter(doneAdapter);

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

        doneList = (ArrayList<Todo>) MainActivity.store.read(doneKey);

        if (doneList == null) {
            doneList = new ArrayList<>();
        }

        noteList = (ArrayList<Note>) MainActivity.store.read(noteKey);

        if (noteList == null) {
            noteList = new ArrayList<>();
        }
    }

    private void writeDone() {
        MainActivity.store.write(doneKey, doneList);
    }

    private void writeTodos() {
        MainActivity.store.write(todoKey, todoList);
    }

    private void writeNotes() {
        MainActivity.store.write(noteKey, noteList);
    }

    @Override
    public void afterTextChanged(Editable s, int position, int type) {

        if (type == TodoAdapter.TYPE) {
            if (position == todoList.size()) {
                //todoList.add(new Todo(s.toString(), ""));
            } else {
                String title = todoList.get(position).getTitle();
                if (!title.equals(s.toString())) {
                    todoList.set(position, new Todo(s.toString(), todoList.get(position).getBody()));
                    todoAdapter.notifyItemChanged(position);
                    writeTodos();
                }
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position, int type) {
        if (type == TodoAdapter.TYPE) {
            if (position < todoList.size()) {
                doneList.add(todoList.remove(position));

                todoAdapter.notifyDataSetChanged();
                doneAdapter.notifyDataSetChanged();
            } else {
                todoAdapter.setCheckListener(null);
                buttonView.setChecked(false);
                todoAdapter.setCheckListener(this);
            }
        } else if (type == DoneAdapter.TYPE){
            todoList.add(doneList.remove(position));

            todoAdapter.notifyDataSetChanged();
            doneAdapter.notifyDataSetChanged();
        }

        writeTodos();
        writeDone();
    }
}
