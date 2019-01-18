package com.lubulwa.kulio

import com.lubulwa.kulio.presenter.HomePresenter
import com.lubulwa.kulio.ui.interfaces.HomeContract
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class SearchFlightScheduleTests {

    lateinit var presenter: HomePresenter
    lateinit var view: HomeContract.View

    @Test
    fun search_withEmptyQuery_callsFindScheduledFlightsFailed() {
        presenter.findScheduledFlights("", "", "")
    }

    @Test
    fun search_withEmptyQuery_doesNotCallFindScheduledFlightsSuccess() {
        presenter.findScheduledFlights("", "", "")

        verify(view, never()).findScheduledFlightsSuccess(null)
    }

    @Before
    fun setup() {
        view = mock()
        presenter = HomePresenter(view)
    }

}