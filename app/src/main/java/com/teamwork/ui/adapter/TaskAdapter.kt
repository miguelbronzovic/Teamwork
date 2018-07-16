package com.teamwork.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.teamwork.R
import com.teamwork.data.model.Task
import com.teamwork.ui.viewmodel.TaskListViewModel
import kotlinx.android.synthetic.main.task_item.view.*


class TaskAdapter(private val taskListViewModel: TaskListViewModel) :
        RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskListViewModel.getItemCount()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindProject(taskListViewModel.getTaskAt(position))
    }

    class TaskViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        fun bindProject(item: Task) {
            itemView.avatar.visibility = View.GONE
            with(item) {
                when {
                    !avatarUrl.isNullOrEmpty() -> avatarUrl.let { itemView.avatar.visibility = View.VISIBLE
                        Picasso.get()
                                .load(it)
                                .centerInside()
                                .fit()
                                .into(itemView.avatar)}
                }
                itemView.name.text = content
                itemView.description.text = description
            }
        }
    }
}