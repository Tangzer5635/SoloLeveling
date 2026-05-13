package models.entities.items;

import models.entities.AbstractEntity;
import models.referencies.Rank;

import java.util.Objects;

public class Item extends AbstractEntity {
    private String nom;
    private float weight;
    private double value;
    private String rarity;
    private Rank rang;

    Item(String nom, float weight, double value, String rarity, Rank rang) {
        setNom(nom);
        setWeight(weight);
        setValue(value);
        setRarity(rarity);
        setRang(rang);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public Rank getRang() {
        return rang;
    }

    public void setRang(Rank rang) {
        this.rang = rang;
    }

    public String sePresenter() {
        return "%s de rang %s qui vaut valeur %f".formatted(
                getNom(),
                getRang(),
                getValue()
        );
    }

    public static String presenterListeItem(java.util.List<Item> items) {
        return items.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Item::sePresenter,
                        java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> " %d x %s ".formatted(entry.getValue(), entry.getKey()))
                .collect(java.util.stream.Collectors.joining(" | ", "", ""));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Float.compare(weight, item.weight) == 0 && Double.compare(value, item.value) == 0 && Objects.equals(nom, item.nom) && Objects.equals(rarity, item.rarity) && rang == item.rang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nom, weight, value, rarity, rang);
    }
}