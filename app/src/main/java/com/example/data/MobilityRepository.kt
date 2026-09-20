package com.example.data

import com.example.model.B2GZoneInsight
import com.example.model.CommuteMode
import com.example.model.CommuteProfile
import com.example.model.IncidentReportEntity
import com.example.model.MobilityContext
import com.example.model.RouteOption
import com.example.model.RouteSegment
import kotlinx.coroutines.flow.Flow

class MobilityRepository(private val dao: IncidentDao) {

  val allReports: Flow<List<IncidentReportEntity>> = dao.getAllReports()

  suspend fun initializePrepopulatedDataIfEmpty() {
    if (dao.getReportCount() == 0) {
      val seedReports = listOf(
        IncidentReportEntity(
          category = "ILUMINACAO",
          title = "Trecho sem iluminação pública após as 18h30",
          description = "Postes apagados na Av. dos Trabalhadores, próximo ao ponto de conexão das indústrias. Alto risco para pedestres.",
          address = "Av. dos Trabalhadores, entre nº 400 e 650",
          timestamp = System.currentTimeMillis() - 1000 * 60 * 35,
          upvotes = 14,
          severity = "ALTA"
        ),
        IncidentReportEntity(
          category = "SINISTRO_TRANSITO",
          title = "Cruzamento perigoso sem semáforo para pedestre",
          description = "Histórico de colisões e atropelamentos de operários durante a troca de turno. Veículos em alta velocidade.",
          address = "Cruzamento Av. Perimetral com Rua 7 de Setembro",
          timestamp = System.currentTimeMillis() - 1000 * 60 * 90,
          upvotes = 22,
          severity = "ALTA"
        ),
        IncidentReportEntity(
          category = "VIOLENCIA",
          title = "Ponto de ônibus isolado com relatos de roubos",
          description = "Ponto de parada sem visibilidade nem comércio aberto no período noturno. Recomenda-se descer 1 ponto antes.",
          address = "Parada de ônibus da Praça das Indústrias",
          timestamp = System.currentTimeMillis() - 1000 * 60 * 180,
          upvotes = 31,
          severity = "ALTA"
        ),
        IncidentReportEntity(
          category = "TRANSPORTE",
          title = "Intervalo excessivo na Linha 402 (Periferia)",
          description = "Tempo de espera superior a 45 minutos no horário noturno, forçando trabalhadores a ficarem expostos no ponto.",
          address = "Terminal Integrado Norte - Plataforma C",
          timestamp = System.currentTimeMillis() - 1000 * 60 * 240,
          upvotes = 18,
          severity = "MEDIA"
        ),
        IncidentReportEntity(
          category = "ACESSIBILIDADE",
          title = "Calçada esburacada e falta de rampa de acesso",
          description = "Pedestres e cadeirantes precisam transitar pela pista de rolamento dividindo espaço com caminhões e ônibus.",
          address = "Rua Santa Rita, acesso ao Hospital Regional",
          timestamp = System.currentTimeMillis() - 1000 * 60 * 320,
          upvotes = 9,
          severity = "MEDIA"
        )
      )
      dao.insertReports(seedReports)
    }
  }

  suspend fun addReport(report: IncidentReportEntity): Long {
    return dao.insertReport(report)
  }

  suspend fun upvoteReport(id: Long) {
    dao.upvoteReport(id)
  }

  suspend fun deleteReport(id: Long) {
    dao.deleteReportById(id)
  }

  fun getCalculatedRoutes(context: MobilityContext): List<RouteOption> {
    return when (context.selectedProfile) {
      CommuteProfile.ACCESSIBILITY -> getPcdRoutes()
      CommuteProfile.ELDERLY -> getElderlyRoutes()
      CommuteProfile.CHILDREN -> getChildrenRoutes()
      CommuteProfile.YOUTH -> getYouthRoutes()
      CommuteProfile.WORKER, CommuteProfile.PEDESTRIAN -> getDefaultWorkerRoutes()
    }
  }

  private fun getDefaultWorkerRoutes(): List<RouteOption> {
    return listOf(
      RouteOption(
        id = "route_safe_ai",
        title = "Rota Segura IA (Recomendada)",
        subtitle = "Prioriza iluminação, baixo risco de assalto e calçadas amplas",
        durationMinutes = 26,
        distanceKm = 4.2,
        walkMinutes = 6,
        safetyScore = 95,
        illuminationScore = 96,
        publicSafetyScore = 94,
        trafficRiskScore = 92,
        accessibilityScore = 88,
        isRecommended = true,
        routeBadge = "SEGURANÇA MÁXIMA (+37 pts)",
        keyAdvantages = listOf(
          "96% das vias monitoradas e com iluminação LED ativa",
          "Evita a viela com histórico de furtos na troca de turno",
          "Ponto de embarque seguro em frente ao comércio e drogaria 24h",
          "Cruzamento com faixa elevada e semáforo sonoro para pedestres"
        ),
        warningNotes = listOf("Apenas 3 min a mais que a rota curta, mas elimina 85% do risco de sinistro"),
        segments = listOf(
          RouteSegment(
            mode = CommuteMode.WALK_ONLY,
            title = "Caminhada Segura",
            instruction = "Siga pela Av. das Palmeiras (calçada larga e postes LED acesos)",
            distanceMeters = 450,
            durationMin = 6,
            safetyHighlights = "Iluminação excelente, fluxo ativo de pedestres",
            isHazardZone = false
          ),
          RouteSegment(
            mode = CommuteMode.TRANSIT_WALK,
            title = "Ônibus Expresso Linha 301 - Distrito Seguro",
            instruction = "Embarque no Ponto Farmácia 24h (Monitorado por câmeras)",
            distanceMeters = 3400,
            durationMin = 16,
            safetyHighlights = "Veículo climatizado com botão de pânico e GPS integrado",
            isHazardZone = false
          ),
          RouteSegment(
            mode = CommuteMode.WALK_ONLY,
            title = "Desembarque & Entrada na Empresa",
            instruction = "Desembarque na Portaria Central e acesse o terminal de pedestres",
            distanceMeters = 350,
            durationMin = 4,
            safetyHighlights = "Área interna empresarial com vigilância e calçada tátil",
            isHazardZone = false
          )
        ),
        pathPoints = listOf(
          com.example.model.MapPoint("p0", "Residência", "Início do trajeto seguro", 0.15f, 0.82f, -23.5505, -46.6333, com.example.model.PointType.ORIGIN),
          com.example.model.MapPoint("p1", "Av. das Palmeiras", "Via 100% iluminada com calçada ampla", 0.28f, 0.70f, -23.5475, -46.6305, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("p2", "Ponto Farmácia 24h", "Parada coberta, monitoramento e comércio aberto", 0.42f, 0.55f, -23.5435, -46.6270, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("p3", "Corredor Seguro de Ônibus", "Faixa exclusiva com velocidade controlada", 0.65f, 0.38f, -23.5385, -46.6225, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("p4", "Pólo Industrial Norte", "Portaria monitorada da Empresa Alpha", 0.85f, 0.18f, -23.5335, -46.6180, com.example.model.PointType.DESTINATION)
        ),
        hazardPoints = listOf(
          com.example.model.MapPoint("h1", "Viela Escura", "Desvio seguro contornou esta área sem luz", 0.38f, 0.80f, -23.5490, -46.6315, com.example.model.PointType.HAZARD),
          com.example.model.MapPoint("h2", "Cruzamento Crítico", "Semáforo sonoro protege este cruzamento", 0.56f, 0.62f, -23.5410, -46.6240, com.example.model.PointType.HAZARD)
        ),
        lightPoints = listOf(
          com.example.model.MapPoint("l1", "LED 01", "Poste LED ativo", 0.18f, 0.79f, -23.5498, -46.6325, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l2", "LED 02", "Poste LED ativo", 0.24f, 0.74f, -23.5485, -46.6315, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l3", "LED 03", "Poste LED ativo", 0.33f, 0.64f, -23.5458, -46.6290, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l4", "LED 04", "Poste LED ativo", 0.42f, 0.55f, -23.5435, -46.6270, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l5", "LED 05", "Poste LED ativo", 0.53f, 0.47f, -23.5410, -46.6248, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l6", "LED 06", "Poste LED ativo", 0.65f, 0.38f, -23.5385, -46.6225, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l7", "LED 07", "Poste LED ativo", 0.76f, 0.27f, -23.5358, -46.6200, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("l8", "LED 08", "Poste LED ativo", 0.83f, 0.20f, -23.5340, -46.6185, com.example.model.PointType.LED_LIGHT)
        ),
        accidentPoints = emptyList(), // Livre de acidentes e colisões ativas
        darkZonePoints = emptyList(), // 0 trechos escuros: 96% iluminada por LED
        steepPoints = listOf(
          com.example.model.MapPoint(
            id = "stp_safe1",
            label = "Rampa Suave Av. Palmeiras",
            description = "Aclive suave e contínuo de 3.2%, 100% acessível para cadeirantes e seguro para bicicletas e motos",
            xNorm = 0.30f,
            yNorm = 0.68f,
            lat = -23.5470,
            lng = -46.6300,
            type = com.example.model.PointType.STEEP_INCLINE,
            severity = "BAIXA",
            slopePercent = 3.2f,
            elevationMeters = 8,
            statusNote = "Declividade suave dentro da norma NBR 9050. Piso plano e antiderrapante."
          )
        ),
        maxInclinePercent = 3.2f,
        elevationGainMeters = 12,
        vehicleSafetyNotes = mapOf(
          "Carro" to "Vias arteriais duplicadas, semáforos inteligentes, sem registro de alagamento ou acidentes.",
          "Moto" to "Asfalto regular sem buracos ou ranhuras perigosas; excelente campo de visão noturna.",
          "Bicicleta" to "Ciclovia segregada com tachões refletivos, aclive suave de apenas 3.2% sem fadiga.",
          "Cadeirante" to "100% de conformidade com NBR 9050: calçadas largas, rampas em todas as esquinas e travessia assistida."
        )
      ),

      RouteOption(
        id = "route_fastest_traditional",
        title = "Rota Rápida Tradicional (Google/Waze)",
        subtitle = "Corta caminho por trecho escuro e cruzamento crítico de acidentes",
        durationMinutes = 23,
        distanceKm = 3.6,
        walkMinutes = 9,
        safetyScore = 58,
        illuminationScore = 42,
        publicSafetyScore = 48,
        trafficRiskScore = 45,
        accessibilityScore = 40,
        isRecommended = false,
        routeBadge = "ALTO RISCO DE SINISTRO",
        keyAdvantages = listOf(
          "3 minutos mais rápida no papel",
          "Trajeto linear curto"
        ),
        warningNotes = listOf(
          "🚨 Acidente ativo: Colisão na Av. Perimetral bloqueando faixa da direita",
          "🌑 400m de escuridão crítica na Viela Santa Clara (sem postes acesos)",
          "⛰️ Ladeira íngreme (+16.5%): Perigosa para motos/bikes e intransitável para cadeirantes"
        ),
        segments = listOf(
          RouteSegment(
            mode = CommuteMode.WALK_ONLY,
            title = "Caminhada por Viela de Pedestre",
            instruction = "Corte pela Viela Santa Clara (Passagem estreita sem iluminação)",
            distanceMeters = 700,
            durationMin = 9,
            safetyHighlights = "ALERTA: Trecho escuro e sem saída visual",
            isHazardZone = true
          ),
          RouteSegment(
            mode = CommuteMode.TRANSIT_WALK,
            title = "Ônibus Linha 110",
            instruction = "Embarque em ponto ermo na Rodovia",
            distanceMeters = 2600,
            durationMin = 12,
            safetyHighlights = "Ponto sem cobertura e sem iluminação",
            isHazardZone = true
          ),
          RouteSegment(
            mode = CommuteMode.WALK_ONLY,
            title = "Travessia da Rodovia Perimetral",
            instruction = "Atravesse 4 pistas em nível sem semáforo",
            distanceMeters = 300,
            durationMin = 2,
            safetyHighlights = "ALTO RISCO: 24,6% dos sinistros de trajeto ocorrem aqui",
            isHazardZone = true
          )
        ),
        pathPoints = listOf(
          com.example.model.MapPoint("fp0", "Residência", "Início do trajeto", 0.15f, 0.82f, -23.5505, -46.6333, com.example.model.PointType.ORIGIN),
          com.example.model.MapPoint("fp1", "Viela Santa Clara", "Trecho escuro de alto risco", 0.38f, 0.80f, -23.5490, -46.6315, com.example.model.PointType.HAZARD),
          com.example.model.MapPoint("fp2", "Ponto Ermo Rodovia", "Parada sem abrigo nem luz", 0.55f, 0.65f, -23.5410, -46.6240, com.example.model.PointType.HAZARD),
          com.example.model.MapPoint("fp3", "Cruzamento Perimetral", "Atropelamentos frequentes", 0.72f, 0.42f, -23.5370, -46.6210, com.example.model.PointType.HAZARD),
          com.example.model.MapPoint("fp4", "Pólo Industrial", "Destino", 0.85f, 0.18f, -23.5335, -46.6180, com.example.model.PointType.DESTINATION)
        ),
        hazardPoints = listOf(
          com.example.model.MapPoint("fh1", "Viela Santa Clara", "4 roubos registrados", 0.38f, 0.80f, -23.5490, -46.6315, com.example.model.PointType.HAZARD),
          com.example.model.MapPoint("fh2", "Ponto Ermo", "Sem iluminação noturna", 0.55f, 0.65f, -23.5410, -46.6240, com.example.model.PointType.HAZARD),
          com.example.model.MapPoint("fh3", "Travessia Rodovia", "Alto índice de sinistros MTE", 0.72f, 0.42f, -23.5370, -46.6210, com.example.model.PointType.HAZARD)
        ),
        lightPoints = listOf(
          com.example.model.MapPoint("fl1", "Poste Isolado", "Iluminação fraca", 0.18f, 0.81f, -23.5500, -46.6330, com.example.model.PointType.LED_LIGHT)
        ),
        accidentPoints = listOf(
          com.example.model.MapPoint(
            id = "acc_perimetral",
            label = "Colisão Cruzamento Perimetral",
            description = "Acidente recente com 2 veículos e 1 motocicleta. Faixa direita e acostamento obstruídos com lentidão.",
            xNorm = 0.72f,
            yNorm = 0.42f,
            lat = -23.5370,
            lng = -46.6210,
            type = com.example.model.PointType.ACCIDENT,
            severity = "ALTA",
            timeReported = "Há 18 min",
            vehiclesAffected = listOf("Carros", "Motos", "Ônibus"),
            statusNote = "Socorro no local. Risco de novos abalroamentos em virtude da pista molhada e falta de sinalização."
          )
        ),
        darkZonePoints = listOf(
          com.example.model.MapPoint(
            id = "dark_viela",
            label = "Trecho Escuro Viela Santa Clara",
            description = "400m contínuos sem iluminação pública funcional. Visibilidade nula após as 18h45.",
            xNorm = 0.38f,
            yNorm = 0.80f,
            lat = -23.5490,
            lng = -46.6315,
            type = com.example.model.PointType.DARK_ZONE,
            severity = "CRITICA",
            timeReported = "Noturno contínuo",
            vehiclesAffected = listOf("Carros", "Motos", "Bicicletas", "Cadeirantes"),
            statusNote = "Postes danificados por vandalismo. Risco extremo de queda para motos/bikes e emboscadas para pedestres."
          )
        ),
        steepPoints = listOf(
          com.example.model.MapPoint(
            id = "stp_viela",
            label = "Ladeira Íngreme da Viela",
            description = "Subida acentuada com declividade de +16.5% e paralelepípedos desnivelados",
            xNorm = 0.46f,
            yNorm = 0.73f,
            lat = -23.5450,
            lng = -46.6280,
            type = com.example.model.PointType.STEEP_INCLINE,
            severity = "ALTA",
            slopePercent = 16.5f,
            elevationMeters = 54,
            statusNote = "Declividade proibitiva para cadeirantes (limite NBR 9050 é 8.33%). Risco de derrapagem para motocicletas e bicicletas na chuva."
          )
        ),
        maxInclinePercent = 16.5f,
        elevationGainMeters = 54,
        vehicleSafetyNotes = mapOf(
          "Carro" to "Lentidão intensa decorrente de acidente na Av. Perimetral. Ladeira estreita com manobra difícil.",
          "Moto" to "PERIGO MÁXIMO: Pista molhada na ladeira de 16.5% com óleo e escuridão total na viela aumentam risco de queda em 400%.",
          "Bicicleta" to "NÃO RECOMENDADO: Subida extenuante (+16.5%) sem ciclovia e trecho sem luz com histórico recente de assaltos.",
          "Cadeirante" to "BARREIRA INTRANSPONÍVEL: Declividade de 16.5% dobra o limite aceitável da NBR 9050 (8.33%). Calçada esburacada sem rampa."
        )
      ),

      RouteOption(
        id = "route_accessible",
        title = "Rota Acessibilidade & Conforto",
        subtitle = "100% livre de barreiras, piso tátil e ônibus com elevador",
        durationMinutes = 29,
        distanceKm = 4.5,
        walkMinutes = 7,
        safetyScore = 91,
        illuminationScore = 92,
        publicSafetyScore = 90,
        trafficRiskScore = 94,
        accessibilityScore = 98,
        isRecommended = false,
        routeBadge = "ACESSIBILIDADE PCD 98%",
        keyAdvantages = listOf(
          "Calçadas rebaixadas e piso podotátil em todo o trajeto",
          "Veículos 100% acessíveis com piso baixo e plataforma",
          "Semáforos sonoros nos cruzamentos",
          "Rampas com inclinação suave regulamentar (NBR 9050)"
        ),
        warningNotes = listOf("Inclui 3 minutos extras para embarque prioritário seguro"),
        segments = listOf(
          RouteSegment(
            mode = CommuteMode.WALK_ONLY,
            title = "Caminhada Acessível",
            instruction = "Desloque-se pela calçada requalificada da Rua das Flores",
            distanceMeters = 500,
            durationMin = 7,
            safetyHighlights = "Rampas em todas as esquinas e piso sem degraus",
            isHazardZone = false
          ),
          RouteSegment(
            mode = CommuteMode.TRANSIT_WALK,
            title = "Linha Acessível 205 (Piso Baixo)",
            instruction = "Embarque no Terminal Acessível com plataforma nivelada",
            distanceMeters = 3600,
            durationMin = 18,
            safetyHighlights = "Espaço reservado para cadeirantes e aviso sonoro de paradas",
            isHazardZone = false
          ),
          RouteSegment(
            mode = CommuteMode.WALK_ONLY,
            title = "Acesso Pavimentado",
            instruction = "Entre pela rampa principal de pedestres da empresa",
            distanceMeters = 400,
            durationMin = 4,
            safetyHighlights = "Corrimão contínuo e piso antiderrapante",
            isHazardZone = false
          )
        ),
        pathPoints = listOf(
          com.example.model.MapPoint("ap0", "Residência", "Início acessível", 0.15f, 0.82f, -23.5505, -46.6333, com.example.model.PointType.ORIGIN),
          com.example.model.MapPoint("ap1", "Rua das Flores", "Calçada nivelada com piso podotátil", 0.22f, 0.68f, -23.5470, -46.6300, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("ap2", "Terminal Acessível", "Plataforma nivelada com elevador", 0.40f, 0.50f, -23.5430, -46.6260, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("ap3", "Corredor Acessível", "Ônibus piso baixo Linha 205", 0.62f, 0.35f, -23.5380, -46.6220, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("ap4", "Pólo Industrial", "Rampa de acesso NBR 9050", 0.85f, 0.18f, -23.5335, -46.6180, com.example.model.PointType.DESTINATION)
        ),
        hazardPoints = emptyList(),
        lightPoints = listOf(
          com.example.model.MapPoint("al1", "LED Especial", "Iluminação de alto contraste", 0.22f, 0.68f, -23.5470, -46.6300, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("al2", "LED Terminal", "Luminária 150W", 0.40f, 0.50f, -23.5430, -46.6260, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("al3", "LED Empresa", "Iluminação portaria", 0.85f, 0.18f, -23.5335, -46.6180, com.example.model.PointType.LED_LIGHT)
        ),
        accidentPoints = emptyList(),
        darkZonePoints = emptyList(),
        steepPoints = listOf(
          com.example.model.MapPoint(
            id = "stp_acc1",
            label = "Rampa Certificada NBR 9050",
            description = "Inclinação plana e regular de apenas 2.5%, perfeita para cadeirantes manuais e motorizados",
            xNorm = 0.25f,
            yNorm = 0.65f,
            lat = -23.5460,
            lng = -46.6290,
            type = com.example.model.PointType.STEEP_INCLINE,
            severity = "BAIXA",
            slopePercent = 2.5f,
            elevationMeters = 6,
            statusNote = "Calçadas reformadas com rampa suave de 2.5% e guia rebaixada."
          )
        ),
        maxInclinePercent = 2.5f,
        elevationGainMeters = 8,
        vehicleSafetyNotes = mapOf(
          "Carro" to "Vias urbanas calmas, tráfego pacificado e zonas de baixa velocidade (30 km/h).",
          "Moto" to "Asfalto novo e plano, sem lombadas irregulares ou buracos perigosos.",
          "Bicicleta" to "Trânsito calmo compartilhável, relevo suave e excelente visibilidade noturna.",
          "Cadeirante" to "EXCELÊNCIA TOTAL (98%): Inclinação máxima de 2.5% (muito abaixo do teto de 8.33% da NBR 9050). Sem degraus nem obstáculos."
        )
      ),

      RouteOption(
        id = "route_moto_bike",
        title = "Rota Ciclovia & Moto Segura",
        subtitle = "Ciclovia segregada, asfalto regular e desvio da colisão da Perimetral",
        durationMinutes = 20,
        distanceKm = 4.0,
        walkMinutes = 2,
        safetyScore = 92,
        illuminationScore = 94,
        publicSafetyScore = 91,
        trafficRiskScore = 95,
        accessibilityScore = 84,
        isRecommended = false,
        routeBadge = "ESPECIAL DUAS RODAS",
        keyAdvantages = listOf(
          "Ciclovia segregada por barreiras de concreto e iluminação contínua",
          "Desvia do engarrafamento do acidente da Av. Perimetral",
          "Relevo suave (max 4.1%) ideal para pedal e tração de moto em piso molhado",
          "Pontos de apoio com borracharia e lojas de conveniência monitoradas"
        ),
        warningNotes = listOf("Requer atenção nos cruzamentos de conversão à direita com veículos"),
        segments = listOf(
          RouteSegment(
            mode = CommuteMode.BIKE,
            title = "Ciclovia Corredor Norte",
            instruction = "Acesse a ciclovia protegida com tachões refletivos",
            distanceMeters = 3200,
            durationMin = 14,
            safetyHighlights = "Pista dedicada, sinalização horizontal termoplástica refletiva",
            isHazardZone = false
          ),
          RouteSegment(
            mode = CommuteMode.MOTORCYCLE,
            title = "Via Arterial Monitorada",
            instruction = "Siga pela faixa preferencial com radares educativos",
            distanceMeters = 800,
            durationMin = 6,
            safetyHighlights = "Velocidade controlada 50 km/h, sem buracos ou ranhuras",
            isHazardZone = false
          )
        ),
        pathPoints = listOf(
          com.example.model.MapPoint("mp0", "Residência", "Início", 0.15f, 0.82f, -23.5505, -46.6333, com.example.model.PointType.ORIGIN),
          com.example.model.MapPoint("mp1", "Ciclovia Eixo Verde", "Pista exclusiva", 0.32f, 0.60f, -23.5440, -46.6280, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("mp2", "Ponto de Apoio Ciclista/Moto", "Conveniência e calibragem", 0.58f, 0.40f, -23.5390, -46.6230, com.example.model.PointType.SAFE_STOP),
          com.example.model.MapPoint("mp3", "Pólo Industrial", "Destino", 0.85f, 0.18f, -23.5335, -46.6180, com.example.model.PointType.DESTINATION)
        ),
        hazardPoints = emptyList(),
        lightPoints = listOf(
          com.example.model.MapPoint("ml1", "LED Ciclovia 1", "LED linear focado", 0.25f, 0.70f, -23.5460, -46.6300, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("ml2", "LED Ciclovia 2", "LED linear focado", 0.45f, 0.50f, -23.5420, -46.6250, com.example.model.PointType.LED_LIGHT),
          com.example.model.MapPoint("ml3", "LED Ciclovia 3", "LED linear focado", 0.70f, 0.30f, -23.5360, -46.6200, com.example.model.PointType.LED_LIGHT)
        ),
        accidentPoints = emptyList(),
        darkZonePoints = emptyList(),
        steepPoints = listOf(
          com.example.model.MapPoint(
            id = "stp_mb",
            label = "Aclive Gradual Eixo Verde",
            description = "Subida gradual de 4.1% bem pavimentada",
            xNorm = 0.40f,
            yNorm = 0.55f,
            lat = -23.5430,
            lng = -46.6260,
            type = com.example.model.PointType.STEEP_INCLINE,
            severity = "BAIXA",
            slopePercent = 4.1f,
            elevationMeters = 16,
            statusNote = "Aclive constante sem sobressaltos ou desníveis abruptos."
          )
        ),
        maxInclinePercent = 4.1f,
        elevationGainMeters = 16,
        vehicleSafetyNotes = mapOf(
          "Carro" to "Rota paralela desobstruída com semáforos em onda verde.",
          "Moto" to "Ótimo trajeto: contorna o acidente da Perimetral e mantém asfalto limpo com boa aderência.",
          "Bicicleta" to "Melhor trajeto para ciclistas: 100% ciclovia protegida, sem disputa de espaço com caminhões.",
          "Cadeirante" to "Acessível: Ciclovia compartilhada de uso misto nos cruzamentos com rampa suave (4.1%)."
        )
      )
    )
  }

  private fun getPcdRoutes(): List<RouteOption> {
    val defaultWorker = getDefaultWorkerRoutes()
    val safePcd = defaultWorker[2].copy(
      id = "route_pcd_accessible",
      title = "Rota 100% Acessível NBR 9050 (Recomendada PCD)",
      subtitle = "Zero degraus, guias rebaixadas, piso podotátil e ônibus com elevador",
      accessibilityScore = 99,
      safetyScore = 96,
      isRecommended = true,
      routeBadge = "♿ 100% ACESSÍVEL NBR 9050",
      keyAdvantages = listOf(
        "100% de conformidade NBR 9050: calçadas táteis e sem desníveis",
        "Guias rebaixadas com inclinação de 2.5% em todas as esquinas e travessias",
        "Ônibus Linha 205 com piso baixo, plataforma elevatória e área reservada",
        "Semáforos sonoros sincronizados com aviso vibratório nos cruzamentos"
      ),
      warningNotes = listOf("Inclui 3 minutos extras para embarque e fixação prioritária segura")
    )
    val metroPcd = defaultWorker[2].copy(
      id = "route_pcd_metro",
      title = "Rota Acessível Metrô / BRT Nivelado",
      subtitle = "Estações com elevadores operacionais e passarelas com esteiras",
      accessibilityScore = 96,
      safetyScore = 93,
      isRecommended = false,
      routeBadge = "ACESSIBILIDADE METRÔ 96%"
    )
    val traditionalWarning = defaultWorker[1].copy(
      title = "Rota Rápida Tradicional (Inacessível PCD)",
      subtitle = "Barreiras graves: ladeira de 16.5%, sem rampa e calçadas quebradas",
      accessibilityScore = 24,
      safetyScore = 40,
      routeBadge = "BARREIRA INTRANSPONÍVEL (24%)",
      warningNotes = listOf(
        "🚨 Declividade de +16.5%: O dobro do limite aceito pela NBR 9050 (8.33%)",
        "🚨 Viela Santa Clara possui degraus de 18cm e calçadas esburacadas sem rampa",
        "🚨 Ponto de ônibus na rodovia não dispõe de plataforma acessível"
      )
    )
    return listOf(safePcd, metroPcd, traditionalWarning)
  }

  private fun getElderlyRoutes(): List<RouteOption> {
    val defaultWorker = getDefaultWorkerRoutes()
    val safeElderly = defaultWorker[0].copy(
      id = "route_elderly_safe",
      title = "Rota Idoso Seguro & Conforto (Recomendada)",
      subtitle = "Calçadas planas, tempo semafórico estendido e paradas com bancos",
      safetyScore = 97,
      accessibilityScore = 97,
      isRecommended = true,
      routeBadge = "🧓 CONFORTO & RITMO SEGURO",
      keyAdvantages = listOf(
        "Calçadas 100% regulares e niveladas, sem buracos ou raízes expostas",
        "Tempo semafórico estendido para ritmo calmo de caminhada (0.8 m/s)",
        "Paradas de ônibus cobertas com bancos confortáveis a cada 300m",
        "Aclive plano e suave (máximo de 2.0%) sem desgaste cardiovascular",
        "Comércio aberto e farmácias 24h ao longo de todo o percurso"
      ),
      warningNotes = listOf("Velocidade calculada para o ritmo calmo e protegido da terceira idade")
    )
    val directBus = defaultWorker[2].copy(
      id = "route_elderly_direct",
      title = "Rota Linha Direta com Assento Prioritário",
      subtitle = "Menos caminhada, abrigo coberto com assento e parada em frente",
      safetyScore = 94,
      accessibilityScore = 94,
      isRecommended = false,
      routeBadge = "ASSENTO PRIORITÁRIO GARANTIDO"
    )
    val traditionalWarning = defaultWorker[1].copy(
      title = "Rota Rápida Tradicional (Alto Risco para Idosos)",
      subtitle = "Calçadas esburacadas, ladeira íngreme e travessia perigosa",
      safetyScore = 48,
      routeBadge = "ALTO RISCO DE QUEDA (48 pts)",
      warningNotes = listOf(
        "🚨 Alto risco de quedas: paralelepípedos soltos na Viela Santa Clara",
        "🚨 Ladeira íngreme de +16.5% provoca esforço físico perigoso",
        "🚨 Travessia sem semáforo ou tempo suficiente para pedestres da terceira idade"
      )
    )
    return listOf(safeElderly, directBus, traditionalWarning)
  }

  private fun getChildrenRoutes(): List<RouteOption> {
    val defaultWorker = getDefaultWorkerRoutes()
    val schoolSafe = defaultWorker[0].copy(
      id = "route_children_school",
      title = "Rota Caminho Escolar Protegido (Recomendada)",
      subtitle = "Zonas 30 km/h, faixas elevadas e trajeto afastado de veículos pesados",
      safetyScore = 98,
      trafficRiskScore = 98,
      isRecommended = true,
      routeBadge = "🧒 ZONA ESCOLAR 30 KM/H",
      keyAdvantages = listOf(
        "Vias pacificadas com limite de velocidade de 30 km/h e radares pedagógicos",
        "Calçadas largas protegidas por gradis metálicos longe de caminhões",
        "Faixas de travessia elevadas de alta visibilidade com agentes escolares",
        "Trajeto por praça arborizada e sem cruzamentos perigosos de tráfego rápido",
        "Câmeras do programa Escola Segura conectadas à Guarda Municipal"
      ),
      warningNotes = listOf("Monitoramento reforçado nos horários de entrada e saída das aulas")
    )
    val parkPath = defaultWorker[2].copy(
      id = "route_children_park",
      title = "Rota Parque & Calçadão Familiar",
      subtitle = "Passeio por área verde 100% segregada de veículos motorizados",
      safetyScore = 95,
      trafficRiskScore = 99,
      isRecommended = false,
      routeBadge = "100% PEDESTRE & FAMÍLIA"
    )
    val traditionalWarning = defaultWorker[1].copy(
      title = "Rota Rápida Tradicional (Perigosa para Crianças)",
      subtitle = "Tráfego pesado de carretas, sem calçada e cruzamento de alto risco",
      safetyScore = 42,
      routeBadge = "ALTO RISCO DE ATROPELAMENTO",
      warningNotes = listOf(
        "🚨 Proibitivo para crianças: travessia de rodovia sem semáforo com carretas a 80 km/h",
        "🚨 Calçada inexistente em 300m, forçando pedestres no acostamento"
      )
    )
    return listOf(schoolSafe, parkPath, traditionalWarning)
  }

  private fun getYouthRoutes(): List<RouteOption> {
    val defaultWorker = getDefaultWorkerRoutes()
    val youthCampus = defaultWorker[0].copy(
      id = "route_youth_campus",
      title = "Rota Universitária & Conectividade Segura (Recomendada)",
      subtitle = "Iluminação LED 97%, pontos movimentados e ciclovia integrada",
      safetyScore = 96,
      illuminationScore = 97,
      isRecommended = true,
      routeBadge = "🎓 CORREDOR JOVEM ILUMINADO",
      keyAdvantages = listOf(
        "Corredor principal com 97% de iluminação LED de alta potência",
        "Pontos de ônibus movimentados com totens de emergência e câmeras",
        "Ciclovia segregada com tachões refletivos conectada a terminais",
        "Fluxo contínuo de estudantes e comércio noturno aberto"
      ),
      warningNotes = listOf("Excelente opção para retorno seguro após as aulas noturnas")
    )
    val bikeRoute = defaultWorker[3].copy(
      title = "Rota Ciclofaixa Expresso Jovem",
      subtitle = "Pista rápida segregada para bike e patinete elétrico",
      routeBadge = "CICLOVIA EXPRESSA JOVEM"
    )
    val traditionalWarning = defaultWorker[1].copy(
      title = "Rota Rápida Tradicional (Alerta Viela Escura)",
      subtitle = "Trecho com 4 roubos registrados a estudantes no período noturno",
      routeBadge = "ALERTA ROUBO NOTURNO",
      warningNotes = listOf(
        "🚨 400m de escuridão total na Viela Santa Clara com histórico de assaltos a estudantes",
        "🚨 Ponto de ônibus sem iluminação nem sinal de celular"
      )
    )
    return listOf(youthCampus, bikeRoute, traditionalWarning)
  }

  fun getB2GInsights(): List<B2GZoneInsight> {
    return listOf(
      B2GZoneInsight(
        id = "zone_periferia_norte",
        zoneName = "Eixo Industrial Periférico Norte",
        neighborhood = "Jardim Esperança / Distrito Industrial",
        riskLevel = "CRITICO",
        primaryDeficit = "Falta de ônibus após 21h e escuridão em 1.2km de trajeto",
        affectedWorkersDaily = 4250,
        recommendedAction = "Implantar 18 postes LED e reforçar 2 partidas da Linha 301 às 22h10 e 22h40",
        estimatedLitigationReduction = "Redução estimada de 41% em sinistros e afastamentos",
        status = "PRIORIDADE ALTA"
      ),
      B2GZoneInsight(
        id = "zone_perimetral_sul",
        zoneName = "Cruzamento Perimetral com Rodovia SP-102",
        neighborhood = "Vila Operária",
        riskLevel = "ALTO",
        primaryDeficit = "Travessia de pedestres em nível sem passarela ou semáforo",
        affectedWorkersDaily = 6800,
        recommendedAction = "Instalação de semáforo com botoeira e faixa elevada para pedestres",
        estimatedLitigationReduction = "Prevenção de 15 a 20 atropelamentos anuais (B2G/SESMT)",
        status = "INTERVENCAO SUGERIDA"
      ),
      B2GZoneInsight(
        id = "zone_terminal_oeste",
        zoneName = "Entorno do Terminal Urbano Oeste",
        neighborhood = "Parque São Jorge",
        riskLevel = "MODERADO",
        primaryDeficit = "Calçadas estreitas esburacadas e pontos de parada sem abrigo",
        affectedWorkersDaily = 3100,
        recommendedAction = "Alargamento de calçadas, piso tátil e instalação de abrigo coberto",
        estimatedLitigationReduction = "Adequação de acessibilidade NBR 9050 e conforto do usuário",
        status = "EM ANALISE"
      )
    )
  }
}
