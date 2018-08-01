package net.aliveplex.witoong623.myjapanesevocabularies.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_vocabulary_tag_detail_fragment.*
import net.aliveplex.witoong623.myjapanesevocabularies.R
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag

class VocabularyTagAdapter : ListAdapter<Tag, VocabularyTagAdapter.VocabularyTagViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyTagViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_vocabulary_tag_detail_fragment, parent, false)
        return VocabularyTagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VocabularyTagViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class VocabularyTagViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val tag_name = tag_name_vocabularytag_detail_et
        lateinit var vocabularyTag: Tag
            private set

        fun bindTo(vocabTag: Tag) {
            vocabularyTag = vocabTag
            tag_name.text = vocabTag.tag_name
        }
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
}