package com.example.repeate4mlesson1.ui.home.new_task

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.repeate4mlesson1.data.TaskEntity
import com.example.repeate4mlesson1.databinding.FragmentNewTaskBinding
import com.example.repeate4mlesson1.utilits.MainApplication
import java.util.*

class NewTaskFragment : Fragment() {

    private var binding: FragmentNewTaskBinding? =null
    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding?.ivPicker?.setImageURI(uri)
    }
    private var pictureUri: Uri? = null

    companion object{
        const val NEW_TASK_RESULT_KEY ="new_task_result_key"
        const val NEW_TASK_TITLE_KEY ="new_task_title"
        const val NEW_TASK_DESCRIPTION_KEY ="new_task_description"
        const val NEW_TASK_PICTURE_KEY ="new_task_picture"

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(LayoutInflater.from(context),container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

//
//        val cal = Calendar.getInstance()
//        val month = cal.get(Calendar.MONTH)+1
//        val year = cal.get(Calendar.YEAR)
//        val hour = cal.get(Calendar.HOUR)
//        val day = cal.get(Calendar.DAY_OF_MONTH)
//
//        val date = "$day.$month.$year"
    }

    private fun initListeners(){
        binding?.btnSave?.setOnClickListener{

            val entity = TaskEntity(
                title = binding?.etTitle?.text.toString(),
                description = binding?.etDescription?.text?.toString(),
                pictureUri = pictureUri?.toString()
            )

            MainApplication.appDatabase?.taskDao?.insert(entity)
            findNavController().navigateUp()


        }
        binding?.ivPicker?.setOnClickListener {
            imagePicker.launch("image/*")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    }
