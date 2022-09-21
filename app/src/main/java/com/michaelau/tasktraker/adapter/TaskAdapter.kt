package com.michaelau.tasktraker.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.michaelau.tasktraker.R
import com.michaelau.tasktraker.data.model.Task
import com.michaelau.tasktraker.databinding.TaskItemBinding

class TaskAdapter (private val context: Context,private val taskListener: TaskListener)
    : ListAdapter<Task, TaskAdapter.TaskViewHolder>(ItemGroupDiffUtill()) {


    var onItemClick: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val cardOption = getItem(position)
        holder.binding.itemLl.backgroundTintList = getColorStateList(context,cardOption.colorTheme)
        holder.binding.taskCheckbox.text = cardOption.title

        holder.binding.delete.setOnClickListener {
            taskListener.deleteItem(cardOption)
            Toast.makeText(context,"Deleted ${cardOption.title}",Toast.LENGTH_SHORT).show()
        }
        holder.binding.edit.setOnClickListener {
            taskListener.getItem(cardOption)
        }

        holder.binding.taskCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            taskListener.updateItem(cardOption,isChecked)
            taskListener.getItem(cardOption)
        }


    }

    class ItemGroupDiffUtill : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    interface TaskListener{
        fun deleteItem(task:Task)
        fun getItem(task:Task)
        fun updateItem(task:Task,isCheck:Boolean)
    }

    inner class TaskViewHolder(val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))

            }
        }

    }
}