package com.tikay.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikay.noteappkmm.domain.note.Note
import com.tikay.noteappkmm.domain.note.NoteRepository
import com.tikay.noteappkmm.domain.utils.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow(NOTE_TITLE, "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow(IS_TITLE_FOCUSED, false)
    private val noteContent = savedStateHandle.getStateFlow(NOTE_CONTENT, "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow(IS_CONTENT_FOCUSED, false)
    private val noteColor = savedStateHandle
        .getStateFlow(NOTE_COLOR, Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NoteDetailState()
    )

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteRepository.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle[NOTE_TITLE] = note.title
                    savedStateHandle[NOTE_CONTENT] = note.content
                    savedStateHandle[NOTE_COLOR] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[NOTE_TITLE] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[NOTE_CONTENT] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle[IS_TITLE_FOCUSED] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle[IS_CONTENT_FOCUSED] = isFocused
    }

    fun saveNote(){
        viewModelScope.launch {
            noteRepository.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    createdAt = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

    companion object {
        private const val NOTE_TITLE = "note_title"
        private const val IS_TITLE_FOCUSED = "is_title_focused"
        private const val NOTE_CONTENT = "note_content"
        private const val IS_CONTENT_FOCUSED = "is_content_focused"
        private const val NOTE_COLOR = "note_color"
    }
}