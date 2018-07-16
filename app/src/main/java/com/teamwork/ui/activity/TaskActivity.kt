package com.teamwork.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.teamwork.R
import com.teamwork.TeamWorkApp
import com.teamwork.data.model.Project
import com.teamwork.di.component.DaggerTaskActivityComponent
import com.teamwork.di.module.TaskActivityModule
import com.teamwork.ui.adapter.TaskAdapter
import com.teamwork.ui.viewmodel.TaskListViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_task.*
import javax.inject.Inject

/**
 * Lists all tasks from the selected procject
 */
class TaskActivity : AppCompatActivity() {

    companion object {
        const val SELECTED_PROJECT = "selected_project"
    }

    @Inject lateinit var viewModel : TaskListViewModel
    @Inject lateinit var progressDialog: ProgressDialog
    @Inject lateinit var taskAdapter: TaskAdapter

    lateinit var component: DaggerTaskActivityComponent

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = DaggerTaskActivityComponent.builder()
                .appComponent(TeamWorkApp.component)
                .taskActivityModule(TaskActivityModule(this))
                .build()
        component.inject(this)

        setContentView(R.layout.activity_task)
        setTitle(R.string.task_activity_title)

        val project = intent.getSerializableExtra(SELECTED_PROJECT) as Project

        viewModel.fetchTasks(project.id)

        task_title.text = getString(R.string.all_tasks, project.name)

        tasks_recycler.setHasFixedSize(true)
        tasks_recycler.layoutManager = LinearLayoutManager(this)
        tasks_recycler.adapter = taskAdapter
    }

    override fun onResume() {
        super.onResume()
        disposable.add(viewModel.isLoading.subscribe({ loading ->
            if (loading) progressDialog.show() else progressDialog.dismiss() }))

        disposable.add(viewModel.taskListSubject.subscribe({ _ ->
            taskAdapter.notifyDataSetChanged() },
                { err -> showErrorMessage(err.message.toString())}))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun showErrorMessage(msg: String) {
        Snackbar.make(root_layout, msg, Snackbar.LENGTH_LONG).show()
    }

}