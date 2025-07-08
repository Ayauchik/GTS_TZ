package kz.petproject.gts_tz.ui.presentation.author

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.domain.use_cases.CreateArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.DeleteArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.EditArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.GetArticleByIdUseCase
import kz.petproject.gts_tz.domain.use_cases.SubmitArticleUseCase

data class CreatePostState(
    val articleId: String? = null,
    val title: String = "",
    val content: String = "",
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val status: String? = "DRAFT",
    val showDeleteConfirmDialog: Boolean = false
)

sealed class CreatePostEffect {
    data class ShowSnackbar(val message: String) : CreatePostEffect()
    data object NavigateBack : CreatePostEffect()
}


class CreatePostViewModel(
    private val createArticleUseCase: CreateArticleUseCase,
    private val submitArticleUseCase: SubmitArticleUseCase,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val editArticleUseCase: EditArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CreatePostState())
    val state = _state.asStateFlow()

    private val _effect = Channel<CreatePostEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        val articleId: String? = savedStateHandle["articleId"]
        if (articleId != null && articleId != "new") {
            // If the ID is valid, trigger the loading function.
            loadArticleForEditing(articleId)
        }
    }

    /**
     * Fetches the data for a single article and populates the state.
     */
    private fun loadArticleForEditing(articleId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getArticleByIdUseCase(articleId)
                .onSuccess { article ->
                    Log.e("viewmodel", "changing the isEditing ${_state.value.isEditing}, before loading")
                    // On success, update the state with the fetched data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEditing = true,
                            articleId = article.id,
                            title = article.title,
                            content = article.content,
                            status = article.status
                        )
                    }
                    Log.e("viewmodel", "changing the isEditing to true ${_state.value.isEditing}, after loading")
                }
                .onFailure { handleFailure(it) }
        }
    }

    fun onDeleteClicked() {
        _state.update { it.copy(showDeleteConfirmDialog = true) }
    }

    fun onDeleteDialogDismiss() {
        _state.update { it.copy(showDeleteConfirmDialog = false) }
    }

    fun onDeleteConfirmed() {
        viewModelScope.launch {
            val articleId = state.value.articleId ?: return@launch
            _state.update { it.copy(isLoading = true, showDeleteConfirmDialog = false) }

            deleteArticleUseCase(articleId)
                .onSuccess {
                    _effect.send(CreatePostEffect.ShowSnackbar("Article deleted successfully"))
                    _effect.send(CreatePostEffect.NavigateBack) // Use the success signal
                }
                .onFailure { handleFailure(it) }
        }
    }

    fun onTitleChange(title: String) = _state.update { it.copy(title = title, error = null) }
    fun onContentChange(content: String) = _state.update { it.copy(content = content, error = null) }


    fun onSaveClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val currentState = state.value

            if (currentState.isEditing) {
                // --- UPDATE LOGIC ---
                Log.e("viewmodel", "${currentState.articleId} editing")
                editArticleUseCase(currentState.articleId!!, currentState.title, currentState.content)
                    .onSuccess {
                        _effect.send(CreatePostEffect.ShowSnackbar("Article updated successfully"))
                        _effect.send(CreatePostEffect.NavigateBack)
                    }
                    .onFailure { handleFailure(it) }
            } else {
                // --- CREATE LOGIC ---
                Log.e("viewmodel", "creating")
                createArticleUseCase(currentState.title, currentState.content)
                    .onSuccess {
                        _effect.send(CreatePostEffect.ShowSnackbar("Draft saved successfully"))
                        _effect.send(CreatePostEffect.NavigateBack)
                    }
                    .onFailure { handleFailure(it) }
            }
        }
    }

    fun onSubmitClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val currentState = state.value

            if (currentState.isEditing) {
                // --- UPDATE AND SUBMIT ---
                Log.e("viewmodel submit", "${currentState.articleId} editing")
                editArticleUseCase(currentState.articleId!!, currentState.title, currentState.content)
                    .onSuccess { updatedArticle ->
                        Log.e("viewmodel submit", "submitting")
                        submitArticleForModeration(updatedArticle.id)
                        Log.e("viewmodel submit", "submitted")
                    }
                    .onFailure { handleFailure(it) }
            } else {
                // --- CREATE AND SUBMIT ---
                Log.e("viewmodel submit", "creating")
                createArticleUseCase(currentState.title, currentState.content)
                    .onSuccess { createdArticle ->
                        submitArticleForModeration(createdArticle.id)
                    }
                    .onFailure { handleFailure(it) }
            }
        }
    }

    private suspend fun submitArticleForModeration(articleId: String) {
        Log.e("viewmodel submit", "sumbitArctileForModeration")
        submitArticleUseCase(articleId)
            .onSuccess {
                _effect.send(CreatePostEffect.ShowSnackbar("Article submitted for moderation"))
                _effect.send(CreatePostEffect.NavigateBack)
            }
            .onFailure { handleFailure(it) }
    }

    private fun handleFailure(exception: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                error = exception.message ?: "An unknown error occurred"
            )
        }
    }
}