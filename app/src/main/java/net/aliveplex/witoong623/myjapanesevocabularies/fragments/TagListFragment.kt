package net.aliveplex.witoong623.myjapanesevocabularies.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import net.aliveplex.witoong623.myjapanesevocabularies.R
import net.aliveplex.witoong623.myjapanesevocabularies.adapters.TagManagerAdapter
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag
import kotlinx.android.synthetic.main.fragment_tag_list.*
import net.aliveplex.witoong623.myjapanesevocabularies.interfaces.ClickListener
import net.aliveplex.witoong623.myjapanesevocabularies.listeners.RecyclerTouchListener
import net.aliveplex.witoong623.myjapanesevocabularies.viewmodels.TagManagerViewModel

class TagListFragment : Fragment() {
    private lateinit var taglistRecyclerView: RecyclerView
    private lateinit var viewModel: TagManagerViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: TagManagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TagManagerViewModel::class.java)
        taglistRecyclerView = tagList_recyclerview
        viewManager = LinearLayoutManager(activity)
        viewAdapter = TagManagerAdapter()
        val divideritemdecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        taglistRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(divideritemdecorator)
        }
        val swipedelete = DeleteTagSwipe(0, ItemTouchHelper.LEFT, viewModel)
        val touchHelper = ItemTouchHelper(swipedelete)
        touchHelper.attachToRecyclerView(taglistRecyclerView)
        viewModel.tagList.observe(this, Observer { list: List<Tag>? -> viewAdapter.submitList(list) })
        tag_list_fab.setOnClickListener { view: View? ->
            view?.findNavController()?.navigate(R.id.taglist_to_addtag)
        }

        taglistRecyclerView.addOnItemTouchListener(RecyclerTouchListener(context!!, taglistRecyclerView, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                val itemId = viewAdapter.getItemId(position)
                Log.d("TagListFragment", "tag id is $itemId")
            }

            override fun onLongClick(view: View, position: Int) {}
        }))
    }

    companion object {
        fun newInstance() = TagListFragment()
    }

    class DeleteTagSwipe(dragDirs: Int, swipeDirs: Int, val viewmodel: TagManagerViewModel) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val tagViewholder = viewHolder as TagManagerAdapter.TagManagerViewHolder
            viewmodel.deleteTag(tagViewholder.tag)
        }
    }
}
