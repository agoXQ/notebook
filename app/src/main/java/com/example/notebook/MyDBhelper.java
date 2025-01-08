package com.example.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class MyDBhelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public MyDBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table note(id integer primary key autoincrement,content text,time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //对note的增删改查
    //插入
    public boolean insertData(String content){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date();
        String finaltime = sdf.format(date);
//        db.execSQL("insert into note(content,time) values(content,?)",new Object[]{sdf.format(date)});
        ContentValues values=new ContentValues();
        values.put("content",content);
        values.put("time",finaltime);
        long i = db.insert("note",null,values);
        return i>0? true:false;
    }
    // 删除,根据记录的id进行删除
    public boolean deleteData(String deleteId){
        int i = db.delete("note","id=?",new String[]{deleteId});
        return i>0? true:false;
    }
    //修改数据,根据记录的id值进行更新
    public boolean updateData(String updateId,String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put("content",content);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date();
        String finaltime = sdf.format(date);
        contentValues.put("time",finaltime);
        int i = db.update("note",contentValues,"id=?",new String[]{updateId});
        return i>0?true:false;
    }
    //查询数据,查询所有内容，将查询的内容进行存储，并将对象存入集合中
    public List<Note> query(){
        List<Note> list = new ArrayList<>();
        Cursor cursor = db.query("note",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Note note = new Note();
            note.setId(String.valueOf(cursor.getInt(0)));
            note.setContent(cursor.getString(1));
            note.setTime(cursor.getString(2));
            list.add(note);
        }
        return list;
    }
}
