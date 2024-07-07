package com.akshay.offlinenotes.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.akshay.offlinenotes.R
import com.akshay.offlinenotes.data.model.NoteModel
import com.akshay.offlinenotes.databinding.ActivityAddNoteBinding
import com.akshay.offlinenotes.ui.viewmodel.NotesViewModel
import com.akshay.offlinenotes.utils.Constants
import com.akshay.offlinenotes.utils.string

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val viewModel: NotesViewModel by viewModels()
    private var noteModel: NoteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkExtra()
        initView()
        setObservers()
    }

    private fun checkExtra() {
        if (intent.hasExtra(Constants.EXTRA_KEY)) {
            noteModel = intent.extras?.get(Constants.EXTRA_KEY) as NoteModel
        }
    }

    private fun initView() {
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.save.setOnClickListener {
            validateAndSave()
        }

        noteModel?.let {
            binding.title.setText(it.title ?: "")
            binding.description.setText(it.description ?: "")
        }

    }

    private fun setObservers() {
        viewModel.operationStatus().observe(this) { status ->
            if (status >= 0) {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
    }

    private fun validateAndSave() {
        if (binding.title.text.isNullOrEmpty()) {
            binding.title.error = getString(R.string.please_enter_title)
        } else if (binding.description.text.isNullOrEmpty()) {
            binding.description.error = getString(R.string.please_enter_description)
        } else {
            noteModel?.let {
                it.apply {
                    title = binding.title.string()
                    description = binding.description.string()
                    viewModel.updateNote(this)
                }
            } ?: run {
                viewModel.insertNote(
                    NoteModel(
                        category = "X",
                        title = binding.title.string(),
                        description = binding.description.string()
                    )
                )
            }
        }
    }


}