package tech.hackcity.educarts.domain.model.payment.sevis

import java.io.Serializable

data class SEVISFeeStep3ResponseData(
    val category: String,
    val city: String,
    val country: String,
    val country_of_birth: String,
    val country_of_citizenship: String,
    val date_of_birth: String,
    val email: String,
    val fee_in_dollars: String,
    val fee_in_naira: String,
    val form: String,
    val form_type: String,
    val given_name: String,
    val id: Int,
    val international_passport: String,
    val last_name: String,
    val order_id: String,
    val passport: String,
    val phone_number: String,
    val sevis_id: String,
    val state: String,
    val street_address_1: String,
    val street_address_2: String,
    val user: String
): Serializable