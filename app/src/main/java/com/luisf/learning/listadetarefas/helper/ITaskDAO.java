package com.luisf.learning.listadetarefas.helper;

import com.luisf.learning.listadetarefas.model.Tarefa;
import java.util.List;

// This class type of Interface, is required by class TaskDAO
public interface ITaskDAO {
    public boolean insert(Tarefa tarefa);
    public boolean update(Tarefa tarefa);
    public boolean delete(Tarefa tarefa);
    public List<Tarefa> selectAll();
}
