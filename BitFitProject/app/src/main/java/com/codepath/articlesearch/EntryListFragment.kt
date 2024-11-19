package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.FragmentEntryListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "EntryListFragment"


/**
 * A simple [Fragment] subclass.
 * Use the [EntryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryListFragment : Fragment() {

    private lateinit var addButton: Button
    private lateinit var entriesRecyclerView: RecyclerView
    private lateinit var entryAdapter: EntryAdapter
    private lateinit var binding2: FragmentEntryListBinding
    private val entries = mutableListOf<DisplayEntry>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private suspend fun updateEntries(databaseList: List<EntryEntity>) {
        val mappedList = databaseList.map { entity ->
            DisplayEntry(
                entity.id,
                entity.entryTitle,
                entity.entryInfo,
                entity.date
            )
        }
        withContext(Dispatchers.Main) {
            entries.clear()
            entries.addAll(mappedList)
            entryAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding2 = FragmentEntryListBinding.inflate(inflater, container, false)
        val entryApplication = requireActivity().application as EntryApplication
        val view = binding2.root


        entriesRecyclerView = binding2.entriesRv
        entryAdapter = EntryAdapter(requireContext(), entries, entryApplication) { entryId ->
            lifecycleScope.launch(Dispatchers.IO) {
                entryApplication.db.exerciseDao().deleteById(entryId)
            }
        }

        entriesRecyclerView.adapter = entryAdapter
        entriesRecyclerView.layoutManager = LinearLayoutManager(context).also {
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            entriesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        addButton = binding2.addButton

        lifecycleScope.launch {
            entryApplication.db.exerciseDao().getAll().collect { databaseList ->
                updateEntries(databaseList)
            }
        }
        addButton.setOnClickListener {

            val intent = Intent(requireContext(), DetailActivity::class.java)
            startActivity(intent)

        }
        return view
    }


    companion object {
        fun newInstance(): EntryListFragment {
            return EntryListFragment()
        }
    }
}