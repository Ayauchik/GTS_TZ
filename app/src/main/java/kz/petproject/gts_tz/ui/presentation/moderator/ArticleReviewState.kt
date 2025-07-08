package kz.petproject.gts_tz.ui.presentation.moderator

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.domain.use_cases.ApproveArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.GetArticleByIdUseCase
import kz.petproject.gts_tz.domain.use_cases.RejectArticleUseCase

data class ArticleReviewState(
    val article: Article? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val showRejectDialog: Boolean = false
)

sealed class ArticleReviewEffect {
    data object RefreshAndNavigateBack : ArticleReviewEffect()
    data class ShowSnackbar(val message: String) : ArticleReviewEffect()
}

class ArticleReviewViewModel(
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val approveArticleUseCase: ApproveArticleUseCase,
    private val rejectArticleUseCase: RejectArticleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleId: String = savedStateHandle.get<String>("articleId")!!

    private val _state = MutableStateFlow(ArticleReviewState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ArticleReviewEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadArticle()
    }

    private fun loadArticle() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getArticleByIdUseCase(articleId)
                .onSuccess { article -> _state.update { it.copy(article = article, isLoading = false) } }
                .onFailure { handleFailure(it) }
        }
    }

    fun onApproveClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            approveArticleUseCase(articleId)
                .onSuccess {
                    _effect.send(ArticleReviewEffect.ShowSnackbar("Article approved"))
                    _effect.send(ArticleReviewEffect.RefreshAndNavigateBack)
                }
                .onFailure { handleFailure(it) }
        }
    }

    fun onRejectClicked() {
        _state.update { it.copy(showRejectDialog = true) }
    }
    
    fun onDialogDismiss() {
         _state.update { it.copy(showRejectDialog = false) }
    }

    fun onRejectConfirmed(reason: String) {
        viewModelScope.launch {
            _state.update { it.copy(showRejectDialog = false, isLoading = true) }
            rejectArticleUseCase(articleId, reason)
                .onSuccess {
                    _effect.send(ArticleReviewEffect.ShowSnackbar("Article rejected"))
                    _effect.send(ArticleReviewEffect.RefreshAndNavigateBack)
                }
                .onFailure { handleFailure(it) }
        }
    }

    private fun handleFailure(exception: Throwable) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = false, error = exception.message) }
            _effect.send(ArticleReviewEffect.ShowSnackbar(exception.message ?: "An error occurred"))
        }
    }
}