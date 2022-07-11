package com.luisf.learning.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.luisf.learning.listadetarefas.R;
import com.luisf.learning.listadetarefas.helper.TaskDAO;
import com.luisf.learning.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText descriptionTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        descriptionTask = findViewById(R.id.descriptionTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOption_check_task:
                String taskInsert = descriptionTask.getText().toString();
                if (!taskInsert.isEmpty()) {
                    // Exec action to save data...
                    TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                    Tarefa myTask = new Tarefa();
                    myTask.setDescriptionTask(taskInsert);

                    taskDAO.insert(myTask);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}