package tech.hackcity.educarts.domain.model.payment

import java.io.Serializable

/**
 *Created by Victor Loveday on 6/1/23
 */
data class School(
    val id: Int,
    val name: String,
    val state: String,
    val countryCode: String,
    val flag: String?
): Serializable
