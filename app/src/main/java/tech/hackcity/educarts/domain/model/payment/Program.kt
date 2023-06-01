package tech.hackcity.educarts.domain.model.payment

import java.io.Serializable

/**
 *Created by Victor Loveday on 6/1/23
 */
data class Program(
    val id: Int,
    val duration: String,
    val course: String,
    val university: String,
    val state: String,
    val tuitionFee: String,
    val applicationFee: String
): Serializable
