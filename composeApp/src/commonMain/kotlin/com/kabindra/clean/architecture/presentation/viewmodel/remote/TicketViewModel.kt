package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.data.request.TicketChangeOwnerRequest
import com.kabindra.clean.architecture.data.request.TicketChangeStateRequest
import com.kabindra.clean.architecture.data.request.TicketDataRequest
import com.kabindra.clean.architecture.data.request.TicketDetailsRequest
import com.kabindra.clean.architecture.data.request.TicketFilterDataRequest
import com.kabindra.clean.architecture.domain.entity.Ticket
import com.kabindra.clean.architecture.domain.entity.TicketChangeOwner
import com.kabindra.clean.architecture.domain.entity.TicketChangeState
import com.kabindra.clean.architecture.domain.entity.TicketDetails
import com.kabindra.clean.architecture.domain.entity.TicketFilter
import com.kabindra.clean.architecture.domain.usecase.remote.TicketUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketViewModel(private val ticketUseCase: TicketUseCase) : ViewModel() {
    private val _ticketState =
        MutableStateFlow<Result<Ticket>>(Result.Initial)
    val ticketState: StateFlow<Result<Ticket>> get() = _ticketState

    private val _ticketFilterState =
        MutableStateFlow<Result<TicketFilter>>(Result.Initial)
    val ticketFilterState: StateFlow<Result<TicketFilter>> get() = _ticketFilterState

    private val _ticketDetailsState =
        MutableStateFlow<Result<TicketDetails>>(Result.Initial)
    val ticketDetailsState: StateFlow<Result<TicketDetails>> get() = _ticketDetailsState

    private val _ticketChangeStateStateItem =
        MutableStateFlow<Result<TicketChangeState>>(Result.Initial)
    val ticketChangeStateStateItem: StateFlow<Result<TicketChangeState>> get() = _ticketChangeStateStateItem

    private val _ticketChangeStateStateDetail =
        MutableStateFlow<Result<TicketChangeState>>(Result.Initial)
    val ticketChangeStateStateDetail: StateFlow<Result<TicketChangeState>> get() = _ticketChangeStateStateDetail

    private val _ticketChangeOwnerState =
        MutableStateFlow<Result<TicketChangeOwner>>(Result.Initial)
    val ticketChangeOwnerState: StateFlow<Result<TicketChangeOwner>> get() = _ticketChangeOwnerState

    fun getTicket(ticketDataRequest: TicketDataRequest) {
        viewModelScope.launch {
            ticketUseCase.executeGetTicket(ticketDataRequest).collect { result ->
                _ticketState.value = result
            }
        }
    }

    fun getTicketFilter(ticketFilterDataRequest: TicketFilterDataRequest) {
        viewModelScope.launch {
            ticketUseCase.executeGetTicketFilter(ticketFilterDataRequest).collect { result ->
                _ticketFilterState.value = result
            }
        }
    }

    fun getTicketDetails(ticketDetailsRequest: TicketDetailsRequest) {
        viewModelScope.launch {
            ticketUseCase.executeGetTicketDetails(ticketDetailsRequest).collect { result ->
                _ticketDetailsState.value = result
            }
        }
    }

    fun getTicketChangeStateItem(ticketChangeStateRequest: TicketChangeStateRequest) {
        viewModelScope.launch {
            ticketUseCase.executeGetTicketChangeState(ticketChangeStateRequest).collect { result ->
                _ticketChangeStateStateItem.value = result
            }
        }
    }

    fun getTicketChangeStateDetail(ticketChangeStateRequest: TicketChangeStateRequest) {
        viewModelScope.launch {
            ticketUseCase.executeGetTicketChangeState(ticketChangeStateRequest).collect { result ->
                _ticketChangeStateStateDetail.value = result
            }
        }
    }

    fun getTicketChangeOwner(ticketChangeOwnerRequest: TicketChangeOwnerRequest) {
        viewModelScope.launch {
            ticketUseCase.executeGetTicketChangeOwner(ticketChangeOwnerRequest).collect { result ->
                _ticketChangeOwnerState.value = result
            }
        }
    }

    fun resetStates() {
        _ticketState.value = Result.Initial
        _ticketFilterState.value = Result.Initial
        _ticketDetailsState.value = Result.Initial
        _ticketChangeStateStateItem.value = Result.Initial
        _ticketChangeStateStateDetail.value = Result.Initial
        _ticketChangeOwnerState.value = Result.Initial
    }
}