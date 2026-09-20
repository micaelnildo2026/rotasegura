package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SafeAmber
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeNavy
import com.example.ui.theme.SafeNavyLight
import com.example.ui.theme.SafeRed

enum class UserJourneyDisplayMode {
  STEP_BY_STEP,
  FULL_MATRIX
}

data class UserJourneyStage(
  val stepNumber: Int,
  val title: String,
  val subtitle: String,
  val icon: ImageVector,
  val faz: String,
  val pensa: String,
  val sente: String,
  val doresNecessidades: String,
  val oportunidades: String
)

val sampleUserJourneyStages = listOf(
  UserJourneyStage(
    stepNumber = 1,
    title = "Etapa 1: Planejamento",
    subtitle = "Saída da fábrica ao fim do turno",
    icon = Icons.Default.Assignment,
    faz = "Bate o ponto na fábrica, desbloqueia o smartphone e verifica as opções de deslocamento no período noturno com pista molhada.",
    pensa = "\"Já passa das 22h, está chovendo e a rua da fábrica costuma ficar deserta. Será que o ônibus passa agora?\"",
    sente = "Apreensão e cansaço físico após 8h de turno; forte receio de ficar vulnerável esperando na calçada escura.",
    doresNecessidades = "Falta de previsibilidade de transporte em horários de troca de turno periféricos; sensação de vulnerabilidade.",
    oportunidades = "Diagnóstico Contextual Proativo da IA (MobiIA): sugere antecipadamente a rota segura considerando chuva, escuridão e dados do turno."
  ),
  UserJourneyStage(
    stepNumber = 2,
    title = "Etapa 2: Escolha de Rota",
    subtitle = "Avaliação 2D e 3D no mapa",
    icon = Icons.Default.Map,
    faz = "Insere o destino residencial, compara a Rota Segura IA contra a Rota Rápida tradicional e explora a elevação 3D dos prédios e postes LED.",
    pensa = "\"Os aplicativos comuns sempre me mandam por uma viela escura porque dizem que é 3 minutos mais rápida. Vale o risco?\"",
    sente = "Alívio e segurança cognitiva ao enxergar visualmente a via com 96% de iluminação e saber que o atalho perigoso foi evitado.",
    doresNecessidades = "Apps tradicionais ignoram segurança humana e priorizam apenas menor tempo; ausência de dados de iluminação pública.",
    oportunidades = "Visualizador Tático Google Maps 2D/3D: Roteamento baseado em Safety Score (0-100), iluminação volumétrica e prevenção de atropelamentos."
  ),
  UserJourneyStage(
    stepNumber = 3,
    title = "Etapa 3: Caminhada & Ponto",
    subtitle = "Primeira milha até o transporte",
    icon = Icons.Default.DirectionsWalk,
    faz = "Desloca-se a pé pela avenida larga indicada pelo aplicativo até o Ponto Seguro de ônibus (em frente à farmácia 24h iluminada).",
    pensa = "\"Ainda bem que vim pela avenida com postes acesos. O ponto da farmácia tem movimento de pessoas e câmeras visíveis.\"",
    sente = "Atenção redobrada, porém com postura tranquila e sensação de controle por estar em trecho com iluminação contínua.",
    doresNecessidades = "Travessias perigosas sem semáforo em vias de alta velocidade; pontos de ônibus desertos, sem abrigo e mal iluminados.",
    oportunidades = "Mapeamento de Paradas Seguras: direcionamento para pontos com comércio noturno ativo e calçadas amplas e acessíveis."
  ),
  UserJourneyStage(
    stepNumber = 4,
    title = "Etapa 4: Navegação Ativa",
    subtitle = "Deslocamento de ônibus e monitoramento",
    icon = Icons.Default.Navigation,
    faz = "Embarca no coletivo, ativa o Modo Trajeto Protegido com monitoramento contínuo e acompanha cada etapa em tempo real.",
    pensa = "\"Se acontecer qualquer incidente na pista ou se o trajeto desviar, tenho o botão SOS e o registro para minha segurança.\"",
    sente = "Proteção contínua e sensação de amparo por saber que o percurso está sendo supervisionado em tempo real.",
    doresNecessidades = "24,6% dos acidentes de trabalho ocorrem no trajeto; falta de assistência imediata em sinistros ou desvios viários.",
    oportunidades = "Modo Trajeto Protegido com SOS Instantâneo: acionamento de emergência no cabeçalho e compartilhamento com SESMT e familiares."
  ),
  UserJourneyStage(
    stepNumber = 5,
    title = "Etapa 5: Chegada ao Lar",
    subtitle = "Última milha e check-in seguro",
    icon = Icons.Default.Home,
    faz = "Desembarca na parada residencial, caminha os últimos 200 metros iluminados, entra em casa e confirma o Check-in Seguro no app.",
    pensa = "\"Cheguei são e salvo. Vou registrar no app sobre aquela lâmpada apagada na esquina para proteger meus colegas amanhã.\"",
    sente = "Alívio profundo, tranquilidade familiar e senso de cidadania ativa ao colaborar com relatos da comunidade.",
    doresNecessidades = "Família em angústia aguardando retorno noturno; falta de canais práticos para notificar a prefeitura sobre lâmpadas e buracos.",
    oportunidades = "Check-in Seguro Automatizado & Banco de Alertas: dados consolidados no Room que alimentam o painel B2G para melhorias na infraestrutura urbana."
  )
)

/**
 * Interactive User Journey Component matching the Hackathon Problem Canvas Template
 */
@Composable
fun UserJourneyMatrixView(
  modifier: Modifier = Modifier
) {
  var displayMode by remember { mutableStateOf(UserJourneyDisplayMode.STEP_BY_STEP) }
  var selectedStageIndex by remember { mutableIntStateOf(0) }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("user_journey_matrix_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

      // Header with Template Branding
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = SafeCyan.copy(alpha = 0.15f)
          ) {
            Text(
              text = "METODOLOGIA DE DESIGN THINKING",
              color = SafeCyan,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Template Jornada do Usuário",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              letterSpacing = (-0.5).sp
            )
          )
          Text(
            text = "Mapeamento empático do trabalhador noturno: da fábrica ao lar",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Surface(
          shape = CircleShape,
          color = SafeNavy,
          modifier = Modifier.size(44.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.Psychology,
              contentDescription = null,
              tint = SafeGreen,
              modifier = Modifier.size(24.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Persona Brief Card
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = SafeNavyLight.copy(alpha = 0.08f),
        border = androidx.compose.foundation.BorderStroke(1.dp, SafeNavyLight.copy(alpha = 0.15f))
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = CircleShape,
            color = SafeGreen.copy(alpha = 0.2f),
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = SafeGreen,
                modifier = Modifier.size(20.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Carlos Eduardo, 34 anos • Operador Industrial (Turno 22h)",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Deslocamento noturno multimodal: caminhada + ônibus na periferia",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // View Mode Toggle (Passo a Passo vs Matriz Completa)
      TabRow(
        selectedTabIndex = displayMode.ordinal,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.primary
      ) {
        Tab(
          selected = displayMode == UserJourneyDisplayMode.STEP_BY_STEP,
          onClick = { displayMode = UserJourneyDisplayMode.STEP_BY_STEP },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.ViewCarousel,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Etapas Detalhadas", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
          }
        )
        Tab(
          selected = displayMode == UserJourneyDisplayMode.FULL_MATRIX,
          onClick = { displayMode = UserJourneyDisplayMode.FULL_MATRIX },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.GridView,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Matriz Completa", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
          }
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      if (displayMode == UserJourneyDisplayMode.STEP_BY_STEP) {
        // Step-by-Step View
        // Stage Selector Pills
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          sampleUserJourneyStages.forEachIndexed { index, stage ->
            val isSelected = selectedStageIndex == index
            FilterChip(
              selected = isSelected,
              onClick = { selectedStageIndex = index },
              label = { Text("Etapa ${stage.stepNumber}", fontSize = 12.sp) },
              leadingIcon = {
                Icon(
                  imageVector = stage.icon,
                  contentDescription = null,
                  modifier = Modifier.size(14.dp)
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = SafeNavy,
                selectedLabelColor = Color.White,
                selectedLeadingIconColor = SafeCyan
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        val currentStage = sampleUserJourneyStages[selectedStageIndex]

        // Current Stage Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SafeNavy,
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "${currentStage.stepNumber}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
              )
            }
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = currentStage.title,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = currentStage.subtitle,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Dimension Rows
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          JourneyDimensionCard(
            label = "FAZ",
            icon = Icons.Default.DirectionsWalk,
            accentColor = SafeCyan,
            text = currentStage.faz
          )
          JourneyDimensionCard(
            label = "PENSA",
            icon = Icons.Default.Psychology,
            accentColor = Color(0xFF38BDF8),
            text = currentStage.pensa
          )
          JourneyDimensionCard(
            label = "SENTE",
            icon = Icons.Default.SentimentSatisfied,
            accentColor = Color(0xFFA78BFA),
            text = currentStage.sente
          )
          JourneyDimensionCard(
            label = "DORES / NECESSIDADES",
            icon = Icons.Default.Warning,
            accentColor = SafeRed,
            text = currentStage.doresNecessidades
          )
          JourneyDimensionCard(
            label = "OPORTUNIDADES (ROTASEGURA)",
            icon = Icons.Default.EmojiObjects,
            accentColor = SafeGreen,
            text = currentStage.oportunidades
          )
        }

      } else {
        // Full Matrix Table View (Horizontal Scrolling Grid like in the template image)
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = "Role horizontalmente para comparar todas as 5 etapas:",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState())
          ) {
            // Dimension Header Column (FAZ, PENSA, SENTE, DORES, OPORTUNIDADES)
            Column(
              modifier = Modifier.width(120.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              // Top Corner
              MatrixHeaderCell(title = "DIMENSÃO", isCategory = true, bgColor = SafeNavy)
              MatrixHeaderCell(title = "• FAZ", isCategory = false, bgColor = SafeCyan)
              MatrixHeaderCell(title = "• PENSA", isCategory = false, bgColor = Color(0xFF38BDF8))
              MatrixHeaderCell(title = "• SENTE", isCategory = false, bgColor = Color(0xFFA78BFA))
              MatrixHeaderCell(title = "• DORES / NECESSIDADES", isCategory = false, bgColor = SafeRed)
              MatrixHeaderCell(title = "• OPORTUNIDADES", isCategory = false, bgColor = SafeGreen)
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Columns for each stage
            sampleUserJourneyStages.forEach { stage ->
              Column(
                modifier = Modifier
                  .width(220.dp)
                  .padding(end = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                MatrixHeaderCell(
                  title = "ETAPA ${stage.stepNumber}",
                  subtitle = stage.title.substringAfter(": "),
                  isCategory = true,
                  bgColor = SafeNavy
                )
                MatrixContentCell(text = stage.faz, minHeight = 85)
                MatrixContentCell(text = stage.pensa, minHeight = 85, isItalic = true)
                MatrixContentCell(text = stage.sente, minHeight = 75)
                MatrixContentCell(text = stage.doresNecessidades, minHeight = 90, highlightBorder = SafeRed)
                MatrixContentCell(text = stage.oportunidades, minHeight = 110, highlightBorder = SafeGreen)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun JourneyDimensionCard(
  label: String,
  icon: ImageVector,
  accentColor: Color,
  text: String
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = accentColor.copy(alpha = 0.08f),
    border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(12.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = label,
          color = accentColor,
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = text,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp, lineHeight = 17.sp),
          color = MaterialTheme.colorScheme.onSurface
        )
      }
    }
  }
}

@Composable
private fun MatrixHeaderCell(
  title: String,
  subtitle: String? = null,
  isCategory: Boolean,
  bgColor: Color
) {
  Surface(
    shape = RoundedCornerShape(6.dp),
    color = bgColor,
    modifier = Modifier
      .fillMaxWidth()
      .height(48.dp)
  ) {
    Box(
      modifier = Modifier.padding(6.dp),
      contentAlignment = Alignment.CenterStart
    ) {
      Column {
        Text(
          text = title,
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = if (isCategory) 12.sp else 11.sp
        )
        if (subtitle != null) {
          Text(
            text = subtitle,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 9.5.sp,
            maxLines = 1
          )
        }
      }
    }
  }
}

@Composable
private fun MatrixContentCell(
  text: String,
  minHeight: Int,
  isItalic: Boolean = false,
  highlightBorder: Color? = null
) {
  Surface(
    shape = RoundedCornerShape(6.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    border = highlightBorder?.let { androidx.compose.foundation.BorderStroke(1.dp, it.copy(alpha = 0.4f)) },
    modifier = Modifier
      .fillMaxWidth()
      .height(minHeight.dp)
  ) {
    Box(
      modifier = Modifier.padding(8.dp),
      contentAlignment = Alignment.TopStart
    ) {
      Text(
        text = text,
        fontSize = 10.5.sp,
        lineHeight = 14.5.sp,
        fontStyle = if (isItalic) androidx.compose.ui.text.font.FontStyle.Italic else androidx.compose.ui.text.font.FontStyle.Normal,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
  }
}
