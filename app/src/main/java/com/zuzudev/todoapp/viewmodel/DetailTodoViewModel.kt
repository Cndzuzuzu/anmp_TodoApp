package com.zuzudev.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zuzudev.todoapp.model.Todo
import com.zuzudev.todoapp.model.TodoDatabase
import com.zuzudev.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application)
    :AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val todoLD = MutableLiveData<Todo>()
    fun addTodo(todo:Todo) {
        launch {
//            val db = TodoDatabase.buildDatabase(getApplication())
            val db = buildDb(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    fun fetch(uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

//    fun update(title:String, notes:String, priority:Int, uuid:Int) {
//        launch {
//            val db = buildDb(getApplication())
//            db.todoDao().update(title, notes, priority, uuid)
//        }
//    }
    fun update(todo:Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(todo.title, todo.notes, todo.priority, todo.isDone, todo.uuid)
        }
    }


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}
