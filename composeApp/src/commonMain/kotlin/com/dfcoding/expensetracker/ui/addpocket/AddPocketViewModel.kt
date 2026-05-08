package com.dfcoding.expensetracker.ui.addpocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfcoding.expensetracker.data.repository.ExpenseRepositoryInterface
import com.dfcoding.expensetracker.domain.model.Pocket
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class AddPocketViewModel(private val repository: ExpenseRepositoryInterface) : ViewModel() {

    fun addPocket(name: String, icon: String,currency: String){
        viewModelScope.launch {
            val pocket  = Pocket(
                name = name,
                icon = icon,
                date = Clock.System.now().toEpochMilliseconds(),
                currency = currency
            )
            repository.addPocket(pocket)

        }
    }

    fun updatePocket(id: Long, name: String, icon: String,currency: String){
        viewModelScope.launch {
            val pocket  = Pocket(
                id = id,
                name = name,
                icon = icon,
                date = Clock.System.now().toEpochMilliseconds(),
                currency = currency
            )
            repository.updatePocket(pocket)

        }
    }
}