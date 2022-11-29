package com.tikay.noteappkmm.data.note

import com.tikay.noteappkmm.domain.note.Note
import database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNote():Note{
    return Note(
        id = id,
        title  = title,
        content = content,
        hexColor = colorHex,
        createdAt = Instant
            .fromEpochSeconds(createdAt)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}