package com.projectigti.irontime.ui.subscriber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.projectigti.irontime.databinding.FragmentSubscriberBinding
import com.projectigti.irontime.extension.hideKeyBoard
import com.projectigti.irontime.repository.DataBaseDataSource
import com.projectigti.irontime.repository.SubscriberRepository

class SubscriberFragment : Fragment(R.layout.fragment_subscriber) {
    private var _binding: FragmentSubscriberBinding? = null
    private val binding get() = _binding!!

    private val args: SubscriberFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubscriberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val viewModel: SubscriberViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDatabase.getInstance(requireContext()).subscriberDAO
                val repository: SubscriberRepository = DataBaseDataSource(subscriberDAO)
                return SubscriberViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.subscriber?.let { subscriber ->
            binding.buttonSubscriber.text = getString(R.string.button_update)
            binding.inputName.setText(subscriber.name.toString())
            binding.inputEmail.setText(subscriber.email.toString())

            binding.buttonDeleteSubscriber.visibility = View.VISIBLE
        }
        observerEvents()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonSubscriber.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()

            viewModel.addOrUpdateSubscriber(name, email, args.subscriber?.id ?: 0)
        }

        binding.buttonDeleteSubscriber.setOnClickListener {
            viewModel.deleteSubscriber(args.subscriber?.id ?: 0)
        }
    }

    private fun observerEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) { subscriberState ->
            when (subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted,
                is SubscriberViewModel.SubscriberState.Updated,
                is SubscriberViewModel.SubscriberState.Deleted -> {
                    clearFields()
                    hideKeyBoard()
                    requireView().requestFocus()

                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun clearFields() {
        binding.inputName.text?.clear()
        binding.inputEmail.text?.clear()
    }

    private fun hideKeyBoard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyBoard()
        }
    }


}