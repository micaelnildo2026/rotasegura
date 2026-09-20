package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.GeminiAdvisorService
import com.example.data.MobilityRepository
import com.example.model.B2GZoneInsight
import com.example.model.CommuteMode
import com.example.model.CommuteProfile
import com.example.model.IncidentReportEntity
import com.example.model.MobilityContext
import com.example.model.RouteOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
  val sender: String, // "USER" or "AI"
  val text: String,
  val timestamp: Long = System.currentTimeMillis()
)

data class MobilityUiState(
  val activeTab: Int = 0, // 0=Rotas, 1=MobiIA, 2=Alertas, 3=B2G
  val context: MobilityContext = MobilityContext(),
  val routes: List<RouteOption> = emptyList(),
  val selectedRoute: RouteOption? = null,
  val reports: List<IncidentReportEntity> = emptyList(),
  val b2gInsights: List<B2GZoneInsight> = emptyList(),
  val isNavigating: Boolean = false,
  val navigationStepIndex: Int = 0,
  val safetyTimerSeconds: Int = 1800, // 30 min safe check-in
  val sosDialogVisible: Boolean = false,
  val sosAlertSent: Boolean = false,
  val newReportDialogVisible: Boolean = false,
  val aiAdvice: String = "",
  val isAiLoading: Boolean = false,
  val chatMessages: List<ChatMessage> = emptyList(),
  val activeCategoryFilter: String? = null, // null = all
  val checkInCompleted: Boolean = false,
  val snackbarMessage: String? = null
)

class MobilityViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: MobilityRepository
  private val aiService = GeminiAdvisorService()

  private val _uiState = MutableStateFlow(MobilityUiState())
  val uiState: StateFlow<MobilityUiState> = _uiState.asStateFlow()

  init {
    val database = AppDatabase.getDatabase(application)
    repository = MobilityRepository(database.incidentDao())

    viewModelScope.launch {
      repository.initializePrepopulatedDataIfEmpty()
    }

    // Collect Room database incident reports reactively
    viewModelScope.launch {
      repository.allReports.collect { list ->
        _uiState.update { it.copy(reports = list) }
      }
    }

    // Initialize routes and B2G insights
    val initialRoutes = repository.getCalculatedRoutes(_uiState.value.context)
    val insights = repository.getB2GInsights()
    _uiState.update {
      it.copy(
        routes = initialRoutes,
        selectedRoute = initialRoutes.firstOrNull(),
        b2gInsights = insights
      )
    }

    // Generate initial contextual mobility advice
    refreshAiAdvice()
  }

  fun setActiveTab(tab: Int) {
    _uiState.update { it.copy(activeTab = tab) }
  }

  fun selectRoute(route: RouteOption) {
    _uiState.update { it.copy(selectedRoute = route) }
  }

  fun updateCommuteProfile(profile: CommuteProfile) {
    val newContext = _uiState.value.context.copy(selectedProfile = profile)
    val updatedRoutes = repository.getCalculatedRoutes(newContext)
    _uiState.update {
      it.copy(
        context = newContext,
        routes = updatedRoutes,
        selectedRoute = updatedRoutes.firstOrNull()
      )
    }
    refreshAiAdvice()
  }

  fun updateCommuteMode(mode: CommuteMode) {
    val newContext = _uiState.value.context.copy(selectedMode = mode)
    val updatedRoutes = repository.getCalculatedRoutes(newContext)
    _uiState.update {
      it.copy(
        context = newContext,
        routes = updatedRoutes,
        selectedRoute = updatedRoutes.firstOrNull()
      )
    }
    refreshAiAdvice()
  }

  fun updateOriginDestination(origin: String, destination: String) {
    val newContext = _uiState.value.context.copy(origin = origin, destination = destination)
    val updatedRoutes = repository.getCalculatedRoutes(newContext)
    _uiState.update {
      it.copy(
        context = newContext,
        routes = updatedRoutes,
        selectedRoute = updatedRoutes.firstOrNull(),
        snackbarMessage = "Rotas recalculadas com parâmetros de segurança atualizados"
      )
    }
    refreshAiAdvice()
  }

  fun refreshAiAdvice() {
    viewModelScope.launch {
      _uiState.update { it.copy(isAiLoading = true) }
      val advice = aiService.generateMobilityAdvice(_uiState.value.context)
      _uiState.update { it.copy(aiAdvice = advice, isAiLoading = false) }
    }
  }

  fun sendChatMessage(query: String) {
    if (query.isBlank()) return
    val userMsg = ChatMessage(sender = "USER", text = query)
    val currentMessages = _uiState.value.chatMessages + userMsg
    _uiState.update { it.copy(chatMessages = currentMessages, isAiLoading = true) }

    viewModelScope.launch {
      val response = aiService.generateMobilityAdvice(_uiState.value.context, query)
      val aiMsg = ChatMessage(sender = "AI", text = response)
      _uiState.update {
        it.copy(
          chatMessages = it.chatMessages + aiMsg,
          isAiLoading = false
        )
      }
    }
  }

  fun startNavigation() {
    _uiState.update {
      it.copy(
        isNavigating = true,
        navigationStepIndex = 0,
        checkInCompleted = false,
        snackbarMessage = "Modo Trajeto Seguro ativado! Monitorando iluminação e percurso."
      )
    }
  }

  fun advanceNavigationStep() {
    val currentRoute = _uiState.value.selectedRoute ?: return
    val nextIndex = _uiState.value.navigationStepIndex + 1
    if (nextIndex < currentRoute.segments.size) {
      _uiState.update { it.copy(navigationStepIndex = nextIndex) }
    } else {
      _uiState.update {
        it.copy(
          isNavigating = false,
          checkInCompleted = true,
          snackbarMessage = "Você chegou ao seu destino em segurança! Check-in concluído."
        )
      }
    }
  }

  fun stopNavigation() {
    _uiState.update {
      it.copy(
        isNavigating = false,
        snackbarMessage = "Navegação encerrada."
      )
    }
  }

  fun performSafetyCheckIn() {
    _uiState.update {
      it.copy(
        checkInCompleted = true,
        snackbarMessage = "Check-in de chegada segura enviado para contatos e SESMT corporativo."
      )
    }
  }

  fun showSosDialog(show: Boolean) {
    _uiState.update { it.copy(sosDialogVisible = show) }
  }

  fun triggerEmergencySos() {
    _uiState.update {
      it.copy(
        sosDialogVisible = false,
        sosAlertSent = true,
        snackbarMessage = "🚨 ALERTA SOS ATIVADO! Coordenadas e rota enviadas para emergência e empresa."
      )
    }
  }

  fun dismissSosAlert() {
    _uiState.update { it.copy(sosAlertSent = false) }
  }

  fun showNewReportDialog(show: Boolean) {
    _uiState.update { it.copy(newReportDialogVisible = show) }
  }

  fun createIncidentReport(
    category: String,
    title: String,
    description: String,
    address: String,
    severity: String
  ) {
    viewModelScope.launch {
      val report = IncidentReportEntity(
        category = category,
        title = title,
        description = description,
        address = address,
        severity = severity,
        timestamp = System.currentTimeMillis(),
        upvotes = 1,
        reportedByUser = true
      )
      repository.addReport(report)
      _uiState.update {
        it.copy(
          newReportDialogVisible = false,
          snackbarMessage = "Alerta registrado! Contribuindo para a segurança de todos os trabalhadores."
        )
      }
    }
  }

  fun upvoteReport(id: Long) {
    viewModelScope.launch {
      repository.upvoteReport(id)
      _uiState.update {
        it.copy(snackbarMessage = "Confirmação de risco registrada.")
      }
    }
  }

  fun setCategoryFilter(category: String?) {
    _uiState.update { it.copy(activeCategoryFilter = category) }
  }

  fun clearSnackbarMessage() {
    _uiState.update { it.copy(snackbarMessage = null) }
  }
}
