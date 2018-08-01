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
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Vocabulary
import kotlinx.android.synthetic.main.recycler_vocab_item_view.*

class DictionaryAdapter : ListAdapter<Vocabulary, DictionaryAdapter.VocabularyViewHolder>(DIFF_CALLBACK) {
    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return item?.id?.toLong() ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabularyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_vocab_item_view, parent, false)
        return VocabularyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VocabularyViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Vocabulary>() {
            override fun areItemsTheSame(oldItem: Vocabulary?, newItem: Vocabulary?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Vocabulary?, newItem: Vocabulary?): Boolean {
                return oldItem == newItem
            }
        }
    }

    class VocabularyViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val kanji_kana_text: TextView
        val type_meaning_text: TextView

        init {
            kanji_kana_text = word_kanji_kana
            type_meaning_text = word_type_meaning
        }

        fun bindTo(vocab: Vocabulary) {
            val firstln = "${vocab.vocab} ${vocab.reading}"
            val secondln = "${vocab.type} ${vocab.meaning}"
            kanji_kana_text.text = firstln
            type_meaning_text.text = secondln
        }
    }
}