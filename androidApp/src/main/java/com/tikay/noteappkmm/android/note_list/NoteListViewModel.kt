package com.tikay.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikay.noteappkmm.domain.note.Note
import com.tikay.noteappkmm.domain.note.NoteDataSource
import com.tikay.noteappkmm.domain.note.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val searchNote = SearchNoteUseCase()

    private val notes = savedStateHandle.getStateFlow(NOTES, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SEARCH_TEXT, "")
    private val isSearchActive = savedStateHandle.getStateFlow(IS_SEARCH_ACTIVE, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNote.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[NOTES] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChanged(text: String) {
        savedStateHandle[SEARCH_TEXT] = text
    }

    fun onToggleSearch() {
        savedStateHandle[IS_SEARCH_ACTIVE] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle[SEARCH_TEXT] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            // this should trigger by default if database is returning flow
            loadNotes()

        }
    }


    companion object {
        private const val NOTES = "notes"
        private const val SEARCH_TEXT = "search_text"
        private const val IS_SEARCH_ACTIVE = "is_search_active"
    }

}