package net.aliveplex.witoong623.myjapanesevocabularies.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.extensions.LayoutContainer
import net.aliveplex.witoong623.myjapanesevocabularies.R
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag
import kotlinx.android.synthetic.main.recycler_tag_list_fragment.*

class TagManagerAdapter : ListAdapter<Tag, TagManagerAdapter.TagManagerViewHolder>(DIFF_CALLBACK) {
    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return item?.id?.toLong() ?: -1
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagManagerViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_tag_list_fragment, parent, false)
        return TagManagerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagManagerViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldItem: Tag?, newItem: Tag?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Tag?, newItem: Tag?): Boolean {
                return oldItem == newItem
            }
        }
    }

    class TagManagerViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val tag_name: TextView = tag_name_view
        val tag_description: TextView = tag_description_view
        lateinit var tag: Tag
            private set

        fun bindTo(tag: Tag) {
            this.tag = tag
            tag_name.text = tag.tag_name
            tag_description.text = tag.description
        }
    }

}