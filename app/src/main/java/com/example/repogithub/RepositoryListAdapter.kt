package com.example.repogithub

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repository_item.view.*


class RepositoryListAdapter(private val repositories : List<Repository>,
                            private val clickListener: (Repository) -> Unit,
                            private val context : Context) : Adapter<RepositoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repository_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]
        holder.bindView(repository, clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(repository: Repository, clickListener: (Repository) -> Unit) {
            val avatar = itemView.repository_item_avatar
            val fullName = itemView.repository_item_full_name
            val description = itemView.repository_item_description
            val htmlUrl = itemView.repository_item_html_url

            avatar?.let { Picasso.get().load(repository.owner.avatarUrl).into(avatar) }
            fullName?.text = repository.fullName
            description?.text = repository.description
            htmlUrl?.text = repository.htmlUrl

            itemView.setOnClickListener{ clickListener(repository) }
        }
    }

}

