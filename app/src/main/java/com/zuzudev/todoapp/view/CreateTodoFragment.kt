package com.zuzudev.todoapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.zuzudev.todoapp.R
import com.zuzudev.todoapp.databinding.FragmentCreateTodoBinding
import com.zuzudev.todoapp.databinding.FragmentTodoListBinding
import com.zuzudev.todoapp.model.Todo
import com.zuzudev.todoapp.util.NotificationHelper
import com.zuzudev.todoapp.util.TodoWorker
import com.zuzudev.todoapp.viewmodel.DetailTodoViewModel
import java.util.concurrent.TimeUnit


class CreateTodoFragment : Fragment(), RadioClickListener, TodoEditClick {
    private lateinit var viewModel:DetailTodoViewModel
    private lateinit var binding: FragmentCreateTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), NotificationHelper.REQUEST_NOTIF)
        }

        binding.todo = Todo("","",3, 0)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        binding.saveListener = this
        binding.radioListener = this



        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

//        binding.btnSubmit.setOnClickListener {

//        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==NotificationHelper.REQUEST_NOTIF) {
            if(grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                NotificationHelper(requireContext())
                    .createNotification("Todo Created",
                        "A new todo has been created! Stay focus!")
            }
        }
    }

    override fun onTodoEditClick(v: View) {
//        val notif = NotificationHelper(view?.context)
//            notif.createNotification("Todo Created", "A new todo has been created! Stay focus!")

            val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(20, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                        "title" to "Todo created",
                        "message" to "Stay focus"
                    )
                )
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)



//            val list = listOf(todo)
            viewModel.addTodo(binding.todo!!)
            Toast.makeText(view?.context, "Data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(v).popBackStack()
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }


}