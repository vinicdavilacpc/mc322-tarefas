# Projeto MC322 - Roguelike Pokémon Deckbuilder

Este projeto foi desenvolvido como parte dos laboratórios da disciplina **MC322 - Programação Orientada a Objetos**.

O objetivo é implementar um jogo inspirado em **Slay the Spire** mas com temática Pokémon, no qual o jogador utiliza um **baralho de cartas** para derrotar inimigos em batalhas por turno.

O projeto foi desenvolvido em **Java** e executado via terminal.

# Estrutura do Projeto

O projeto segue a estrutura padrão criada pelo VSCode para projetos Java:

```text
.
├─ src/
│  ├─ App.java
│  ├─ Heroi.java
│  ├─ Inimigo.java
│  ├─ Carta.java
│  ├─ CartaDano.java
│  ├─ CartaEscudo.java
│  └─ ...
├─ lib/
├─ bin/
└─ README.md
```

Onde:

- **src** — contém todos os arquivos `.java` do projeto  
- **lib** — pasta reservada para dependências externas (não utilizada neste projeto)  
- **bin** — arquivos `.class` gerados após a compilação  

# Como Compilar o Projeto

No diretório raiz do projeto, execute:

```bash
javac -d bin $(find src -name "*.java")
```

Esse comando compila todos os arquivos `.java` dentro da pasta `src` e coloca os arquivos compilados (`.class`) na pasta `bin`.

# Como Executar o Projeto

Após compilar, execute:

```bash
java -cp bin App
```

Isso iniciará o programa e o sistema de combate será executado no terminal.

# Como Jogar

Após compilar, o jogo se inicia mostrando uma tela com uma mensagem de aparição do adversário e as informações do jogador e do adversário, como vida, energia e escudo (Obs.: o Inimigo não possui energia e seus ataques são premeditados a cada rodada):

```
A wild Pikachu has appeared!

-------------------------------------------
Charmander
HP: [████████████████████] 20/20 | Energy: 5 | Shield: 0
VS.
Pikachu
HP: [████████████████████] 20/20 | Shield: 0
-------------------------------------------
```

A cada rodada, o inimigo premedita seus ataques para tornar o jogo mais dinâmico e guiar a escolha do jogador:

```
Pikachu is powering up! (Damage: 5)
```
ou
```
Pikachu is raising their defense! (Shield: 3)
```
ou
```
Pikachu is getting stronger (Damage increase: 2)
```

Em seguida, o jogo começa com o turno do jogador e aparecerá um menu com as opções da rodada, as opções serão aleatórias, com 5 cartas puxadas da pilha de compra no início de cada rodada e descartadas para a pilha de descarte após seu uso, além da energia restante do jogador e um input com a escolha a ser feita:

```
Charmander, you're up! Choose your next move.
Energy remaining: 5/5
1: Use Harden (Cost: 1) - Grants 2 points of shield
2: Use Barrier (Cost: 2) - Grants 3 points of shield
3: Use Iron Defense (Cost: 5) - Grants 10 points of shield
4: Use Acid Armor (Cost: 4) - Grants 7 points of shield
5: Use Shell Armor (Cost: 3) - Grants 5 points of shield
6. End Round
Your choice:
```

As opções selecionadas mostrarão uma mensagem na tela que informa o que foi feito pelo jogador. A rodada termina apenas quando o jogador decidir encerrar a rodada, no caso acima, pela opção 6. (Obs.: Essa opção é sempre a última da lista, então se o jogador utilizar alguma carta, ela irá sumir da mão do jogador e ir para a pilha de descarte, fazendo com que a opção de encerrar turno vá para o índice 5 e assim por diante).

Exemplo de ataque do jogador com scratch:

```
>>> Charmander used Scratch!
```

Assim que a rodada do jogador termina, o inimigo fará seu movimento, podendo atacar ou se defender com valores de dano e defesa fixos, uma mensagem na tela mostrará se o inimigo atacou:

```
===========================================
It's the opponent's turn! Pikachu is choosing their move.
Pikachu attacks for 5 damage!
Charmander's health is now 15.
===========================================
```

Se defendeu:
```
===========================================
It's the opponent's turn! Pikachu is choosing their move.
Pikachu used the Shield!
Pikachu's shield is now 3.
===========================================
```

Ou aumentou seu dano:
```
===========================================
It's the opponent's turn! Pikachu is choosing their move.
Pikachu raised their damage!
Pikachu's attack is now 2.
===========================================
```

O jogo termina quando a vida do jogador ou do inimigo chegar a 0:

```
End of the game!
Pikachu triumphed over Charmander!
```
(Derrota do jogador)

```
Enemy fainted! Pikachu health is now 0

End of the game!
Charmander rises victorious defeating Pikachu!
```
(Vitória do jogador)

# Cartas
Cartas são o ponto chave de cada batalha, é com elas que o jogador faz as ações de cada rodada.

Cartas podem ser de ataque:
```
3: Use Flamethrower (Cost: 5) - Deals 8 points of damage
```
(Dá 8 pontos de dano no alvo)

De defesa:
```
1: Use Barrier (Cost: 2) - Grants 3 points of shield
```
(Dá 3 pontos de escudo para o usuário)

Ou de efeitos:
```
4: Use Light Ball (Cost: 5) - Increases 2 points of damage
```
(Aumenta o dano do usuário em 2 pontos)

# Efeitos
Efeitos são aplicados pelos personagens a cada rodada de acordo com a descrição da carta de efeito utilizada.

Os efeitos podem ser buffs (melhoram as habilidades de quem os usou. Ex.: Força) ou debuffs (prejudicam o alvo a que foi atingido. Ex.: Veneno)

Exemplo de Buff que aumenta o dano em 2 pontos (Força):

```
3: Use Light Ball (Cost: 5) - Increases 2 points of damage
```

Exemplo de Debuff que dá dano por rodada (Veneno):

```
2: Use Poison Jab (Cost: 3) - Triggers poison and causes 3 points of damage
```

A lista de efeitos aplicados é anotada abaixo da vida de cada personagem:

```
Pikachu
HP: [████████████████░░░░] 16/20 | Shield: 0
Active effects: 
Strength (2) Poison (3) 
```
Obs.: Ao lado de cada buff fica o quanto ele melhora o dano, Força é permanente até o fim da batalha. Ao lado de cada debuff fica o quanto de dano ele está dando, Veneno dá x pontos de dano e a cada rodada ele dá x - 1 pontos de dano, até que x - n seja igual a 0 (onde n é o número de rodadas).

# Tecnologias Utilizadas

- Java 25
- Visual Studio Code
- Git e GitHub

# Autores

Projeto desenvolvido por:

- Isabella Favaron Rover, RA281248
- Vinícius Cappelli d'Avila, RA185507