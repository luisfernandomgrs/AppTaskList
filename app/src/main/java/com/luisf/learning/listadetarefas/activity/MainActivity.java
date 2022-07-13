package com.luisf.learning.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luisf.learning.listadetarefas.R;
import com.luisf.learning.listadetarefas.adapter.TarefaAdapter;
import com.luisf.learning.listadetarefas.databinding.ActivityMainBinding;
import com.luisf.learning.listadetarefas.helper.DbHelper;
import com.luisf.learning.listadetarefas.helper.RecyclerItemClickListener;
import com.luisf.learning.listadetarefas.helper.TaskDAO;
import com.luisf.learning.listadetarefas.model.Tarefa;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.scheduling.Task;

public class MainActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa currentTaskSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // recycler settings
        recyclerView = findViewById(R.id.recyclerView);

        // database settings
        DbHelper db = new DbHelper(getApplicationContext());

        /*
        * fields settings - Action to insert records on table
        *
        ContentValues cv = new ContentValues();
        cv.put("description", "Ir ao Supermercado");
        db.getWritableDatabase().insert("task", null, cv);
        *
        * But the recommendation, is use of class DAO (Data Access Object)
        */

        //configurar o evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Log.i("clique", "onItemClick");
                                Tarefa selectedTask = listaTarefas.get(position);
                                Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                intent.putExtra("selectedTask", selectedTask);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                currentTaskSelected = listaTarefas.get(position);

                                //Log.i("clique", "onLongItemClick");
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //setting layout to use the dialog...
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir a tarefa \"" + currentTaskSelected.getDescriptionTask() + "\" ?");

                                dialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                                        if (taskDAO.delete(currentTaskSelected)) {
                                            carregarListaTarefas();
                                            Toast.makeText(getApplicationContext(), "Success on delete current task", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Fail on delete current task", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaTarefas () {
        /*
        //listaTarefas.clear();

        // carregar lista de tarefas
        Tarefa tarefa1 = new Tarefa(); tarefa1.setId(1l); tarefa1.setDescriptionTask("Ir ao mercado");
        listaTarefas.add(tarefa1);
        Tarefa tarefa2 = new Tarefa(); tarefa2.setId(2l); tarefa2.setDescriptionTask("Ir a feira");
        listaTarefas.add(tarefa2);
        Tarefa tarefa3 = new Tarefa(); tarefa3.setId(3l); tarefa3.setDescriptionTask("Ler um livro");
        listaTarefas.add(tarefa3);
        */
        // create viewing to the list of tasks
        TaskDAO taskDAO = new TaskDAO(getApplicationContext());
        listaTarefas = taskDAO.selectAll();

        // configurar adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        // configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            return true;
        //}

        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        this.carregarListaTarefas();
        super.onStart();
    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}