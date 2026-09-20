package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.MobilityViewModel
import com.example.ui.components.RotaSeguraBottomNav
import com.example.ui.components.RotaSeguraTopBar
import com.example.ui.components.SosEmergencyDialog
import com.example.ui.screens.AlertsScreen
import com.example.ui.screens.B2GGovernanceScreen
import com.example.ui.screens.MobiIaScreen
import com.example.ui.screens.RoutesScreen
import com.example.ui.theme.RotaSeguraTheme

class MainActivity : ComponentActivity() {

  private val viewModel: MobilityViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      RotaSeguraTheme {
        RotaSeguraApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun RotaSeguraApp(viewModel: MobilityViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(uiState.snackbarMessage) {
    uiState.snackbarMessage?.let { msg ->
      snackbarHostState.showSnackbar(msg)
      viewModel.clearSnackbarMessage()
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      RotaSeguraTopBar(
        isProtectedMode = uiState.isNavigating,
        onSosClick = { viewModel.showSosDialog(true) },
        onJourneyClick = { viewModel.setActiveTab(3) }
      )
    },
    bottomBar = {
      RotaSeguraBottomNav(
        currentTab = uiState.activeTab,
        onTabSelected = { viewModel.setActiveTab(it) }
      )
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
  ) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding)) {
      when (uiState.activeTab) {
        0 -> RoutesScreen(viewModel = viewModel, uiState = uiState)
        1 -> MobiIaScreen(viewModel = viewModel, uiState = uiState)
        2 -> AlertsScreen(viewModel = viewModel, uiState = uiState)
        3 -> B2GGovernanceScreen(viewModel = viewModel, uiState = uiState)
      }
    }
  }

  if (uiState.sosDialogVisible) {
    SosEmergencyDialog(
      onDismiss = { viewModel.showSosDialog(false) },
      onConfirmSos = { viewModel.triggerEmergencySos() }
    )
  }
}

