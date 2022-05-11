package com.sonnt.moneymanagement.data.network.response

class AuthenticationResponse(): BaseResponse<AuthInfo>() {
    override var data: AuthInfo? = null
}

class AuthInfo(
    var userId: Long = 0L,
    var jwtToken: String = ""
)