package com.zuzudev.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.zuzudev.todoapp.model.Todo

interface TodoCheckedChangeListener {
    fun onTodoCheckedChangeListener(cb: CompoundButton,
                         isChecked:Boolean,
                         obj: Todo)
}
interface TodoEditClick {
    fun onTodoEditClick(v: View)
//    fun onTodoSaveChangesClick(v: View, obj:Todo)
}

interface RadioClickListener {
    fun onRadioClick(v:View)
}


