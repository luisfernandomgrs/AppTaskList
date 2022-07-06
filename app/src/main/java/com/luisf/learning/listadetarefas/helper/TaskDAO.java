package com.luisf.learning.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luisf.learning.listadetarefas.model.Tarefa;

import java.util.List;

// this class type require a interface class...
public class TaskDAO implements ITaskDAO {
    private SQLiteDatabase writeEscreve;
    private SQLiteDatabase readLe;

    public TaskDAO(Context context) {
        DbHelper db = new DbHelper(context);
        writeEscreve = db.getWritableDatabase();
        readLe = db.getReadableDatabase();
    }

    @Override
    public boolean insert(Tarefa taskContent) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("description", taskContent.getDescriptionTask());
            //cv.put("status", "D"); //in case de other new parameter

            writeEscreve.insert(DbHelper.TABLE_TASK, null, cv);

            Log.i("INFO_DB", "Success on inserting new records on table");
        } catch (Exception e) {
            Log.i("INFO_DB", "Fail on inserting new records on table | " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Tarefa tarefa) {
        return false;
    }

    @Override
    public boolean delete(Tarefa tarefa) {
        return false;
    }

    @Override
    public List<Tarefa> selectAll() {
        return null;
    }
}
