package com.projectigti.irontime.ui.subscriberdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.projectigti.irontime.R
import com.projectigti.irontime.data.db.AppDatabase
import com.projectigti.irontime.data.db.dao.SubscriberDAO
import com.projectigti.irontime.data.db.model.SubscriberEntity
import com.projectigti.irontime.databinding.FragmentSubscriberDetailBinding
import com.projectigti.irontime.extension.navigateWithAnimations
import com.projectigti.irontime.repository.DataBaseDataSource
import com.projectigti.irontime.repository.SubscriberRepository

class SubscriberDetailFragment : Fragment(R.layout.fragment_subscriber_detail) {


    private var _binding: FragmentSubscriberDetailBinding? = null
    private val binding get() = _binding!!

    private val args: SubscriberDetailFragmentArgs by navArgs()

    private val viewModel: SubscriberDetailViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDatabase.getInstance(requireContext()).subscriberDAO
                val repository: SubscriberRepository = DataBaseDataSource(subscriberDAO)
                return SubscriberDetailViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubscriberDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewListener()
        observeViewModelEvents()
        observerEvents()
    }

    override fun onResume() {
        super.onResume()
        args.subscriber?.let { viewModel.getSubscriber(it.id) }
    }

    private fun configureBinding(subscriber: SubscriberEntity) {
        binding.subscriberName.setText(subscriber.name)
        binding.subscriberEmail.setText(subscriber.email)
        binding.subscriberPhone.setText(subscriber.phone)
        binding.subscriberClasses.setText(subscriber.classes.toString())
    }

    private fun observerEvents() {
        viewModel.subscriberEvent.observe(viewLifecycleOwner) { subscriber ->
            configureBinding(subscriber)
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeViewModelEvents() {
        val checkins = args.subscriber?.checkins

        if (checkins != null) {
            val subscriberListAdapter = SubscriberDetailAdapter(checkins).apply { }

            binding.recyclerCheckins.run {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        }
    }

    private fun configureViewListener() {
        binding.buttonGotoUpdate.setOnClickListener {
            val directions =
                SubscriberDetailFragmentDirections
                    .actionSubscriberDetailFragmentToSubscriberFragment(args.subscriber)
            findNavController().navigateWithAnimations(directions)
        }

        binding.buttonInsertClasses.setOnClickListener {
            createDialogInsertClasses()
        }
    }

    private fun createDialogInsertClasses() {
        val numberList = Array(500) { i -> i.toString() }
        val builder: AlertDialog.Builder? = activity?.let {

            val builder = AlertDialog.Builder(it)

            builder.apply {
                var choice = 1
                setTitle("Add Classes")
                setNeutralButton("Cancel") { _, _ -> }
                setPositiveButton("Add") { _, _ ->
                    args.subscriber?.let { subscriber ->
                        viewModel.insertClasses(subscriber.id, choice)
                    }
                }

                setSingleChoiceItems(numberList, 1) { dialog, which ->
                    choice = which
                }
            }
        }

        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

}