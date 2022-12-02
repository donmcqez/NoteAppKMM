package com.tikay.noteappkmm.data.note

import com.tikay.noteappkmm.data.note.repository.NoteDataSource
import com.tikay.noteappkmm.domain.note.Note
import com.tikay.noteappkmm.domain.note.NoteRepository

/**
 * Implements NoteRepository interface using SqlDelight
 */
class NoteRepositoryImpl(noteData: NoteDataSource) : NoteRepository {

    private val noteDataSource = noteData

    override suspend fun insertNote(note: Note) {
        noteDataSource.insertNote(note)
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteDataSource.getNoteById(id)
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteDataSource.getAllNotes()
    }

    override suspend fun deleteNoteById(id: Long) {
        noteDataSource.deleteNoteById(id)
    }
}