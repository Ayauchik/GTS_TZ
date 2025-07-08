package kz.petproject.gts_tz.ui.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.petproject.gts_tz.data.local.SessionManager

class MainViewModel(sessionManager: SessionManager) : ViewModel() {
    private val _userRole = MutableStateFlow(sessionManager.getUserRole())
    val userRole = _userRole.asStateFlow()
}