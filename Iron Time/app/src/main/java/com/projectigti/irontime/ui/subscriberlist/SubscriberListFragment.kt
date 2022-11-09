package com.projectigti.irontime.ui.subscriberlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.projectigti.irontime.R
import com.projectigti.irontime.data.db.AppDatabase
import com.projectigti.irontime.data.db.dao.SubscriberDAO
import com.projectigti.irontime.databinding.FragmentSubscriberListBinding
import com.projectigti.irontime.extension.navigateWithAnimations
import com.projectigti.irontime.repository.DataBaseDataSource
import com.projectigti.irontime.repository.SubscriberRepository

class SubscriberListFragment : Fragment(R.layout.fragment_subscriber_list) {

    private var _binding: FragmentSubscriberListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriberListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDatabase.getInstance(requireContext()).subscriberDAO
                val repository: SubscriberRepository = DataBaseDataSource(subscriberDAO)
                return SubscriberListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubscriberListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        configureViewListener()
    }

    private fun observeViewModelEvents() {

        viewModel.allSubscribersEvent.observe(viewLifecycleOwner, Observer { list ->
            val subscriberListAdapter = SubscriberListAdapter(list).apply {
                onItemClick = { subscriber ->
                    val directions = SubscriberListFragmentDirections
                        .actionSubscriberListFragmentToSubscriberFragment(subscriber)
                    findNavController().navigateWithAnimations(directions)
                }

                onCheckinButtonClick = { subscriber ->
                    Snackbar.make(requireView(), subscriber.name, Snackbar.LENGTH_LONG).show()
                }


            }

            binding.recyclerSubscriber.run {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscribers()
    }

    private fun configureViewListener() {
        binding.fabAddSubscriber.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_subscriberListFragment_to_subscriberFragment)
        }
    }


}