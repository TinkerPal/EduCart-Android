package tech.hackcity.educarts.domain.model.support

import java.io.Serializable

data class FaqCategory(
    val `data`: List<Faq>,
    val description: String,
    val image: String?,
    val title: String
): Serializable