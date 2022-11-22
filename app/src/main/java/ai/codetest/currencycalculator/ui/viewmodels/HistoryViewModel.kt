package ai.codetest.currencycalculator.ui.viewmodels

import ai.codetest.currencycalculator.biz.Repo
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    repo: Repo
) : ViewModel() {
    val operations = repo.getOperations()
}