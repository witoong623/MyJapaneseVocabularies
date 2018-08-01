package net.aliveplex.witoong623.myjapanesevocabularies.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_vocabulary_detail.*

import net.aliveplex.witoong623.myjapanesevocabularies.R
import net.aliveplex.witoong623.myjapanesevocabularies.adapters.VocabularyTagAdapter
import net.aliveplex.witoong623.myjapanesevocabularies.viewmodels.VocabularyDetailViewModel

class VocabularyDetailFragment : Fragment() {

    companion object {
        fun newInstance() = VocabularyDetailFragment()
    }

    private lateinit var viewModel: VocabularyDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vocabulary_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VocabularyDetailViewModel::class.java)
        val vocabularyId = arguments!!.getInt("vocabularyId")
        viewModel.vocabulary.observe(this, Observer { v ->
            vocab.text = v?.vocab
            reading_detail.text = v?.reading
            meaning.text = v?.meaning
            type.text = v?.type
        })
        viewModel.getVocabularyDetail(vocabularyId)

        val tagDropdownAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item)
        tagDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tag_dropdown_spiner.adapter = tagDropdownAdapter
        viewModel.availableTag.observe(this, Observer {
            val stringList = it?.map { itt -> itt.tag_name }
            tagDropdownAdapter.clear()
            tagDropdownAdapter.addAll(stringList)
        })

        tag_vocabulary_btn.setOnClickListener {
            val tagName = tag_dropdown_spiner.selectedItem.toString()
            viewModel.tagVocabulary(tagName)
        }

        val viewManager = LinearLayoutManager(activity)
        val viewAdapter = VocabularyTagAdapter()
        val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        vocabulary_tag_list.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
        }
        val swipeDelete = DeleteVocabularyTagSwipe(0, ItemTouchHelper.LEFT, viewModel)
        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(vocabulary_tag_list)
        viewModel.tagOfVocabulary.observe(this, Observer {
          viewAdapter.submitList(it)
        })
    }

    class DeleteVocabularyTagSwipe(dragDirs: Int, swipeDirs: Int, val viewmodel: VocabularyDetailViewModel) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            val tagViewholder = viewHolder as VocabularyTagAdapter.VocabularyTagViewHolder
            viewmodel.deleteVocabularyTag(tagViewholder.vocabularyTag)
        }

    }
}
