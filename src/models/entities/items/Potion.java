package models.entities.items;

import models.referencies.Rank;
import models.referencies.TypePotion;

public class Potion extends Item {
    private TypePotion typePotion;

    Potion(String nom, float weight, double value, String rarity, Rank rang, TypePotion typePotion) {
        super(nom, weight, value, rarity, rang);
        setTypePotion(typePotion);
    }

    public TypePotion getTypePotion() {
        return typePotion;
    }

    public void setTypePotion(TypePotion typePotion) {
        this.typePotion = typePotion;
    }
}
