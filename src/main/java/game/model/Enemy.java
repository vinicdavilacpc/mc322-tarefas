package game.model;

import game.core.Battle;
import game.effect.Effect;
import game.effect.StrengthEffect;
import game.view.GameConsoleView;

/**
 * Representa o adversário controlado pelo sistema.
 * Contém a lógica de tomada de decisão do inimigo durante o combate.
 */
public class Enemy extends Entity {
    /** Dano base causado pelo inimigo. */
    private int damage;
    /** Quantidade de escudo gerado nas ações defensivas. */
    private int defense; 
    /** Valor pelo qual o dano base aumenta nas ações de buff. */
    private int strength;

    /**
     * Construtor da classe Enemy.
     *
     * @param name     Nome do inimigo.
     * @param health   Vida máxima.
     * @param shield   Escudo inicial.
     * @param damage   Dano base de ataque.
     * @param defense  Escudo gerado na defesa.
     * @param strength Aumento de poder de ataque.
     * @param color    Cor de exibição no console.
     */
    public Enemy(String name, int health, int shield, int damage, int defense, int strength, String color) {
        super(name, health, shield, color);
        this.damage = damage;
        this.defense = defense;
        this.strength = strength;
    }

    /** @return O dano base do inimigo. */
    public int getDamage() { return damage; }
    /** @return O escudo gerado pela defesa do inimigo. */
    public int getDefense() { return defense; }
    /** @return O incremento de força por buff. */
    public int getStrength() { return strength; }

    /**
     * Calcula o dano total do inimigo, somando o dano base com quaisquer efeitos de Força ("Strength") ativos.
     *
     * @return O dano total calculado.
     */
    public int getTotalDamage() {
        int total = this.damage;
        for (Effect effect : this.getEffects()) {
            if (effect.getName().equals("Strength")) {
                total += effect.getAmount();
            }
        }
        return total;
    }

    /**
     * Realiza um ataque contra o herói especificado.
     *
     * @param hero   O alvo do ataque.
     * @param damage Quantidade de dano infligido.
     */
    public void attack(Hero hero, int damage) {
        hero.takeDamage(damage);
    }

    /**
     * Executa a lógica do turno do inimigo com base num ciclo pré-definido de ações.
     * * Turno 1: Ataque;
     * Turno 2: Defesa;
     * Turno 3: Buff (Força).
     *
     * @param hero   Herói enfrentado.
     * @param turn   Número de estado da ação (1, 2 ou 3) indicando qual intenção o inimigo realizará.
     * @param battle Instância da batalha (usada para inscrever efeitos de status).
     * @param view   O visualizador para exibir o texto das ações no console.
     * @return O próximo estado de turno para o inimigo (ex: 2 se acabou de executar 1).
     */
    public int enemyTurn(Hero hero, int turn, Battle battle, GameConsoleView view) {
        view.displayEnemyAction("\n===========================================");
        view.displayEnemyAction(String.format("It's the opponent's turn! %s is choosing their move.", this.getColoredName()));

        if (turn == 1) {
            int realDamage = this.getTotalDamage();
            view.displayEnemyAction(String.format("%s attacks for %d damage!", this.getColoredName(), realDamage));
            this.attack(hero, realDamage);
            view.displayEnemyAction(String.format("%s's health is now %d.", hero.getColoredName(), hero.getHealth()));
            turn = 2;
            
        } else if (turn == 2) {
            view.displayEnemyAction(String.format("%s used the shield!", this.getColoredName()));
            this.gainShield(this.getDefense());
            view.displayEnemyAction(String.format("%s's shield is now %d.", this.getColoredName(), this.getShield()));
            turn = 3;
            
        } else if (turn == 3) {
            view.displayEnemyAction(String.format("%s raised their damage!", this.getColoredName()));
            StrengthEffect appliedEffect = new StrengthEffect("Strength", this, this.getStrength());
            boolean isNew = this.applyEffect(appliedEffect);
            if (isNew) {
                battle.subscribe(appliedEffect);
            }
            view.displayEnemyAction(String.format("%s's attack is now %d.", this.getColoredName(), this.getTotalDamage()));
            turn = 1;
        }

        view.displayEnemyAction("===========================================\n");
        return turn;
    }
}