package com.dfcoding.expensetracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.database.GetTotalAmount
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomePocketViewModel(private val repository: ExpenseRepository) : ViewModel() {

    val pockets: StateFlow<List<Pocket>> = repository.getAllPockets().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val totalAmount: StateFlow<Double> = repository.getTotalAmount().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val totalNumberOfExpenses: StateFlow<Double> = repository.getTotalNumberOfExpenses().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    fun getPocketById(id: Long): Pocket? {
        return pockets.value.find { it.id == id }
    }

    fun deletePocket(id: Long) {
        viewModelScope.launch {
            repository.deletePocket(id)
        }
    }

}