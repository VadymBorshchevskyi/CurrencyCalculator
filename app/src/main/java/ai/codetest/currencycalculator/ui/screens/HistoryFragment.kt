package ai.codetest.currencycalculator.ui.screens

import ai.codetest.currencycalculator.databinding.HistoryFragmentBinding
import ai.codetest.currencycalculator.ui.adapters.HistoryAdapter
import ai.codetest.currencycalculator.ui.viewmodels.HistoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: HistoryFragmentBinding
    private val viewMode: HistoryViewModel by viewModels()
    private val adapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewMode.operations
                    .collectLatest {
                        adapter.submitList(it)
                    }
            }
        }
    }
}