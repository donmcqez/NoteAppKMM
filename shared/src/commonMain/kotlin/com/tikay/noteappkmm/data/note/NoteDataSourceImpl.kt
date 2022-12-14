package com.tikay.noteappkmm.data.note

import com.tikay.noteappkmm.data.note.repository.NoteDataSource
import com.tikay.noteappkmm.database.NoteDatabase
import com.tikay.noteappkmm.domain.note.Note
import com.tikay.noteappkmm.domain.utils.DateTimeUtil

/**
 *  Aggregates data from local and remote sources
 */
class NoteDataSourceImpl(db: NoteDatabase) : NoteDataSource {
    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            createdAt = DateTimeUtil.toEpochMillis(note.createdAt)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes()
            .executeAsList()
            .map {
                it.toNote()
            }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}