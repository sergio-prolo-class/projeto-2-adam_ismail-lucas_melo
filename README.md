## Java of Empires


projeto desenvolvido na disciplina de Programação Orientada a Objetos (2025-2).

# Funcionalidades Implementadas

## Título: ⚔️ Sistema de Combate ⚔️ <br>
 **⚔️ Ataque Básico (3 pontos):** 
- *Implementa sistema de ataque onde personagens podem atacar outros personagens no jogo.*

 **💀 Sistema de Morte (3 pontos):**
- *Personagens com vida zero são removidos do jogo com efeitos visuais.*

 **📏 Alcance Váriavel (4 pontos):**
- *Ataques têm limite de distância para acertar, variando conforme tipo de personagem.*

 **💨 Esquiva (5 pontos):**
- *Alvos têm chance de esquivar ataques, evitando completamente o dano.*

## Título: 🎮 Controles Avançados 🎮 <br>
  **🔘 Filtro por Tipo (4 pontos):**
- *Implementa radio buttons para filtrar quais personagens serão afetados pelos comandos.*

**🐴 Controle de Montaria (5 pontos):**
- *Adiciona botão para alternar estado montado/desmontado dos personagens com montaria.*

 **⌨️ Atalhos de Teclado (6 pontos):**
- *Implementa controles por teclado para facilitar comandos rápidos.*

## Título: 🏛️ Arquitetura de Software 🏛️ <br>
 **⚙️ Arquivo de Configurações (3 pontos):**
- *Centralizar valores constantes em arquivo ou classe de configuração.*

## Título: 🖥️ Interface do Usuário 🖥️ <br>
  **❤️ Barra de Vida (4 pontos):**
- *Indicador visual da vida atual de cada personagem acima do sprite.*

### Controles e Interação
- **Movimentação:** Controle de direção dos personagens controlados via teclado (`W`, `A`, `S`, `D`).
- **Ações de Unidade:**
  - `Espaço`: Realiza a ação de ataque com a unidade selecionada.
  - `M`: Alterna o estado de montaria (para unidades compatíveis).
  - `C`: Coleta recursos dísponiveis por perto (para unidades compatíveis).
  - `Tab`: Alterna entre os filtros de tipo.
- **Criação de Unidades:** Atalhos numéricos para spawnar entidades no mapa:
  - `1`: Aldeão
  - `2`: Arqueiro
  - `3`: Cavaleiro
 
  ---

  ## Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/java-of-empires.git
``
2. **Navegue até o diretório:**
- `cd projeto-2-pedro_valete-davi_capella/`

3. **Execute:**

- `./gradlew run`

---

## Decisões de Design Importantes

### 1. Gestão de Foco
Um desafio comum em jogos **Swing** com interface que utilizam botões é a perda de foco do teclado.
- **Problema:** Ao clicar nos botões de criação ou filtros ("Criar Arqueiro", "Filtro Aldeão"), o `JFrame` perdia o foco, inutilizando todas as teclas clicadas a seguir.
- **Solução:** Implementamos `setFocusable(false)` em todos os botões presentes na interface.  
Isso permite a interação com o mouse, mas devolve imediatamente o foco para a janela principal.

### 2. Centralização de Constantes
Para evitar o uso de números ou strings soltas no código sem explicação e facilitar a manutenção futura.
- **Problema:** Valores como os de ataque e movimentação dos personagens dificultam alterações e geram inconsistências.
- **Solução:** Implementação de uma classe dedicada para armazenar todos os atributos globais e estáticos do projeto.
- **Benefício:** Permite alterar configurações gerais do jogo (como os valores citados) em um único arquivo, propagando a mudança automaticamente para todo o projeto.
