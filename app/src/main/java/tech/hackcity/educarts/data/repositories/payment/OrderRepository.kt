package tech.hackcity.educarts.data.repositories.payment

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tech.hackcity.educarts.data.network.RetrofitInstance
import tech.hackcity.educarts.data.network.SafeApiRequest
import tech.hackcity.educarts.data.storage.SharePreferencesManager
import tech.hackcity.educarts.domain.model.history.OrderDetailsResponse
import tech.hackcity.educarts.domain.model.payment.OrderSummaryResponse
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISCategoryResponse
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep1Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep2Response
import tech.hackcity.educarts.domain.model.payment.sevis.SEVISFeeStep3Response
import tech.hackcity.educarts.domain.model.support.MultipleChoiceResponse

/**
 *Created by Victor Loveday on 8/3/23
 */
class OrderRepository(
    private val api: RetrofitInstance,
) : SafeApiRequest() {

    suspend fun trackOrder(orderId: String): OrderDetailsResponse {
        return apiRequest { api.orderAPI.trackOrder(orderId) }
    }

    suspend fun fetchOrderSummary(orderType: String): OrderSummaryResponse {
        return apiRequest { api.orderAPI.fetchOrderSummary(orderType) }
    }
}