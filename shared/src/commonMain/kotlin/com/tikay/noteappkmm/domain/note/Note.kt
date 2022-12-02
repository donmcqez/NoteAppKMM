package com.tikay.noteappkmm.domain.note

import com.tikay.noteappkmm.presentation.*
import kotlinx.datetime.LocalDateTime

data class Note(
    val id:Long?,
    val title:String,
    val content:String,
    val colorHex:Long,
    val createdAt:LocalDateTime
){
    companion object{
        private val colors = listOf(RedOrangeHex, RedPinkHex, BabyBlueHex, VioletHex, LightGreenHex)

        fun generateRandomColor() = colors.random();
    }
}
