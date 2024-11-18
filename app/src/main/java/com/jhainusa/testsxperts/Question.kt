package com.jhainusa.testsxperts

data class Question(
    val question_text: String?,
    val image_url: String?,
    val option: List<String>?,
    val correct_answer: String?
)
{
    constructor() : this(null,null,null,null)
}