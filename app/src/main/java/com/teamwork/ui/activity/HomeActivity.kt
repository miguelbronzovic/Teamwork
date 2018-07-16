package com.teamwork.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.teamwork.R
import com.teamwork.TeamWorkApp
import com.teamwork.data.model.Project
import com.teamwork.di.component.DaggerHomeActivityComponent
import com.teamwork.di.module.HomeActivityModule
import com.teamwork.ui.adapter.ProjectAdapter
import com.teamwork.ui.viewmodel.ProjectListViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

/**
 * Main activity, lists all projects from the backend
 */
class HomeActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: ProjectListViewModel
    @Inject lateinit var progressDialog: ProgressDialog
    @Inject lateinit var projectAdapter: ProjectAdapter

    lateinit var component: DaggerHomeActivityComponent

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = DaggerHomeActivityComponent.builder()
                .appComponent(TeamWorkApp.component)
                .homeActivityModule(HomeActivityModule(this))
                .build()
        component.inject(this)

        setContentView(R.layout.activity_home)
        setTitle(R.string.home_activity_title)

        projects_recycler.setHasFixedSize(true)
        projects_recycler.layoutManager = LinearLayoutManager(this)
        projects_recycler.adapter = projectAdapter

        viewModel.fetchProjects()
    }

    override fun onResume() {
        super.onResume()
        disposable.add(viewModel.isLoading.subscribe({ loading ->
            if (loading) progressDialog.show() else progressDialog.dismiss() }))

        disposable.add(viewModel.projectListSubject.subscribe({ _ ->
            projectAdapter.notifyDataSetChanged() },
                { err -> showErrorMessage(err.message.toString())}))

        disposable.add(projectAdapter.projectSelectedSubject.subscribe({ p -> goToTaskActivity(p)}))
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun showErrorMessage(msg: String) {
        Snackbar.make(root_layout, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun goToTaskActivity(project: Project) {
        val intent = Intent(this, TaskActivity::class.java)
        intent.putExtra(TaskActivity.SELECTED_PROJECT, project)
        startActivity(intent)
    }
}