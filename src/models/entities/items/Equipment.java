package models.entities.items;

import models.referencies.Rank;
import models.referencies.TypeEquipement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipment extends Item {
    private List<Equipment> equipments = new ArrayList<>();
    private TypeEquipement typeEquipement;
    private int capacity;

    Equipment(String nom, float weight, double value, String rarity, Rank rang, TypeEquipement typeEquipement, int capacity) {
        super(nom, weight, value, rarity, rang);
        setTypeEquipement(typeEquipement);
        setCapacity(capacity);
    }

    public TypeEquipement getTypeEquipement() {
        return typeEquipement;
    }

    public void setTypeEquipement(TypeEquipement typeEquipement) {
        this.typeEquipement = typeEquipement;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equals(equipments, equipment.equipments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), equipments);
    }

    public String sePresenter() {
        return "%s de rang %s".formatted(
                getTypeEquipement(),
                getRang()
        );
    }

    public static String presenterListeEquip(java.util.List<Equipment> equipments) {
        return equipments.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Equipment::sePresenter,
                        java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> " %d x %s ".formatted(entry.getValue(), entry.getKey()))
                .collect(java.util.stream.Collectors.joining(" | ", "", ""));
    }
}