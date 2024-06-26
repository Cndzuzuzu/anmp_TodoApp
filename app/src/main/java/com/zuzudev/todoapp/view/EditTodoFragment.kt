package com.zuzudev.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.zuzudev.todoapp.R
import com.zuzudev.todoapp.databinding.FragmentCreateTodoBinding
import com.zuzudev.todoapp.databinding.FragmentEditTodoBinding
import com.zuzudev.todoapp.model.Todo
import com.zuzudev.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment() , RadioClickListener, TodoEditClick {
    private lateinit var binding:FragmentEditTodoBinding
    private lateinit var viewModel: DetailTodoViewModel
//    private lateinit var todo:Todo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTodoBinding.inflate(inflater,container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        binding.txtHeading.text = "Edit Todo"
        binding.btnSubmit.text = "Save Changes"

//        binding.btnSubmit.setOnClickListener {
//            val radio =
//                view.findViewById<RadioButton>(binding.radioGroupPriority.checkedRadioButtonId)
//            viewModel.update(binding.txtTitle.text.toString(),
//                binding.txtNotes.text.toString(), radio.tag.toString().toInt(), uuid)
//            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }

        binding.radioListener = this
        binding.saveListener = this

    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            binding.todo = it
//            binding.txtTitle.setText(it.title)
//            todo=it
//            binding.txtNotes.setText(it.notes)
//            when (it.priority) {
//                1 -> binding.radioLow.isChecked = true
//                2 -> binding.radioMedium.isChecked = true
//                else -> binding.radioHigh.isChecked = true
//            }

        })
    }

    override fun onRadioClick(v: View) {
//        obj.priority = priority
//        binding.todo.let{
//
//        }
        binding.todo!!.priority = v.tag.toString().toInt()
    }

    override fun onTodoEditClick(v: View) {
        viewModel.update(binding.todo!!)
        Toast.makeText(v.context, "Todo Updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(v).popBackStack()
    }



}