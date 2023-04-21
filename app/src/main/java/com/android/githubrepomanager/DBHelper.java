package com.android.githubrepomanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE "+Params.TABLE_NAME+"("+Params.KEY_ID+" INTEGER PRIMARY KEY, "+Params.KEY_OWNERNAME+" TEXT, "+Params.KEY_REPONAME+" TEXT, "+Params.KEY_REPODESCRIPTION+" TEXT, "+Params.KEY_URL+" TEXT)";
        Log.d("testing", "create query: "+createQuery);
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRepo(Repo repo){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Params.KEY_OWNERNAME, repo.getOwnerName());
        contentValues.put(Params.KEY_REPONAME, repo.getRepoName());
        contentValues.put(Params.KEY_REPODESCRIPTION, repo.getDescription());
        contentValues.put(Params.KEY_URL, repo.getUrl());

        database.insert(Params.TABLE_NAME, null, contentValues);
        Log.d("testing", "repo inserted successfully");
        database.close();
    }

    public List<Repo> getAllRepo(){
        List<Repo> repoList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "select * from "+Params.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                Repo repo = new Repo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                repoList.add(repo);
            }while (cursor.moveToNext());
        }
        return repoList;
    }
}
