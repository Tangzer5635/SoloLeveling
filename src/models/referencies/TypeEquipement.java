package models.referencies;

public enum TypeEquipement {
    ARME(5.0f, 100.0, "RARE"),
    ARMURE(12.0f, 180.0, "EPIC"),
    CASQUE(3.5f, 75.0, "UNCOMMON"),
    GANTS(1.5f, 40.0, "COMMON"),
    BOTTES(2.5f, 60.0, "COMMON"),
    ANNEAU(0.2f, 250.0, "LEGENDARY"),
    COLLIER(0.4f, 220.0, "EPIC");

    private final float weight;
    private final double value;
    private final String rarity;

    TypeEquipement(float weight, double value, String rarity) {
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