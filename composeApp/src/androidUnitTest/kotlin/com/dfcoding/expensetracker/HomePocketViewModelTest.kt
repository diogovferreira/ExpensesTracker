package com.dfcoding.expensetracker

import app.cash.turbine.test
import com.dfcoding.expensetracker.domain.model.Pocket
import com.dfcoding.expensetracker.ui.home.HomePocketViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class HomePocketViewModelTest {

    private lateinit var fakeRepo: FakeExpenseRepository
    private lateinit var viewModel: HomePocketViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeRepo = FakeExpenseRepository()
        viewModel = HomePocketViewModel(fakeRepo)
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `pockets state emits list from repository`() = runTest {
        val testPockets = listOf(
            Pocket(id = 1L, name = "Work", icon = "wallet", date = 0L, currency = "EUR"),
            Pocket(id = 2L, name = "Personal", icon = "home", date = 0L, currency = "EUR")
        )

        viewModel.pockets.test {
            awaitItem()

            fakeRepo.setPockets(testPockets)

            val emission = awaitItem()
            assertEquals(2, emission.size)
            assertEquals("Work", emission[0].name)
        }
    }

    @Test
    fun `delete pocket removes it from state`() = runTest {
        fakeRepo.setPockets(listOf(
            Pocket(id = 1L, name = "Work", icon = "wallet", date = 0L, currency = "USD")
        ))

        viewModel.deletePocket(1L)

        viewModel.pockets.test {
            val emission = awaitItem()
            assertTrue(emission.isEmpty())
        }
    }
}