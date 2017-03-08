
package com.jishang.bimeng.utils.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Xing,Ming
 * @version 2016年6月3日 下午6:45:32
 */
public class SQLOperateImpl implements SQLOperate {
    private DBOpenHelper dbOpenHelper;

    public SQLOperateImpl(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    @Override
    public void insert(PersonEntity p) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper._ID, p.getHuanid());
        values.put(DBOpenHelper.NAME, p.getName());
        values.put(DBOpenHelper.IMG, p.getImg());
        db.insert(DBOpenHelper.STUDENT_TABLE, null, values);
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(DBOpenHelper.STUDENT_TABLE, DBOpenHelper._ID + "=?", new String[] {
            String.valueOf(id)
        });

    }

    @Override
    public void updatedata(PersonEntity p) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper._ID, p.getHuanid());
        values.put(DBOpenHelper.NAME, p.getName());
        db.update(DBOpenHelper.STUDENT_TABLE, values, DBOpenHelper._ID + "=?", new String[] {
            String.valueOf(p.getHuanid())
        });

    }

    @Override
    public List<PersonEntity> find() {
        List<PersonEntity> persons = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.STUDENT_TABLE, null, null, null, null, null, null);
        if (cursor != null) {
            persons = new ArrayList<PersonEntity>();
            while (cursor.moveToNext()) {
                PersonEntity person = new PersonEntity();
                String huanid = cursor.getString(cursor.getColumnIndex(DBOpenHelper._ID));
                String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME));
                String img = cursor.getString(cursor.getColumnIndex(DBOpenHelper.IMG));
                person.setHuanid(huanid);
                ;
                person.setName(name);
                person.setImg(img);
                persons.add(person);
            }
        }

        return persons;
    }

    @Override
    public PersonEntity findById(String huanid) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.STUDENT_TABLE, null, DBOpenHelper._ID + "=?",
                new String[] {
                    String.valueOf(huanid)
                }, null, null, null);
        PersonEntity person = null;
        if (cursor != null && cursor.moveToFirst()) {
            person = new PersonEntity();
            int _id = cursor.getInt(cursor.getColumnIndex(DBOpenHelper._ID));
            String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NAME));
            person.setHuanid(huanid);
            ;
            person.setName(name);
        }
        return person;
    }

}
