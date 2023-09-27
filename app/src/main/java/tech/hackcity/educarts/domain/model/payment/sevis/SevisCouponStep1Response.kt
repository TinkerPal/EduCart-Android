package tech.hackcity.educarts.domain.model.payment.sevis

data class SevisCouponStep1Response(
    val `data`: SevisCouponStep1ResponseData,
    val error: Boolean,
    val message: String
)