package models.referencies;

public enum Rank {
    E(1),
    D(2),
    C(3),
    B(4),
    A(5),
    S(6),
    SS(7),
    NATIONAL(8);

    private final int powerLevel;

    Rank(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public int getPowerLevel() {
        return powerLevel;
    }
}