package com.codepath.articlesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.codepath.articlesearch.databinding.FragmentDashboardBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        val entryApplication = requireActivity().application as EntryApplication
        val view = binding.root

        lifecycleScope.launch(Dispatchers.IO) {

            val totalEntries = entryApplication.db.exerciseDao().getTotalEntries()
            val mostRecentEntryTitle = entryApplication.db.exerciseDao().getMostRecentEntryTitle()
            val mostRecentEntryText = entryApplication.db.exerciseDao().getMostRecentEntryText()

            // Update UI on the main thread
            view.findViewById<TextView>(R.id.total_entries)?.text = totalEntries.toString()
            view.findViewById<TextView>(R.id.recent_entry_header)?.text = mostRecentEntryTitle
            view.findViewById<TextView>(R.id.recent_entry_body)?.text = mostRecentEntryText

        }



        return view
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}