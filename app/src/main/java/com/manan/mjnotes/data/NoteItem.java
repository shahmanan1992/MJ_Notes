package com.manan.mjnotes.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Manan on 6/28/2016.
 */
public class NoteItem {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static NoteItem getNew()
    {
        Locale locale=new Locale("en_US");
        Locale.setDefault(locale);

        //Creating new date pattern
        String date="yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat format=new SimpleDateFormat(date);
        String key=format.format(new Date());
        NoteItem note=new NoteItem();
        note.setKey(key);
        note.setValue("");
        return note;
    }

    public String toString()
    {
        return this.getValue();
    }
}
