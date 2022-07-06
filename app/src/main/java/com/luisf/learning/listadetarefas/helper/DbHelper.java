package com.luisf.learning.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
   public static int VERSION = 1;
   public static String NOME_DB = "db_taskList";
   public static String TABLE_TASK = "task";

   public DbHelper(@Nullable Context context) {
      super(context, NOME_DB, null, VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      /*
      * Create first table into database
      * */
      String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + " (" +
              "id INTEGER PRIMARY KEY AUTOINCREMENT," +
              "description VARCHAR(255) NOT NULL);"; //The type TEXT, is very longer to your data

      try {
         db.execSQL(sql);
         Log.i("INFO DB", "Success when create table on database");
      } catch (Exception e) {
         Log.i("INFO DB", "Create table error, " + e.getMessage());
      }
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //This method is called when your application is updated, in case of
      // install a new version when has is installed a old version of
      // application.
   }
}