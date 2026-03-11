package com.dfcoding.expensetracker.ui.pockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepository
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PocketListViewModel(private val repository: ExpenseRepository) : ViewModel() {

    val pockets: StateFlow<List<Pocket>> = repository.getAllPockets().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addPocket(pocket: Pocket) {
        viewModelScope.launch {
            repository.addPocket(pocket)
        }
    }

    fun updatePocket(pocket: Pocket) {
        viewModelScope.launch {
            repository.updatePocket(pocket)
        }
    }


    fun deletePocket(id: Long) {
        viewModelScope.launch {
            repository.deletePocket(id)
        }
    }



}