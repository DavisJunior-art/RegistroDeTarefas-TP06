package com.example.registrodetarefas

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTasksChanged: () -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.taskName)
        val descriptionText: TextView = itemView.findViewById(R.id.taskDescription)
        val completeButton: Button = itemView.findViewById(R.id.completeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.nameText.text = task.name
        holder.descriptionText.text = task.description

        val strikeThrough = Paint.STRIKE_THRU_TEXT_FLAG
        holder.nameText.paintFlags = if (task.completed) {
            holder.nameText.paintFlags or strikeThrough
        } else {
            holder.nameText.paintFlags and strikeThrough.inv()
        }
        holder.descriptionText.paintFlags = if (task.completed) {
            holder.descriptionText.paintFlags or strikeThrough
        } else {
            holder.descriptionText.paintFlags and strikeThrough.inv()
        }

        holder.completeButton.text = if (task.completed) {
            holder.itemView.context.getString(R.string.reopen_task)
        } else {
            holder.itemView.context.getString(R.string.complete_task)
        }
        holder.completeButton.contentDescription = if (task.completed) {
            holder.itemView.context.getString(R.string.reopen_task)
        } else {
            holder.itemView.context.getString(R.string.complete_task)
        }

        holder.completeButton.setOnClickListener {
            task.completed = !task.completed
            notifyItemChanged(holder.bindingAdapterPosition)
            onTasksChanged()
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.lastIndex)
    }
}
