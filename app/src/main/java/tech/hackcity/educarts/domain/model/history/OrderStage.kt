package tech.hackcity.educarts.domain.model.history

data class OrderStage(
    val date: String?,
    val description: String,
    val is_completed: Boolean,
    val stage: String
)