package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafeAmber
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeNavy
import com.example.ui.theme.SafeNavyLight
import com.example.ui.theme.SafePrimaryGradientEnd
import com.example.ui.theme.SafePrimaryGradientStart
import com.example.ui.theme.SafeRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RotaSeguraTopBar(
  isProtectedMode: Boolean,
  onSosClick: () -> Unit,
  onJourneyClick: (() -> Unit)? = null
) {
  TopAppBar(
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(
              Brush.linearGradient(
                colors = listOf(SafePrimaryGradientStart, SafePrimaryGradientEnd)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Logo RotaSegura",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
        }

        Column {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "Toyota Rota Segura",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.2.sp
              )
            )
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = SafeRed.copy(alpha = 0.15f)
            ) {
              Text(
                text = "Toyota RS",
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SafeRed,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
              )
            }
          }
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(if (isProtectedMode) SafeGreen else SafeAmber)
            )
            Text(
              text = if (isProtectedMode) "Modo Trajeto Protegido" else "Mobilidade Segura & Inclusiva",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    },
    actions = {
      if (onJourneyClick != null) {
        IconButton(
          onClick = onJourneyClick,
          modifier = Modifier.testTag("top_bar_journey_button")
        ) {
          Icon(
            imageVector = Icons.Default.Psychology,
            contentDescription = "Jornada do Usuário",
            tint = SafeCyan,
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Button(
        onClick = onSosClick,
        colors = ButtonDefaults.buttonColors(
          containerColor = SafeRed,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
          horizontal = 12.dp,
          vertical = 6.dp
        ),
        modifier = Modifier
          .padding(end = 8.dp)
          .testTag("sos_top_button")
      ) {
        Icon(
          imageVector = Icons.Default.Emergency,
          contentDescription = "Botão SOS",
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "SOS",
          fontWeight = FontWeight.Black,
          fontSize = 13.sp
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.surface,
      titleContentColor = MaterialTheme.colorScheme.onSurface
    )
  )
}

@Composable
fun RotaSeguraBottomNav(
  currentTab: Int,
  onTabSelected: (Int) -> Unit
) {
  NavigationBar(
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 8.dp,
    modifier = Modifier.testTag("bottom_nav_bar")
  ) {
    NavigationBarItem(
      selected = currentTab == 0,
      onClick = { onTabSelected(0) },
      icon = {
        Icon(
          imageVector = Icons.Default.Map,
          contentDescription = "Rotas Seguras"
        )
      },
      label = { Text("Rotas", fontSize = 12.sp) },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
      ),
      modifier = Modifier.testTag("nav_tab_routes")
    )

    NavigationBarItem(
      selected = currentTab == 1,
      onClick = { onTabSelected(1) },
      icon = {
        Icon(
          imageVector = Icons.Default.Assistant,
          contentDescription = "MobiIA Assistente"
        )
      },
      label = { Text("MobiIA", fontSize = 12.sp) },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
      ),
      modifier = Modifier.testTag("nav_tab_ia")
    )

    NavigationBarItem(
      selected = currentTab == 2,
      onClick = { onTabSelected(2) },
      icon = {
        Icon(
          imageVector = Icons.Default.NotificationsActive,
          contentDescription = "Alertas de Risco"
        )
      },
      label = { Text("Alertas", fontSize = 12.sp) },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
      ),
      modifier = Modifier.testTag("nav_tab_alerts")
    )

    NavigationBarItem(
      selected = currentTab == 3,
      onClick = { onTabSelected(3) },
      icon = {
        Icon(
          imageVector = Icons.Default.Analytics,
          contentDescription = "Painel B2G Empresas"
        )
      },
      label = { Text("Jornada/B2G", fontSize = 11.5.sp) },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
      ),
      modifier = Modifier.testTag("nav_tab_b2g")
    )
  }
}

@Composable
fun SafetyScoreBadge(score: Int, modifier: Modifier = Modifier) {
  val (color, label) = when {
    score >= 90 -> SafeGreen to "Excelente"
    score >= 75 -> SafeCyan to "Seguro"
    score >= 60 -> SafeAmber to "Atenção"
    else -> SafeRed to "Crítico"
  }

  Surface(
    shape = RoundedCornerShape(12.dp),
    color = color.copy(alpha = 0.15f),
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
        imageVector = if (score >= 75) Icons.Default.CheckCircle else Icons.Default.Warning,
        contentDescription = null,
        tint = color,
        modifier = Modifier.size(14.dp)
      )
      Text(
        text = "$score/100",
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = color
      )
      Text(
        text = "($label)",
        fontSize = 11.sp,
        color = color
      )
    }
  }
}

@Composable
fun SafetyPillarBar(
  label: String,
  score: Int,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier
) {
  val progress = (score / 100f).coerceIn(0f, 1f)
  val barColor = when {
    score >= 85 -> SafeGreen
    score >= 65 -> SafeAmber
    else -> SafeRed
  }

  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(14.dp),
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = label,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
      Text(
        text = "$score%",
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
        color = barColor
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = barColor,
      trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
  }
}

@Composable
fun SosEmergencyDialog(
  onDismiss: () -> Unit,
  onConfirmSos: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    icon = {
      Box(
        modifier = Modifier
          .size(54.dp)
          .clip(CircleShape)
          .background(SafeRed.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Emergency,
          contentDescription = null,
          tint = SafeRed,
          modifier = Modifier.size(32.dp)
        )
      }
    },
    title = {
      Text(
        text = "Confirmar Alerta SOS?",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge
      )
    },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "Ao confirmar, sua localização em tempo real, trajeto e status serão enviados imediatamente para:",
          style = MaterialTheme.typography.bodyMedium
        )
        Text(
          text = "• Central de Segurança / SESMT da Empresa",
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary,
          fontSize = 13.sp
        )
        Text(
          text = "• Contatos de Emergência Cadastrados",
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.primary,
          fontSize = 13.sp
        )
        Text(
          text = "• Linha direta 190 (Polícia Militar / Resgate)",
          fontWeight = FontWeight.SemiBold,
          color = SafeRed,
          fontSize = 13.sp
        )
      }
    },
    confirmButton = {
      Button(
        onClick = onConfirmSos,
        colors = ButtonDefaults.buttonColors(containerColor = SafeRed),
        modifier = Modifier.testTag("confirm_sos_button")
      ) {
        Text("DISPARAR ALERTA SOS", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) {
        Text("Cancelar")
      }
    }
  )
}
