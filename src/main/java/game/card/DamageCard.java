package game.card;

import game.effect.Effect;
import game.map_event.Battle;
import game.model.Entity;
import game.model.Hero;

/**
 * Representa uma carta com foco ofensivo. 
 * Ao ser usada, consome a energia do herói e aplica dano direto à saúde do alvo.
 */
public class DamageCard extends Card {
    /** Valor de dano base causado pela carta. */
    private int damage;

    /**
     * Construtor da carta de dano.
     *
     * @param name        O nome da carta.
     * @param description A descrição da carta.
     * @param energyCost  O custo de energia para utilização.
     * @param damage      A quantidade de dano gerada na ativação.
     * @param color       A cor ANSI da carta.
     */
    public DamageCard(String name, String description, int energyCost, int damage, String color) {
        super(name, description, energyCost, color);
        this.damage = damage;
    }

    /** @return A quantidade de dano base da carta. */
    public int getDamage() { return damage; }

    /**
     * Utiliza a carta, aplicando dano ao alvo modificado pelos efeitos de Força ("Strength") 
     * que o herói possa ter, consumindo a energia devida em seguida.
     *
     * @param hero   O herói atacante.
     * @param target A entidade alvo do dano.
     * @param battle O contexto da batalha atual.
     */
    @Override
    public void use(Hero hero, Entity target, Battle battle) {
        int totalDamage = this.damage;

        for (Effect effect : hero.getEffects()) {
            if (effect.getName().equals("Strength")) {
                totalDamage += effect.getAmount();
            }
        }
        
        target.takeDamage(totalDamage);
        hero.drainEnergy(this.getEnergyCost());
    }
}