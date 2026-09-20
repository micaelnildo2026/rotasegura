package com.example.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Commute transportation modes
 */
enum class CommuteMode(val label: String, val iconName: String) {
  TRANSIT_WALK("Ônibus + A pé", "DirectionsBus"),
  WALK_ONLY("Apenas A pé", "DirectionsWalk"),
  BIKE("Bicicleta", "DirectionsBike"),
  CAR("Carro", "DirectionsCar"),
  MOTORCYCLE("Moto", "TwoWheeler"),
  WHEELCHAIR("Cadeirante", "Accessible")
}

/**
 * User vulnerability or priority profile - highly inclusive for PCD, Elderly, Children, Youth, and Workers
 */
enum class CommuteProfile(
  val label: String,
  val shortLabel: String,
  val iconEmoji: String,
  val description: String
) {
  ACCESSIBILITY(
    label = "PCD / Acessibilidade NBR 9050",
    shortLabel = "♿ PCD",
    iconEmoji = "♿",
    description = "Rotas 100% acessíveis, sem degraus, guias rebaixadas, rampas suaves (<=8.33%) e ônibus com elevador"
  ),
  ELDERLY(
    label = "Idosos (60+)",
    shortLabel = "🧓 Idosos",
    iconEmoji = "🧓",
    description = "Piso regular sem desníveis, ritmo calmo, tempo estendido para travessia e paradas com bancos de descanso"
  ),
  CHILDREN(
    label = "Crianças & Escolares",
    shortLabel = "🧒 Crianças",
    iconEmoji = "🧒",
    description = "Zonas 30 km/h, calçadas largas protegidas, travessias elevadas e rotas longe de tráfego pesado"
  ),
  YOUTH(
    label = "Jovens & Estudantes",
    shortLabel = "🎓 Jovens",
    iconEmoji = "🎓",
    description = "Polos de ensino, vias iluminadas por LED, ciclovias segregadas e corredores de transporte de alto fluxo"
  ),
  WORKER(
    label = "Trabalhador de Turnos",
    shortLabel = "👷 Trabalhador",
    iconEmoji = "👷",
    description = "Prevenção de sinistro de percurso MTE, iluminação contínua e paradas seguras e monitoradas"
  ),
  PEDESTRIAN(
    label = "Pedestre Geral",
    shortLabel = "🚶 Pedestre",
    iconEmoji = "🚶",
    description = "Iluminação pública contínua, calçadas desobstruídas e travessias semafóricas seguras"
  )
}

/**
 * A calculated mobility route option comparing safety vs speed
 */
data class RouteOption(
  val id: String,
  val title: String,
  val subtitle: String,
  val durationMinutes: Int,
  val distanceKm: Double,
  val walkMinutes: Int,
  val safetyScore: Int, // 0 - 100
  val illuminationScore: Int, // 0 - 100
  val publicSafetyScore: Int, // 0 - 100
  val trafficRiskScore: Int, // 0 - 100 (higher = safer, low risk)
  val accessibilityScore: Int, // 0 - 100
  val isRecommended: Boolean = false,
  val routeBadge: String? = null,
  val keyAdvantages: List<String>,
  val warningNotes: List<String> = emptyList(),
  val segments: List<RouteSegment>,
  val pathPoints: List<MapPoint> = emptyList(),
  val hazardPoints: List<MapPoint> = emptyList(),
  val lightPoints: List<MapPoint> = emptyList(),
  // Advanced Telemetry: Accidents, Dark Zones per vehicle, and Steep Incline
  val accidentPoints: List<MapPoint> = emptyList(),
  val darkZonePoints: List<MapPoint> = emptyList(),
  val steepPoints: List<MapPoint> = emptyList(),
  val maxInclinePercent: Float = 3.5f,
  val elevationGainMeters: Int = 14,
  val vehicleSafetyNotes: Map<String, String> = emptyMap()
)

enum class PointType {
  ORIGIN,
  DESTINATION,
  SAFE_STOP,
  HAZARD,
  LED_LIGHT,
  ACCIDENT,       // Acidente de trânsito / colisão no local
  DARK_ZONE,      // Trecho escuro / Iluminação deficiente para veículos
  STEEP_INCLINE   // Trecho íngreme / Ladeira / Declividade acentuada
}

data class MapPoint(
  val id: String,
  val label: String,
  val description: String = "",
  val xNorm: Float, // 0.0 to 1.0 on local map canvas
  val yNorm: Float, // 0.0 to 1.0 on local map canvas
  val lat: Double = -23.5505,
  val lng: Double = -46.6333,
  val type: PointType = PointType.ORIGIN,
  // Telemetry attributes for accidents, dark routes and steep slopes
  val severity: String = "MEDIA", // "BAIXA", "MEDIA", "ALTA", "CRITICA"
  val timeReported: String = "", // e.g. "Há 18 min"
  val vehiclesAffected: List<String> = emptyList(), // "Carros", "Motos", "Bicicletas", "Cadeirantes"
  val slopePercent: Float = 0f, // e.g. 16.5f for +16.5% incline
  val elevationMeters: Int = 0,
  val statusNote: String = "" // e.g. "Pista parcialmente bloqueada", "Sem iluminação em 400m"
)

data class Building3D(
  val id: String,
  val label: String,
  val xNorm: Float,
  val yNorm: Float,
  val widthNorm: Float,
  val heightNorm: Float,
  val depthNorm: Float, // Height in 3D projection
  val colorHex: Long = 0xFF1E293B
)

data class RouteSegment(
  val mode: CommuteMode,
  val title: String,
  val instruction: String,
  val distanceMeters: Int,
  val durationMin: Int,
  val safetyHighlights: String,
  val isHazardZone: Boolean = false
)

/**
 * Incident / risk report stored in Room
 */
@Entity(tableName = "incident_reports")
data class IncidentReportEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val category: String, // "ILUMINACAO", "VIOLENCIA", "SINISTRO_TRANSITO", "TRANSPORTE", "ACESSIBILIDADE"
  val title: String,
  val description: String,
  val address: String,
  val timestamp: Long = System.currentTimeMillis(),
  val upvotes: Int = 1,
  val severity: String = "MEDIA", // "ALTA", "MEDIA", "BAIXA"
  val reportedByUser: Boolean = false
)

/**
 * B2G Municipal & Corporate Safety Insights
 */
data class B2GZoneInsight(
  val id: String,
  val zoneName: String,
  val neighborhood: String,
  val riskLevel: String, // "CRITICO", "ALTO", "MODERADO"
  val primaryDeficit: String,
  val affectedWorkersDaily: Int,
  val recommendedAction: String,
  val estimatedLitigationReduction: String,
  val status: String // "PRIORIDADE ALTA", "EM ANALISE", "INTERVENCAO SUGERIDA"
)

/**
 * Live context used for AI route evaluation
 */
data class MobilityContext(
  val currentTimeDescription: String = "19:45 (Horário Noturno)",
  val weatherDescription: String = "Choviscando / Pista Molhada",
  val origin: String = "Rua das Palmeiras, 142 (Residência)",
  val destination: String = "Distrito Industrial Norte (Empresa Alpha)",
  val selectedProfile: CommuteProfile = CommuteProfile.WORKER,
  val selectedMode: CommuteMode = CommuteMode.TRANSIT_WALK
)
