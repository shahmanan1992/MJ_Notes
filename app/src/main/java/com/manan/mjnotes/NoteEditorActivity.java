package com.manan.mjnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.manan.mjnotes.data.NoteItem;

/**
 * Created by Manan on 6/29/2016.
 */
public class NoteEditorActivity extends Activity {

    private NoteItem note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=this.getIntent();
        note=new NoteItem();
        note.setKey(intent.getStringExtra("key"));
        note.setValue(intent.getStringExtra("value"));


        EditText edit= (EditText) findViewById(R.id.editText);
        edit.setText(note.getValue());
        edit.setSelection(note.getValue().length());
    }

    private void saveAndFinish()
    {
        EditText edit= (EditText) findViewById(R.id.editText);
        String noteText=edit.getText().toString();

        Intent intent=new Intent();
        intent.putExtra("key",note.getKey());
        intent.putExtra("value",noteText);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            saveAndFinish();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}
