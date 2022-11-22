package ai.codetest.currencycalculator.ui.screens

import ai.codetest.currencycalculator.R
import ai.codetest.currencycalculator.databinding.ConvertFragmentBinding
import ai.codetest.currencycalculator.ui.extensions.hideKeyboard
import ai.codetest.currencycalculator.ui.viewmodels.ConvertViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ConvertFragment : Fragment() {

    private lateinit var binding: ConvertFragmentBinding
    private val viewModel: ConvertViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = ConvertFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedDay
                    .collectLatest { selectedDay ->
                        val formatter: DateTimeFormatter =
                            DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        binding.dayValue.text = selectedDay.atZone(ZoneId.systemDefault()).format(formatter)
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .collectLatest { state ->
                        binding.progress.isVisible = state.isLoading
                        binding.errorBanner.isVisible = state.hasError
                        binding.convert.isEnabled = !state.hasError
                        binding.originCurrencyValue.setText("", false)
                        binding.toCurrencyValue.setText("", false)
                        binding.valueAmount.text = ""
                        val adapter = ArrayAdapter(requireContext(),
                            android.R.layout.simple_list_item_1, state.rates.map { it.code })
                        binding.originCurrencyValue.setAdapter(adapter)
                        binding.toCurrencyValue.setAdapter(adapter)
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.amount
                    .collectLatest { amount ->
                        binding.valueAmount.text = amount
                    }
            }
        }

        binding.changeDay.setOnClickListener {
            showCalendar()
        }

        binding.retryButton.setOnClickListener {
            viewModel.getRates()
        }

        binding.convert.setOnClickListener {
            viewModel.convert(
                binding.originCurrencyValue.text.toString(),
                binding.toCurrencyValue.text.toString(),
                binding.originCurrencyAmount.text.toString()
            )
        }

        binding.originCurrencyAmount.setOnFocusChangeListener { _, _ ->
            binding.originCurrencyAmount.hideKeyboard()
        }
    }

    private fun showCalendar() {
        lifecycleScope.launch {
            val selectedDay = viewModel.selectedDay.first()
            val constraints = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
                .build()
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.calendarTitle))
                    .setCalendarConstraints(constraints)
                    .setSelection(selectedDay.toEpochMilli())
                    .build()
            datePicker.addOnPositiveButtonClickListener { date ->
                viewModel.selectDate(date)
            }

            datePicker.show(parentFragmentManager, null)
        }
    }
}