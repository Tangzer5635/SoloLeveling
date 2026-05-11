package models.referencies;

public enum TypePotion {
    HEALING(0.5f, 50.0, "COMMON"),
    MANA(0.4f, 60.0, "UNCOMMON"),
    STAMINA(0.6f, 45.0, "COMMON"),
    ANTIDOTE(0.3f, 75.0, "RARE");

    private final float weight;
    private final double value;
    private final String rarity;

    TypePotion(float weight, double value, String rarity) {
        this.weight = weight;
        this.value = value;
        this.rarity = rarity;
    }

    public float getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }

    public String getRarity() {
        return rarity;
    }
}