package com.madhavsolanki.firebase.models

data class NoteItem(
    val title:String ,
    val description:String,
    val noteId:String
)
{
    constructor(): this("", "","")
}
