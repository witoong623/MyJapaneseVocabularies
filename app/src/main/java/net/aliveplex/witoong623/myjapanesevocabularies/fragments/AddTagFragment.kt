package net.aliveplex.witoong623.myjapanesevocabularies.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_add_tag.*
import net.aliveplex.witoong623.myjapanesevocabularies.R
import net.aliveplex.witoong623.myjapanesevocabularies.databases.Tag
import net.aliveplex.witoong623.myjapanesevocabularies.utils.hideKeyboard
import net.aliveplex.witoong623.myjapanesevocabularies.viewmodels.AddTagResult
import net.aliveplex.witoong623.myjapanesevocabularies.viewmodels.AddTagStatus
import net.aliveplex.witoong623.myjapanesevocabularies.viewmodels.AddTagViewModel
import java.util.*

class AddTagFragment : Fragment() {
    private lateinit var viewModel: AddTagViewModel
    private lateinit var tag_name: TextInputEditText
    private lateinit var tag_description: TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_tag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tag_name = tag_name_et
        tag_description = tag_description_et
        viewModel = ViewModelProviders.of(this).get(AddTagViewModel::class.java)
        viewModel.addTagResultObserable.observe(this, Observer { result: AddTagResult? ->
            if (result?.status == AddTagStatus.Success) {
                hideKeyboard(activity!!)
                NavHostFragment.findNavController(this@AddTagFragment).popBackStack()
            }
        })

        add_tag_btn.setOnClickListener {
            if (!tag_name.text.isEmpty()) {
                val tag: Tag
                if (!tag_description.text.isEmpty()) {
                    tag = Tag(tag_name.text.toString(), tag_description.text.toString(), Date())
                } else {
                    tag = Tag(tag_name.text.toString(), null, Date())
                }
                viewModel.addNewTag(tag)
            }
        }
    }

    companion object {
        fun newInstance() = AddTagFragment()
    }
}
