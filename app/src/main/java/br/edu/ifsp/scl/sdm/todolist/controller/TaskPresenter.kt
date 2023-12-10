package br.edu.ifsp.scl.sdm.todolist.controller

import androidx.fragment.app.Fragment
import androidx.room.Room
import br.edu.ifsp.scl.sdm.todolist.model.database.ToDoListDatabase
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskPresenter(private val taskView: TaskView) {

    private val taskdaoImpl = Room.databaseBuilder(
        (taskView as Fragment).requireContext(),
        ToDoListDatabase::class.java,
        ToDoListDatabase.TO_DO_LIST_DATABASE
    ).build().getTaskDao()

    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskdaoImpl.createTask(task)

        }
    }

    fun getTasks() {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = taskdaoImpl.retrieveTasks()
            taskView.updateTaskList(tasks)
        }
    }

    fun editTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskdaoImpl.updateTask(task)
        }
    }

    fun removeTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskdaoImpl.deleteTask(task)
        }
    }

    interface TaskView {
        fun updateTaskList(tasks: List<Task>)
    }
}