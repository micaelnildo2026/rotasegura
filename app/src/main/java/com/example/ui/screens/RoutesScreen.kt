package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CommuteMode
import com.example.model.CommuteProfile
import com.example.model.RouteOption
import com.example.model.RouteSegment
import com.example.ui.MobilityUiState
import com.example.ui.MobilityViewModel
import com.example.ui.components.InteractiveRouteMapView
import com.example.ui.components.SafetyPillarBar
import com.example.ui.components.SafetyScoreBadge
import com.example.ui.components.launchGoogleMapsDirections
import com.example.ui.theme.SafeAmber
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeNavy
import com.example.ui.theme.SafeNavyLight
import com.example.ui.theme.SafePrimaryGradientStart
import com.example.ui.theme.SafeRed

@Composable
fun RoutesScreen(
  viewModel: MobilityViewModel,
  uiState: MobilityUiState
) {
  var isEditingAddresses by remember { mutableStateOf(false) }
  var originText by remember { mutableStateOf(uiState.context.origin) }
  var destinationText by remember { mutableStateOf(uiState.context.destination) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .testTag("routes_screen"),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 80.dp)
  ) {

    // Hero Section with Image & Safe Mobility Mission Statement
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.img_mobility_hero),
          contentDescription = "Mobilidade Segura e Iluminada",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Transparent,
                  SafeNavy.copy(alpha = 0.85f),
                  MaterialTheme.colorScheme.background
                )
              )
            )
        )
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SafeRed.copy(alpha = 0.25f)
          ) {
            Text(
              text = "TOYOTA ROTA SEGURA (TOYOTA RS)",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Mobilidade Segura & Inclusiva",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              letterSpacing = (-0.5).sp
            )
          )
          Text(
            text = "Rotas adaptadas por IA para PCD, Idosos, Crianças, Jovens e Trabalhadores",
            color = Color.White.copy(alpha = 0.9f),
            style = MaterialTheme.typography.bodySmall
          )
        }
      }
    }

    // Active Navigation Banner if in Progress
    if (uiState.isNavigating) {
      item {
        ActiveNavigationCard(
          uiState = uiState,
          onAdvance = { viewModel.advanceNavigationStep() },
          onStop = { viewModel.stopNavigation() },
          onCheckIn = { viewModel.performSafetyCheckIn() }
        )
      }
    }

    // Origin & Destination Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .testTag("route_input_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Percurso Monitorado",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
            TextButton(
              onClick = { isEditingAddresses = !isEditingAddresses }
            ) {
              Text(if (isEditingAddresses) "Concluir" else "Alterar")
            }
          }

          if (isEditingAddresses) {
            OutlinedTextField(
              value = originText,
              onValueChange = { originText = it },
              label = { Text("Origem (Ex: Residência)") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
              value = destinationText,
              onValueChange = { destinationText = it },
              label = { Text("Destino (Ex: Empresa / Trabalho)") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
              onClick = {
                viewModel.updateOriginDestination(originText, destinationText)
                isEditingAddresses = false
              },
              modifier = Modifier.align(Alignment.End)
            ) {
              Text("Recalcular Rotas Seguras")
            }
          } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.NearMe,
                contentDescription = null,
                tint = SafeCyan,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = uiState.context.origin,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = SafeRed,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = uiState.context.destination,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
              )
            }
          }

          HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

          // Context badges (Night time, Rain, Profile)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = SafeNavyLight.copy(alpha = 0.1f)
              ) {
                Text(
                  text = "🌙 ${uiState.context.currentTimeDescription}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = SafeCyan.copy(alpha = 0.12f)
              ) {
                Text(
                  text = "🌧️ ${uiState.context.weatherDescription}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
              }
            }
          }
        }
      }
    }

    // Prominent PCD & Accessibility Highlight Banner
    item {
      val isPcdSelected = uiState.context.selectedProfile == CommuteProfile.ACCESSIBILITY
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
          .testTag("pcd_accessibility_banner"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isPcdSelected) SafeCyan.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(
          width = if (isPcdSelected) 2.dp else 1.dp,
          color = if (isPcdSelected) SafeCyan else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Surface(
                shape = CircleShape,
                color = SafeCyan.copy(alpha = 0.2f)
              ) {
                Text(
                  text = "♿",
                  fontSize = 18.sp,
                  modifier = Modifier.padding(6.dp)
                )
              }
              Column {
                Text(
                  text = "Modo Acessibilidade PCD & Inclusão",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "Rotas 100% sem degraus (NBR 9050) & Adaptadas",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Button(
              onClick = {
                viewModel.updateCommuteProfile(
                  if (isPcdSelected) CommuteProfile.WORKER else CommuteProfile.ACCESSIBILITY
                )
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isPcdSelected) SafeCyan else MaterialTheme.colorScheme.primaryContainer,
                contentColor = if (isPcdSelected) Color.Black else MaterialTheme.colorScheme.onPrimaryContainer
              ),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
              shape = RoundedCornerShape(20.dp)
            ) {
              Text(
                text = if (isPcdSelected) "♿ PCD Ativo" else "Ativar PCD",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          if (isPcdSelected) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Surface(shape = RoundedCornerShape(6.dp), color = SafeCyan.copy(alpha = 0.2f)) {
                Text("♿ Zero Degraus", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SafeNavy, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
              Surface(shape = RoundedCornerShape(6.dp), color = SafeGreen.copy(alpha = 0.2f)) {
                Text("📐 Rampas <= 2.5%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SafeNavy, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
              Surface(shape = RoundedCornerShape(6.dp), color = SafeAmber.copy(alpha = 0.2f)) {
                Text("🚌 Ônibus Elevador", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SafeNavy, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
              }
            }
          }
        }
      }
    }

    // Commute Profile Selector Pills
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Text(
          text = "Perfil de Mobilidade Inclusiva",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          CommuteProfile.values().forEach { profile ->
            val isSelected = uiState.context.selectedProfile == profile
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.updateCommuteProfile(profile) },
              label = {
                Text(
                  text = "${profile.iconEmoji} ${profile.shortLabel}",
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              },
              leadingIcon = if (isSelected) {
                {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                  )
                }
              } else null,
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
              )
            )
          }
        }
      }
    }

    // Route Options Header
    item {
      PaddingTitle(
        title = "Opções de Trajeto Avaliadas por IA",
        subtitle = "Comparativo de segurança, iluminação e risco de sinistro"
      )
    }

    // Interactive Google Maps 2D/3D Visualizer for Selected Route
    item {
      uiState.selectedRoute?.let { selectedRoute ->
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
          InteractiveRouteMapView(
            route = selectedRoute,
            originAddress = uiState.context.origin,
            destinationAddress = uiState.context.destination
          )
        }
      }
    }

    // Route Option Cards
    items(uiState.routes) { route ->
      val isSelected = uiState.selectedRoute?.id == route.id
      RouteOptionCard(
        route = route,
        isSelected = isSelected,
        originAddress = uiState.context.origin,
        destinationAddress = uiState.context.destination,
        onSelect = { viewModel.selectRoute(route) },
        onStartNavigation = {
          viewModel.selectRoute(route)
          viewModel.startNavigation()
        }
      )
    }

    // Detailed Segments for the Selected Route
    item {
      uiState.selectedRoute?.let { route ->
        RouteDetailSegments(route = route)
      }
    }
  }
}

@Composable
fun RouteOptionCard(
  route: RouteOption,
  isSelected: Boolean,
  originAddress: String,
  destinationAddress: String,
  onSelect: () -> Unit,
  onStartNavigation: () -> Unit
) {
  val context = LocalContext.current
  val borderColor = when {
    isSelected && route.isRecommended -> SafeGreen
    isSelected -> MaterialTheme.colorScheme.primary
    else -> Color.Transparent
  }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 6.dp)
      .border(
        width = if (isSelected) 2.dp else 0.dp,
        color = borderColor,
        shape = RoundedCornerShape(16.dp)
      )
      .clickable { onSelect() }
      .testTag("route_card_${route.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) {
        MaterialTheme.colorScheme.surface
      } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
      }
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          route.routeBadge?.let { badge ->
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = if (route.isRecommended) SafeGreen.copy(alpha = 0.15f) else SafeRed.copy(alpha = 0.15f),
              modifier = Modifier.padding(bottom = 6.dp)
            ) {
              Text(
                text = badge,
                color = if (route.isRecommended) SafeGreen else SafeRed,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
          Text(
            text = route.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = route.subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        SafetyScoreBadge(score = route.safetyScore)
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Stats row: Duration, Distance, Walk
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        StatItem(label = "Tempo", value = "${route.durationMinutes} min")
        StatItem(label = "Distância", value = "${route.distanceKm} km")
        StatItem(label = "Caminhada", value = "${route.walkMinutes} min a pé")
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Telemetry row for Accidents, Dark Zones, and Slope
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // Accident tag
        val hasAccidents = route.accidentPoints.isNotEmpty()
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (hasAccidents) SafeRed.copy(alpha = 0.12f) else SafeGreen.copy(alpha = 0.12f),
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.CarCrash,
              contentDescription = null,
              tint = if (hasAccidents) SafeRed else SafeGreen,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = if (hasAccidents) "${route.accidentPoints.size} Sinistro" else "0 Sinistros",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (hasAccidents) SafeRed else SafeGreen
            )
          }
        }

        // Dark zone tag
        val hasDarkZones = route.darkZonePoints.isNotEmpty()
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (hasDarkZones) Color(0xFF6366F1).copy(alpha = 0.12f) else SafeGreen.copy(alpha = 0.12f),
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.NightlightRound,
              contentDescription = null,
              tint = if (hasDarkZones) Color(0xFF6366F1) else SafeGreen,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = if (hasDarkZones) "${route.darkZonePoints.size} Escura" else "100% LED",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (hasDarkZones) Color(0xFF4F46E5) else SafeGreen
            )
          }
        }

        // Incline tag
        val isSteep = route.maxInclinePercent > 8.33f
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = if (isSteep) SafeAmber.copy(alpha = 0.15f) else SafeGreen.copy(alpha = 0.12f),
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Landscape,
              contentDescription = null,
              tint = if (isSteep) SafeAmber else SafeGreen,
              modifier = Modifier.size(12.dp)
            )
            Text(
              text = "${route.maxInclinePercent}% " + if (isSteep) "Íngreme" else "Suave",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = if (isSteep) Color(0xFFB45309) else SafeGreen
            )
          }
        }
      }

      if (isSelected) {
        Spacer(modifier = Modifier.height(10.dp))

        // Safety Pillars Breakdown
        SafetyPillarBar(
          label = "Iluminação das Vias",
          score = route.illuminationScore,
          icon = Icons.Default.Lightbulb,
          modifier = Modifier.padding(vertical = 2.dp)
        )
        SafetyPillarBar(
          label = "Segurança Pública / Sem Ocorrências",
          score = route.publicSafetyScore,
          icon = Icons.Default.Security,
          modifier = Modifier.padding(vertical = 2.dp)
        )
        SafetyPillarBar(
          label = "Prevenção de Sinistros de Trânsito",
          score = route.trafficRiskScore,
          icon = Icons.Default.Traffic,
          modifier = Modifier.padding(vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Key advantages or warnings
        if (route.keyAdvantages.isNotEmpty()) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            route.keyAdvantages.forEach { adv ->
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = SafeGreen,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = adv,
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp)
                )
              }
            }
          }
        }

        if (route.warningNotes.isNotEmpty()) {
          Spacer(modifier = Modifier.height(6.dp))
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            route.warningNotes.forEach { warn ->
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Warning,
                  contentDescription = null,
                  tint = if (route.isRecommended) SafeAmber else SafeRed,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = warn,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.5.sp,
                    color = if (route.isRecommended) MaterialTheme.colorScheme.onSurface else SafeRed
                  )
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = onStartNavigation,
            modifier = Modifier
              .weight(1.3f)
              .testTag("start_navigation_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (route.isRecommended) SafePrimaryGradientStart else MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Navegar IA",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }

          OutlinedButton(
            onClick = {
              launchGoogleMapsDirections(
                context = context,
                origin = originAddress,
                destination = destinationAddress
              )
            },
            modifier = Modifier
              .weight(1f)
              .testTag("open_gmaps_from_card_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
          ) {
            Icon(
              imageVector = Icons.Default.Map,
              contentDescription = null,
              tint = SafeCyan,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Google Maps",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }
        }
      }
    }
  }
}

@Composable
fun RouteDetailSegments(route: RouteOption) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = "Etapas e Contexto de Segurança",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(12.dp))

      route.segments.forEachIndexed { index, segment ->
        Row(modifier = Modifier.fillMaxWidth()) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
          ) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(
                  if (segment.isHazardZone) SafeRed.copy(alpha = 0.15f)
                  else SafeGreen.copy(alpha = 0.15f)
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = when (segment.mode) {
                  CommuteMode.TRANSIT_WALK -> Icons.Default.DirectionsBus
                  CommuteMode.WALK_ONLY -> Icons.Default.DirectionsWalk
                  else -> Icons.Default.Navigation
                },
                contentDescription = null,
                tint = if (segment.isHazardZone) SafeRed else SafeGreen,
                modifier = Modifier.size(16.dp)
              )
            }
            if (index < route.segments.size - 1) {
              Box(
                modifier = Modifier
                  .width(2.dp)
                  .height(44.dp)
                  .background(MaterialTheme.colorScheme.outlineVariant)
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = segment.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
              )
              Text(
                text = "${segment.durationMin} min",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Text(
              text = segment.instruction,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "🛡️ ${segment.safetyHighlights}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                color = if (segment.isHazardZone) SafeRed else SafeGreen
              ),
              modifier = Modifier.padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
      }
    }
  }
}

@Composable
fun ActiveNavigationCard(
  uiState: MobilityUiState,
  onAdvance: () -> Unit,
  onStop: () -> Unit,
  onCheckIn: () -> Unit
) {
  val route = uiState.selectedRoute ?: return
  val currentSegment = route.segments.getOrNull(uiState.navigationStepIndex)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .testTag("active_navigation_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SafeNavy),
    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
              .size(10.dp)
              .clip(CircleShape)
              .background(SafeGreen)
          )
          Text(
            text = "TRAJETO EM ANDAMENTO",
            color = SafeGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp
          )
        }

        IconButton(onClick = onStop) {
          Icon(
            imageVector = Icons.Default.Stop,
            contentDescription = "Encerrar",
            tint = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = currentSegment?.instruction ?: "Siga pelo trajeto seguro monitorado",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = Color.White
        )
      )

      Text(
        text = "Segurança do Ponto: ${currentSegment?.safetyHighlights ?: "Vias monitoradas"}",
        style = MaterialTheme.typography.bodySmall.copy(color = SafeCyan),
        modifier = Modifier.padding(top = 4.dp)
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onAdvance,
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(containerColor = SafeGreen)
        ) {
          Text("Próxima Etapa")
        }

        OutlinedButton(
          onClick = onCheckIn,
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
          Text("Check-in Seguro")
        }
      }
    }
  }
}

@Composable
fun StatItem(label: String, value: String) {
  Column {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
    )
  }
}

@Composable
fun PaddingTitle(title: String, subtitle: String) {
  Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
    Text(
      text = title,
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
    )
    Text(
      text = subtitle,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
