package com.akshay.offlinenotes.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshay.offlinenotes.R
import com.akshay.offlinenotes.data.model.NoteModel
import com.akshay.offlinenotes.databinding.NotesListFragmentBinding
import com.akshay.offlinenotes.ui.view.AddNoteActivity
import com.akshay.offlinenotes.ui.view.adapter.NoteListAdapter
import com.akshay.offlinenotes.ui.viewmodel.NotesViewModel
import com.akshay.offlinenotes.utils.Constants
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotesListFragment : Fragment() {

    private var _binding: NotesListFragmentBinding? = null
    private lateinit var noteListAdapter: NoteListAdapter
    private val viewModel: NotesViewModel by viewModels()
    private val binding get() = _binding!!
    private var notesList = ArrayList<NoteModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = NotesListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getData()
    }

    private fun initViews() {
        binding.notesList.apply {
            noteListAdapter = NoteListAdapter { pos, id ->
                when (id) {
                    R.id.delete_note -> {
                        viewModel.deleteNote(notesList[pos])
                    }

                    R.id.edit_note -> {
                        requireActivity().startActivity(
                            Intent(
                                requireContext(),
                                AddNoteActivity::class.java
                            ).apply {
                                putExtra(Constants.EXTRA_KEY, notesList[pos])
                            }
                        )
                    }
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteListAdapter
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let { char ->
                    if (char.isNotEmpty())
                        getData(char.toString())
                    else
                        getData()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun getData(search: String? = null) {
        lifecycleScope.launch {
            viewModel.getNotes(search)?.collect { notes ->
                if (notes.isNotEmpty()) {
                    notesList = ArrayList(notes)
                    noteListAdapter.updateList(notesList)
                    binding.listViews.visibility = View.VISIBLE
                    binding.noNotes.visibility = View.GONE
                } else {
                    if (search.isNullOrEmpty()) {
                        binding.listViews.visibility = View.GONE
                    }
                    else{
                        binding.searchBar.visibility = View.VISIBLE
                        binding.notesList.visibility = View.GONE
                    }
                    binding.noNotes.visibility = View.VISIBLE
                }

                Log.d("NotesList", "flow called")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}