package tech.hackcity.educarts.domain.model.payment.sevis

data class SevisCouponStep2Response(
    val `data`: SevisCouponStep2ResponseData,
    val error: Boolean,
    val message: String
)