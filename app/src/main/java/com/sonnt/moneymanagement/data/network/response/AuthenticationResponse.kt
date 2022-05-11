package com.sonnt.moneymanagement.data.network.response

//data class AuthenticationResponse(): BaseResponse<AuthInfo>() {
//    override var data: AuthInfo? = null
//}

data class AuthenticationResponse(
    var code: Int = 0,
    var message: String = "",
    var data: AuthInfo = AuthInfo()
)

data class AuthInfo(
    var userId: Long = 0L,
    var jwtToken: String = ""
)