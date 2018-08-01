package net.aliveplex.witoong623.myjapanesevocabularies.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.aliveplex.witoong623.myjapanesevocabularies.R
import kotlinx.android.synthetic.main.fragment_dictionary.*
import net.aliveplex.witoong623.myjapanesevocabularies.adapters.DictionaryAdapter
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Vocabulary
import net.aliveplex.witoong623.myjapanesevocabularies.interfaces.ClickListener
import net.aliveplex.witoong623.myjapanesevocabularies.listeners.RecyclerTouchListener
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.findNavController
import net.aliveplex.witoong623.myjapanesevocabularies.VOCABULARY_ID
import net.aliveplex.witoong623.myjapanesevocabularies.utils.hideKeyboard
import net.aliveplex.witoong623.myjapanesevocabularies.utils.isJapanese
import net.aliveplex.witoong623.myjapanesevocabularies.viewmodels.DictionaryViewModel
import java.util.*


class DictionaryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: DictionaryAdapter

    companion object {
        fun newInstance() = DictionaryFragment()
    }

    private lateinit var viewModel: DictionaryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DictionaryViewModel::class.java)
        recyclerView = tagList
        viewManager = LinearLayoutManager(activity)
        viewAdapter = DictionaryAdapter()
        val divideritemdecorator = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(divideritemdecorator)
        }
        viewModel.getFoundVocabs().observe(this, Observer{  list: List<Vocabulary>? -> viewAdapter.submitList(list)})

        val ctx = context as Context
        recyclerView.addOnItemTouchListener(RecyclerTouchListener(ctx, recyclerView, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                val itemId = viewAdapter.getItemId(position)
                val bundle = Bundle()
                hideKeyboard(activity!!)
                bundle.putInt(VOCABULARY_ID, itemId.toInt())
                view.findNavController().navigate(R.id.dictionary_to_vacabularydetail, bundle)
            }

            override fun onLongClick(view: View, position: Int) {}
        }))
        searchInput.addTextChangedListener(KeywordTextWatcher(textInputLayout, viewModel))
    }
}
class KeywordTextWatcher(val textinputLayout: TextInputLayout, val dcvm: DictionaryViewModel): TextWatcher {
    private var timer: Timer? = null
    private val DELAY: Long = 1500

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (timer != null) {
            timer?.cancel()
        }
    }

    override fun afterTextChanged(s: Editable) {
        val keyword = s.toString()

        if (keyword.isEmpty()) {
            textinputLayout.error = null
            return
        }

        if (!keyword.isJapanese()) {
            textinputLayout.error = "ใช้ภาษาญี่ปุ่นเท่านั้น"
            return
        }
        else {
            textinputLayout.error = null
        }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                dcvm.searchKeyword(keyword)
            }
        }, DELAY)
    }
}
