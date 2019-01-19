package com.lubulwa.kulio.ui.interfaces

import com.lubulwa.kulio.model.TokenResponse

interface AuthContract {

    interface View {
        fun getTokenSuccess(tokenResponse: TokenResponse)
        fun getTokenFailed(errorMessage: String, errorCode: Int = 0)
    }

    interface Presenter {
        fun getToken(clientId: String, clientSecret: String)
    }

}