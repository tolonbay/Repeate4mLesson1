package com.example.repeate4mlesson1.ui.home.new_task

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.repeate4mlesson1.databinding.ItemTaskBinding

class TaskAdapter(private var tasks: MutableList<TaskModel>):RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var onDelete: ((Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun renew(newList: List<TaskModel>){
        tasks.clear()
        tasks.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(
          ItemTaskBinding.inflate(
              LayoutInflater.from(parent.context)
          )
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])


    }

    override fun getItemCount() = tasks.size


    inner class ViewHolder( val binding: ItemTaskBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(task: TaskModel){
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.description

            if(task.pictureUri != null){
                binding.ivPicture.setImageURI(Uri.parse(task.pictureUri))
                binding.ivPicture.visibility = View.VISIBLE
            }

            binding.root.setOnLongClickListener {
                onDelete?.invoke(task.id)
                true
            }
        }
    }

}

