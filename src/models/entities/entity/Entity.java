package models.entities.entity;

import models.entities.AbstractEntity;
import models.exception.EntityException;
import models.referencies.Rank;

import java.util.Objects;

public abstract class Entity extends AbstractEntity {
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
        if (!super.equals(o)) return false;
        Entity entity = (Entity) o;
        return Double.compare(power, entity.power) == 0 && Objects.equals(name, entity.name) && rang == entity.rang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, power, rang);
    }
}