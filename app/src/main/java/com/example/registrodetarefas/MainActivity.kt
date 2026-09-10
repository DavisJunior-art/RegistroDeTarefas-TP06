package com.example.registrodetarefas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var taskCountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val addButton = findViewById<Button>(R.id.addButton)
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        taskCountText = findViewById(R.id.taskCountText)

        taskAdapter = TaskAdapter(mutableListOf()) {
            updateTaskCount()
        }
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = taskAdapter
        updateTaskCount()

        addButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            nameInput.error = null
            descriptionInput.error = null

            when {
                name.isEmpty() -> {
                    nameInput.error = getString(R.string.name_required)
                }
                description.isEmpty() -> {
                    descriptionInput.error = getString(R.string.description_required)
                }
                else -> {
                    taskAdapter.addTask(Task(name, description))
                    nameInput.text.clear()
                    descriptionInput.text.clear()
                    updateTaskCount()
                    tasksRecyclerView.scrollToPosition(taskAdapter.itemCount - 1)
                }
            }
        }
    }

    private fun updateTaskCount() {
        val total = taskAdapter.itemCount
        taskCountText.text = resources.getQuantityString(R.plurals.task_count, total, total)
    }
}
