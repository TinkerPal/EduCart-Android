package tech.hackcity.educarts.data.repositories.payment

import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest

/**
 *Created by Victor Loveday on 6/1/23
 */
class ApplicationFeeRepository(
    private val api: RetrofitInstance
): SafeApiRequest() {
}