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

Após compilar, o jogo se inicia mostrando uma tela com uma mensagem de aparição do adversário e as informações do jogador e do adversário:

```
A wild Pikachu has appeared!

-------------------------------------------
Charmander
(Health: 20 | Energy: 5 | Shield: 0)
VS.
Pikachu
(Health: 20 | Shield: 0)
-------------------------------------------
```

Em seguida, o jogo começa com o turno do jogador e aparecerá um menu com as opções da rodada, além da energia restante do jogador e um input com a escolha a ser feita:

```
Charmander, you're up! Choose your next move.
Energy remaining: 5/5
1: Use Scratch (Cost: 2, Damage: 4)
2: Use Flamethrower (Cost: 4, Damage: 8)
3: Use Shell Armor (Cost: 3, Defense: 5)
4: Use Iron Defense (Cost: 5, Defense: 10)
5: End round
Your choice:
```

As opções selecionadas mostrarão uma mensagem na tela que informa o que foi feito pelo jogador. A rodada termina apenas quando o jogador decidir encerrar a rodada, no caso acima, pela opção 5.

Exemplo de ataque do jogador com scratch:

```
>>> Charmander used Scratch!

Enemy hit! Pikachu health is now 16
```

Assim que a rodada do jogador termina, o inimigo fará seu movimento, podendo atacar ou se defender com uma carta de ataque ou carta de escudo, respectivamente e uma mensagem na tela mostrará se o inimigo atacou:

```
===========================================
It's the opponent's turn! Pikachu is choosing their move.
Pikachu attacks for 5 damage!
Charmander's health is now 15.
===========================================
```

Ou se defendeu:
```
===========================================
It's the opponent's turn! Pikachu is choosing their move.
Pikachu used Barrier!
Pikachu's shield is now 3.
===========================================
```

O jogo termina quando a vida do jogador ou do inimigo chegar a 0:

```
End of the game!
Pikachu triumphed over Charmander!
```

```
Enemy fainted! Pikachu health is now 0

End of the game!
Charmander rises victorious defeating Pikachu!
```

# Tecnologias Utilizadas

- Java 25
- Visual Studio Code
- Git e GitHub

# Autores

Projeto desenvolvido por:

- Isabella Favaron Rover, RA281248
- Vinícius Cappelli d'Avila, RA185507