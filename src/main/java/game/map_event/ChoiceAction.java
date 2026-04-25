package game.map_event;

import game.model.Hero;

@FunctionalInterface
public interface ChoiceAction {
    void apply(Hero hero);
}