package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KnowledgeItem
import com.example.data.LocalAiKnowledgeBase
import com.example.ui.ChatMessage
import com.example.ui.MobilityUiState
import com.example.ui.MobilityViewModel
import com.example.ui.theme.SafeCyan
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.SafeNavy

@Composable
fun MobiIaScreen(
  viewModel: MobilityViewModel,
  uiState: MobilityUiState
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Chat, 1: Biblioteca 128
  var userQueryInput by remember { mutableStateOf("") }
  var isDiagnosticExpanded by remember { mutableStateOf(false) }

  // Knowledge base state
  var selectedCategory by remember { mutableStateOf("Todas") }
  var knowledgeSearchQuery by remember { mutableStateOf("") }
  var expandedQuestionId by remember { mutableStateOf<String?>(null) }
  var copiedToastMessage by remember { mutableStateOf<String?>(null) }
  val clipboardManager = LocalClipboardManager.current

  val quickPrompts = listOf(
    "Acidente de trajeto pelo MTE?",
    "Limite de rampa NBR 9050?",
    "Distância para ciclistas?",
    "Por que não tirar capacete?",
    "Lei da parada segura noturna",
    "Garoa e pista escorregadia"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 72.dp)
      .testTag("mobi_ia_screen")
  ) {

    // Clean Header
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(SafeNavy)
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(SafeCyan.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = SafeCyan,
              modifier = Modifier.size(20.dp)
            )
          }
          Column {
            Text(
              text = "MobiIA",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            )
            Text(
              text = "Assistente de Mobilidade Segura",
              color = Color.White.copy(alpha = 0.75f),
              fontSize = 11.sp
            )
          }
        }

        IconButton(
          onClick = { viewModel.refreshAiAdvice() },
          modifier = Modifier.testTag("refresh_ia_advice")
        ) {
          Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Atualizar",
            tint = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }

    // Clean Segmented Tabs (Chat vs Biblioteca)
    Surface(
      color = SafeNavy,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
          .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
          .padding(3.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (selectedTab == 0) Color.White else Color.Transparent)
            .clickable { selectedTab = 0 }
            .padding(vertical = 8.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "💬 Chat da Viagem",
            fontSize = 12.sp,
            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
            color = if (selectedTab == 0) SafeNavy else Color.White.copy(alpha = 0.85f)
          )
        }

        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (selectedTab == 1) Color.White else Color.Transparent)
            .clickable { selectedTab = 1 }
            .padding(vertical = 8.dp),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              text = "📚 Biblioteca (128)",
              fontSize = 12.sp,
              fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
              color = if (selectedTab == 1) SafeNavy else Color.White.copy(alpha = 0.85f)
            )
          }
        }
      }
    }

    // TAB 0: CHAT INTELIGENTE
    if (selectedTab == 0) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {

        // Compact Collapsible Context Bar
        Surface(
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { isDiagnosticExpanded = !isDiagnosticExpanded },
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Psychology,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(16.dp)
                )
                Text(
                  text = "Contexto: ${uiState.context.selectedProfile.label} • ${uiState.context.selectedMode.label} • Noturno",
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Medium,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
              Icon(
                imageVector = if (isDiagnosticExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
              )
            }

            AnimatedVisibility(visible = isDiagnosticExpanded) {
              Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                  containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 8.dp)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Text(
                    text = "Diagnóstico Contínuo da Rota",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  if (uiState.isAiLoading && uiState.aiAdvice.isEmpty()) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                      CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp)
                      Text("Analisando variáveis da rota...", fontSize = 11.sp)
                    }
                  } else {
                    Text(
                      text = uiState.aiAdvice,
                      style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                      color = MaterialTheme.colorScheme.onSurface
                    )
                  }
                }
              }
            }
          }
        }

        // Quick prompts suggestions
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          quickPrompts.forEach { prompt ->
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
              modifier = Modifier.clickable {
                userQueryInput = prompt
                viewModel.sendChatMessage(prompt)
              }
            ) {
              Text(
                text = prompt,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        // Messages List
        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          contentPadding = PaddingValues(vertical = 8.dp)
        ) {
          if (uiState.chatMessages.isEmpty()) {
            item {
              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                  containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 16.dp)
              ) {
                Column(
                  modifier = Modifier.padding(16.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = SafeCyan,
                    modifier = Modifier.size(28.dp)
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = "Como a MobiIA pode ajudar você?",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = "Tire dúvidas sobre acidentes de trabalho (MTE/CAT), normas de acessibilidade (NBR 9050), cuidados no trânsito ou a segurança de sua rota.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                  )
                }
              }
            }
          }

          items(uiState.chatMessages) { message ->
            ChatMessageBubble(message = message)
          }

          if (uiState.isAiLoading && uiState.chatMessages.isNotEmpty()) {
            item {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
              ) {
                CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp)
                Text(
                  text = "MobiIA está consultando os dados...",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }

        // Input Bar at bottom
        Surface(
          tonalElevation = 4.dp,
          color = MaterialTheme.colorScheme.surface,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedTextField(
              value = userQueryInput,
              onValueChange = { userQueryInput = it },
              placeholder = { Text("Digite sua pergunta ou trajeto...", fontSize = 13.sp) },
              modifier = Modifier
                .weight(1f)
                .testTag("ia_chat_input"),
              singleLine = true,
              shape = RoundedCornerShape(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
              onClick = {
                if (userQueryInput.isNotBlank()) {
                  viewModel.sendChatMessage(userQueryInput)
                  userQueryInput = ""
                }
              },
              modifier = Modifier
                .size(42.dp)
                .background(SafeNavy, CircleShape)
                .testTag("ia_chat_send_button")
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Enviar",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }

    // TAB 1: BIBLIOTECA DE CONHECIMENTO (128 QUESTÕES)
    if (selectedTab == 1) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 16.dp)
      ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Search Input
        OutlinedTextField(
          value = knowledgeSearchQuery,
          onValueChange = { knowledgeSearchQuery = it },
          placeholder = { Text("Buscar entre 128 dúvidas de trânsito, leis...", fontSize = 12.5.sp) },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
          },
          trailingIcon = {
            if (knowledgeSearchQuery.isNotEmpty()) {
              IconButton(onClick = { knowledgeSearchQuery = "" }) {
                Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = "Limpar",
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Filter Chips
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          LocalAiKnowledgeBase.getCategories().forEach { cat ->
            FilterChip(
              selected = selectedCategory == cat,
              onClick = { selectedCategory = cat },
              label = { Text(cat, fontSize = 11.sp) },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = SafeNavy,
                selectedLabelColor = Color.White
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Filtered Questions List
        val filteredQuestions = remember(selectedCategory, knowledgeSearchQuery) {
          LocalAiKnowledgeBase.items.filter { item ->
            val matchesCategory = selectedCategory == "Todas" || item.category == selectedCategory
            val matchesQuery = knowledgeSearchQuery.isBlank() ||
              item.question.contains(knowledgeSearchQuery, ignoreCase = true) ||
              item.keywords.any { it.contains(knowledgeSearchQuery, ignoreCase = true) }
            matchesCategory && matchesQuery
          }
        }

        // Toast message when copied
        copiedToastMessage?.let { toast ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = SafeGreen.copy(alpha = 0.15f),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = SafeGreen, modifier = Modifier.size(14.dp))
              Text(text = toast, fontSize = 11.sp, color = SafeGreen, fontWeight = FontWeight.SemiBold)
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${filteredQuestions.size} dúvidas encontradas",
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Text(
            text = "⚡ 100% Offline",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = SafeGreen
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(bottom = 16.dp)
        ) {
          items(filteredQuestions) { item ->
            val isExpanded = expandedQuestionId == item.id
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isExpanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
              ),
              elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 2.dp else 0.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                      expandedQuestionId = if (isExpanded) null else item.id
                    },
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Text(text = item.categoryIcon, fontSize = 18.sp)
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = item.question,
                      fontSize = 12.5.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                      text = item.category,
                      fontSize = 10.5.sp,
                      color = MaterialTheme.colorScheme.primary
                    )
                  }
                  Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Recolher" else "Expandir",
                    tint = SafeCyan,
                    modifier = Modifier.size(18.dp)
                  )
                }

                // Expanded Answer
                AnimatedVisibility(visible = isExpanded) {
                  Column(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(top = 10.dp)
                  ) {
                    Surface(
                      shape = RoundedCornerShape(8.dp),
                      color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Text(
                        text = item.answer,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(10.dp)
                      )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                      Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SafeNavy,
                        modifier = Modifier
                          .weight(1f)
                          .clickable {
                            selectedTab = 0
                            userQueryInput = item.question
                            viewModel.sendChatMessage(item.question)
                          }
                      ) {
                        Row(
                          modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.Center
                        ) {
                          Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                          )
                          Spacer(modifier = Modifier.width(4.dp))
                          Text(
                            text = "Perguntar no Chat",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                          )
                        }
                      }

                      Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                          .clickable {
                            clipboardManager.setText(AnnotatedString(item.answer))
                            copiedToastMessage = "Resposta sobre ${item.category} copiada!"
                          }
                      ) {
                        Row(
                          modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.Center
                        ) {
                          Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(12.dp)
                          )
                          Spacer(modifier = Modifier.width(4.dp))
                          Text(
                            text = "Copiar",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                          )
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
  val isUser = message.sender == "USER"
  val clipboardManager = LocalClipboardManager.current
  var copied by remember { mutableStateOf(false) }

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
  ) {
    Surface(
      shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (isUser) 16.dp else 4.dp,
        bottomEnd = if (isUser) 4.dp else 16.dp
      ),
      color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
      tonalElevation = 1.dp,
      modifier = Modifier.widthIn(max = 330.dp)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = if (isUser) "Você" else "MobiIA",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (isUser) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.primary
              )
            )
            if (!isUser) {
              val isLocal = message.text.contains("MobiIA Local") || message.text.contains("Base Local")
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (isLocal) SafeGreen.copy(alpha = 0.2f) else SafeCyan.copy(alpha = 0.2f)
              ) {
                Text(
                  text = if (isLocal) "⚡ Base Local" else "✨ IA Contextual",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isLocal) SafeGreen else SafeCyan,
                  modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
              }
            }
          }

          if (!isUser) {
            IconButton(
              onClick = {
                clipboardManager.setText(AnnotatedString(message.text))
                copied = true
              },
              modifier = Modifier.size(20.dp)
            ) {
              Icon(
                imageVector = if (copied) Icons.Default.Check else Icons.Default.ContentCopy,
                contentDescription = "Copiar",
                tint = if (copied) SafeGreen else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(13.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = message.text,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 20.sp,
            color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }
  }
}
