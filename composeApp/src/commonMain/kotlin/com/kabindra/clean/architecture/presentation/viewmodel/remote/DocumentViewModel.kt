package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.TicketDocumentRemoveRequest
import com.kabindra.clean.architecture.domain.entity.TicketDocumentRemove
import com.kabindra.clean.architecture.domain.usecase.remote.DocumentUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DocumentViewModel(private val documentUseCase: DocumentUseCase) : ViewModel() {
    private val _documentRemoveState =
        MutableStateFlow<Result<TicketDocumentRemove>>(Result.Initial)
    val documentRemoveState: StateFlow<Result<TicketDocumentRemove>> get() = _documentRemoveState

    fun getDocumentRemove(ticketDocumentRemoveRequest: TicketDocumentRemoveRequest) {
        viewModelScope.launch {
            documentUseCase.executeGetDocumentRemove(ticketDocumentRemoveRequest)
                .collect { result ->
                    _documentRemoveState.value = result
                }
        }
    }

    fun resetStates() {
        _documentRemoveState.value = Result.Initial
    }
}