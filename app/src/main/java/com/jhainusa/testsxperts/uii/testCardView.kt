package com.jhainusa.testsxperts.uii

data class testCardView(
    val testname: String? = null,
    val testImage: String? = null,
    val Subjects:Subjects?=null
// This should match exactly
){
    constructor() : this(null, null,null)
}

