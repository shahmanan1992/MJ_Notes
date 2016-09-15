package com.manan.mjnotes;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.manan.mjnotes.data.NoteItem;
import com.manan.mjnotes.data.NotesDataSource;

import java.util.List;

public class MainActivity extends ListActivity {

    private static final int EDITOR_ACTIVITY_REQUEST = 1001;
    private static final int MENU_DELETE_ID=1002;
    private int currentNoteId;
    private static NotesDataSource dataSource;
    private List<NoteItem> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu(getListView());

        dataSource=new NotesDataSource(this);
//        List<NoteItem> notes=dataSource.getAllNotes();
//        NoteItem note=notes.get(0);
//        note.setValue("Updated value");
//        dataSource.update(note);

//        notes=dataSource.getAllNotes();
//        Log.i("Notes",note.getKey());
        refreshDisplay();
    }

    private void refreshDisplay() {
        notes=dataSource.getAllNotes();
        ArrayAdapter<NoteItem> adapter=new ArrayAdapter<NoteItem>(this,R.layout.activity_list_item,notes);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.app_create)
        {
            createNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        NoteItem note=NoteItem.getNew();
        Intent intent=new Intent(this,NoteEditorActivity.class);
        intent.putExtra("key",note.getKey());
        intent.putExtra("value",note.getValue());
        startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        NoteItem note=notes.get(position);
        Intent intent=new Intent(this,NoteEditorActivity.class);
        intent.putExtra("key",note.getKey());
        intent.putExtra("value",note.getValue());
        startActivityForResult(intent,EDITOR_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==EDITOR_ACTIVITY_REQUEST && resultCode==RESULT_OK)
        {
            NoteItem note=new NoteItem();
            note.setKey(data.getStringExtra("key"));
            note.setValue(data.getStringExtra("value"));
            dataSource.update(note);
            refreshDisplay();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
        currentNoteId=(int)info.id;
        menu.add(0,MENU_DELETE_ID,0,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==MENU_DELETE_ID)
        {
            NoteItem note=notes.get(currentNoteId);
            dataSource.remove(note);
            refreshDisplay();
        }
        return super.onContextItemSelected(item);
    }
}
