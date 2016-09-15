package com.manan.mjnotes.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.*;

/**
 * Created by Manan on 6/28/2016.
 */
public class NotesDataSource {

    private static final String PREFKEY="notes";
    private SharedPreferences shared;
    public NotesDataSource(Context context)
    {
        shared=context.getSharedPreferences(PREFKEY,Context.MODE_PRIVATE);
    }

    public List<NoteItem> getAllNotes()
    {
        Map<String,?> allNotes=shared.getAll();
        allNotes.remove(allNotes.get(0));
        List<NoteItem> notes=new ArrayList<NoteItem>();


        SortedSet<String> sortedNotes=new TreeSet<String>(allNotes.keySet());
        for(String s:sortedNotes)
        {
            NoteItem note=new NoteItem();
            note.setKey(s);
            note.setValue(String.valueOf(allNotes.get(s)));
            notes.add(note);
        }

        return notes;
    }


    public boolean update(NoteItem note)
    {
        SharedPreferences.Editor editor=shared.edit();
        editor.putString(note.getKey(),note.getValue());
        editor.commit();
        return true;
    }

    public boolean remove(NoteItem note)
    {
        if(shared.contains(note.getKey()))
        {
            SharedPreferences.Editor editor=shared.edit();
            editor.remove(note.getKey());
            editor.commit();
        }

        return true;
    }
}
