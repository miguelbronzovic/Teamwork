package com.teamwork.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.teamwork.R
import com.teamwork.data.model.Project
import com.teamwork.ui.viewmodel.ProjectListViewModel
import com.teamwork.ui.viewmodel.ProjectViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.project_item.view.*


class ProjectAdapter(private val projectListViewModel: ProjectListViewModel) :
        RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    val projectSelectedSubject = PublishSubject.create<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ProjectViewHolder(view, projectSelectedSubject, projectListViewModel)
    }

    override fun getItemCount(): Int {
        return projectListViewModel.getItemCount()
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bindProject(projectListViewModel.getProjectViewModelAt(position))
    }

    class ProjectViewHolder(view: View, private val subject: PublishSubject<Project>, private val projectListViewModel: ProjectListViewModel)
        : RecyclerView.ViewHolder(view) {
        fun bindProject(item: ProjectViewModel) {
            itemView.logo.visibility = View.GONE
            with(item) {
                itemView.starred.setImageDrawable(when {
                    starred -> itemView.context.resources.getDrawable(R.drawable.ic_star_yellow_24dp)
                    else -> itemView.context.resources.getDrawable(R.drawable.ic_star_border_24dp)
                })
                when {
                    !logo.isNullOrEmpty() -> logo.let { itemView.logo.visibility = View.VISIBLE
                        Picasso.get()
                            .load(it)
                            .centerInside()
                            .fit()
                            .into(itemView.logo)}
                }
                itemView.name.text = name
                itemView.description.text = description
                itemView.project_card.setOnClickListener({ subject.onNext(projectListViewModel.getProjectAt(position = adapterPosition)) })
            }
        }
    }
}