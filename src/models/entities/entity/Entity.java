package models.entities.entity;

import models.exception.EntityException;
import models.referencies.Rank;

import java.util.Objects;

public abstract class Entity {
    private String name;
    private double power;
    private Rank rang;

    Entity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String nom) throws EntityException {
        if (nom == null || nom.isBlank()) {
            throw new EntityException("Le nom ne peut pas être vide.");
        }

        this.name = nom;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) throws EntityException {
        if (power < 0) {
            throw new EntityException("La puissance ne peut pas être négative.");
        }

        this.power = power;
    }

    public Rank getRang() {
        return rang;
    }

    public void setRang(Rank rang) throws EntityException {
        if (rang == null) {
            throw new EntityException("Le rang ne peut pas être null.");
        }

        this.rang = rang;
    }

    public abstract String sePresenter();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}