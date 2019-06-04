package com.example.repogithub

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit private var repositoryListAdapter : RepositoryListAdapter
    lateinit private var repositories : MutableList<Repository>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositories = ArrayList()
        repositoryListAdapter = RepositoryListAdapter(repositories,
            { repository: Repository -> redirectBrowser(repository.htmlUrl) },this)

        val recyclerView = repository_list_recyclerview
        recyclerView.adapter = repositoryListAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
    }

    fun sendRepository(view : View) {

        val call = GitHubInitializer().repositoryService().getRepository(repository_edittext.text.toString())
        call.enqueue(object : Callback<Repository> {
            override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                val repository = response.body()
                repository_edittext.error = null
                repository?.let {
                    addRepository(it)
                }
                if (repository == null) {
                    val message = "Repository ${repository_edittext.text.toString()} not found!"
                    repository_edittext.error = message
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Repository>, t: Throwable) {
                val message = "Repository ${repository_edittext.text.toString()} not found!"
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun addRepository(repository: Repository) {
        val contains = repositories.contains(repository)
        if (contains) {
            repository_edittext.error = "Repository ${repository.fullName} already added!"
            Toast.makeText(this, "Repository ${repository.fullName} already added", Toast.LENGTH_LONG).show()
            return
        }
        repository_edittext.text = null
        repository_edittext.error = null
        repositories.add(repository)
        repositoryListAdapter.notifyDataSetChanged()
    }

    private fun redirectBrowser(url : String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}