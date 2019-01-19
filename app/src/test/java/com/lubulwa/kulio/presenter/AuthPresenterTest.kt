package com.lubulwa.kulio.presenter

import com.lubulwa.kulio.BuildConfig
import com.lubulwa.kulio.ui.interfaces.AuthContract
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthPresenterTest {

    private lateinit var view: AuthContract.View

    private lateinit var authPresenter: AuthPresenter

    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    @Before
    fun setUp() {
        view = mock()
        authPresenter = AuthPresenter(view)
    }

    @Test
    fun shouldNotReturnAcccessTokenWhenParametersEmpty() {
        authPresenter.getToken("","")

        verify(view, never()).getTokenSuccess(any())
    }

    @Test
    fun shouldReturnSuccessWithCorrectCreds() {
        authPresenter.getToken(BuildConfig.LufthansaApiKey,BuildConfig.LufthansaSecretKey)

        verify(view).getTokenSuccess(anyObject())
    }
}