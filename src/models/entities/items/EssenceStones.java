package models.entities.items;

import models.referencies.Rank;
import models.referencies.TypeGem;

public class EssenceStones extends Item {
    private TypeGem typeGem;

    EssenceStones( String nom, float weight, double value, String rarity, Rank rang, TypeGem typeGem) {
        super( nom, weight, value, rarity, rang);
    }

    public TypeGem getTypeGem() {
        return typeGem;
    }

    public void setTypeGem(TypeGem typeGem) {
        this.typeGem = typeGem;
    }
}
