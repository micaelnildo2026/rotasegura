package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.B2GZoneInsight
import com.example.ui.MobilityUiState
import com.example.ui.MobilityViewModel
import com.example.ui.components.UserJourneyMatrixView
import com.example.ui.theme.SafeAmber
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeNavy
import com.example.ui.theme.SafeNavyLight
import com.example.ui.theme.SafeRed

@Composable
fun B2GGovernanceScreen(
  viewModel: MobilityViewModel,
  uiState: MobilityUiState
) {
  var selectedSubTab by remember { mutableIntStateOf(0) } // 0 = Jornada do Usuário, 1 = Painel B2G

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 72.dp)
      .testTag("b2g_governance_screen"),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {

    // Title and Scope
    item {
      Column {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = SafeCyan.copy(alpha = 0.15f)
        ) {
          Text(
            text = "DESIGN THINKING & INTELIGÊNCIA B2G",
            color = SafeCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Jornada do Usuário & Painel B2G",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "Mapeamento da dor do trabalhador, dados de sinistralidade (MTE) e governança urbana",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Sub-tab switcher: Jornada do Usuário vs Painel B2G
    item {
      TabRow(
        selectedTabIndex = selectedSubTab,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary
      ) {
        Tab(
          selected = selectedSubTab == 0,
          onClick = { selectedSubTab = 0 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Jornada do Usuário", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        )
        Tab(
          selected = selectedSubTab == 1,
          onClick = { selectedSubTab = 1 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Assessment,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Painel B2G & KPIs", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        )
      }
    }

    if (selectedSubTab == 0) {
      // TAB 0: Complete User Journey Component
      item {
        UserJourneyMatrixView()
      }

      // Action card to jump to Routes
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SafeNavy),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = SafeGreen)
              Text(
                text = "Como o RotaSegura Responde a Essa Jornada?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Cada dor identificada no mapeamento (trechos escuros, apps focados apenas em velocidade, travessias sem semáforo) é solucionada diretamente no algoritmo de rotas com Safety Score e na visualização 2D/3D com Google Maps.",
              style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.9f))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
              onClick = { viewModel.setActiveTab(0) },
              colors = ButtonDefaults.buttonColors(containerColor = SafeGreen),
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("Experimentar a Rota no Mapa", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      // TAB 1: Macro Evidence KPIs Cards (directly based on the problem canvas attachment)
      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            MacroKpiCard(
              label = "Sinistros no Percurso (MTE)",
              value = "24,6%",
              subtext = "De todos os acidentes de trabalho no Brasil",
              accentColor = SafeRed,
              modifier = Modifier.weight(1f)
            )
            MacroKpiCard(
              label = "Vítimas de Trânsito 2026",
              value = "12.156",
              subtext = "Média de 67 mortes/dia (CNN / Brasil)",
              accentColor = SafeAmber,
              modifier = Modifier.weight(1f)
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            MacroKpiCard(
              label = "Redução em Litígios",
              value = "-38%",
              subtext = "Economia em custos de sinistros e ações",
              accentColor = SafeGreen,
              modifier = Modifier.weight(1f)
            )
            MacroKpiCard(
              label = "Trabalhadores Protegidos",
              value = "14.150",
              subtext = "Em corredores e polos industriais",
              accentColor = SafeCyan,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }

      // Hackathon Mission Alignment Card (5 Whys Root Cause to Solution)
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = SafeNavy),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = SafeCyan
              )
              Text(
                text = "Solução da Causa Raiz (AEVO / Jump Start)",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "\"Falta uma inteligência capaz de interpretar continuamente o contexto e adaptar as decisões de mobilidade...\"",
              style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White.copy(alpha = 0.85f),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
              )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "O RotaSegura transforma dados fragmentados (GPS, ocorrências, bilhetagem e denúncias comunitárias) em intervenções urbanas acionáveis e rotas protegidas em tempo real para a população vulnerável e periférica.",
              style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.9f))
            )
          }
        }
      }

      // Critical Zones Section
      item {
        Text(
          text = "Corredores Críticos Mapeados para Intervenção B2G",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      // List of B2G Priority Zones
      items(uiState.b2gInsights) { insight ->
        B2GZoneCard(insight = insight)
      }

      // Export & Action Buttons
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Text(
              text = "Ações para Gestores Públicos e CIPA/SESMT",
              fontWeight = FontWeight.Bold,
              style = MaterialTheme.typography.titleSmall
            )

            Button(
              onClick = { /* Export action */ },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(imageVector = Icons.Default.Download, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Exportar Relatório SESMT / CIPA (PDF/JSON)")
            }

            OutlinedButton(
              onClick = { /* B2G License */ },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(imageVector = Icons.Default.LocationCity, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Licenciar Dados Anonimizados para Prefeitura (B2G)")
            }
          }
        }
      }
    }
  }
}

@Composable
fun MacroKpiCard(
  label: String,
  value: String,
  subtext: String,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall.copy(
          fontWeight = FontWeight.Black,
          color = accentColor
        )
      )
      Text(
        text = subtext,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
fun B2GZoneCard(insight: B2GZoneInsight) {
  val riskColor = when (insight.riskLevel) {
    "CRITICO" -> SafeRed
    "ALTO" -> SafeAmber
    else -> SafeCyan
  }

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = insight.zoneName,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = insight.neighborhood,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Surface(
          shape = RoundedCornerShape(6.dp),
          color = riskColor.copy(alpha = 0.15f)
        ) {
          Text(
            text = insight.status,
            color = riskColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Déficit Detectado: ${insight.primaryDeficit}",
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.SemiBold,
          color = SafeRed
        )
      )

      Text(
        text = "Recomendação de IA: ${insight.recommendedAction}",
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.Medium,
          color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(top = 2.dp)
      )

      HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "👥 ${insight.affectedWorkersDaily} operários/dia",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = insight.estimatedLitigationReduction,
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = SafeGreen
          )
        )
      }
    }
  }
}
