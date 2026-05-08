package com.dfcoding.expensetracker

import app.cash.turbine.test
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.ui.addpocket.AddPocketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddPocketViewModelTest {

    private lateinit var fakeRepo: FakeExpenseRepository

    private lateinit var viewModel: AddPocketViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeRepo = FakeExpenseRepository()
        viewModel = AddPocketViewModel(fakeRepo)
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addPocket adds pocket to repository`() = runTest {
        viewModel.addPocket(name = "Work", icon = "wallet", currency = "EUR")

        advanceUntilIdle()

        fakeRepo.getAllPockets().test {
            val pockets = awaitItem()
            assertEquals(1, pockets.size)
            assertEquals("Work", pockets[0].name)
        }
    }


    @Test
    fun `updatePocket updates pocket in repository`() = runTest {

        val pocket = Pocket(id = 1L, name = "Work", icon = "wallet", date = 0L, currency = "EUR")
        viewModel.addPocket(name = pocket.name, icon = pocket.icon, currency = pocket.currency)

        advanceUntilIdle()

        fakeRepo.getAllPockets().test {
            val pockets = awaitItem()
            assertEquals(1, pockets.size)
            assertEquals(pocket.name, pockets[0].name)
        }

        viewModel.updatePocket(id = pocket.id, name = "Vacation", icon = pocket.icon, currency = pocket.currency)

        advanceUntilIdle()

        fakeRepo.getAllPockets().test {
            val pockets = awaitItem()
            assertEquals(1, pockets.size)
            assertEquals(pocket.name, pockets[0].name)
        }

    }



}