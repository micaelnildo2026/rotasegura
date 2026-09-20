package com.example.data

import com.example.model.MobilityContext

data class KnowledgeItem(
  val id: String,
  val category: String,
  val categoryIcon: String,
  val question: String,
  val answer: String,
  val keywords: List<String>
)

data class KnowledgeMatch(
  val item: KnowledgeItem,
  val score: Int,
  val relatedQuestions: List<String> = emptyList()
)

object LocalAiKnowledgeBase {

  val items: List<KnowledgeItem> = listOf(
    // =========================================================================
    // 1. SINISTROS E ACIDENTES DE TRAJETO (MTE / PREVIDÊNCIA / SESMT) - 12
    // =========================================================================
    KnowledgeItem(
      id = "sin_1",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "O que é considerado acidente de trajeto pela legislação brasileira?",
      answer = "Segundo o Artigo 21, inciso IV, alínea 'd' da Lei 8.213/1991, acidente de trajeto é aquele sofrido pelo segurado no percurso da sua residência para o local de trabalho ou deste para aquela, qualquer que seja o meio de locomoção utilizado, inclusive veículo de propriedade do trabalhador.",
      keywords = listOf("acidente de trajeto", "definicao", "lei 8213", "artigo 21", "percurso", "casa trabalho", "o que e")
    ),
    KnowledgeItem(
      id = "sin_2",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "O acidente de trajeto ainda é equiparado a acidente de trabalho?",
      answer = "Sim. Embora a Medida Provisória 905/2019 tenha tentado revogar essa equiparação temporariamente, a MP perdeu a vigência em abril de 2020. Portanto, o acidente de trajeto continua plenamente equiparado a acidente de trabalho para todos os efeitos previdenciários e legais.",
      keywords = listOf("equiparado", "acidente de trabalho", "mp 905", "vigencia", "continua", "lei atual", "equiparacao")
    ),
    KnowledgeItem(
      id = "sin_3",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Como abrir a CAT (Comunicação de Acidente de Trabalho)?",
      answer = "A CAT pode ser cadastrada eletronicamente pelo portal Meu INSS ou pelo aplicativo oficial da Previdência Social. É necessário informar os dados da empresa, do trabalhador, o local exato do sinistro, o boletim de ocorrência (se houver) e o atestado médico com o código CID correspondente.",
      keywords = listOf("abrir cat", "comunicacao de acidente", "inss", "emitir cat", "passo a passo", "meu inss")
    ),
    KnowledgeItem(
      id = "sin_4",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Qual o prazo legal que a empresa tem para emitir a CAT?",
      answer = "Pelo Art. 22 da Lei 8.213/1991, a empresa deve emitir a CAT até o primeiro dia útil seguinte ao da ocorrência. Em caso de morte do trabalhador, a comunicação à autoridade competente deve ser imediata.",
      keywords = listOf("prazo cat", "quando emitir", "primeiro dia util", "lei", "tempo para abrir", "prazo legal")
    ),
    KnowledgeItem(
      id = "sin_5",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "O trabalhador tem direito à estabilidade no emprego após acidente de trajeto?",
      answer = "Sim. Caso o trabalhador fique afastado por mais de 15 dias e receba o benefício previdenciário B91 (auxílio por incapacidade temporária acidentário), ele tem garantia de estabilidade provisória no emprego por no mínimo 12 meses após a alta médica (Art. 118 da Lei 8.213/91 e Súmula 378 do TST).",
      keywords = listOf("estabilidade", "12 meses", "demissao", "b91", "afastamento", "garantia de emprego", "sumula 378")
    ),
    KnowledgeItem(
      id = "sin_6",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Qual a porcentagem de acidentes de trabalho que ocorrem no trajeto no Brasil?",
      answer = "Pesquisas da Revista de Saúde Pública da USP e registros do Ministério do Trabalho e Emprego (MTE) apontam que os acidentes de trajeto representam cerca de 24,6% a 28% de todos os acidentes de trabalho no Brasil, com custo humano e previdenciário estimado em bilhões anualmente.",
      keywords = listOf("porcentagem", "estatistica", "mte", "24,6", "dados", "saude publica", "taxa de sinistro")
    ),
    KnowledgeItem(
      id = "sin_7",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "O que fazer imediatamente após um acidente a caminho do trabalho?",
      answer = "1. Garanta a segurança física e acione socorro (SAMU 192 ou Bombeiros 193). 2. Registre Boletim de Ocorrência (B.O.). 3. Busque atendimento médico com prontuário detalhado e CID. 4. Notifique imediatamente o RH/SESMT da empresa com comprovantes de horário e local para abertura tempestiva da CAT.",
      keywords = listOf("o que fazer", "imediatamente apos", "socorro", "boletim de ocorrencia", "primeiros passos", "procedimento")
    ),
    KnowledgeItem(
      id = "sin_8",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Se o trabalhador desviar da rota habitual e sofrer acidente, ainda é coberto?",
      answer = "A jurisprudência do TST estabelece que desvios razoáveis para necessidades cotidianas (ex: deixar filhos na escola, abastecer veículo ou comprar remédio) mantêm a cobertura como acidente de trajeto. Desvios prolongados por motivos puramente recreativos podem descaracterizar o nexo.",
      keywords = listOf("desvio de rota", "rota habitual", "desviar caminho", "jurisprudencia", "tst", "cobertura", "filho escola")
    ),
    KnowledgeItem(
      id = "sin_9",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Quem pode emitir a CAT se a empresa se recusar?",
      answer = "Se a empresa não emitir a CAT no prazo legal, ela pode ser formalizada pelo próprio trabalhador acidentado, seus dependentes, entidade sindical representativa da categoria, médico assistente ou qualquer autoridade pública (Art. 22, § 2º da Lei 8.213/91).",
      keywords = listOf("empresa recusou", "quem pode emitir", "sindicato", "medico", "recusa de cat", "trabalhador emitir")
    ),
    KnowledgeItem(
      id = "sin_10",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Quais documentos são exigidos para comprovar acidente de trajeto?",
      answer = "Recomenda-se reunir: 1. Atestado médico com diagnóstico e CID; 2. Boletim de Ocorrência policial; 3. Espelho de ponto ou escala de trabalho do dia; 4. Registro de GPS ou trajeto do app RotaSegura; 5. Dados de eventuais testemunhas presentes no local.",
      keywords = listOf("documentos necessarios", "comprovar", "provas", "boletim", "cid", "atestado", "espelho de ponto")
    ),
    KnowledgeItem(
      id = "sin_11",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "Como o SESMT e a CIPA podem utilizar o RotaSegura na SIPAT?",
      answer = "O RotaSegura fornece mapas de calor de sinistros e telemetria de pontos escuros da região da empresa. Na SIPAT, os dados podem orientar treinamentos de direção defensiva, campanhas de iluminação e auditorias de rotas seguras para os turnos de entrada e saída.",
      keywords = listOf("sipat", "sesmt", "cipa", "treinamento", "campanha", "prevencao na empresa", "recursos humanos")
    ),
    KnowledgeItem(
      id = "sin_12",
      category = "Sinistros & Legislação",
      categoryIcon = "🚨",
      question = "A empresa é obrigada a pagar o FGTS durante o afastamento por acidente de trajeto?",
      answer = "Sim. Conforme o Artigo 15, § 5º da Lei 8.036/1990, o depósito do FGTS na conta vinculada do trabalhador é obrigatório durante todo o período de afastamento por motivo de acidente de trabalho ou trajeto.",
      keywords = listOf("fgts", "afastamento", "pagamento fgts", "deposito", "lei 8036", "durante auxilio")
    ),

    // =========================================================================
    // 2. ACESSIBILIDADE, PCD E CADEIRANTES (NBR 9050 / MOBILIDADE) - 12
    // =========================================================================
    KnowledgeItem(
      id = "pcd_1",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Qual é a inclinação máxima de rampa permitida pela norma NBR 9050?",
      answer = "A norma ABNT NBR 9050 estipula inclinação máxima recomendada de 8,33% (proporção 1:12) para desníveis gerais. Em casos excepcionais de reformas onde seja impossível aplicar 8,33%, admite-se até 10% para desníveis de no máximo 20cm e 12,5% para desníveis de até 7,5cm.",
      keywords = listOf("nbr 9050", "inclinacao maxima", "rampa", "8.33", "norma abnt", "graus", "proporcao")
    ),
    KnowledgeItem(
      id = "pcd_2",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Como o aplicativo avalia se uma rota é acessível para cadeirantes?",
      answer = "O RotaSegura analisa 4 pilares: 1. Declividade máxima contínua (alerta vermelho se ultrapassar 8,33%); 2. Existência de guias rebaixadas em todas as esquinas; 3. Conservação do pavimento da calçada (ausência de buracos ou degraus); 4. Iluminação noturna adequada para visualizar desníveis.",
      keywords = listOf("rota acessivel", "como avalia", "criterios pcd", "cadeirante app", "score acessibilidade")
    ),
    KnowledgeItem(
      id = "pcd_3",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Quais os riscos de declividades acima de 8,33% para cadeiras de rodas manuais?",
      answer = "Ladeiras acima de 8,33% provocam sobrecarga osteomuscular intensa nos membros superiores, risco iminente de tombamento para trás (capotamento) na subida e perda de atrito nos aros de propulsão na descida, podendo levar a colisões graves com o tráfego.",
      keywords = listOf("riscos ladeira", "tombamento", "subida perigosa", "esforco", "perigo rampa", "cadeira manual")
    ),
    KnowledgeItem(
      id = "pcd_4",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Qual a largura livre mínima exigida para calçadas acessíveis?",
      answer = "A NBR 9050 exige uma faixa livre e contínua de circulação com largura mínima de 1,20m, sendo recomendável 1,50m para permitir manobras confortáveis e cruzamento de duas cadeiras de rodas sem colisão.",
      keywords = listOf("largura calcada", "faixa livre", "1.20m", "1.50m", "espaco minimo", "nbr 9050 calcadas")
    ),
    KnowledgeItem(
      id = "pcd_5",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Piso tátil direcional e piso tátil de alerta: qual a diferença?",
      answer = "O piso tátil direcional (linhas longitudinais) orienta a direção contínua da caminhada de pessoas com deficiência visual. O piso tátil de alerta (relevo esférico/moeda) sinaliza perigos iminentes, rebaixamentos, inícios de rampas, escadas e portas de elevadores.",
      keywords = listOf("piso tatil", "direcional", "alerta", "diferenca piso", "deficiencia visual", "cegos")
    ),
    KnowledgeItem(
      id = "pcd_6",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "O que fazer ao encontrar calçadas esburacadas ou sem guia rebaixada?",
      answer = "Pelo RotaSegura, você pode emitir um Alerta Colaborativo em tempo real para avisar outros trabalhadores e sincronizar o ponto com o Painel de Governança B2G municipal. Também é possível registrar denúncia via central 156 da Prefeitura.",
      keywords = listOf("calcada quebrada", "sem guia rebaixada", "denunciar", "alerta colaborativo", "reclamar 156")
    ),
    KnowledgeItem(
      id = "pcd_7",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Cadeirantes têm direito a elevador operante em 100% dos ônibus urbanos?",
      answer = "Sim. O Decreto Federal 5.296/2004 e a Lei Brasileira de Inclusão (Lei 13.146/2015) exigem acessibilidade universal no transporte público. O elevador deve estar em pleno funcionamento. Em caso de defeito recorrente, denuncie à SPTrans/órgão gestor municipal.",
      keywords = listOf("elevador onibus", "transporte pcd", "decreto 5296", "lei brasileira de inclusao", "direito cadeirante")
    ),
    KnowledgeItem(
      id = "pcd_8",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Como funciona o cálculo de esforço em rotas com aclive no app?",
      answer = "O RotaSegura cruza modelos digitais de elevação do relevo. Em trajetos com mais de 5% de inclinação em mais de 100 metros contínuos, o app alerta fadiga severa e sugere rotas alternativas em vias de nível ou linhas de ônibus adaptadas.",
      keywords = listOf("calculo esforco", "fadiga", "relevo digital", "subida cansativa", "algoritmo pcd")
    ),
    KnowledgeItem(
      id = "pcd_9",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Quais cuidados especiais o cadeirante deve adotar em dias chuvosos?",
      answer = "Em pista molhada, o atrito nos aros manuais diminui drasticamente, aumentando a chance de derrapagem. Evite calçadas de pedras portuguesas ou pisos cerâmicos lisos, utilize luvas antiderrapantes e prefira rotas com marquises e asfalto com drenagem rápida.",
      keywords = listOf("cadeirante chuva", "pista molhada pcd", "aro escorregadio", "luvas", "cuidados chuva cadeirante")
    ),
    KnowledgeItem(
      id = "pcd_10",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Qual a importância de semáforos sonoros para travessia de pedestres?",
      answer = "Semáforos com sinalização sonora e táctil avisam de forma auditiva quando o tempo de travessia está seguro para pedestres com deficiência visual, além de alertar o ritmo do fechamento do sinal, garantindo cruzamento seguro em avenidas largas.",
      keywords = listOf("semaforo sonoro", "travessia segura", "sinal auditivo", "deficientes visuais", "cruzamento")
    ),
    KnowledgeItem(
      id = "pcd_11",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Como solicitar adaptação e rebaixamento de guias em pontos de ônibus?",
      answer = "Solicitações podem ser abertas via Ouvidoria Municipal, aplicativo 156 ou pelo canal de demandas B2G do RotaSegura. A Lei 13.146/2015 tipifica a negligência como infração à garantia de acessibilidade urbana.",
      keywords = listOf("solicitar rebaixamento", "ponto acessivel", "guia rebaixada prefeitura", "ouvidoria 156")
    ),
    KnowledgeItem(
      id = "pcd_12",
      category = "Acessibilidade & PCD",
      categoryIcon = "♿",
      question = "Como a iluminação pública noturna afeta diretamente usuários de cadeira de rodas?",
      answer = "Em trechos escuros, desníveis de 2cm, rachaduras, tampas de bueiro desniveladas e poças d'água ficam invisíveis, elevando o risco de travamento das rodinhas dianteiras (castors) e queda frontal violenta do usuário.",
      keywords = listOf("iluminacao cadeirante", "risco noturno pcd", "queda cadeira", "tampa bueiro", "castors travamento")
    ),

    // =========================================================================
    // 3. CICLISTAS E MOBILIDADE ATIVA (BICICLETAS) - 12
    // =========================================================================
    KnowledgeItem(
      id = "cic_1",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Qual é a distância lateral mínima que veículos devem manter ao ultrapassar ciclistas?",
      answer = "O Artigo 201 do Código de Trânsito Brasileiro (CTB) estabelece que o condutor deve manter a distância lateral mínima de 1,50 metro ao ultrapassar ciclistas. O desrespeito é infração média com penalidade de multa.",
      keywords = listOf("distancia lateral", "1.5m", "artigo 201", "ctb ultrapassagem", "metro e meio ciclista")
    ),
    KnowledgeItem(
      id = "cic_2",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Qual a diferença entre ciclovia, ciclofaixa e ciclorrota?",
      answer = "• Ciclovia: Espaço segregado fisicamente do tráfego motorizado por meio-fio, gradil ou mureta. • Ciclofaixa: Faixa exclusiva delimitada no mesmo nível da pista por pintura e tachões. • Ciclorrota: Vias compartilhadas sinalizadas com velocidade reduzida que conectam rotas ciclísticas.",
      keywords = listOf("ciclovia", "ciclofaixa", "ciclorrota", "diferenca ciclovia", "segregada", "faixa exclusiva")
    ),
    KnowledgeItem(
      id = "cic_3",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "O uso de capacete é obrigatório para ciclistas urbanos no Brasil?",
      answer = "Pelo CTB, o capacete não é item de uso obrigatório por lei federal para bicicletas convencionais, mas é fortemente recomendado por especialistas. Em caso de colisão ou queda, o capacete reduz em até 85% o risco de lesões cranioencefálicas graves.",
      keywords = listOf("capacete ciclista", "obrigatorio capacete", "lei bike", "tce", "seguranca ciclista")
    ),
    KnowledgeItem(
      id = "cic_4",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Quais equipamentos são legalmente obrigatórios em bicicletas segundo o CTB?",
      answer = "Conforme o Artigo 105 do CTB e Resolução CONTRAN 993/2023, são obrigatórios: campainha (buzina), espelho retrovisor do lado esquerdo e sinalização reflexiva dianteira (branca), traseira (vermelha), lateral e nos pedais.",
      keywords = listOf("equipamentos obrigatorios bike", "artigo 105 ctb", "retrovisor", "campainha", "refletivo")
    ),
    KnowledgeItem(
      id = "cic_5",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Ciclista pode pedalar em calçadas destinadas a pedestres?",
      answer = "Não, via de regra. O trânsito de bicicletas em calçadas só é permitido quando expressamente autorizado e sinalizado pela autoridade de trânsito local (Art. 59 do CTB). Na calçada comum, o ciclista deve desmontar e empurrar a bicicleta.",
      keywords = listOf("bicicleta na calcada", "pode pedalar calcada", "artigo 59", "desmontar bike", "regras ciclista")
    ),
    KnowledgeItem(
      id = "cic_6",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Como pedalar com segurança em trechos com subida íngreme?",
      answer = "Reduza a marcha com antecedência antes do início do aclive, mantenha cadência de pedalada constante em torno de 70 a 80 RPM, apoie o peso ligeiramente à frente sobre o guidão para evitar que a roda empine e não force os joelhos.",
      keywords = listOf("subir ladeira bike", "marcha correta", "subida ingreme bike", "cadencia", "pedalar ladeira")
    ),
    KnowledgeItem(
      id = "cic_7",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "O que fazer ao se deparar com trecho escuro na rota de bicicleta?",
      answer = "Acione farol dianteiro de alta potência (mínimo 300 lumens), reduza a velocidade pela metade para antecipar buracos, evite usar fones de ouvido para escutar o tráfego e mantenha jaqueta ou colete com elementos refletivos visíveis a 150 metros.",
      keywords = listOf("pedalar no escuro", "bike a noite", "farol bicicleta", "iluminacao bike", "colete refletivo")
    ),
    KnowledgeItem(
      id = "cic_8",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Como evitar o acidente de 'dooring' (abertura repentina de portas de carros)?",
      answer = "Mantenha sempre uma distância lateral segura de pelo menos 1 metro de veículos estacionados, reduza a velocidade em corredores com carros parados e observe se há motoristas dentro dos veículos pelos vidros ou espelhos.",
      keywords = listOf("dooring", "porta de carro", "abrir porta", "colisao porta", "distancia carros estacionados")
    ),
    KnowledgeItem(
      id = "cic_9",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Quais os cuidados com pista molhada e óleo no asfalto para ciclistas?",
      answer = "A água misturada com poeira e óleo reduz o coeficiente de atrito drasticamente. Reduza a pressão dos pneus ligeiramente para aumentar a área de contato, evite frear na curva (freie sempre em linha reta) e nunca passe sobre faixas de tinta molhadas.",
      keywords = listOf("chuva bicicleta", "pista molhada bike", "oleo asfalto", "derrapagem", "tinta faixa escorregadia")
    ),
    KnowledgeItem(
      id = "cic_10",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Como prevenir assaltos a ciclistas no retorno noturno do trabalho?",
      answer = "Escolha a 'Rota Segura IA' que prioriza avenidas monitoradas, evite ciclovias desertas com passagens sob viadutos sem iluminação, cadastre trajetos em grupo com colegas de turno e utilize suportes de celular fixos sem ostentação.",
      keywords = listOf("assalto ciclista", "roubo bike", "seguranca noturna bike", "trajeto seguro ciclista")
    ),
    KnowledgeItem(
      id = "cic_11",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "É permitido transportar bicicleta no metrô, trem ou ônibus?",
      answer = "Na maioria das capitais (como São Paulo e Rio), o embarque de bicicletas no metrô e trens urbanos é liberado em horários específicos: de segunda a sexta após as 20h30 e aos sábados, domingos e feriados o dia todo.",
      keywords = listOf("bicicleta metro", "bike no trem", "integracao bike transporte", "embarque bicicleta")
    ),
    KnowledgeItem(
      id = "cic_12",
      category = "Ciclistas & Bicicletas",
      categoryIcon = "🚲",
      question = "Como reportar ciclofaixas com pavimento danificado ou invadidas por carros?",
      answer = "Utilize o botão de 'Reportar Incidente' no RotaSegura com categoria 'Infraestrutura Cicloviária'. O aplicativo geo-referencia a coordenada e envia diretamente para o módulo de fiscalização pública B2G da cidade.",
      keywords = listOf("reportar ciclofaixa", "carro na ciclovia", "buraco ciclofaixa", "denuncia mobilidade")
    ),

    // =========================================================================
    // 4. MOTOCICLISTAS (SEGURANÇA EM DUAS RODAS) - 12
    // =========================================================================
    KnowledgeItem(
      id = "mot_1",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Quais as principais causas de sinistros graves com motociclistas a trabalho?",
      answer = "As principais causas são: excesso de velocidade em corredores viários, pontos cegos de caminhões e ônibus, conversões à esquerda imprevistas de automóveis, piso escorregadio em trechos escuros e linhas cortantes (cerol/linha chilena).",
      keywords = listOf("causa acidente moto", "motociclista sinistro", "ponto cego caminhao", "corredor moto")
    ),
    KnowledgeItem(
      id = "mot_2",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "O que diz o CTB sobre trafegar no corredor entre as faixas de veículos?",
      answer = "O CTB não proíbe expressamente o tráfego no corredor (o veto original ao Art. 56 foi mantido), desde que feito em velocidade compatível com a segurança quando o trânsito estiver lento ou parado, mantendo farol aceso e atenção redobrada.",
      keywords = listOf("corredor de moto", "andar no corredor", "artigo 56 ctb", "transito moto corredor", "legalidade")
    ),
    KnowledgeItem(
      id = "mot_3",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "A antena corta-pipa é obrigatória para motociclistas?",
      answer = "Sim. Pela Resolução CONTRAN 356 e Lei Federal 12.009/2009, o dispositivo aparador de linha (antena corta-pipa) e o protetor de pernas/motor (mata-cachorro) são itens de segurança de porte obrigatório para motofretistas e mototaxistas.",
      keywords = listOf("antena corta pipa", "cerol", "resolucao 356", "mata cachorro", "obrigatorio moto")
    ),
    KnowledgeItem(
      id = "mot_4",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Quais os equipamentos de proteção individual (EPIs) essenciais para motos?",
      answer = "1. Capacete com viseira transparente e selo INMETRO; 2. Jaqueta resistente com proteções articuladas nos ombros e cotovelos; 3. Luvas com reforço nas palmas; 4. Calça resistente e botas de cano médio com sola antiderrapante; 5. Colete com faixas refletivas.",
      keywords = listOf("epi moto", "capacete inmetro", "jaqueta protecao", "luvas moto", "bota motociclista")
    ),
    KnowledgeItem(
      id = "mot_5",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "O que fazer ao se deparar com uma curva íngreme em descida de moto?",
      answer = "Reduza a marcha antes da curva para acionar o freio-motor, olhe sempre para o ponto de saída da curva (não para o guard-rail ou barranco), mantenha aceleração suave e constante para tracionar o pneu traseiro e evite frear bruscamente na inclinação.",
      keywords = listOf("curva descida moto", "ladeira moto", "freio motor", "fazer curva moto", "inclinacao")
    ),
    KnowledgeItem(
      id = "mot_6",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "O que fazer em caso de aquaplanagem com a motocicleta?",
      answer = "Mantenha o guidão totalmente reto e firme, não aperte nenhum freio (dianteiro ou traseiro), alivie progressivamente o acelerador sem movimentos bruscos e aguarde o pneu retomar o contato com o asfalto antes de mudar de direção.",
      keywords = listOf("aquaplanagem moto", "pista alagada moto", "derrapagem chuva moto", "perda atrito")
    ),
    KnowledgeItem(
      id = "mot_7",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Como fugir dos pontos cegos de ônibus e carretas?",
      answer = "Se você não consegue enxergar o motorista pelo retrovisor externo dele, ele certamente não está vendo você. Nunca ultrapasse pela direita, não permaneça ao lado da cabine de caminhões em conversões e buzine brevemente antes de iniciar a ultrapassagem.",
      keywords = listOf("ponto cego caminhao moto", "retrovisor caminhao", "ultrapassagem caminhao", "risco carreta")
    ),
    KnowledgeItem(
      id = "mot_8",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Quais os riscos de ruas sem iluminação pública para o motociclista?",
      answer = "O farol da motocicleta ilumina em média 30 metros. A 60 km/h, o piloto percorre 16,6 metros por segundo. Em ruas escuras, buracos, tampas de bueiro soltas e pedestres de roupa escura só se tornam visíveis quando já não há distância de parada segura.",
      keywords = listOf("rua escura moto", "visibilidade noturna moto", "tempo de parada", "distancia farol")
    ),
    KnowledgeItem(
      id = "mot_9",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Qual a diferença entre freios ABS e CBS na motocicleta?",
      answer = "• ABS (Anti-lock Braking System): Impede o travamento das rodas em frenagens de pânico, evitando perda de controle. • CBS (Combined Braking System): Distribui eletrônica ou hidraulicamente a força entre a roda dianteira e traseira ao pisar no pedal traseiro.",
      keywords = listOf("freio abs", "freio cbs", "diferenca freios moto", "travamento roda moto")
    ),
    KnowledgeItem(
      id = "mot_10",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Como a fadiga do fim de turno afeta o reflexo ao pilotar moto?",
      answer = "Trabalhadores em jornadas de 8h a 12h sofrem redução de até 50% na velocidade de reação motora e estreitamento do campo de visão periférica (visão em túnel), multiplicando por 4 o risco de colisão no percurso de retorno para casa.",
      keywords = listOf("fadiga turno moto", "sono pilotar", "cansaco retorno trabalho", "reflexo lento")
    ),
    KnowledgeItem(
      id = "mot_11",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Como proceder ao presenciar uma colisão de moto com ferido?",
      answer = "1. Sinalize a via imediatamente a montante (antes do local); 2. Ligue para o SAMU (192); 3. NUNCA retire o capacete da vítima; 4. NUNCA mova a vítima ou dê água, prevenindo lesões cervicais irreversíveis.",
      keywords = listOf("acidente com motoqueiro", "tirar capacete", "samu 192", "socorro moto", "colisao via")
    ),
    KnowledgeItem(
      id = "mot_12",
      category = "Motociclistas",
      categoryIcon = "🏍️",
      question = "Como o RotaSegura ajuda quem pilota moto em turnos industriais?",
      answer = "O RotaSegura mapeia acidentes ativos em tempo real, traçados sem óleo na pista, rotas com asfalto integro e sugere alternativas que contornam congestionamentos com menor risco de prensagem por veículos pesados.",
      keywords = listOf("app motoqueiro", "rotasegura moto", "rota protegida moto", "turno industrial")
    ),

    // =========================================================================
    // 5. MOTORISTAS DE CARRO E VEÍCULOS LEVES - 10
    // =========================================================================
    KnowledgeItem(
      id = "car_1",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Como conduzir com segurança em avenidas com pedestres e baixa iluminação?",
      answer = "Reduza a velocidade para 30 ou 40 km/h, use farol baixo regulado (nunca farol alto que cega outros usuários), redobre a atenção nas proximidades de pontos de ônibus e esquinas com árvores frondosas e mantenha o pé descansado sobre o pedal de freio.",
      keywords = listOf("dirigir no escuro", "pedestre a noite", "farol regulado", "velocidade segura carro")
    ),
    KnowledgeItem(
      id = "car_2",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Qual a velocidade adequada em vias urbanas sob pista molhada e garoa?",
      answer = "Recomenda-se reduzir a velocidade em 20% a 30% em relação ao limite da via e dobrar a distância de seguimento do veículo da frente (de 2 segundos para 4 segundos no método da contagem).",
      keywords = listOf("velocidade pista molhada", "distancia seguimento", "chuva carro", "direcao defensiva")
    ),
    KnowledgeItem(
      id = "car_3",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Como proceder ao se aproximar de um acidente sinalizado no mapa?",
      answer = "Reduza a velocidade com antecedência ligando o pisca-alerta brevemente para avisar quem vem atrás, não diminua para filmar com o celular (evite engavetamento por distração) e siga a sinalização das equipes de resgate.",
      keywords = listOf("aproximar acidente", "colisao sinalizada", "pisca alerta", "curiosidade transito")
    ),
    KnowledgeItem(
      id = "car_4",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Qual a diferença entre distância de reação e distância de frenagem?",
      answer = "• Distância de Reação: Espaço percorrido desde o instante em que o motorista enxerga o perigo até pisar no freio (média 0,75s a 1s). • Distância de Frenagem: Espaço percorrido após o acionamento do freio até a imobilização total do carro.",
      keywords = listOf("distancia de reacao", "distancia de frenagem", "tempo de parada", "fisica transito")
    ),
    KnowledgeItem(
      id = "car_5",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Como descer ladeiras íngremes no trânsito urbano com segurança?",
      answer = "Engrene a mesma marcha que utilizaria para subir a ladeira (geralmente 2ª marcha), aproveitando o freio-motor. Nunca desça em ponto morto ('na banguela'), pois além de ser infração de trânsito, causa superaquecimento e perda total dos freios.",
      keywords = listOf("descer ladeira carro", "freio motor", "banguela", "ponto morto descida", "superaquecimento freio")
    ),
    KnowledgeItem(
      id = "car_6",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Qual a infração e riscos do uso de celular ao volante no trajeto de trabalho?",
      answer = "Segurar ou manusear telefone celular ao volante é infração gravíssima (Art. 252, parágrafo único do CTB), com multa de R$ 293,47 e 7 pontos na CNH. O ato desvia a atenção por até 4,5 segundos, o equivalente a percorrer 100m às cegas a 80 km/h.",
      keywords = listOf("celular ao volante", "infracao gravissima", "multa celular", "artigo 252", "distracao")
    ),
    KnowledgeItem(
      id = "car_7",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "O que fazer se o veículo apresentar pane em trecho escuro e perigoso?",
      answer = "1. Tente encostar o carro em local iluminado ou recuo seguro; 2. Ligue o pisca-alerta imediatamente; 3. Posicione o triângulo de sinalização a pelo menos 30 metros da traseira; 4. Permaneça em local seguro (fora da pista, atrás da mureta); 5. Acione o socorro pelo app.",
      keywords = listOf("pane no carro", "carro quebrou", "trecho escuro pane", "triangulo sinalizacao")
    ),
    KnowledgeItem(
      id = "car_8",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Como ajustar espelhos retrovisores para eliminar pontos cegos?",
      answer = "Ajuste os espelhos laterais abrindo-os até que você mal consiga ver a lateral do próprio veículo. Quando um carro ultrapassar pela esquerda, ele só deve sair do espelho central quando já estiver visível no retrovisor externo e no campo periférico.",
      keywords = listOf("ajustar retrovisor", "eliminar ponto cego", "espelho retrovisor carro")
    ),
    KnowledgeItem(
      id = "car_9",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "O que é direção defensiva preventiva no deslocamento corporativo?",
      answer = "É o conjunto de medidas adotadas pelo condutor para antecipar situações de perigo criadas por outros motoristas, falhas de infraestrutura viária ou condições climáticas adversas, evitando o sinistro independentemente de quem tem a razão jurídica.",
      keywords = listOf("direcao defensiva", "prevencao", "deslocamento corporativo", "antecipacao de risco")
    ),
    KnowledgeItem(
      id = "car_10",
      category = "Motoristas & Carros",
      categoryIcon = "🚗",
      question = "Como a carona solidária corporativa com o RotaSegura reduz riscos?",
      answer = "Ao agrupar trabalhadores em trajetos pré-validados pela IA com pontos de embarque em locais iluminados, a carona solidária reduz o número de veículos em vias de risco, inibe assaltos em pontos de espera e diminui a pegada de carbono da empresa.",
      keywords = listOf("carona solidaria", "compartilhamento trajeto", "esg", "reducao sinistro")
    ),

    // =========================================================================
    // 6. PEDESTRES E TRABALHADORES A PÉ - 10
    // =========================================================================
    KnowledgeItem(
      id = "ped_1",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Quais as regras de travessia segura quando não houver semáforo na via?",
      answer = "Conforme o Artigo 69 do CTB, o pedestre deve atravessar sempre na faixa de pedestres quando houver uma a menos de 50 metros. Quando não houver, deve cruzar em linha reta perpendicular à via, em local de boa visibilidade, com atenção aos dois sentidos.",
      keywords = listOf("travessia segura", "sem semaforo", "artigo 69", "faixa de pedestres", "cruzar rua")
    ),
    KnowledgeItem(
      id = "ped_2",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Por que evitar fones de ouvido e celular ao caminhar à noite?",
      answer = "O uso de fones com cancelamento de ruído e telas brilhantes anula a visão periférica e a audição do pedestre, impedindo a percepção de aproximação de veículos elétricos/silenciosos ou de abordagens criminosas a tempo de buscar abrigo.",
      keywords = listOf("fones de ouvido a noite", "celular caminhando", "atencao pedestre", "distracao auditiva")
    ),
    KnowledgeItem(
      id = "ped_3",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Como se portar em trechos com calçada interrompida ou em obras?",
      answer = "Se for obrigado a caminhar temporariamente na pista de rolamento, caminhe sempre no sentido contrário ao tráfego de veículos (pela esquerda), mantendo contato visual com os motoristas e buscando retornar à calçada o mais rápido possível.",
      keywords = listOf("calcada interrompida", "obra na calcada", "caminhar pela esquerda", "sentido contrario")
    ),
    KnowledgeItem(
      id = "ped_4",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "O que fazer se perceber que está sendo seguido em uma rua escura?",
      answer = "1. Não vá para casa imediatamente; 2. Mude de calçada para confirmar a suspeita; 3. Dirija-se a um estabelecimento comercial iluminado (farmácia 24h, posto ou mercado); 4. Acione o Botão SOS do RotaSegura e ligue 190 se a ameaça persistir.",
      keywords = listOf("sendo seguido", "rua deserta", "seguranca pedestre", "ameaca rua", "procedimento seguir")
    ),
    KnowledgeItem(
      id = "ped_5",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Como o pedestre pode se tornar mais visível para os motoristas à noite?",
      answer = "Utilize roupas claras ou acessórios com detalhes refletivos (na mochila ou tênis), utilize a lanterna do smartphone apontada para o chão ao atravessar vias escuras e espere os veículos pararem completamente antes de pisar na faixa.",
      keywords = listOf("visibilidade pedestre", "roupa clara", "lanterna celular", "atravessar a noite")
    ),
    KnowledgeItem(
      id = "ped_6",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Qual a prioridade do pedestre na faixa segundo o CTB?",
      answer = "O Artigo 70 do CTB estabelece que os pedestres que estiverem atravessando a via sobre as faixas delimitadas têm prioridade absoluta de passagem, exceto nos locais com sinalização semafórica onde devem obedecer às fases do sinal.",
      keywords = listOf("prioridade na faixa", "artigo 70 ctb", "preferencia pedestre", "faixa de seguranca")
    ),
    KnowledgeItem(
      id = "ped_7",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Quais cuidados ao caminhar em ladeiras íngremes em dias chuvosos?",
      answer = "Evite calçadas com pedras polidas, musgo ou revestimentos cerâmicos que viram pistas de gelo. Dê passos curtos mantendo o centro de gravidade alinhado sobre os pés e utilize calçados com sola de borracha antiderrapante.",
      keywords = listOf("ladeira chuva pedestre", "queda calcada", "musgo escorrega", "calcado antiderrapante")
    ),
    KnowledgeItem(
      id = "ped_8",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "O que é o botão SOS do RotaSegura e como funciona?",
      answer = "É um acionador rápido de emergência no app que envia as coordenadas GPS em tempo real para seus contatos de emergência cadastrados, além de discar diretamente com um toque para o 190 (Polícia Militar) ou 192 (SAMU).",
      keywords = listOf("botao sos", "como funciona sos", "panico", "emergencia app", "gps socorro")
    ),
    KnowledgeItem(
      id = "ped_9",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Como solicitar calçamento ou sinalização de faixa na prefeitura?",
      answer = "Você pode registrar a demanda pelo módulo Cidadão do RotaSegura ou via portal 156. O app reúne dados agregados de fluxo de pedestres para comprovar o impacto da melhoria no orçamento municipal.",
      keywords = listOf("solicitar faixa", "calcada prefeitura", "reclamar faixa pedestre", "portal 156")
    ),
    KnowledgeItem(
      id = "ped_10",
      category = "Pedestres",
      categoryIcon = "🚶",
      question = "Por que a rota mais curta muitas vezes não é a mais segura para o pedestre?",
      answer = "Rotas mais curtas frequentemente passam por vielas estreitas sem iluminação, pontilhões ermos e cruzamentos de tráfego rápido sem faixa. O RotaSegura calcula o índice de segurança priorizando iluminação e movimento de pessoas.",
      keywords = listOf("rota mais curta", "caminho perigoso", "atalho", "por que desviar", "indice seguranca")
    ),

    // =========================================================================
    // 7. TRANSPORTE PÚBLICO E PONTOS DE ÔNIBUS - 10
    // =========================================================================
    KnowledgeItem(
      id = "bus_1",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "O que é a 'Lei da Parada Segura' noturna para mulheres e vulneráveis?",
      answer = "Em diversas cidades brasileiras (como SP, RJ, BH e Curitiba), leis municipais e estaduais permitem que mulheres e pessoas com deficiência solicitem desembarque fora do ponto oficial após as 22h, desde que na rota da linha e em local seguro de parada.",
      keywords = listOf("lei da parada segura", "desembarque fora do ponto", "apos 22h", "mulheres onibus", "parada noturna")
    ),
    KnowledgeItem(
      id = "bus_2",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "Como escolher o melhor ponto de ônibus no retorno da madrugada?",
      answer = "No RotaSegura, selecione paradas marcadas com selo 'Ponto Protegido' (verdes no mapa). Elas possuem postes LED num raio de 15 metros, comércio noturno em funcionamento (farmácia, posto) e abrigo coberto contra chuva.",
      keywords = listOf("ponto protegido", "espera onibus noite", "ponto iluminado", "madrugada onibus")
    ),
    KnowledgeItem(
      id = "bus_3",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "O que fazer em caso de assédio ou importunação sexual dentro do ônibus?",
      answer = "1. Avise o motorista ou cobrador imediatamente para que não abram as portas até a chegada da polícia; 2. Peça apoio em voz alta aos outros passageiros; 3. Ligue 190 (Polícia Militar) ou acione o canal Disque 180 (Central de Atendimento à Mulher).",
      keywords = listOf("assedio onibus", "importunacao sexual", "disque 180", "ajuda no onibus", "policia coletivo")
    ),
    KnowledgeItem(
      id = "bus_4",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "Como o RotaSegura mapeia paradas de ônibus seguras?",
      answer = "O sistema cruza dados abertos de transporte metropolitano com auditorias de iluminação urbana e ocorrências de segurança pública. Cada parada recebe um Score de Espera Segura de 0 a 100.",
      keywords = listOf("mapeamento paradas", "score espera segura", "auditoria pontos", "seguranca no ponto")
    ),
    KnowledgeItem(
      id = "bus_5",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "Quais os horários de maior incidência de furtos em terminais urbanos?",
      answer = "Estatísticas de segurança pública revelam que os picos ocorrem entre 06h00 e 07h30 (ida matinal) e das 17h30 às 19h30 (volta), além das saídas tardias de turno das 22h às 23h30 em terminais com policiamento reduzido.",
      keywords = listOf("furto terminal", "horario de pico", "roubo no ponto", "estatistica transporte")
    ),
    KnowledgeItem(
      id = "bus_6",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "Como planejar uma baldeação segura à noite?",
      answer = "Evite transferências em pontos isolados na rua; prefira sempre terminais fechados e integrados onde há agentes de fiscalização e câmeras. O RotaSegura prioriza rotas diretas ou com baldeação em estações vigiadas.",
      keywords = listOf("baldeacao", "troca de onibus", "terminal fechado", "conexao segura")
    ),
    KnowledgeItem(
      id = "bus_7",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "O que fazer se o coletivo estiver sofrendo direção perigosa pelo motorista?",
      answer = "Anote o prefixo do veículo, a linha e o horário da viagem. Você pode registrar reclamação imediata na ouvidoria da concessionária ou órgão regulador de transportes municipal e alertar a CIPA da sua empresa se for linha fretada.",
      keywords = listOf("motorista correndo", "direcao perigosa onibus", "reclamar onibus", "prefixo")
    ),
    KnowledgeItem(
      id = "bus_8",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "O que é telemetria preditiva de ônibus e como ela evita espera em locais ermos?",
      answer = "É o monitoramento via GPS da posição do ônibus em tempo real. O trabalhador só sai da empresa ou de casa no momento exato em que o veículo estiver a 3 minutos do ponto, reduzindo o tempo de exposição em locais desertos.",
      keywords = listOf("telemetria gps", "onibus tempo real", "tempo de espera", "nao ficar no ponto")
    ),
    KnowledgeItem(
      id = "bus_9",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "Quais cuidados adotar na escada e plataforma de embarque/desembarque?",
      answer = "Espere o ônibus parar totalmente antes de descer do degrau, segure firme nos corrimãos e preste atenção em motociclistas que possam estar ultrapassando o ônibus pela direita de forma irregular.",
      keywords = listOf("embarque seguro", "queda degrau", "moto pela direita", "descer do onibus")
    ),
    KnowledgeItem(
      id = "bus_10",
      category = "Transporte Coletivo",
      categoryIcon = "🚌",
      question = "Como solicitar a instalação de abrigo em pontos de ônibus da periferia?",
      answer = "Demandas de novos abrigos devem ser formalizadas na prefeitura ou secretaria de mobilidade. A reunião de colaboradores de indústrias através de abaixo-assinado acelera a priorização do poder público.",
      keywords = listOf("pedir abrigo onibus", "ponto coberto", "chuva no ponto", "prefeitura cobertura")
    ),

    // =========================================================================
    // 8. ILUMINAÇÃO PÚBLICA E ROTAS ESCURAS - 10
    // =========================================================================
    KnowledgeItem(
      id = "luz_1",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Como a iluminação LED reduz a criminalidade e os sinistros de trânsito?",
      answer = "Estudos de segurança urbana demonstram que a modernização por LED reduz em até 36% os crimes contra o patrimônio à noite e diminui em até 40% os atropelamentos de pedestres, pois melhora a nitidez e o Índice de Reprodução de Cor (IRC).",
      keywords = listOf("iluminacao led", "reducao crime", "36 por cento", "atropelamento noturno", "irc")
    ),
    KnowledgeItem(
      id = "luz_2",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Como o aplicativo RotaSegura classifica um trecho como 'Rota Escura'?",
      answer = "O sistema monitora a distância entre postes de luz funcionais: trechos com espaçamento superior a 60 metros sem iluminação ou com histórico de lâmpadas queimadas reportadas por usuários recebem a classificação de 'Zona Escura Crítica'.",
      keywords = listOf("como classifica rota escura", "zona escura", "criterio luz", "poste queimado")
    ),
    KnowledgeItem(
      id = "luz_3",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Quem é o responsável legal pela manutenção da iluminação pública nas ruas?",
      answer = "A Constituição Federal (Art. 149-A) atribui a competência da iluminação pública aos Municípios (Prefeituras), custeada pela contribuição CIP/COSIP na conta de luz. A prefeitura pode operar diretamente ou via Concessão/PPP.",
      keywords = listOf("responsavel iluminacao", "prefeitura", "cosip", "artigo 149-a", "concessionaria luz")
    ),
    KnowledgeItem(
      id = "luz_4",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "O que fazer se os postes da rua da minha empresa estiverem apagados há dias?",
      answer = "Abra chamado imediatamente na central de iluminação da prefeitura (canal 156 ou concessionária municipal) informando o número do poste gravado na plaqueta metálica. No RotaSegura, marque o ponto para alertar os colegas de turno.",
      keywords = listOf("poste apagado", "rua escura empresa", "plaqueta poste", "abrir chamado luz")
    ),
    KnowledgeItem(
      id = "luz_5",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Quais os tipos de sinistros mais frequentes em vias sem luz?",
      answer = "Em primeiro lugar estão os atropelamentos de pedestres em travessias desprovidas de faixas iluminadas, seguidos de quedas em buracos de calçadas, colisões traseiras com veículos estacionados e emboscadas/roubos com retenção de vítima.",
      keywords = listOf("sinistros frequentes", "acidente sem luz", "atropelamento", "buraco oculto")
    ),
    KnowledgeItem(
      id = "luz_6",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Qual a diferença entre lâmpadas de vapor de sódio antigas e luminárias LED?",
      answer = "As antigas lâmpadas de vapor de sódio emitem luz amarelada com baixo IRC (20 a 30%), distorcendo cores e profundidade. O LED emite luz branca neutra (4000K a 5000K) com IRC acima de 70%, permitindo distinguir rostos, placas e obstáculos a longa distância.",
      keywords = listOf("vapor de sodio", "led diferenca", "luz amarela", "irc cor", "visibilidade")
    ),
    KnowledgeItem(
      id = "luz_7",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Como a escuridão afeta a percepção de profundidade de motoristas e motociclistas?",
      answer = "A escuridão reduz o contraste e a acuidade visual estéreo, fazendo com que distâncias pareçam maiores do que realmente são e retardando a percepção de pedestres em roupas escuras em até 1,5 segundo.",
      keywords = listOf("percepcao profundidade", "visao noturna", "contraste", "acuidade visual")
    ),
    KnowledgeItem(
      id = "luz_8",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Quais dicas seguir se for obrigado a transitar por trecho escuro?",
      answer = "Mantenha passos rápidos e firmes, guarde o celular no bolso interior da jaqueta, utilize calçado adequado, caminhe no centro da via se as calçadas estiverem obstruídas por vegetação e avise um familiar via compartilhamento de trajeto do app.",
      keywords = listOf("dicas rua escura", "caminhar no breu", "atravessar sem luz", "precaucao")
    ),
    KnowledgeItem(
      id = "luz_9",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Como o crowdsourcing de iluminação no RotaSegura ajuda a prefeitura?",
      answer = "Cada alerta de lâmpada queimada ou blackout cadastrado pelos trabalhadores é consolidado no Painel B2G, gerando ordens de serviço georreferenciadas para as equipes de manutenção das concessionárias municipais.",
      keywords = listOf("crowdsourcing iluminacao", "painel b2g", "ordem de servico", "manutencao luz")
    ),
    KnowledgeItem(
      id = "luz_10",
      category = "Iluminação & Rotas Escuras",
      categoryIcon = "🌑",
      question = "Como registrar no app um ponto cego de iluminação?",
      answer = "Toque no botão '+' no mapa, selecione a categoria 'Ponto sem Iluminação', confirme a localização GPS e descreva brevemente a situação (ex: '2 postes apagados na curva do viaduto'). O ponto fica visível para todos os usuários.",
      keywords = listOf("registrar ponto cego", "marcar rua escura", "colaborativo luz", "adicionar alerta")
    ),

    // =========================================================================
    // 9. TOPOGRAFIA, DECLIVIDADE E RAMPAS ÍNGREMES - 10
    // =========================================================================
    KnowledgeItem(
      id = "dec_1",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "O que significa a porcentagem de declividade (%) de uma rua?",
      answer = "A declividade representa o desnível vertical em relação à distância horizontal percorrida. Uma inclinação de 10% significa que a via sobe (ou desce) 10 metros de altura a cada 100 metros percorridos horizontalmente.",
      keywords = listOf("o que e declividade", "porcentagem rampa", "desnivel", "calculo inclinacao", "grau subida")
    ),
    KnowledgeItem(
      id = "dec_2",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Por que o limite de 8,33% da NBR 9050 é vital para a acessibilidade?",
      answer = "Acima de 8,33%, a força da gravidade vence a capacidade de frenagem manual das mãos nos aros da cadeira de rodas e o esforço de propulsão causa fadiga cardíaca e muscular rápida em poucos metros de subida.",
      keywords = listOf("limite 8.33", "nbr 9050 vital", "esforco rampa", "fadiga cadeirante")
    ),
    KnowledgeItem(
      id = "dec_3",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Quais os riscos mecânicos para freios em ladeiras acima de 12%?",
      answer = "O uso contínuo dos freios em declives severos provoca o fenômeno de 'fading' (superaquecimento com perda de atrito nas pastilhas e ebulição do fluido de freio), levando à falha completa da capacidade de parada do veículo.",
      keywords = listOf("fading freio", "superaquecimento pastilha", "descida 12 por cento", "falha de freio")
    ),
    KnowledgeItem(
      id = "dec_4",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Qual marcha engrenar para subir uma ladeira íngreme de carro ou moto?",
      answer = "Engrene a 1ª ou 2ª marcha antes de iniciar a subida, mantendo o motor na faixa de torque ideal (geralmente entre 2.000 e 3.500 RPM). Evite trocar de marcha no meio do aclive para não perder tração e engasgar o motor.",
      keywords = listOf("marcha subida", "primeira marcha", "torque motor", "subir ladeira carro", "trocar marcha")
    ),
    KnowledgeItem(
      id = "dec_5",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Como o RotaSegura calcula o relevo e as curvas de nível em 3D?",
      answer = "O motor espacial do RotaSegura renderiza o relevo topográfico em matriz de alturas 3D, calculando o ganho de elevação acumulado e marcando trechos com inclinações perigosas em âmbar e vermelho.",
      keywords = listOf("relevo 3d", "curvas de nivel", "topografia app", "matriz de altura")
    ),
    KnowledgeItem(
      id = "dec_6",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Quais cuidados pedestres idosos e gestantes devem tomar em ladeiras?",
      answer = "Evite carregar peso excessivo nos braços (prefira mochilas bem distribuídas nas costas), utilize calçados com boa aderência e busque itinerários com corrimãos ou degraus padronizados em vez de rampas lisas.",
      keywords = listOf("idoso ladeira", "gestante aclive", "risco queda idoso", "corrimão calcada")
    ),
    KnowledgeItem(
      id = "dec_7",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Quais os perigos adicionais em ladeiras de paralelepípedo molhadas?",
      answer = "O paralelepípedo molhado perde quase todo o atrito com pneus e solas de sapato, comportando-se como sabão. Para motos e bicicletas, a frenagem deve ser extremamente suave e em linha reta.",
      keywords = listOf("paralelepipedo molhado", "rua de pedra ladeira", "derrapagem paralelepipedo")
    ),
    KnowledgeItem(
      id = "dec_8",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Quando o RotaSegura recomenda desviar de uma ladeira mesmo aumentando a distância?",
      answer = "O app sugere contorno quando a inclinação superar 10% para ciclistas/cadeirantes ou quando houver registro de chuva forte que transforme a ladeira em rota de enxurrada e lama.",
      keywords = listOf("quando desviar ladeira", "contorno subida", "aumentar distancia", "enxurrada ladeira")
    ),
    KnowledgeItem(
      id = "dec_9",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "Como descer uma ladeira de bicicleta com segurança total?",
      answer = "Mantenha o corpo posicionado para trás com o quadril recuado em relação ao selim, flexione levemente os joelhos e cotovelos e dose os dois freios suavemente, aplicando 70% da força no freio dianteiro e 30% no traseiro.",
      keywords = listOf("descer ladeira bike", "posicao descida bicicleta", "dosar freios bike")
    ),
    KnowledgeItem(
      id = "dec_10",
      category = "Declividade & Rampas",
      categoryIcon = "⛰️",
      question = "O que são faixas de escape em vias com declive acentuado?",
      answer = "São áreas de desaceleração de emergência preenchidas com cascalho ou argila expandida instaladas em descidas de serra e grandes avenidas para parar veículos pesados que perderam os freios sem causar mortes.",
      keywords = listOf("faixa de escape", "area de escape", "caminhao sem freio", "descida de serra")
    ),

    // =========================================================================
    // 10. CLIMA, CHUVA, ALAGAMENTO E FATORES AMBIENTAIS - 10
    // =========================================================================
    KnowledgeItem(
      id = "cli_1",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Por que os primeiros minutos de garoa são os mais perigosos no trânsito?",
      answer = "Nos primeiros minutos de chuva leve, a água se mistura à fuligem, poeira e óleo acumulados sobre o asfalto seco, formando uma película altamente lubrificante e escorregadia semelhante a sabão.",
      keywords = listOf("primeiros minutos chuva", "asfalto sabao", "garoa perigosa", "oleo asfalto chuva")
    ),
    KnowledgeItem(
      id = "cli_2",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "O que é aquaplanagem e como o motorista deve reagir?",
      answer = "É a perda de contato dos pneus com o solo devido à formação de uma lâmina d'água entre a borracha e o asfalto. Reação correta: tire o pé do acelerador, segure o volante reto e firme, NÃO pise no freio e aguarde o pneu reconectar com o piso.",
      keywords = listOf("o que e aquaplanagem", "agir aquaplanagem", "lamina de agua", "pneu sem contato")
    ),
    KnowledgeItem(
      id = "cli_3",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Qual o nível máximo seguro de água para carros e motos passarem em alagamentos?",
      answer = "A regra de ouro é nunca ultrapassar se a água atingir a altura do centro da roda (metade do aro). Acima disso, há risco iminente de calço hidráulico no motor, arrastamento do veículo pela correnteza ou queda em bueiros abertos.",
      keywords = listOf("limite alagamento", "altura da agua", "calco hidraulico", "passar na agua", "enchente")
    ),
    KnowledgeItem(
      id = "cli_4",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Como o RotaSegura recalcula trajetos em caso de tempestade?",
      answer = "A IA cruza dados de pluviometria em tempo real com o mapa de pontos históricos de alagamento da Defesa Civil, desviando automaticamente a rota para vias em cota de nível mais elevada.",
      keywords = listOf("recalculo tempestade", "defesa civil chuva", "alagamento rota", "cota elevada")
    ),
    KnowledgeItem(
      id = "cli_5",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Quais os riscos para pedestres caminhando perto de bueiros durante enchentes?",
      answer = "A força da água frequentemente arranca tampas de ferro fundido de bueiros e bocas de lobo. O pedestre pode ser sugado para galerias de drenagem pluvial submersas sem chance de escape. Evite caminhar por enxurradas turvas.",
      keywords = listOf("bueiro enchente", "tampa arrancada", "risco pedestre agua", "enxurrada bueiro")
    ),
    KnowledgeItem(
      id = "cli_6",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "O que fazer se avistar fios elétricos caídos na via após tempestade?",
      answer = "Mantenha distância mínima de 10 metros e considere o cabo sempre energizado. Não toque em poças d'água próximas. Se o cabo cair sobre seu carro, permaneça dentro dele sem tocar no solo e acione os Bombeiros (193).",
      keywords = listOf("fio eletrico caido", "cabo energia pista", "choque eletrico tempestade", "193 bombeiros")
    ),
    KnowledgeItem(
      id = "cli_7",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Como conduzir com neblina ou cerração densa no trajeto noturno?",
      answer = "Use exclusivamente o farol baixo ou farol de neblina. NUNCA use farol alto (a luz reflete nas gotículas e cria uma parede branca cegante), reduza a velocidade e guie-se pelas faixas de sinalização brancas pintadas no acostamento.",
      keywords = listOf("neblina", "cerracao", "farol de neblina", "parede branca", "dirigir na neblina")
    ),
    KnowledgeItem(
      id = "cli_8",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Por que verificar as palhetas do limpador de para-brisa antes da estação de chuvas?",
      answer = "Palhetas ressecadas deixam riscos d'água no vidro que refratam a luz dos faróis contrários à noite, provocando ofuscamento e perda total de visão em cruzamentos.",
      keywords = listOf("palheta limpador", "para-brisa", "vidro embacado", "manutencao chuva")
    ),
    KnowledgeItem(
      id = "cli_9",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Quais as melhores roupas de proteção para motociclistas em dias de chuva?",
      answer = "Conjunto de capa de chuva impermeável de PVC ou poliamida com costuras seladas a quente, faixas fluorescentes e refletivas, sobreluvas impermeáveis e bota de borracha vulcanizada.",
      keywords = listOf("capa de chuva moto", "impermeavel motoqueiro", "costura selada", "roupa chuva")
    ),
    KnowledgeItem(
      id = "cli_10",
      category = "Clima & Chuva",
      categoryIcon = "🌧️",
      question = "Como a chuva altera a frequência do transporte público e aumenta riscos no ponto?",
      answer = "O trânsito retarda as linhas em até 40%, gerando superlotação e filas em paradas sem abrigo. O RotaSegura recomenda aguardar em locais cobertos credenciados com aviso de aproximação em tempo real.",
      keywords = listOf("onibus na chuva", "atraso coletivo chuva", "ponto sem abrigo chuva", "espera onibus")
    ),

    // =========================================================================
    // 11. PRIMEIROS SOCORROS, EMERGÊNCIA E BOTÃO SOS - 10
    // =========================================================================
    KnowledgeItem(
      id = "soc_1",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Como acionar o Botão SOS no aplicativo RotaSegura?",
      answer = "O Botão SOS vermelho está fixado no canto superior direito de todas as telas. Ao tocá-lo, abre-se uma janela de confirmação de 1 toque que liga imediatamente para a Polícia (190), SAMU (192) ou Bombeiros (193) e dispara alerta de localização para contatos.",
      keywords = listOf("acionar sos", "botao emergencia app", "como usar sos", "ligar 190 192")
    ),
    KnowledgeItem(
      id = "soc_2",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Quais são os principais números telefônicos de emergência pública no Brasil?",
      answer = "• 190: Polícia Militar (crimes e assaltos em andamento) • 192: SAMU (urgências e emergências médicas) • 193: Corpo de Bombeiros (resgate de vítimas presas em ferragens, atropelamentos graves e incêndios) • 199: Defesa Civil.",
      keywords = listOf("telefones de emergencia", "190", "192", "193", "199", "numeros socorro")
    ),
    KnowledgeItem(
      id = "soc_3",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Por que é proibido retirar o capacete de um motociclista acidentado?",
      answer = "A retirada inadequada do capacete por leigos pode movimentar bruscamente a coluna cervical fraturada, comprimindo ou seccionando a medula espinhal e causando tetraplegia ou óbito instantâneo da vítima.",
      keywords = listOf("nao tirar capacete", "medula espinhal", "lesao cervical", "tetraplegia", "primeiros socorros moto")
    ),
    KnowledgeItem(
      id = "soc_4",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "A qual distância mínima o triângulo de sinalização deve ser posicionado?",
      answer = "Pelo Art. 46 do CTB e Resolução CONTRAN 36, o triângulo deve ser colocado a pelo menos 30 metros da traseira do veículo em vias urbanas. Em rodovias com chuva ou neblina, dobre a distância proporcionalmente à velocidade da via (ex: via de 80km/h = 80 a 160 passos).",
      keywords = listOf("distancia triangulo", "30 metros", "artigo 46", "resolucao 36", "sinalizar acidente")
    ),
    KnowledgeItem(
      id = "soc_5",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Como realizar massagem cardíaca (RCP) em via pública até a chegada do SAMU?",
      answer = "Se a vítima não responder e não respirar: posicione as mãos sobrepostas no centro do peito (sobre o osso esterno), mantenha os braços esticados e comprima com força e rapidez de 100 a 120 compressões por minuto, afundando o tórax cerca de 5 a 6 cm.",
      keywords = listOf("massagem cardiaca", "rcp", "parada cardiaca via", "100 a 120 compressoes", "suporte basico de vida")
    ),
    KnowledgeItem(
      id = "soc_6",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Como estancar uma hemorragia grave de um ferido em via pública?",
      answer = "Aplique pressão direta e contínua sobre o ferimento utilizando um pano limpo, gaze ou vestimenta. Mantenha a pressão constante sem remover o pano inicial. Nunca coloque substâncias estranhas (pó de café, terra) sobre a ferida.",
      keywords = listOf("estancar sangramento", "hemorragia", "pressao direta", "pano limpo ferida")
    ),
    KnowledgeItem(
      id = "soc_7",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "O que fazer se você presenciar uma tentativa de assalto?",
      answer = "Não reaja, não faça movimentos bruscos e evite olhar fixamente nos olhos dos agressores. Mantenha as mãos visíveis. Assim que estiver em segurança, acione o 190 e cadastre o alerta de violência no RotaSegura para alertar outros cidadãos.",
      keywords = listOf("presenciar assalto", "nao reagir", "tentativa de roubo", "o que fazer assalto")
    ),
    KnowledgeItem(
      id = "soc_8",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Como compartilhar minha localização em tempo real com contatos de confiança?",
      answer = "No RotaSegura, ative a opção 'Compartilhar Trajeto Seguro'. O aplicativo gera um link criptografado com monitoramento em tempo real de trajeto que informa seus familiares quando você chegar em segurança ao trabalho ou em casa.",
      keywords = listOf("compartilhar localizacao", "tempo real familiares", "trajeto seguro link", "rastreamento familiar")
    ),
    KnowledgeItem(
      id = "soc_9",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "O que deve conter um kit básico de primeiros socorros automotivo?",
      answer = "Embora não seja mais obrigatório por lei, recomenda-se portar: luvas descartáveis de procedimento, compressas de gaze esterilizada, rolo de atadura de crepom, esparadrapo, tesoura sem ponta e solução antisséptica.",
      keywords = listOf("kit primeiros socorros", "gaze", "atadura", "luvas", "o que levar carro")
    ),
    KnowledgeItem(
      id = "soc_10",
      category = "Emergência & Primeiros Socorros",
      categoryIcon = "🏥",
      question = "Quais informações fornecer ao atendente do SAMU (192) ou Bombeiros (193)?",
      answer = "1. Endereço exato com número aproximado e ponto de referência claro; 2. Quantidade de vítimas e estado de consciência (se respondem ou respiram); 3. Se há vazamento de combustível ou risco de explosão; 4. Tipo de veículos envolvidos.",
      keywords = listOf("o que falar ao samu", "passar endereco 192", "informacoes socorrista", "referencia acidente")
    ),

    // =========================================================================
    // 12. GOVERNANÇA B2G, ESG E EMPRESAS (JUMP START / CIDADES INTELIGENTES) - 10
    // =========================================================================
    KnowledgeItem(
      id = "gov_1",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Como as prefeituras podem utilizar os dados do RotaSegura para gestão viária?",
      answer = "Através do Painel B2G, a Secretaria de Mobilidade e Obras visualiza mapas de calor de acidentes de trajeto, buracos e déficits de iluminação pública reportados pelos trabalhadores, direcionando investimentos de pavimentação e LED de forma eficiente.",
      keywords = listOf("painel b2g", "prefeitura dados", "gestao viaria", "secretaria mobilidade", "mapa de calor")
    ),
    KnowledgeItem(
      id = "gov_2",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Como os acidentes de trajeto impactam o FAP (Fator Acidentário de Prevenção) das empresas?",
      answer = "Embora acidentes de trajeto não entrem diretamente no cálculo da alíquota do FAP desde resoluções recentes do CNPS, eles geram custos de absenteísmo, reposição emergencial de mão-de-obra, horas extras e potenciais litígios trabalhistas.",
      keywords = listOf("fap", "fator acidentario", "custo empresa", "absenteismo", "rh sinistro")
    ),
    KnowledgeItem(
      id = "gov_3",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Como o RotaSegura se alinha às metas de ESG e aos Objetivos da ONU (ODS 11)?",
      answer = "O RotaSegura cumpre diretamente o ODS 11 (Cidades e Comunidades Sustentáveis) e ODS 3 (Saúde e Bem-Estar), reduzindo mortes no trânsito, promovendo mobilidade ativa segura e gerando dados para cidades mais resilientes e inclusivas.",
      keywords = listOf("esg", "ods 11", "ods 3", "onu cidades sustentaveis", "sustentabilidade corporativa")
    ),
    KnowledgeItem(
      id = "gov_4",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "O que é o Painel de Governança B2G presente no aplicativo?",
      answer = "É uma interface executiva voltada a gestores públicos e diretores de RH que consolida índices de habitabilidade, conformidade com a NBR 9050, taxas de sinistros por corredor industrial e solicitações de intervenção urbana prioritárias.",
      keywords = listOf("painel b2g aplicativo", "interface executiva", "gestores publicos", "relatorios mobilidade")
    ),
    KnowledgeItem(
      id = "gov_5",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Como o RotaSegura protege a privacidade dos usuários segundo a LGPD?",
      answer = "Todos os dados de rotas e telemetria enviados ao painel de inteligência são submetidos a anonimização e agregação espacial com k-anonimato. Nenhuma informação pessoal ou identidade do trabalhador é compartilhada publicamente.",
      keywords = listOf("lgpd", "privacidade", "dados anonimos", "k-anonimato", "seguranca de dados")
    ),
    KnowledgeItem(
      id = "gov_6",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "O que estabelece a Convenção 155 da OIT sobre segurança no trabalho?",
      answer = "A Convenção 155 da OIT, ratificada pelo Brasil (Decreto 1.254/1994), determina que políticas nacionais devem prevenir acidentes e danos à saúde que tenham conexão com o trabalho ou ocorram durante o seu curso, incluindo os deslocamentos indispensáveis.",
      keywords = listOf("convencao 155 oit", "organizacao internacional trabalho", "decreto 1254", "seguranca do trabalho")
    ),
    KnowledgeItem(
      id = "gov_7",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Qual o custo econômico dos sinistros de trânsito para o SUS e para a sociedade?",
      answer = "Estimativas do IPEA apontam que os sinistros de trânsito custam mais de R$ 50 bilhões por ano ao Brasil, englobando internações no SUS, reabilitação física, perda de produtividade produtiva e despesas previdenciárias de pensão por morte.",
      keywords = listOf("custo ipea", "custo sus transito", "50 bilhoes", "impacto economico", "previdencia")
    ),
    KnowledgeItem(
      id = "gov_8",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Como integrar o RotaSegura ao sistema de fretamento corporativo da empresa?",
      answer = "O RotaSegura oferece integração com frotas de fretamento, mapeando os pontos de embarque de maior conveniência e menor risco noturno para que a van ou ônibus da empresa recolha os trabalhadores sem expô-los a locais ermos.",
      keywords = listOf("fretamento corporativo", "onibus da empresa", "integracao rh", "ponto de recolha")
    ),
    KnowledgeItem(
      id = "gov_9",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "De que forma o app apoia planos de mobilidade urbana sustentável (PlanMob)?",
      answer = "A Lei Federal 12.587/2012 exige que municípios acima de 20 mil habitantes formulem o PlanMob. Os dados de rotas ativas e barreiras de acessibilidade do RotaSegura fornecem evidências empíricas para a elaboração do plano.",
      keywords = listOf("planmob", "lei 12587", "politica nacional mobilidade", "plano de mobilidade")
    ),
    KnowledgeItem(
      id = "gov_10",
      category = "Governança & ESG",
      categoryIcon = "🏛️",
      question = "Como sugerir o RotaSegura como benefício na minha empresa?",
      answer = "Você pode apresentar a proposta à CIPA e ao SESMT da sua unidade fabril ou corporativa. O RotaSegura reduz atestados médicos por lesões no trajeto e agrega valor à reputação da marca empregadora.",
      keywords = listOf("sugerir empresa", "beneficio corporativo", "apresentar cipa", "marca empregadora")
    )
  )

  /**
   * Returns all available categories
   */
  fun getCategories(): List<String> {
    return listOf("Todas") + items.map { it.category }.distinct()
  }

  /**
   * Returns all questions count (over 100 questions guaranteed)
   */
  fun getTotalQuestionsCount(): Int = items.size

  /**
   * Intelligent search and intent matcher
   */
  fun searchAnswer(query: String?, context: MobilityContext? = null): KnowledgeMatch? {
    if (query.isNullOrBlank()) return null
    val cleanQuery = query.lowercase().trim()

    // 1. Direct ID or question exact match
    val exactMatch = items.firstOrNull {
      it.question.lowercase().contains(cleanQuery) || cleanQuery.contains(it.question.lowercase())
    }
    if (exactMatch != null) {
      return KnowledgeMatch(
        item = exactMatch,
        score = 100,
        relatedQuestions = getRelatedQuestions(exactMatch)
      )
    }

    // 2. Tokenized keyword scoring
    val tokens = cleanQuery.split(" ", "?", "!", ",", ".", ";", "-", "/", "(", ")")
      .map { it.trim() }
      .filter { it.length >= 3 && !isStopWord(it) }

    var bestMatch: KnowledgeItem? = null
    var highestScore = 0

    for (item in items) {
      var score = 0

      // Match against question words
      val qWords = item.question.lowercase().split(" ", "?", "!")
      for (token in tokens) {
        if (qWords.any { it.contains(token) || token.contains(it) }) {
          score += 15
        }
      }

      // Match against keywords list
      for (kw in item.keywords) {
        if (cleanQuery.contains(kw.lowercase())) {
          score += 25
        }
        for (token in tokens) {
          if (kw.lowercase().contains(token)) {
            score += 10
          }
        }
      }

      // Bonus if matches context modal/profile
      if (context != null) {
        if (context.selectedMode.label.lowercase().contains("ciclo") && item.category.contains("Ciclista")) score += 5
        if (context.selectedMode.label.lowercase().contains("moto") && item.category.contains("Motociclista")) score += 5
        if (context.selectedProfile.label.lowercase().contains("pcd") && item.category.contains("Acessibilidade")) score += 5
      }

      if (score > highestScore) {
        highestScore = score
        bestMatch = item
      }
    }

    // Only return if score passes confidence threshold
    if (bestMatch != null && highestScore >= 15) {
      return KnowledgeMatch(
        item = bestMatch,
        score = highestScore,
        relatedQuestions = getRelatedQuestions(bestMatch)
      )
    }

    return null
  }

  private fun getRelatedQuestions(item: KnowledgeItem): List<String> {
    return items.filter { it.category == item.category && it.id != item.id }
      .take(2)
      .map { it.question }
  }

  private fun isStopWord(word: String): Boolean {
    val stopWords = setOf("que", "qual", "como", "onde", "quando", "para", "com", "uma", "uma", "uns", "umas", "dos", "das", "por", "sobre", "isso", "esta", "este", "tem", "ter", "ser", "sao", "voce", "pelo", "pela")
    return stopWords.contains(word)
  }
}
