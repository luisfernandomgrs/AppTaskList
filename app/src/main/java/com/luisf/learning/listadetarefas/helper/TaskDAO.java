package com.luisf.learning.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luisf.learning.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

// this class type require a interface class...
public class TaskDAO implements ITaskDAO {
    private SQLiteDatabase dbWrite_Escreve;
    private SQLiteDatabase dbRead_Le;

    public TaskDAO(Context context) {
        DbHelper db = new DbHelper(context);
        dbWrite_Escreve = db.getWritableDatabase();
        dbRead_Le = db.getReadableDatabase();
    }

    @Override
    public boolean insert(Tarefa taskContent) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("description", taskContent.getDescriptionTask());
            //cv.put("status", "D"); //in case de other new parameter

            dbWrite_Escreve.insert(DbHelper.TABLE_TASK, null, cv);

            Log.i("INFO_DB", "Success on inserting new records on table");
        } catch (Exception e) {
            Log.i("INFO_DB", "Fail on inserting new records at table | " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Tarefa tarefa) {

        try {
            ContentValues cv = new ContentValues();
            String[] args = {tarefa.getId().toString()};

            //cv.put("id", String.valueOf(tarefa.getId()));
            cv.put("description", tarefa.getDescriptionTask());

            dbWrite_Escreve.update(DbHelper.TABLE_TASK, cv, "(id=?)", args);
            Log.i("INFO_DB", "Success on update current record at table");
        } catch (Exception e) {
            Log.i("INFO_DB", "Fail on update record at table | " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Tarefa tarefa) {
        try {
            String[] args = {tarefa.getId().toString()};

            dbWrite_Escreve.delete(DbHelper.TABLE_TASK, "(id=?)", args);

            Log.i("INFO_DB", "Success on delete current record, at table");
        } catch (Exception e) {
            Log.i("INFO_DB", "Fail on delete current record, at table | " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> selectAll() {
        int iField_id, iField_descriptionTask;
        List<Tarefa> taskList = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABLE_TASK + ";";
        Cursor cDbTask = dbRead_Le.rawQuery(sql, null);
        iField_id = cDbTask.getColumnIndex("id");
        iField_descriptionTask = cDbTask.getColumnIndex("description");

        try {
            cDbTask.moveToFirst();

            for (int iPosition = cDbTask.getPosition(); iPosition < cDbTask.getCount(); iPosition++) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(cDbTask.getLong(iField_id));
                tarefa.setDescriptionTask(cDbTask.getString(iField_descriptionTask));
                taskList.add(tarefa);
                cDbTask.moveToNext();
            }
        }
        catch (Exception e) {
            return new ArrayList<>();
        }

        return taskList;
    }
}
