package com.example.repeate4mlesson1.ui.home.new_task

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.repeate4mlesson1.R
import com.example.repeate4mlesson1.data.TaskEntity
import com.example.repeate4mlesson1.databinding.FragmentHomeBinding
import com.example.repeate4mlesson1.utilits.MainApplication
import com.example.repeate4mlesson1.utilits.MainApplication.Companion.appDatabase
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val taskAdapter = TaskAdapter(mutableListOf())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
        requestPermission()
        initViews()
        initListeners()
        getActualTasks(appDatabase?.taskDao?.getAll())
    }

    private fun requestPermission(){
        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){}
        launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }


    private fun initViews() {

        binding.rvTask.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter

        }
        taskAdapter.onDelete = {
            AlertDialog.Builder(requireContext())
                .setPositiveButton("Да") { d, _ ->
                    appDatabase?.taskDao?.deleteBy(
                        it.toLong()
                    )
                    getActualTasks(appDatabase?.taskDao?.getAll())
                    d.dismiss()
                }
                .setNegativeButton("Нет") { d, _ ->
                    d.dismiss()
                }
                .setTitle("Внимание")
                .setMessage("Удалить задачу?")
                .create()
                .show()
        }
    }
        @SuppressLint("SuspiciousIndentation")
        private fun getActualTasks(tasks: List<TaskEntity>?) {
            lifecycleScope.launch {
                var taskModel = mutableListOf<TaskModel>()
                    taskModel.clear()
                    taskModel= tasks?.map {
                        TaskModel(
                            id = it.id.toInt(),
                            title = it.title,
                            description = it.description,
                            pictureUri = it.pictureUri
                        )
                    } as MutableList<TaskModel>
                taskModel.let {
                    taskAdapter.renew(it)
                }
            }
        }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.burger_menu, menu)",
        "com.example.repeate4mlesson1.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.burger_menu,menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.sort_by_letter->{
                getActualTasks(appDatabase?.taskDao?.getAllSortByLetter())
                true
            }
            R.id.sort_by_time->{
                getActualTasks(appDatabase?.taskDao?.getAllSortByTime())
                true
            }
            else -> {
                false
            }
        }
    }

    private fun initListeners() {
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
