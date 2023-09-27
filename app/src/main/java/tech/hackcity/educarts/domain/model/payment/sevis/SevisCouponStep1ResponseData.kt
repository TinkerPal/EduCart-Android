package tech.hackcity.educarts.domain.model.payment.sevis

data class SevisCouponStep1ResponseData(
    val form_type: String,
    val sevis_coupon: String,
    val user: String
)