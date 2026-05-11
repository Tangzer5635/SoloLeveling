package models.entities.items;

import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypeGem;
import models.referencies.TypePotion;

public final class FactoryItem {
    private FactoryItem() {
    }

    public static Item createEquipment(String nom, Rank rang, TypeEquipement typeEquipement, int capacity) {
        return new Equipment(nom, typeEquipement.getWeight(), typeEquipement.getValue(), typeEquipement.getRarity(), rang, typeEquipement, capacity);
    }

    public static Item createGem(String nom, float weight, double value, String rarity, Rank rang, TypeGem typeGem) {
        return new EssenceStones(nom, weight, value, rarity, rang, typeGem);
    }

    public static Item createPotion(String nom, Rank rang, TypePotion typePotion) {
        return new Potion(
                nom,typePotion.getWeight(),typePotion.getValue(),typePotion.getRarity(),rang,typePotion);
    }
}