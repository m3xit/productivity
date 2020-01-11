package com.example.productivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity implements NotesAdapter.ItemClickListener {

    private EditText todoText;
    private RecyclerView notesView;

    private NotesAdapter adapter;
    private List<Note> notes;

    private static String key = "com.example.productivity.todoString";
    static String noteEditExtra = "com.example.productivity.noteEdit";
    static String noteReturnExtra = "com.example.productivity.noteReturn";

    private int requestCodeEditNote = 1;
    private int noteEdited = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.todo);
        setContentView(R.layout.activity_to_do);

        todoText = findViewById(R.id.todo_text);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(key, 0);
        String todoString = settings.getString(key, "");
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

        notes = new ArrayList<>();
        notes.add(new Note("Test1", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test2", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test3", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test4", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test5", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test6", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test7", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test8", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test9", "BodyBodyBodyBodyBodyBodyBody"));
        notes.add(new Note("Test10", "BodyBodyBodyBodyBodyBodyBody"));

        notesView = findViewById(R.id.notes);
        notesView.setHasFixedSize(true);

        adapter = new NotesAdapter(notes);
        adapter.setClickListener(this);
        notesView.setLayoutManager(new GridLayoutManager(this, 2));
        notesView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        notesView.setAdapter(adapter);
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

    @Override
    public void onItemClick(View view, int position) {
        System.out.println(position + "''''''''''''''''''''''''''''''''");
        Intent intent = new Intent(this, EditNoteActivity.class);
        noteEdited = position;
        intent.putExtra(noteEditExtra, notes.get(noteEdited));
        startActivityForResult(intent, requestCodeEditNote);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeEditNote) {
            if (resultCode == RESULT_OK) {
                notes.remove(noteEdited);
                notes.add(0, (Note) data.getExtras().get(noteReturnExtra));
                adapter.notifyDataSetChanged();
            }
        }
    }
}

class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}