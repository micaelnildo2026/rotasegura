package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.WheelchairPickup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.IncidentReportEntity
import com.example.ui.MobilityUiState
import com.example.ui.MobilityViewModel
import com.example.ui.theme.SafeAmber
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeRed

@Composable
fun AlertsScreen(
  viewModel: MobilityViewModel,
  uiState: MobilityUiState
) {
  var showAddDialog by remember { mutableStateOf(false) }

  val categories = listOf(
    null to "Todos",
    "ILUMINACAO" to "💡 Iluminação",
    "VIOLENCIA" to "🛡️ Segurança Pública",
    "SINISTRO_TRANSITO" to "⚠️ Sinistros",
    "TRANSPORTE" to "🚌 Ônibus/Horários",
    "ACESSIBILIDADE" to "♿ Acessibilidade"
  )

  val filteredReports = if (uiState.activeCategoryFilter == null) {
    uiState.reports
  } else {
    uiState.reports.filter { it.category == uiState.activeCategoryFilter }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .testTag("alerts_screen")
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 72.dp),
      contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Column {
          Text(
            text = "Alertas Colaborativos de Mobilidade",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Alimente a inteligência das rotas e ajude outros trabalhadores a evitar perigos",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      // Filter chips
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          categories.forEach { (catKey, label) ->
            val isSelected = uiState.activeCategoryFilter == catKey
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setCategoryFilter(catKey) },
              label = { Text(label, fontSize = 12.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
              )
            )
          }
        }
      }

      if (filteredReports.isEmpty()) {
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "Nenhum alerta nesta categoria no momento.",
                style = MaterialTheme.typography.bodyMedium
              )
            }
          }
        }
      } else {
        items(filteredReports) { report ->
          IncidentReportCard(
            report = report,
            onUpvote = { viewModel.upvoteReport(report.id) }
          )
        }
      }
    }

    // FAB to create new report
    FloatingActionButton(
      onClick = { showAddDialog = true },
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = Color.White,
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(end = 16.dp, bottom = 84.dp)
        .testTag("add_alert_fab")
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Reportar Risco")
        Spacer(modifier = Modifier.width(6.dp))
        Text("Reportar Risco", fontWeight = FontWeight.Bold, fontSize = 13.sp)
      }
    }
  }

  if (showAddDialog) {
    NewIncidentDialog(
      onDismiss = { showAddDialog = false },
      onSubmit = { cat, title, desc, addr, sev ->
        viewModel.createIncidentReport(cat, title, desc, addr, sev)
        showAddDialog = false
      }
    )
  }
}

@Composable
fun IncidentReportCard(
  report: IncidentReportEntity,
  onUpvote: () -> Unit
) {
  val (catIcon, catColor, catName) = when (report.category) {
    "ILUMINACAO" -> Triple(Icons.Default.Lightbulb, SafeAmber, "Iluminação")
    "VIOLENCIA" -> Triple(Icons.Default.Security, SafeRed, "Segurança Pública")
    "SINISTRO_TRANSITO" -> Triple(Icons.Default.CarCrash, SafeRed, "Sinistro / Trânsito")
    "TRANSPORTE" -> Triple(Icons.Default.DirectionsBus, SafeCyan, "Transporte")
    "ACESSIBILIDADE" -> Triple(Icons.Default.WheelchairPickup, SafeGreen, "Acessibilidade")
    else -> Triple(Icons.Default.ReportProblem, SafeAmber, "Geral")
  }

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(catColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = catIcon,
              contentDescription = null,
              tint = catColor,
              modifier = Modifier.size(18.dp)
            )
          }
          Text(
            text = catName,
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = catColor
            )
          )
        }

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = if (report.severity == "ALTA") SafeRed.copy(alpha = 0.15f) else SafeAmber.copy(alpha = 0.15f)
        ) {
          Text(
            text = "Severidade: ${report.severity}",
            color = if (report.severity == "ALTA") SafeRed else SafeAmber,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = report.title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )

      Text(
        text = report.description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 4.dp)
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = report.address,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Confirmado por ${report.upvotes} trabalhadores",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedButton(
          onClick = onUpvote,
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text("Confirmar Risco (+1)", fontSize = 11.sp)
        }
      }
    }
  }
}

@Composable
fun NewIncidentDialog(
  onDismiss: () -> Unit,
  onSubmit: (category: String, title: String, description: String, address: String, severity: String) -> Unit
) {
  var selectedCategory by remember { mutableStateOf("ILUMINACAO") }
  var title by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var address by remember { mutableStateOf("") }
  var severity by remember { mutableStateOf("ALTA") }

  val availableCategories = listOf(
    "ILUMINACAO" to "Iluminação Deficiente",
    "VIOLENCIA" to "Risco de Roubo / Violência",
    "SINISTRO_TRANSITO" to "Sinistro / Perigo no Trânsito",
    "TRANSPORTE" to "Falta de Ônibus / Superlotação",
    "ACESSIBILIDADE" to "Calçada Obstruída / Sem Rampa"
  )

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text("Reportar Risco no Trajeto", fontWeight = FontWeight.Bold)
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text("Tipo de Risco:", style = MaterialTheme.typography.labelMedium)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          availableCategories.forEach { (catKey, catLabel) ->
            FilterChip(
              selected = selectedCategory == catKey,
              onClick = { selectedCategory = catKey },
              label = { Text(catLabel, fontSize = 11.sp) }
            )
          }
        }

        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("Título (Ex: Poste apagado no ponto)") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Detalhes do Risco") },
          modifier = Modifier.fillMaxWidth(),
          maxLines = 3
        )

        OutlinedTextField(
          value = address,
          onValueChange = { address = it },
          label = { Text("Local / Rua / Ponto de Referência") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Text("Severidade:", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          listOf("ALTA", "MEDIA", "BAIXA").forEach { sev ->
            FilterChip(
              selected = severity == sev,
              onClick = { severity = sev },
              label = { Text(sev) }
            )
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (title.isNotBlank() && address.isNotBlank()) {
            onSubmit(selectedCategory, title, description, address, severity)
          }
        }
      ) {
        Text("Publicar Alerta")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) {
        Text("Cancelar")
      }
    }
  )
}
