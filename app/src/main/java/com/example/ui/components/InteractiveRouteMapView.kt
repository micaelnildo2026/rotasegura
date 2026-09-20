package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RotateLeft
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material.icons.filled.Streetview
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WheelchairPickup
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.Building3D
import com.example.model.MapPoint
import com.example.model.PointType
import com.example.model.RouteOption
import com.example.ui.theme.SafeAmber
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeNavy
import com.example.ui.theme.SafeRed
import kotlin.math.cos
import kotlin.math.sin

enum class MapViewerMode {
  TACTICAL_3D_2D,
  GOOGLE_MAPS_WEB
}

enum class SpatialPerspective(val label: String, val pitch: Float, val is3D: Boolean) {
  TOP_DOWN_2D("2D Topo", 0f, false),
  ISOMETRIC_3D("3D Isométrico", 48f, true),
  IMMERSIVE_3D("3D Imersivo", 68f, true)
}

/**
 * Interactive Route Map Viewer with Google Maps Integration,
 * 2D (Top-down) and 3D (Isometric perspective with 3D buildings & volumetric LED illumination).
 */
@Composable
fun InteractiveRouteMapView(
  route: RouteOption,
  originAddress: String,
  destinationAddress: String,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var is3D by remember { mutableStateOf(true) }
  var viewerMode by remember { mutableStateOf(MapViewerMode.TACTICAL_3D_2D) }
  var zoom by remember { mutableFloatStateOf(1.05f) }
  var yawDeg by remember { mutableFloatStateOf(-25f) }
  var pitchDegCustom by remember { mutableFloatStateOf(48f) }
  var isFullScreen3D by remember { mutableStateOf(false) }
  var showLedLights by remember { mutableStateOf(true) }
  var showHazards by remember { mutableStateOf(true) }
  var showAccidents by remember { mutableStateOf(true) }
  var showDarkZones by remember { mutableStateOf(true) }
  var showSteepRoutes by remember { mutableStateOf(true) }
  var showBuildings by remember { mutableStateOf(true) }
  var selectedPoint by remember { mutableStateOf<MapPoint?>(null) }

  // Animated pitch: 0 for 2D, pitchDegCustom for 3D
  val pitchDeg by animateFloatAsState(
    targetValue = if (is3D) pitchDegCustom else 0f,
    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
    label = "pitch_anim"
  )

  val animatedYaw by animateFloatAsState(
    targetValue = if (is3D) yawDeg else 0f,
    animationSpec = tween(durationMillis = 250),
    label = "yaw_anim"
  )

  val animatedZoom by animateFloatAsState(
    targetValue = zoom,
    animationSpec = tween(durationMillis = 250),
    label = "zoom_anim"
  )

  // Animated progress for worker simulation along route
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_trans")
  val workerProgress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 6000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "worker_progress"
  )

  val hazardPulse by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 1.4f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "hazard_pulse"
  )

  val buildings = remember { generateSampleUrbanBuildings() }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("interactive_route_map_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {

      // Map Header with Google Maps Branding & Direct Launch
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(SafeNavy)
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White.copy(alpha = 0.15f),
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Map,
                contentDescription = "Google Maps",
                tint = SafeCyan,
                modifier = Modifier.size(20.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Google Maps 3D",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.width(6.dp))
              Surface(
                shape = RoundedCornerShape(4.dp),
                color = if (route.isRecommended) SafeGreen else SafeAmber
              ) {
                Text(
                  text = if (is3D) "3D ESPACIAL" else "2D PLANO",
                  color = SafeNavy,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Black,
                  modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                )
              }
            }
            Text(
              text = "${route.distanceKm} km • ${route.durationMinutes} min • Safety ${route.safetyScore}/100",
              color = Color.White.copy(alpha = 0.75f),
              fontSize = 11.sp
            )
          }
        }

        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Fullscreen expand button
          IconButton(
            onClick = { isFullScreen3D = true },
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.15f))
              .testTag("expand_3d_fullscreen_button")
          ) {
            Icon(
              imageVector = Icons.Default.Fullscreen,
              contentDescription = "Expandir 3D",
              tint = SafeCyan,
              modifier = Modifier.size(18.dp)
            )
          }

          // Button to Open Native Google Maps
          Button(
            onClick = {
              launchGoogleMapsDirections(
                context = context,
                origin = originAddress,
                destination = destinationAddress
              )
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.White,
              contentColor = SafeNavy
            ),
            shape = RoundedCornerShape(20.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 5.dp),
            modifier = Modifier.testTag("open_in_google_maps_button")
          ) {
            Icon(
              imageVector = Icons.Default.OpenInNew,
              contentDescription = null,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Maps", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      // Mode Selector Tabs (Tactical 2D/3D vs Google Maps Live Web)
      TabRow(
        selectedTabIndex = viewerMode.ordinal,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary
      ) {
        Tab(
          selected = viewerMode == MapViewerMode.TACTICAL_3D_2D,
          onClick = { viewerMode = MapViewerMode.TACTICAL_3D_2D },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.ViewInAr,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Modelo Espacial 3D", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
          }
        )
        Tab(
          selected = viewerMode == MapViewerMode.GOOGLE_MAPS_WEB,
          onClick = { viewerMode = MapViewerMode.GOOGLE_MAPS_WEB },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Directions,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Google Maps Ao Vivo", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
          }
        )
      }

      // Main View Area
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(360.dp)
          .background(Color(0xFF070D18))
      ) {
        if (viewerMode == MapViewerMode.TACTICAL_3D_2D) {
          // Tactical 2D/3D Canvas with drag-to-orbit gesture
          Canvas(
            modifier = Modifier
              .fillMaxSize()
              .testTag("tactical_map_canvas")
              .pointerInput(Unit) {
                detectTapGestures(
                  onTap = { tapOffset ->
                    val tapped = findTappedPoint(
                      tapOffset = tapOffset,
                      canvasWidth = size.width.toFloat(),
                      canvasHeight = size.height.toFloat(),
                      route = route,
                      pitchDeg = pitchDeg,
                      yawDeg = animatedYaw,
                      zoom = animatedZoom
                    )
                    selectedPoint = tapped
                  },
                  onDoubleTap = {
                    yawDeg = -25f
                    pitchDegCustom = 48f
                    zoom = 1.05f
                    is3D = true
                  }
                )
              }
              .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                  change.consume()
                  yawDeg += dragAmount.x * 0.45f
                  if (is3D) {
                    pitchDegCustom = (pitchDegCustom - dragAmount.y * 0.35f).coerceIn(12f, 75f)
                  }
                }
              }
          ) {
            drawTacticalMap(
              route = route,
              buildings = if (showBuildings) buildings else emptyList(),
              pitchDeg = pitchDeg,
              yawDeg = animatedYaw,
              zoom = animatedZoom,
              showLed = showLedLights,
              showHazards = showHazards,
              showAccidents = showAccidents,
              showDarkZones = showDarkZones,
              showSteepRoutes = showSteepRoutes,
              workerProgress = workerProgress,
              hazardPulse = hazardPulse
            )
          }

          // Top Floating Overlay: 3 Perspective Angle Pills (2D, 3D Isométrica, 3D Imersiva)
          Row(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(10.dp)
              .clip(RoundedCornerShape(24.dp))
              .background(SafeNavy.copy(alpha = 0.90f))
              .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
              .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (!is3D) SafeCyan else Color.Transparent,
              modifier = Modifier
                .clickable { is3D = false }
                .testTag("toggle_2d_mode")
            ) {
              Text(
                text = "2D Topo",
                color = if (!is3D) SafeNavy else Color.White,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
              )
            }
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (is3D && pitchDegCustom < 58f) SafeGreen else Color.Transparent,
              modifier = Modifier
                .clickable {
                  is3D = true
                  pitchDegCustom = 48f
                }
                .testTag("toggle_3d_mode")
            ) {
              Text(
                text = "3D Isométrico",
                color = if (is3D && pitchDegCustom < 58f) SafeNavy else Color.White,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
              )
            }
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (is3D && pitchDegCustom >= 58f) SafeCyan else Color.Transparent,
              modifier = Modifier
                .clickable {
                  is3D = true
                  pitchDegCustom = 68f
                }
                .testTag("toggle_3d_immersive_mode")
            ) {
              Text(
                text = "3D Imersivo",
                color = if (is3D && pitchDegCustom >= 58f) SafeNavy else Color.White,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
              )
            }
          }

          // Top Right Floating Controls: 3D Compass Gizmo & Orbital Control Stack
          Column(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(10.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Interactive 3D Spatial Compass Gizmo
            SpatialCompassGizmo(
              yawDeg = animatedYaw,
              pitchDeg = pitchDeg,
              onResetNorth = {
                yawDeg = 0f
                pitchDegCustom = 48f
                is3D = true
              }
            )

            // Zoom & Orbital Camera Controls
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              MapGlassIconButton(
                icon = Icons.Default.ZoomIn,
                contentDesc = "Zoom Mais",
                onClick = { if (zoom < 2.2f) zoom += 0.2f }
              )
              MapGlassIconButton(
                icon = Icons.Default.ZoomOut,
                contentDesc = "Zoom Menos",
                onClick = { if (zoom > 0.6f) zoom -= 0.2f }
              )
            }

            if (is3D) {
              Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                MapGlassIconButton(
                  icon = Icons.Default.RotateLeft,
                  contentDesc = "Girar Esquerda 45°",
                  onClick = { yawDeg = (yawDeg - 45f) % 360f }
                )
                MapGlassIconButton(
                  icon = Icons.Default.RotateRight,
                  contentDesc = "Girar Direita 45°",
                  onClick = { yawDeg = (yawDeg + 45f) % 360f }
                )
              }
            }
          }

          // Gesture Instruction Tooltip Banner
          Box(
            modifier = Modifier
              .align(Alignment.TopCenter)
              .padding(top = 46.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(SafeNavy.copy(alpha = 0.70f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.TouchApp,
                contentDescription = null,
                tint = SafeCyan,
                modifier = Modifier.size(12.dp)
              )
              Text(
                text = "Arraste para orbitar 360° / inclinar • Toque duplo redefine",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 9.sp
              )
            }
          }

          // Bottom Route Perspective Indicator & Telemetry Ribbon
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(10.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(SafeNavy.copy(alpha = 0.88f))
              .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .background(if (route.isRecommended) SafeGreen else SafeAmber, CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Trajeto 3D", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
              if (route.accidentPoints.isNotEmpty() && showAccidents) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .background(SafeRed, CircleShape)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("🚨 ${route.accidentPoints.size} Acidente", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
              }
              if (route.darkZonePoints.isNotEmpty() && showDarkZones) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .background(Color(0xFF818CF8), CircleShape)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("🌑 ${route.darkZonePoints.size} Escuro", color = Color.White, fontSize = 10.sp)
                }
              }
              if (route.maxInclinePercent > 0 && showSteepRoutes) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .background(if (route.maxInclinePercent > 8.33f) SafeRed else SafeAmber, CircleShape)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("▲ ${route.maxInclinePercent}%", color = Color.White, fontSize = 10.sp)
                }
              }
              if (showLedLights) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .background(SafeCyan, CircleShape)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("${route.lightPoints.size} LEDs", color = Color.White, fontSize = 10.sp)
                }
              }
            }
          }

          // Selected Point Popup Card
          if (selectedPoint != null) {
            Box(
              modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
            ) {
              selectedPoint?.let { point ->
                PointDetailCard(
                  point = point,
                  onDismiss = { selectedPoint = null },
                  onNavigateGoogleMaps = {
                    launchGoogleMapsPoint(context, point.lat, point.lng, point.label)
                  }
                )
              }
            }
          }

        } else {
          // Google Maps Live WebView View
          GoogleMapsEmbedView(
            originAddress = originAddress,
            destinationAddress = destinationAddress,
            onReturnToCanvas = { viewerMode = MapViewerMode.TACTICAL_3D_2D }
          )
        }
      }

      // Bottom Layer Toggles Bar (Postes LED, Acidentes, Trechos Escuros, Inclinação, Prédios 3D)
      if (viewerMode == MapViewerMode.TACTICAL_3D_2D) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Camadas:",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          FilterChip(
            selected = showLedLights,
            onClick = { showLedLights = !showLedLights },
            label = { Text("Iluminação LED (${route.lightPoints.size})", fontSize = 11.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Brightness5,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SafeCyan.copy(alpha = 0.2f),
              selectedLabelColor = SafeNavy
            )
          )

          FilterChip(
            selected = showAccidents,
            onClick = { showAccidents = !showAccidents },
            label = { Text("Acidentes (${route.accidentPoints.size})", fontSize = 11.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.CarCrash,
                contentDescription = null,
                tint = SafeRed,
                modifier = Modifier.size(12.dp)
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SafeRed.copy(alpha = 0.2f),
              selectedLabelColor = SafeNavy
            )
          )

          FilterChip(
            selected = showDarkZones,
            onClick = { showDarkZones = !showDarkZones },
            label = { Text("Rotas Escuras (${route.darkZonePoints.size})", fontSize = 11.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.NightlightRound,
                contentDescription = null,
                tint = Color(0xFF6366F1),
                modifier = Modifier.size(12.dp)
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = Color(0xFF6366F1).copy(alpha = 0.2f),
              selectedLabelColor = SafeNavy
            )
          )

          FilterChip(
            selected = showSteepRoutes,
            onClick = { showSteepRoutes = !showSteepRoutes },
            label = { Text("Declividade (${route.maxInclinePercent}%)", fontSize = 11.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Landscape,
                contentDescription = null,
                tint = SafeAmber,
                modifier = Modifier.size(12.dp)
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SafeAmber.copy(alpha = 0.2f),
              selectedLabelColor = SafeNavy
            )
          )

          FilterChip(
            selected = showHazards,
            onClick = { showHazards = !showHazards },
            label = { Text("Zonas de Risco (${route.hazardPoints.size})", fontSize = 11.sp) },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = SafeRed.copy(alpha = 0.2f),
              selectedLabelColor = SafeNavy
            )
          )

          if (is3D) {
            FilterChip(
              selected = showBuildings,
              onClick = { showBuildings = !showBuildings },
              label = { Text("Prédios 3D", fontSize = 11.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = SafeGreen.copy(alpha = 0.2f),
                selectedLabelColor = SafeNavy
              )
            )
          }
        }
      }
    }
  }

  // Fullscreen 3D Spatial Simulator Dialog
  if (isFullScreen3D) {
    FullScreen3DSpatialDialog(
      route = route,
      buildings = if (showBuildings) buildings else emptyList(),
      initialYaw = yawDeg,
      initialPitch = pitchDegCustom,
      initialZoom = zoom,
      showLed = showLedLights,
      showHazards = showHazards,
      showAccidents = showAccidents,
      showDarkZones = showDarkZones,
      showSteepRoutes = showSteepRoutes,
      showBuildings = showBuildings,
      workerProgress = workerProgress,
      hazardPulse = hazardPulse,
      originAddress = originAddress,
      destinationAddress = destinationAddress,
      onDismiss = { isFullScreen3D = false },
      onToggleLed = { showLedLights = !showLedLights },
      onToggleHazards = { showHazards = !showHazards },
      onToggleAccidents = { showAccidents = !showAccidents },
      onToggleDarkZones = { showDarkZones = !showDarkZones },
      onToggleSteepRoutes = { showSteepRoutes = !showSteepRoutes },
      onToggleBuildings = { showBuildings = !showBuildings }
    )
  }
}

/**
 * Tactical Canvas Drawing Engine for 2D and 3D Isometric View
 */
private fun DrawScope.drawTacticalMap(
  route: RouteOption,
  buildings: List<Building3D>,
  pitchDeg: Float,
  yawDeg: Float,
  zoom: Float,
  showLed: Boolean,
  showHazards: Boolean,
  showAccidents: Boolean = true,
  showDarkZones: Boolean = true,
  showSteepRoutes: Boolean = true,
  workerProgress: Float,
  hazardPulse: Float
) {
  val cx = size.width / 2f
  val cy = size.height / 2f
  val pitchRad = Math.toRadians(pitchDeg.toDouble()).toFloat()
  val yawRad = Math.toRadians(yawDeg.toDouble()).toFloat()

  // Helper function to project (normX, normY, normZ) to screen coordinates
  fun project(xNorm: Float, yNorm: Float, zNorm: Float = 0f): Offset {
    val dx = (xNorm - 0.5f) * size.width * zoom
    val dy = (yNorm - 0.5f) * size.height * zoom

    // Rotate around center by yaw
    val rotX = dx * cos(yawRad) - dy * sin(yawRad)
    val rotY = dx * sin(yawRad) + dy * cos(yawRad)

    // Apply pitch elevation
    val finalX = cx + rotX
    val finalY = cy + rotY * cos(pitchRad) - (zNorm * 180f * sin(pitchRad))

    return Offset(finalX, finalY)
  }

  // 1. Draw Ground Plane Grid & Streets
  val groundP1 = project(0.05f, 0.05f, 0f)
  val groundP2 = project(0.95f, 0.05f, 0f)
  val groundP3 = project(0.95f, 0.95f, 0f)
  val groundP4 = project(0.05f, 0.95f, 0f)

  val groundPath = Path().apply {
    moveTo(groundP1.x, groundP1.y)
    lineTo(groundP2.x, groundP2.y)
    lineTo(groundP3.x, groundP3.y)
    lineTo(groundP4.x, groundP4.y)
    close()
  }

  // Ground base with high-tech deep tactical gradient
  drawPath(
    path = groundPath,
    brush = Brush.verticalGradient(
      colors = listOf(Color(0xFF0D1829), Color(0xFF070E1A))
    )
  )

  // Street Grid Lines (Secondary Streets)
  val gridLines = listOf(0.2f, 0.4f, 0.6f, 0.8f)
  for (gx in gridLines) {
    drawLine(
      color = Color(0xFF1E2D42).copy(alpha = 0.45f),
      start = project(gx, 0.05f, 0f),
      end = project(gx, 0.95f, 0f),
      strokeWidth = 1.5f
    )
  }
  for (gy in gridLines) {
    drawLine(
      color = Color(0xFF1E2D42).copy(alpha = 0.45f),
      start = project(0.05f, gy, 0f),
      end = project(0.95f, gy, 0f),
      strokeWidth = 1.5f
    )
  }

  // Primary Road Arteries with Sidewalks & Crosswalks
  drawMainAvenues(::project)

  // 2. Draw 3D Buildings (Depth-sorted via Painter's algorithm based on camera orientation)
  val sortedBuildings = if (pitchDeg > 5f) {
    buildings.sortedBy { b ->
      val centerX = b.xNorm + b.widthNorm / 2f - 0.5f
      val centerY = b.yNorm + b.heightNorm / 2f - 0.5f
      centerX * sin(yawRad) + centerY * cos(yawRad)
    }
  } else {
    buildings
  }

  for (b in sortedBuildings) {
    drawBuilding3D(
      building = b,
      project = ::project,
      pitchDeg = pitchDeg,
      yawRad = yawRad
    )
  }

  // 3. Draw LED Streetlight Volumetric Cones
  if (showLed) {
    for (light in route.lightPoints) {
      val groundPos = project(light.xNorm, light.yNorm, 0f)
      val poleTopPos = project(light.xNorm, light.yNorm, 0.12f)

      // Light pool on ground (radial diffusion)
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(SafeCyan.copy(alpha = 0.50f), Color.Transparent),
          center = groundPos,
          radius = 34f * zoom
        ),
        radius = 34f * zoom,
        center = groundPos
      )

      // 3D Lamp Post with fixture & volumetric beam
      if (pitchDeg > 8f) {
        drawLine(
          color = Color.White.copy(alpha = 0.7f),
          start = groundPos,
          end = poleTopPos,
          strokeWidth = 2.5f
        )
        // Glowing fixture head
        drawCircle(
          color = SafeCyan,
          radius = 3.5f * zoom,
          center = poleTopPos
        )
        drawCircle(
          color = Color.White,
          radius = 1.8f * zoom,
          center = poleTopPos
        )

        // Volumetric conical light beam
        val conePath = Path().apply {
          moveTo(poleTopPos.x, poleTopPos.y)
          lineTo(groundPos.x - 22f * zoom, groundPos.y)
          lineTo(groundPos.x + 22f * zoom, groundPos.y)
          close()
        }
        drawPath(
          path = conePath,
          brush = Brush.verticalGradient(
            colors = listOf(SafeCyan.copy(alpha = 0.38f), Color.Transparent),
            startY = poleTopPos.y,
            endY = groundPos.y
          )
        )
      }
    }
  }

  // 4. Draw Hazard / Sinistro Risk Zones (Pulsing Concentric Circles)
  if (showHazards) {
    for (hazard in route.hazardPoints) {
      val hPos = project(hazard.xNorm, hazard.yNorm, 0f)
      val r = 28f * zoom * hazardPulse

      // Outer Pulse ring
      drawCircle(
        color = SafeRed.copy(alpha = 0.20f),
        radius = r,
        center = hPos
      )
      drawCircle(
        color = SafeRed.copy(alpha = 0.6f),
        radius = 16f * zoom,
        center = hPos,
        style = Stroke(width = 2.5f)
      )
      drawCircle(
        color = SafeRed,
        radius = 6f * zoom,
        center = hPos
      )
      drawCircle(
        color = Color.White,
        radius = 2.5f * zoom,
        center = hPos
      )
    }
  }

  // 4b. Draw Active Accident Alerts (Shockwaves, Hazard Beacon & Tether)
  if (showAccidents) {
    drawAccidentMarkers(route, ::project, zoom, hazardPulse, pitchDeg)
  }

  // 4c. Draw Dark Route Zones (Shadow Pools, Unlit Night Alerts)
  if (showDarkZones) {
    drawDarkZones(route, ::project, zoom, pitchDeg)
  }

  // 4d. Draw Steep Slope Incline Warnings & Elevation Grades
  if (showSteepRoutes) {
    drawSteepSlopeMarkers(route, ::project, zoom, pitchDeg)
  }

  // 5. Draw Glowing Route Polyline with 3D Elevation
  if (route.pathPoints.isNotEmpty()) {
    val routeScreenPoints = route.pathPoints.map { project(it.xNorm, it.yNorm, 0.02f) }

    val routePath = Path().apply {
      moveTo(routeScreenPoints.first().x, routeScreenPoints.first().y)
      for (i in 1 until routeScreenPoints.size) {
        lineTo(routeScreenPoints[i].x, routeScreenPoints[i].y)
      }
    }

    val routeColor = if (route.isRecommended) SafeGreen else SafeAmber
    val glowColor = if (route.isRecommended) SafeCyan else SafeRed

    // Outer broad neon glow
    drawPath(
      path = routePath,
      color = glowColor.copy(alpha = 0.38f),
      style = Stroke(
        width = 18f * zoom,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )

    // Inner bright core
    drawPath(
      path = routePath,
      color = routeColor,
      style = Stroke(
        width = 6.5f * zoom,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )

    // 6. Draw Simulated Worker / Transit Avatar Moving along Route in 3D
    if (routeScreenPoints.size >= 2) {
      val totalSegments = routeScreenPoints.size - 1
      val progressPerSegment = 1f / totalSegments
      val segIndex = (workerProgress / progressPerSegment).toInt().coerceIn(0, totalSegments - 1)
      val segProgress = (workerProgress - segIndex * progressPerSegment) / progressPerSegment

      val pStart = routeScreenPoints[segIndex]
      val pEnd = routeScreenPoints[segIndex + 1]
      val curX = pStart.x + (pEnd.x - pStart.x) * segProgress
      val curY = pStart.y + (pEnd.y - pStart.y) * segProgress
      val workerPos = Offset(curX, curY)

      // Drop shadow on street
      drawCircle(
        color = Color.Black.copy(alpha = 0.65f),
        radius = 12f * zoom,
        center = workerPos.copy(y = workerPos.y + 5f)
      )

      // Radar Sonar Wavefront scanning ahead
      drawCircle(
        color = SafeCyan.copy(alpha = 0.40f),
        radius = 22f * zoom * hazardPulse,
        center = workerPos,
        style = Stroke(width = 1.8f)
      )

      // Elevated 3D Avatar Core
      drawCircle(
        color = SafeCyan,
        radius = 9f * zoom,
        center = workerPos
      )
      drawCircle(
        color = Color.White,
        radius = 5f * zoom,
        center = workerPos
      )
      drawCircle(
        color = SafeNavy,
        radius = 2.5f * zoom,
        center = workerPos
      )

      // Forward Heading Vector Pointer
      val dirX = pEnd.x - pStart.x
      val dirY = pEnd.y - pStart.y
      val dirLen = kotlin.math.hypot(dirX, dirY)
      if (dirLen > 0.01f) {
        val normDirX = dirX / dirLen
        val normDirY = dirY / dirLen
        val arrowTip = Offset(workerPos.x + normDirX * 16f * zoom, workerPos.y + normDirY * 16f * zoom)
        drawLine(
          color = SafeGreen,
          start = workerPos,
          end = arrowTip,
          strokeWidth = 3f,
          cap = StrokeCap.Round
        )
      }
    }

    // 7. Draw Waypoint Markers with 3D Elevated Laser Tethers
    for (point in route.pathPoints) {
      val groundPos = project(point.xNorm, point.yNorm, 0f)
      val pos = project(point.xNorm, point.yNorm, 0.08f) // Elevated in 3D

      // Vertical holographic laser tether in 3D
      if (pitchDeg > 10f) {
        drawLine(
          color = when (point.type) {
            PointType.ORIGIN -> SafeCyan.copy(alpha = 0.85f)
            PointType.DESTINATION -> SafeGreen.copy(alpha = 0.85f)
            PointType.HAZARD -> SafeRed.copy(alpha = 0.85f)
            else -> Color(0xFF38BDF8).copy(alpha = 0.85f)
          },
          start = groundPos,
          end = pos,
          strokeWidth = 2.2f
        )
        // Ground anchor ring
        drawCircle(
          color = Color.White.copy(alpha = 0.45f),
          radius = 5.5f * zoom,
          center = groundPos,
          style = Stroke(width = 1.4f)
        )
      }

      // Elevated floating beacon
      when (point.type) {
        PointType.ORIGIN -> {
          drawCircle(color = SafeCyan.copy(alpha = 0.35f), radius = 14f * zoom, center = pos)
          drawCircle(color = SafeCyan, radius = 9f * zoom, center = pos)
          drawCircle(color = Color.White, radius = 4f * zoom, center = pos)
        }
        PointType.DESTINATION -> {
          drawCircle(color = SafeGreen.copy(alpha = 0.35f), radius = 16f * zoom, center = pos)
          drawCircle(color = SafeGreen, radius = 10f * zoom, center = pos)
          drawCircle(color = SafeNavy, radius = 5f * zoom, center = pos)
        }
        PointType.SAFE_STOP -> {
          drawCircle(color = Color(0xFF38BDF8).copy(alpha = 0.35f), radius = 12f * zoom, center = pos)
          drawCircle(color = Color(0xFF38BDF8), radius = 8f * zoom, center = pos)
          drawCircle(color = Color.White, radius = 3.5f * zoom, center = pos)
        }
        PointType.HAZARD -> {
          drawCircle(color = SafeRed.copy(alpha = 0.35f), radius = 14f * zoom, center = pos)
          drawCircle(color = SafeRed, radius = 8f * zoom, center = pos)
          drawCircle(color = Color.White, radius = 3.5f * zoom, center = pos)
        }
        PointType.ACCIDENT -> {
          drawCircle(color = SafeRed.copy(alpha = 0.45f), radius = 16f * zoom, center = pos)
          drawCircle(color = SafeRed, radius = 9f * zoom, center = pos)
          drawCircle(color = Color.White, radius = 3.5f * zoom, center = pos)
        }
        PointType.DARK_ZONE -> {
          drawCircle(color = Color(0xFF6366F1).copy(alpha = 0.45f), radius = 14f * zoom, center = pos)
          drawCircle(color = Color(0xFF6366F1), radius = 8f * zoom, center = pos)
          drawCircle(color = Color.White, radius = 3f * zoom, center = pos)
        }
        PointType.STEEP_INCLINE -> {
          drawCircle(color = SafeAmber.copy(alpha = 0.45f), radius = 14f * zoom, center = pos)
          drawCircle(color = SafeAmber, radius = 8f * zoom, center = pos)
          drawCircle(color = Color.White, radius = 3f * zoom, center = pos)
        }
        else -> Unit
      }
    }
  }
}

/**
 * Draws main street avenues with sidewalks & pedestrian crosswalks
 */
private fun DrawScope.drawMainAvenues(project: (Float, Float, Float) -> Offset) {
  // Main Avenue (Av. das Palmeiras / Av. Central dos Trabalhadores)
  val road1Start = project(0.10f, 0.85f, 0f)
  val road1Mid = project(0.40f, 0.55f, 0f)
  val road1End = project(0.90f, 0.15f, 0f)

  val roadPath = Path().apply {
    moveTo(road1Start.x, road1Start.y)
    lineTo(road1Mid.x, road1Mid.y)
    lineTo(road1End.x, road1End.y)
  }

  // Sidewalk Curbs (Meio-fio em concreto)
  drawPath(
    path = roadPath,
    color = Color(0xFF475569),
    style = Stroke(width = 32f, cap = StrokeCap.Round, join = StrokeJoin.Round)
  )

  // Asphalt Surface
  drawPath(
    path = roadPath,
    color = Color(0xFF1E293B),
    style = Stroke(width = 25f, cap = StrokeCap.Round, join = StrokeJoin.Round)
  )

  // Dashed center lane
  drawPath(
    path = roadPath,
    color = Color(0xFFFCD34D).copy(alpha = 0.75f),
    style = Stroke(
      width = 2.5f,
      cap = StrokeCap.Round,
      join = StrokeJoin.Round
    )
  )

  // Secondary Safe Connection Street
  val secRoadPath = Path().apply {
    moveTo(project(0.20f, 0.90f, 0f).x, project(0.20f, 0.90f, 0f).y)
    lineTo(project(0.30f, 0.70f, 0f).x, project(0.30f, 0.70f, 0f).y)
    lineTo(project(0.65f, 0.38f, 0f).x, project(0.65f, 0.38f, 0f).y)
  }
  drawPath(
    path = secRoadPath,
    color = Color(0xFF334155),
    style = Stroke(width = 20f, cap = StrokeCap.Round, join = StrokeJoin.Round)
  )
  drawPath(
    path = secRoadPath,
    color = Color(0xFF182232),
    style = Stroke(width = 16f, cap = StrokeCap.Round, join = StrokeJoin.Round)
  )

  // Pedestrian Crosswalk (Faixa de Pedestres) at intersection (0.40f, 0.55f)
  val crossCenter = project(0.40f, 0.55f, 0f)
  for (i in -2..2) {
    val pA = Offset(crossCenter.x + i * 4.5f - 9f, crossCenter.y + i * 2f - 6f)
    val pB = Offset(crossCenter.x + i * 4.5f + 9f, crossCenter.y + i * 2f + 6f)
    drawLine(
      color = Color.White.copy(alpha = 0.85f),
      start = pA,
      end = pB,
      strokeWidth = 3f,
      cap = StrokeCap.Square
    )
  }
}

/**
 * Draws active accident beacons, warning shockwave ripples and emergency flare indicators.
 * Clean, high-contrast, uncluttered styling.
 */
private fun DrawScope.drawAccidentMarkers(
  route: RouteOption,
  project: (Float, Float, Float) -> Offset,
  zoom: Float,
  hazardPulse: Float,
  pitchDeg: Float
) {
  for (accident in route.accidentPoints) {
    val groundPos = project(accident.xNorm, accident.yNorm, 0f)
    val elevatedPos = project(accident.xNorm, accident.yNorm, 0.10f)

    // Expanding shockwave ripple (animated with hazardPulse)
    val rippleRadius = 26f * zoom * hazardPulse
    drawCircle(
      color = SafeRed.copy(alpha = 0.18f * (1f - hazardPulse * 0.5f)),
      radius = rippleRadius,
      center = groundPos
    )
    drawCircle(
      color = SafeRed.copy(alpha = 0.55f),
      radius = 16f * zoom,
      center = groundPos,
      style = Stroke(width = 2f)
    )

    // 3D holographic emergency tether
    if (pitchDeg > 8f) {
      drawLine(
        color = SafeRed.copy(alpha = 0.85f),
        start = groundPos,
        end = elevatedPos,
        strokeWidth = 2.4f
      )
      drawCircle(
        color = Color.White.copy(alpha = 0.5f),
        radius = 5f * zoom,
        center = groundPos,
        style = Stroke(width = 1.2f)
      )
    }

    val beaconCenter = if (pitchDeg > 8f) elevatedPos else groundPos

    // Outer emergency beacon glow
    drawCircle(
      color = SafeRed.copy(alpha = 0.35f),
      radius = 15f * zoom,
      center = beaconCenter
    )

    // Diamond emergency badge
    val dSize = 10f * zoom
    val diamondPath = Path().apply {
      moveTo(beaconCenter.x, beaconCenter.y - dSize)
      lineTo(beaconCenter.x + dSize, beaconCenter.y)
      lineTo(beaconCenter.x, beaconCenter.y + dSize)
      lineTo(beaconCenter.x - dSize, beaconCenter.y)
      close()
    }
    drawPath(path = diamondPath, color = SafeRed)
    drawPath(path = diamondPath, color = Color.White, style = Stroke(width = 1.6f))

    // Inner cross mark (distress glyph)
    val cCross = 3.5f * zoom
    drawLine(
      color = Color.White,
      start = Offset(beaconCenter.x - cCross, beaconCenter.y - cCross),
      end = Offset(beaconCenter.x + cCross, beaconCenter.y + cCross),
      strokeWidth = 1.8f
    )
    drawLine(
      color = Color.White,
      start = Offset(beaconCenter.x - cCross, beaconCenter.y + cCross),
      end = Offset(beaconCenter.x + cCross, beaconCenter.y - cCross),
      strokeWidth = 1.8f
    )
  }
}

/**
 * Draws dark unlit road sections with shaded ambient ground diffusion and night risk markers.
 */
private fun DrawScope.drawDarkZones(
  route: RouteOption,
  project: (Float, Float, Float) -> Offset,
  zoom: Float,
  pitchDeg: Float
) {
  for (darkPoint in route.darkZonePoints) {
    val groundPos = project(darkPoint.xNorm, darkPoint.yNorm, 0f)
    val elevatedPos = project(darkPoint.xNorm, darkPoint.yNorm, 0.08f)

    // Dark shadow pool representing unlit / blackout street sector
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(Color(0xD0030712), Color(0x700F172A), Color.Transparent),
        center = groundPos,
        radius = 36f * zoom
      ),
      radius = 36f * zoom,
      center = groundPos
    )

    // Dashed caution perimeter in indigo
    drawCircle(
      color = Color(0xFF818CF8).copy(alpha = 0.65f),
      radius = 20f * zoom,
      center = groundPos,
      style = Stroke(
        width = 1.6f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f))
      )
    )

    // 3D tether in dark blue/indigo
    if (pitchDeg > 8f) {
      drawLine(
        color = Color(0xFF818CF8).copy(alpha = 0.75f),
        start = groundPos,
        end = elevatedPos,
        strokeWidth = 2f
      )
    }

    val beaconCenter = if (pitchDeg > 8f) elevatedPos else groundPos

    // Dark Zone Badge
    drawCircle(
      color = Color(0xFF1E1B4B),
      radius = 9f * zoom,
      center = beaconCenter
    )
    drawCircle(
      color = Color(0xFF818CF8),
      radius = 9f * zoom,
      center = beaconCenter,
      style = Stroke(width = 1.6f)
    )

    // Crescent moon / night motif dot
    drawCircle(
      color = Color(0xFFC7D2FE),
      radius = 3.5f * zoom,
      center = Offset(beaconCenter.x - 1.2f * zoom, beaconCenter.y)
    )
    drawCircle(
      color = Color(0xFF1E1B4B),
      radius = 3f * zoom,
      center = Offset(beaconCenter.x + 0.8f * zoom, beaconCenter.y - 0.5f * zoom)
    )
  }
}

/**
 * Draws steep slope markers with topographic gradient chevrons and elevation delta guides.
 */
private fun DrawScope.drawSteepSlopeMarkers(
  route: RouteOption,
  project: (Float, Float, Float) -> Offset,
  zoom: Float,
  pitchDeg: Float
) {
  for (steep in route.steepPoints) {
    val groundPos = project(steep.xNorm, steep.yNorm, 0f)
    val elevatedPos = project(steep.xNorm, steep.yNorm, 0.09f)

    val slopeColor = if (steep.slopePercent > 8.33f) SafeRed else SafeAmber

    // Topographic contour arcs on ground
    for (step in 1..2) {
      val arcRadius = (14f + step * 8f) * zoom
      drawCircle(
        color = slopeColor.copy(alpha = 0.25f / step),
        radius = arcRadius,
        center = groundPos,
        style = Stroke(
          width = 1.4f,
          pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 3f))
        )
      )
    }

    // 3D elevation measurement vertical post
    if (pitchDeg > 8f) {
      drawLine(
        color = slopeColor.copy(alpha = 0.85f),
        start = groundPos,
        end = elevatedPos,
        strokeWidth = 2.2f
      )
      // Base anchor
      drawCircle(
        color = slopeColor.copy(alpha = 0.5f),
        radius = 5f * zoom,
        center = groundPos,
        style = Stroke(width = 1.2f)
      )
    }

    val beaconCenter = if (pitchDeg > 8f) elevatedPos else groundPos

    // Slope Badge
    drawCircle(
      color = slopeColor.copy(alpha = 0.30f),
      radius = 12f * zoom,
      center = beaconCenter
    )
    drawCircle(
      color = SafeNavy,
      radius = 8.5f * zoom,
      center = beaconCenter
    )
    drawCircle(
      color = slopeColor,
      radius = 8.5f * zoom,
      center = beaconCenter,
      style = Stroke(width = 1.8f)
    )

    // Stacked slope chevron ^ indicating incline
    val chevPath = Path().apply {
      moveTo(beaconCenter.x - 4f * zoom, beaconCenter.y + 2f * zoom)
      lineTo(beaconCenter.x, beaconCenter.y - 3f * zoom)
      lineTo(beaconCenter.x + 4f * zoom, beaconCenter.y + 2f * zoom)
    }
    drawPath(
      path = chevPath,
      color = slopeColor,
      style = Stroke(width = 1.8f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
  }
}

/**
 * Draws an extruded 3D building block with 360° dynamic facade culling,
 * matrix of illuminated windows & rooftop architecture.
 */
private fun DrawScope.drawBuilding3D(
  building: Building3D,
  project: (Float, Float, Float) -> Offset,
  pitchDeg: Float,
  yawRad: Float
) {
  val x1 = building.xNorm
  val y1 = building.yNorm
  val x2 = building.xNorm + building.widthNorm
  val y2 = building.yNorm + building.heightNorm
  val z = building.depthNorm

  // Ground base corners
  val b1 = project(x1, y1, 0f)
  val b2 = project(x2, y1, 0f)
  val b3 = project(x2, y2, 0f)
  val b4 = project(x1, y2, 0f)

  // Top elevated corners
  val t1 = project(x1, y1, z)
  val t2 = project(x2, y1, z)
  val t3 = project(x2, y2, z)
  val t4 = project(x1, y2, z)

  // Cast realistic shadow on ground in 3D
  if (pitchDeg > 8f) {
    val shadowAngleRad = Math.toRadians(45.0).toFloat()
    val shadowLen = z * 85f
    val sx = cos(shadowAngleRad) * shadowLen
    val sy = sin(shadowAngleRad) * shadowLen

    val shadowPath = Path().apply {
      moveTo(b1.x, b1.y)
      lineTo(b2.x + sx, b2.y + sy)
      lineTo(b3.x + sx, b3.y + sy)
      lineTo(b4.x, b4.y)
      close()
    }
    drawPath(path = shadowPath, color = Color.Black.copy(alpha = 0.38f))

    // Helper to draw a wall face only if it is facing the camera (cross product test)
    fun drawWallIfVisible(
      p1: Offset, p2: Offset, p3: Offset, p4: Offset,
      wallTopColor: Color, wallBottomColor: Color,
      hasWindows: Boolean = true
    ) {
      val cross = (p2.x - p1.x) * (p4.y - p1.y) - (p2.y - p1.y) * (p4.x - p1.x)
      if (cross > 0f) {
        val wallPath = Path().apply {
          moveTo(p1.x, p1.y)
          lineTo(p2.x, p2.y)
          lineTo(p3.x, p3.y)
          lineTo(p4.x, p4.y)
          close()
        }
        drawPath(
          path = wallPath,
          brush = Brush.verticalGradient(
            colors = listOf(wallTopColor, wallBottomColor),
            startY = p4.y.coerceAtMost(p3.y),
            endY = p1.y.coerceAtLeast(p2.y)
          )
        )
        // Subtle wall edge line
        drawPath(
          path = wallPath,
          color = Color(0xFF334155).copy(alpha = 0.5f),
          style = Stroke(width = 1f)
        )

        // Matrix of illuminated windows on facade
        if (hasWindows && pitchDeg > 15f) {
          val numRows = 3
          val numCols = 3
          for (r in 0 until numRows) {
            val vFrac1 = (r + 0.25f) / numRows
            val vFrac2 = (r + 0.75f) / numRows
            for (c in 0 until numCols) {
              val hFrac1 = (c + 0.25f) / numCols
              val hFrac2 = (c + 0.75f) / numCols

              fun interp(hf: Float, vf: Float): Offset {
                val bottom = Offset(
                  p1.x + (p2.x - p1.x) * hf,
                  p1.y + (p2.y - p1.y) * hf
                )
                val top = Offset(
                  p4.x + (p3.x - p4.x) * hf,
                  p4.y + (p3.y - p4.y) * hf
                )
                return Offset(
                  bottom.x + (top.x - bottom.x) * vf,
                  bottom.y + (top.y - bottom.y) * vf
                )
              }

              val w1 = interp(hFrac1, vFrac1)
              val w2 = interp(hFrac2, vFrac1)
              val w3 = interp(hFrac2, vFrac2)
              val w4 = interp(hFrac1, vFrac2)

              val winPath = Path().apply {
                moveTo(w1.x, w1.y)
                lineTo(w2.x, w2.y)
                lineTo(w3.x, w3.y)
                lineTo(w4.x, w4.y)
                close()
              }

              val hash = kotlin.math.abs((building.id.hashCode() + r * 7 + c * 13) % 5)
              val winColor = when (hash) {
                0, 1 -> SafeCyan.copy(alpha = 0.75f)
                2 -> Color(0xFFFDE68A).copy(alpha = 0.85f)
                else -> Color(0xFF1E293B).copy(alpha = 0.35f)
              }
              drawPath(path = winPath, color = winColor)
            }
          }
        }
      }
    }

    // South wall (b4 -> b3 -> t3 -> t4)
    drawWallIfVisible(b4, b3, t3, t4, Color(0xFF2A394D), Color(0xFF131B27))
    // East wall (b3 -> b2 -> t2 -> t3)
    drawWallIfVisible(b3, b2, t2, t3, Color(0xFF202C3D), Color(0xFF0F1722))
    // North wall (b2 -> b1 -> t1 -> t2)
    drawWallIfVisible(b2, b1, t1, t2, Color(0xFF253447), Color(0xFF111925))
    // West wall (b1 -> b4 -> t4 -> t1)
    drawWallIfVisible(b1, b4, t4, t1, Color(0xFF1D2838), Color(0xFF0E1520))
  }

  // Rooftop polygon (t1 -> t2 -> t3 -> t4)
  val roofPath = Path().apply {
    moveTo(t1.x, t1.y)
    lineTo(t2.x, t2.y)
    lineTo(t3.x, t3.y)
    lineTo(t4.x, t4.y)
    close()
  }

  drawPath(
    path = roofPath,
    brush = Brush.verticalGradient(
      colors = listOf(Color(0xFF334155), Color(0xFF1E293B)),
      startY = t1.y.coerceAtMost(t4.y),
      endY = t2.y.coerceAtLeast(t3.y)
    )
  )

  // Glowing rooftop edge highlight
  drawPath(
    path = roofPath,
    color = SafeCyan.copy(alpha = 0.5f),
    style = Stroke(width = 1.4f)
  )

  // Architectural Rooftop Structure (Helipad or HVAC ventilation unit)
  val rCenterX = (t1.x + t2.x + t3.x + t4.x) / 4f
  val rCenterY = (t1.y + t2.y + t3.y + t4.y) / 4f
  val roofRadius = kotlin.math.abs(t2.x - t1.x).coerceAtLeast(8f) * 0.18f

  if (pitchDeg > 15f && z >= 0.14f) {
    drawCircle(
      color = Color(0xFFFCD34D).copy(alpha = 0.7f),
      radius = roofRadius,
      center = Offset(rCenterX, rCenterY),
      style = Stroke(width = 1.2f)
    )
    drawCircle(
      color = SafeCyan.copy(alpha = 0.8f),
      radius = roofRadius * 0.35f,
      center = Offset(rCenterX, rCenterY)
    )
  }
}

/**
 * Pre-configured realistic urban building footprints for the 3D map
 */
private fun generateSampleUrbanBuildings(): List<Building3D> {
  return listOf(
    Building3D("b1", "Residencial A", 0.12f, 0.65f, 0.09f, 0.08f, 0.14f),
    Building3D("b2", "Comércio Local", 0.28f, 0.78f, 0.08f, 0.07f, 0.10f),
    Building3D("b3", "Drogaria 24h Iluminada", 0.38f, 0.45f, 0.07f, 0.08f, 0.12f),
    Building3D("b4", "Centro Comercial Seguro", 0.48f, 0.52f, 0.11f, 0.09f, 0.18f),
    Building3D("b5", "Complexo Logístico Norte", 0.62f, 0.22f, 0.13f, 0.10f, 0.16f),
    Building3D("b6", "Pólo Industrial Alpha", 0.76f, 0.08f, 0.15f, 0.12f, 0.22f),
    Building3D("b7", "Fábrica Setor Sul", 0.78f, 0.35f, 0.10f, 0.09f, 0.13f),
    Building3D("b8", "Galpão Industrial", 0.55f, 0.68f, 0.09f, 0.08f, 0.11f)
  )
}

/**
 * Finds if user tapped near any MapPoint on the canvas
 */
private fun findTappedPoint(
  tapOffset: Offset,
  canvasWidth: Float,
  canvasHeight: Float,
  route: RouteOption,
  pitchDeg: Float,
  yawDeg: Float,
  zoom: Float
): MapPoint? {
  val cx = canvasWidth / 2f
  val cy = canvasHeight / 2f
  val pitchRad = Math.toRadians(pitchDeg.toDouble()).toFloat()
  val yawRad = Math.toRadians(yawDeg.toDouble()).toFloat()

  val allPoints = route.pathPoints + route.hazardPoints + route.accidentPoints + route.darkZonePoints + route.steepPoints

  for (pt in allPoints) {
    val dx = (pt.xNorm - 0.5f) * canvasWidth * zoom
    val dy = (pt.yNorm - 0.5f) * canvasHeight * zoom
    val rotX = dx * cos(yawRad) - dy * sin(yawRad)
    val rotY = dx * sin(yawRad) + dy * cos(yawRad)
    val px = cx + rotX
    val py = cy + rotY * cos(pitchRad) - (0.05f * 180f * sin(pitchRad))

    val distSq = (tapOffset.x - px) * (tapOffset.x - px) + (tapOffset.y - py) * (tapOffset.y - py)
    if (distSq <= 1200f) { // ~35px touch target radius
      return pt
    }
  }
  return null
}

/**
 * Floating Info Card displayed when tapping any point on the 2D/3D map
 */
@Composable
private fun PointDetailCard(
  point: MapPoint,
  onDismiss: () -> Unit,
  onNavigateGoogleMaps: () -> Unit
) {
  val accentColor = when (point.type) {
    PointType.ORIGIN -> SafeCyan
    PointType.DESTINATION -> SafeGreen
    PointType.HAZARD -> SafeRed
    PointType.ACCIDENT -> SafeRed
    PointType.DARK_ZONE -> Color(0xFF818CF8)
    PointType.STEEP_INCLINE -> SafeAmber
    else -> Color(0xFF38BDF8)
  }

  Surface(
    shape = RoundedCornerShape(16.dp),
    color = SafeNavy.copy(alpha = 0.96f),
    shadowElevation = 10.dp,
    border = androidx.compose.foundation.BorderStroke(1.2.dp, accentColor.copy(alpha = 0.6f)),
    modifier = Modifier
      .fillMaxWidth(0.95f)
      .testTag("point_detail_popup")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .background(color = accentColor.copy(alpha = 0.22f), shape = CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = when (point.type) {
              PointType.ORIGIN -> Icons.Default.Navigation
              PointType.DESTINATION -> Icons.Default.Directions
              PointType.HAZARD -> Icons.Default.Warning
              PointType.ACCIDENT -> Icons.Default.CarCrash
              PointType.DARK_ZONE -> Icons.Default.NightlightRound
              PointType.STEEP_INCLINE -> Icons.Default.Landscape
              else -> Icons.Default.Brightness5
            },
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = point.label,
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 13.5.sp
            )
            if (point.type == PointType.ACCIDENT && point.severity.isNotEmpty()) {
              Surface(
                color = SafeRed.copy(alpha = 0.25f),
                shape = RoundedCornerShape(4.dp)
              ) {
                Text(
                  text = point.severity,
                  color = SafeRed,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                )
              }
            }
          }

          if (point.description.isNotEmpty()) {
            Text(
              text = point.description,
              color = Color.White.copy(alpha = 0.85f),
              fontSize = 11.sp,
              lineHeight = 15.sp
            )
          }
        }

        Spacer(modifier = Modifier.width(6.dp))

        IconButton(
          onClick = onDismiss,
          modifier = Modifier.size(26.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Fechar",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.size(16.dp)
          )
        }
      }

      // Specialized contextual telemetry badges
      when (point.type) {
        PointType.ACCIDENT -> {
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            if (point.timeReported.isNotEmpty()) {
              Surface(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = "⏱️ ${point.timeReported}",
                  color = Color.White.copy(alpha = 0.9f),
                  fontSize = 10.sp,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            if (point.vehiclesAffected.isNotEmpty()) {
              Text(
                text = "Afeta: ${point.vehiclesAffected.joinToString(", ")}",
                color = Color(0xFFFCA5A5),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
          if (point.statusNote.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "⚠️ ${point.statusNote}",
              color = Color(0xFFFED7AA),
              fontSize = 10.5.sp
            )
          }
        }
        PointType.DARK_ZONE -> {
          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            color = Color(0xFF312E81).copy(alpha = 0.5f),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
              Text(
                text = "🚗 Carros / 🏍️ Motos: Reduzir velocidade • Faróis altos",
                color = Color(0xFFC7D2FE),
                fontSize = 9.5.sp
              )
              Text(
                text = "🚲 Ciclistas / ♿ Cadeirantes: Calçada com buracos ocultos",
                color = Color(0xFFE0E7FF),
                fontSize = 9.5.sp
              )
            }
          }
          if (point.statusNote.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "ℹ️ ${point.statusNote}",
              color = Color(0xFF93C5FD),
              fontSize = 10.sp
            )
          }
        }
        PointType.STEEP_INCLINE -> {
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              color = SafeAmber.copy(alpha = 0.2f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = "Declividade: +${point.slopePercent}%",
                color = SafeAmber,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
            if (point.elevationMeters > 0) {
              Surface(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = "Desnível: +${point.elevationMeters}m",
                  color = Color.White.copy(alpha = 0.9f),
                  fontSize = 10.sp,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }
            if (point.slopePercent > 8.33f) {
              Text(
                text = "♿ Inacessível NBR 9050 (>8.33%)",
                color = SafeRed,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold
              )
            } else {
              Text(
                text = "♿ Acessível NBR 9050",
                color = SafeGreen,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
          if (point.statusNote.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "📌 ${point.statusNote}",
              color = Color(0xFFFDE68A),
              fontSize = 10.sp
            )
          }
        }
        else -> Unit
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Coordinates and Google Maps Launch Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Lat/Lng: %.4f, %.4f".format(point.lat, point.lng),
          color = SafeCyan.copy(alpha = 0.8f),
          fontSize = 9.5.sp
        )

        Button(
          onClick = onNavigateGoogleMaps,
          colors = ButtonDefaults.buttonColors(containerColor = SafeCyan, contentColor = SafeNavy),
          shape = RoundedCornerShape(12.dp),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 2.dp),
          modifier = Modifier.height(26.dp)
        ) {
          Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(11.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = "Abrir Maps", fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

/**
 * Embedded Google Maps WebView for live web navigation & satellite visualization.
 * Configured with software layer and onRenderProcessGone handler to prevent GPU/MESA crashes.
 */
@Composable
private fun GoogleMapsEmbedView(
  originAddress: String,
  destinationAddress: String,
  onReturnToCanvas: (() -> Unit)? = null
) {
  val context = LocalContext.current
  val encodedOrigin = Uri.encode(originAddress)
  val encodedDestination = Uri.encode(destinationAddress)
  val mapsUrl = "https://www.google.com/maps/dir/?api=1&origin=$encodedOrigin&destination=$encodedDestination&travelmode=transit"

  var hasRendererCrashed by remember { mutableStateOf(false) }

  if (hasRendererCrashed) {
    // Graceful fallback card when renderer process terminates in headless / GPU-less environments
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0F172A))
        .padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(SafeCyan.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Map,
            contentDescription = null,
            tint = SafeCyan,
            modifier = Modifier.size(24.dp)
          )
        }

        Text(
          text = "Visualização do Google Maps",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = Color.White
        )

        Text(
          text = "A renderização WebGL do Maps foi interrompida no emulador sem aceleração gráfica direta. Você pode abrir o trajeto no Google Maps ou voltar ao Modelo 3D nativo.",
          fontSize = 11.5.sp,
          color = Color.White.copy(alpha = 0.8f),
          textAlign = TextAlign.Center,
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(
            onClick = {
              try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
              } catch (ignored: Exception) {
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = SafeCyan, contentColor = SafeNavy),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Abrir no App", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }

          if (onReturnToCanvas != null) {
            OutlinedButton(
              onClick = onReturnToCanvas,
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
              Text("Voltar ao 3D", fontSize = 11.sp)
            }
          }
        }
      }
    }
  } else {
    AndroidView(
      factory = { ctx ->
        WebView(ctx).apply {
          // Disable hardware acceleration to avoid Mesa GPU rendernode crashes on headless emulator
          setLayerType(View.LAYER_TYPE_SOFTWARE, null)
          settings.javaScriptEnabled = true
          settings.domStorageEnabled = true
          settings.cacheMode = WebSettings.LOAD_DEFAULT
          settings.mediaPlaybackRequiresUserGesture = true

          webViewClient = object : WebViewClient() {
            override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
              // CRITICAL: Handle renderer crash gracefully so Android OS does not kill the app process!
              hasRendererCrashed = true
              try {
                (view?.parent as? ViewGroup)?.removeView(view)
                view?.destroy()
              } catch (ignored: Exception) {
              }
              return true
            }
          }
          loadUrl(mapsUrl)
        }
      },
      update = { webView ->
        if (!hasRendererCrashed) {
          try {
            webView.loadUrl(mapsUrl)
          } catch (ignored: Exception) {
          }
        }
      },
      modifier = Modifier
        .fillMaxSize()
        .testTag("google_maps_webview")
    )
  }
}

@Composable
private fun MapGlassIconButton(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  contentDesc: String,
  onClick: () -> Unit
) {
  Surface(
    shape = CircleShape,
    color = SafeNavy.copy(alpha = 0.88f),
    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
    shadowElevation = 4.dp,
    modifier = Modifier
      .size(36.dp)
      .clickable { onClick() }
  ) {
    Box(contentAlignment = Alignment.Center) {
      Icon(
        imageVector = icon,
        contentDescription = contentDesc,
        tint = Color.White,
        modifier = Modifier.size(18.dp)
      )
    }
  }
}

/**
 * Launches official Google Maps app or browser navigation
 */
fun launchGoogleMapsDirections(context: Context, origin: String, destination: String) {
  val uri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=${Uri.encode(origin)}&destination=${Uri.encode(destination)}&travelmode=transit")
  val intent = Intent(Intent.ACTION_VIEW, uri).apply {
    setPackage("com.google.android.apps.maps")
  }
  try {
    context.startActivity(intent)
  } catch (e: Exception) {
    // If Google Maps app is not installed, open in browser
    val webIntent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(webIntent)
  }
}

/**
 * Interactive 3D Spatial Compass Gizmo with rotating needle & real-time telemetry
 */
@Composable
fun SpatialCompassGizmo(
  yawDeg: Float,
  pitchDeg: Float,
  onResetNorth: () -> Unit,
  modifier: Modifier = Modifier
) {
  val normalizedYaw = ((yawDeg % 360f) + 360f) % 360f
  val headingText = when (normalizedYaw.toInt()) {
    in 338..360, in 0..22 -> "N"
    in 23..67 -> "NE"
    in 68..112 -> "E"
    in 113..157 -> "SE"
    in 158..202 -> "S"
    in 203..247 -> "SW"
    in 248..292 -> "W"
    else -> "NW"
  }

  Surface(
    shape = RoundedCornerShape(16.dp),
    color = SafeNavy.copy(alpha = 0.90f),
    border = androidx.compose.foundation.BorderStroke(1.dp, SafeCyan.copy(alpha = 0.35f)),
    shadowElevation = 6.dp,
    modifier = modifier
      .clickable { onResetNorth() }
      .testTag("spatial_compass_gizmo")
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Box(
        modifier = Modifier.size(28.dp),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val center = Offset(size.width / 2f, size.height / 2f)
          val r = size.width / 2f - 2f

          // Outer compass ring
          drawCircle(
            color = SafeCyan.copy(alpha = 0.35f),
            radius = r,
            center = center,
            style = Stroke(width = 1.4f)
          )

          // Rotating needle pointing to North
          val rad = Math.toRadians(-yawDeg.toDouble()).toFloat()
          val needleLen = r * 0.82f
          val northTip = Offset(
            center.x + sin(rad) * needleLen,
            center.y - cos(rad) * needleLen
          )
          val southTip = Offset(
            center.x - sin(rad) * (needleLen * 0.65f),
            center.y + cos(rad) * (needleLen * 0.65f)
          )

          // North needle (Red)
          drawLine(
            color = SafeRed,
            start = center,
            end = northTip,
            strokeWidth = 2.8f,
            cap = StrokeCap.Round
          )
          // South needle (White)
          drawLine(
            color = Color.White.copy(alpha = 0.6f),
            start = center,
            end = southTip,
            strokeWidth = 1.8f,
            cap = StrokeCap.Round
          )
          // Center hub
          drawCircle(color = SafeNavy, radius = 2.5f, center = center)
          drawCircle(color = Color.White, radius = 1.2f, center = center)
        }
      }

      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "$headingText ${normalizedYaw.toInt()}°",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 10.5.sp
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = "NORTE",
            color = SafeCyan,
            fontSize = 7.5.sp,
            fontWeight = FontWeight.Black
          )
        }
        Text(
          text = "Tilt: ${pitchDeg.toInt()}°",
          color = Color.White.copy(alpha = 0.7f),
          fontSize = 8.5.sp
        )
      }
    }
  }
}

/**
 * Fullscreen Interactive 3D Spatial Simulator Modal Dialog
 * Provides edge-to-edge tactical 360° orbit visualization with telemetry and HUD controls
 */
@Composable
fun FullScreen3DSpatialDialog(
  route: RouteOption,
  buildings: List<Building3D>,
  initialYaw: Float,
  initialPitch: Float,
  initialZoom: Float,
  showLed: Boolean,
  showHazards: Boolean,
  showAccidents: Boolean = true,
  showDarkZones: Boolean = true,
  showSteepRoutes: Boolean = true,
  showBuildings: Boolean,
  workerProgress: Float,
  hazardPulse: Float,
  originAddress: String,
  destinationAddress: String,
  onDismiss: () -> Unit,
  onToggleLed: () -> Unit,
  onToggleHazards: () -> Unit,
  onToggleAccidents: () -> Unit = {},
  onToggleDarkZones: () -> Unit = {},
  onToggleSteepRoutes: () -> Unit = {},
  onToggleBuildings: () -> Unit
) {
  val context = LocalContext.current
  var is3D by remember { mutableStateOf(true) }
  var zoom by remember { mutableFloatStateOf(initialZoom.coerceAtLeast(1.15f)) }
  var yawDeg by remember { mutableFloatStateOf(initialYaw) }
  var pitchDegCustom by remember { mutableFloatStateOf(initialPitch) }
  var selectedPoint by remember { mutableStateOf<MapPoint?>(null) }

  val pitchDeg by animateFloatAsState(
    targetValue = if (is3D) pitchDegCustom else 0f,
    animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
    label = "fs_pitch_anim"
  )
  val animatedYaw by animateFloatAsState(
    targetValue = if (is3D) yawDeg else 0f,
    animationSpec = tween(durationMillis = 200),
    label = "fs_yaw_anim"
  )
  val animatedZoom by animateFloatAsState(
    targetValue = zoom,
    animationSpec = tween(durationMillis = 200),
    label = "fs_zoom_anim"
  )

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .testTag("fullscreen_3d_spatial_dialog"),
      color = Color(0xFF060B14)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {

        // Edge-to-Edge 3D Tactical Canvas with Orbital Gesture Support
        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .testTag("fullscreen_tactical_canvas")
            .pointerInput(Unit) {
              detectTapGestures(
                onTap = { tapOffset ->
                  val tapped = findTappedPoint(
                    tapOffset = tapOffset,
                    canvasWidth = size.width.toFloat(),
                    canvasHeight = size.height.toFloat(),
                    route = route,
                    pitchDeg = pitchDeg,
                    yawDeg = animatedYaw,
                    zoom = animatedZoom
                  )
                  selectedPoint = tapped
                },
                onDoubleTap = {
                  yawDeg = -25f
                  pitchDegCustom = 48f
                  zoom = 1.15f
                  is3D = true
                }
              )
            }
            .pointerInput(Unit) {
              detectDragGestures { change, dragAmount ->
                change.consume()
                yawDeg += dragAmount.x * 0.40f
                if (is3D) {
                  pitchDegCustom = (pitchDegCustom - dragAmount.y * 0.30f).coerceIn(12f, 75f)
                }
              }
            }
        ) {
          drawTacticalMap(
            route = route,
            buildings = if (showBuildings) buildings else emptyList(),
            pitchDeg = pitchDeg,
            yawDeg = animatedYaw,
            zoom = animatedZoom,
            showLed = showLed,
            showHazards = showHazards,
            showAccidents = showAccidents,
            showDarkZones = showDarkZones,
            showSteepRoutes = showSteepRoutes,
            workerProgress = workerProgress,
            hazardPulse = hazardPulse
          )
        }

        // Top HUD Header & Live Telemetry Ribbon
        Column(
          modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                colors = listOf(SafeNavy.copy(alpha = 0.95f), SafeNavy.copy(alpha = 0.85f), Color.Transparent)
              )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = SafeCyan.copy(alpha = 0.2f),
                modifier = Modifier.size(34.dp)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.ViewInAr,
                    contentDescription = null,
                    tint = SafeCyan,
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "Simulador Espacial 3D",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (route.isRecommended) SafeGreen else SafeAmber
                  ) {
                    Text(
                      text = "${route.safetyScore}/100 SCORE",
                      color = SafeNavy,
                      fontSize = 8.5.sp,
                      fontWeight = FontWeight.Black,
                      modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                  }
                }
                Text(
                  text = "${route.title} • ${route.distanceKm} km • ${route.durationMinutes} min",
                  color = Color.White.copy(alpha = 0.75f),
                  fontSize = 11.sp
                )
              }
            }

            IconButton(
              onClick = onDismiss,
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
                .testTag("close_fullscreen_3d_button")
            ) {
              Icon(
                imageVector = Icons.Default.FullscreenExit,
                contentDescription = "Fechar Tela Cheia",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          // Live Telemetry Readout Strip
          val normYaw = ((animatedYaw % 360f) + 360f) % 360f
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color.Black.copy(alpha = 0.40f))
              .padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "🧭 Azimute: ${normYaw.toInt()}°",
              color = SafeCyan,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "📐 Pitch: ${pitchDeg.toInt()}°",
              color = Color.White.copy(alpha = 0.85f),
              fontSize = 10.sp
            )
            Text(
              text = "🔍 Zoom: %.1fx".format(zoom),
              color = Color.White.copy(alpha = 0.85f),
              fontSize = 10.sp
            )
            Text(
              text = "💡 ${route.lightPoints.size} LEDs",
              color = SafeGreen,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        // Top Left: 3 Camera Presets (2D Topo, 3D 48°, 3D 68°)
        Row(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 96.dp, start = 14.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(SafeNavy.copy(alpha = 0.90f))
            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .padding(2.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (!is3D) SafeCyan else Color.Transparent,
            modifier = Modifier.clickable { is3D = false }
          ) {
            Text(
              text = "2D Topo",
              color = if (!is3D) SafeNavy else Color.White,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
            )
          }
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (is3D && pitchDegCustom < 58f) SafeGreen else Color.Transparent,
            modifier = Modifier.clickable {
              is3D = true
              pitchDegCustom = 48f
            }
          ) {
            Text(
              text = "3D Isométrico",
              color = if (is3D && pitchDegCustom < 58f) SafeNavy else Color.White,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
            )
          }
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (is3D && pitchDegCustom >= 58f) SafeCyan else Color.Transparent,
            modifier = Modifier.clickable {
              is3D = true
              pitchDegCustom = 68f
            }
          ) {
            Text(
              text = "3D Imersivo",
              color = if (is3D && pitchDegCustom >= 58f) SafeNavy else Color.White,
              fontSize = 10.5.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
            )
          }
        }

        // Top Right: Compass Gizmo & Full Orbital Controls
        Column(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 96.dp, end = 14.dp),
          horizontalAlignment = Alignment.End,
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          SpatialCompassGizmo(
            yawDeg = animatedYaw,
            pitchDeg = pitchDeg,
            onResetNorth = {
              yawDeg = 0f
              pitchDegCustom = 48f
              is3D = true
            }
          )

          // Zoom Controls
          Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            MapGlassIconButton(
              icon = Icons.Default.ZoomIn,
              contentDesc = "Aproximar",
              onClick = { if (zoom < 2.5f) zoom += 0.25f }
            )
            MapGlassIconButton(
              icon = Icons.Default.ZoomOut,
              contentDesc = "Afastar",
              onClick = { if (zoom > 0.6f) zoom -= 0.25f }
            )
          }

          if (is3D) {
            // Horizontal Orbit Controls
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              MapGlassIconButton(
                icon = Icons.Default.RotateLeft,
                contentDesc = "Girar Esquerda",
                onClick = { yawDeg = (yawDeg - 30f) % 360f }
              )
              MapGlassIconButton(
                icon = Icons.Default.RotateRight,
                contentDesc = "Girar Direita",
                onClick = { yawDeg = (yawDeg + 30f) % 360f }
              )
            }
            // Vertical Tilt Controls
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
              MapGlassIconButton(
                icon = Icons.Default.KeyboardArrowUp,
                contentDesc = "Inclinar Cima",
                onClick = { pitchDegCustom = (pitchDegCustom + 10f).coerceIn(12f, 75f) }
              )
              MapGlassIconButton(
                icon = Icons.Default.KeyboardArrowDown,
                contentDesc = "Inclinar Baixo",
                onClick = { pitchDegCustom = (pitchDegCustom - 10f).coerceIn(12f, 75f) }
              )
            }
          }
        }

        // Gesture Hint Banner
        Box(
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 76.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SafeNavy.copy(alpha = 0.85f))
            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.TouchApp,
              contentDescription = null,
              tint = SafeCyan,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = "Arraste livremente para orbitar 360° e inclinar • Toque duplo redefine câmera",
              color = Color.White.copy(alpha = 0.90f),
              fontSize = 10.sp
            )
          }
        }

        // Selected Point Popup Card
        if (selectedPoint != null) {
          Box(
            modifier = Modifier
              .align(Alignment.Center)
              .padding(16.dp)
          ) {
            selectedPoint?.let { point ->
              PointDetailCard(
                point = point,
                onDismiss = { selectedPoint = null },
                onNavigateGoogleMaps = {
                  launchGoogleMapsPoint(context, point.lat, point.lng, point.label)
                }
              )
            }
          }
        }

        // Bottom Layer Toggles & Action Bar
        Surface(
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth(),
          color = SafeNavy.copy(alpha = 0.95f),
          tonalElevation = 8.dp
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier
                .weight(1f)
                .horizontalScroll(rememberScrollState()),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              FilterChip(
                selected = showLed,
                onClick = onToggleLed,
                label = { Text("LED 3D", fontSize = 11.sp) },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.Brightness5,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp)
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SafeCyan.copy(alpha = 0.25f),
                  selectedLabelColor = SafeNavy
                )
              )

              FilterChip(
                selected = showAccidents,
                onClick = onToggleAccidents,
                label = { Text("Acidentes (${route.accidentPoints.size})", fontSize = 11.sp) },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.CarCrash,
                    contentDescription = null,
                    tint = SafeRed,
                    modifier = Modifier.size(12.dp)
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SafeRed.copy(alpha = 0.25f),
                  selectedLabelColor = SafeNavy
                )
              )

              FilterChip(
                selected = showDarkZones,
                onClick = onToggleDarkZones,
                label = { Text("Escuro (${route.darkZonePoints.size})", fontSize = 11.sp) },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.NightlightRound,
                    contentDescription = null,
                    tint = Color(0xFF818CF8),
                    modifier = Modifier.size(12.dp)
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = Color(0xFF6366F1).copy(alpha = 0.25f),
                  selectedLabelColor = SafeNavy
                )
              )

              FilterChip(
                selected = showSteepRoutes,
                onClick = onToggleSteepRoutes,
                label = { Text("Declive (${route.maxInclinePercent}%)", fontSize = 11.sp) },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.Landscape,
                    contentDescription = null,
                    tint = SafeAmber,
                    modifier = Modifier.size(12.dp)
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SafeAmber.copy(alpha = 0.25f),
                  selectedLabelColor = SafeNavy
                )
              )

              FilterChip(
                selected = showHazards,
                onClick = onToggleHazards,
                label = { Text("Riscos", fontSize = 11.sp) },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp)
                  )
                },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = SafeRed.copy(alpha = 0.25f),
                  selectedLabelColor = SafeNavy
                )
              )

              if (is3D) {
                FilterChip(
                  selected = showBuildings,
                  onClick = onToggleBuildings,
                  label = { Text("Prédios 3D", fontSize = 11.sp) },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SafeGreen.copy(alpha = 0.25f),
                    selectedLabelColor = SafeNavy
                  )
                )
              }
            }

            Button(
              onClick = {
                launchGoogleMapsDirections(
                  context = context,
                  origin = originAddress,
                  destination = destinationAddress
                )
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = SafeCyan,
                contentColor = SafeNavy
              ),
              shape = RoundedCornerShape(18.dp),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
              modifier = Modifier.height(32.dp)
            ) {
              Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text("Google Maps", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

      }
    }
  }
}

/**
 * Launches Google Maps centered on a specific waypoint coordinate
 */
fun launchGoogleMapsPoint(context: Context, lat: Double, lng: Double, label: String) {
  val uri = Uri.parse("geo:$lat,$lng?q=$lat,$lng(${Uri.encode(label)})")
  val intent = Intent(Intent.ACTION_VIEW, uri).apply {
    setPackage("com.google.android.apps.maps")
  }
  try {
    context.startActivity(intent)
  } catch (e: Exception) {
    val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
    context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
  }
}

