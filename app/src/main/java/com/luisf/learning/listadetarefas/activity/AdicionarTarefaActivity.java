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
    private Tarefa currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        descriptionTask = findViewById(R.id.descriptionTask);

        //get task id when editing data...
        currentTask = (Tarefa) getIntent().getSerializableExtra("selectedTask");
        if ((currentTask != null) && (currentTask.getId() > 0)) {
            descriptionTask.setText(currentTask.getDescriptionTask());
        }
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
                String taskDescription = descriptionTask.getText().toString();

                //valid task description
                if (!taskDescription.isEmpty()) {
                    // Exec action to save data...
                    TaskDAO taskDAO = new TaskDAO(getApplicationContext());

                    //on update
                    if ((currentTask != null) && (currentTask.getId() > 0)) {
                        Tarefa taskUpdate = new Tarefa();

                        taskUpdate.setId(currentTask.getId());
                        taskUpdate.setDescriptionTask(taskDescription);

                        if (taskDAO.update(taskUpdate)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Success on update current task", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Fail on update current task", Toast.LENGTH_LONG).show();
                        }
                    }
                    //on insert
                    else {
                        Tarefa myTask = new Tarefa();
                        myTask.setDescriptionTask(taskDescription);

                        if (taskDAO.insert(myTask)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Success on insert the new task", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Fail on insert the new task", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}