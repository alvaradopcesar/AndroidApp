package iosco.app.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iosco.app.helpers.BaseDAO;

/**
 * Created by usuario on 11/03/2016.
 */
public class NoteDao extends BaseDAO {
    private static final String TAG = NoteDao.class.getSimpleName();
    private static NoteDao noteDao;

    public synchronized static NoteDao getInstance(Context context){
        if(noteDao == null){
            noteDao = new NoteDao(context);
        }
        return noteDao;
    }

    public NoteDao(Context context) {
        initDBHelper(context);
    }

    public String getNote(int id) {
        String flag = "";
        int count = 0;
        try{
            openDBHelper();
            SQL = "select * from notes where id="+id;
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    flag = cursor.getString(cursor.getColumnIndex("text"));
                    count++;
                    cursor.moveToNext();
                }
            }
        } catch (Exception ex){
            Log.e(TAG, "Error when getNote");
            ex.printStackTrace();
        }finally {
            cursor.close();
            closeDBHelper();
            Log.v(TAG, "End getNote");
        }

        if(count == 0){
            addNote("",id);
            flag = getNote(id);
        }
        return flag;
    }

    public boolean addNote(String text, int id){
        boolean flag = true;
        Log.v(TAG, "Start addPersona");
        try{
            openDBHelper();
            contentValues = new ContentValues();
            contentValues.put("text", text);
            contentValues.put("id", id);
            dbHelper.getDatabase().insert("notes",null,contentValues);
            dbHelper.setTransactionSuccessful();
        } catch (Exception ex){
            Log.e(TAG, "Error when addNote");
            ex.printStackTrace();
            flag = false;
        } finally {
            Log.v(TAG, "End addNote");
            closeDBHelper();
        }
        return flag;
    }

    public boolean updateNote(String text, int id) {
        String SQL = " id="+id;
        boolean flag = true;
        Log.v(TAG, "Start updateNote");
        try{
            openDBHelper();
            contentValues = new ContentValues();
            contentValues.put("text", text);
            dbHelper.getDatabase().updateWithOnConflict("notes", contentValues, SQL, null, SQLiteDatabase.CONFLICT_IGNORE);
            dbHelper.setTransactionSuccessful();
        } catch (Exception ex){
            Log.e(TAG, "Error when updateNote");
            ex.printStackTrace();
            flag = false;
        } finally {
            Log.e(TAG, "End updateNote");
            closeDBHelper();
        }
        return flag;
    }
}
