package tech.hackcity.educarts.domain.model.support

data class Faq(
    val id: Int,
    val question: String,
    val answer: String,
    val category: String,
)