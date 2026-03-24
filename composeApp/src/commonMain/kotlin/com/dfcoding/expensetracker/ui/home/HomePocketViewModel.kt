package com.dfcoding.expensetracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
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

    fun getPocketById(id: Long): Pocket? {
        return pockets.value.find { it.id == id }
    }

    fun deletePocket(id: Long) {
        viewModelScope.launch {
            repository.deletePocket(id)
        }
    }
}