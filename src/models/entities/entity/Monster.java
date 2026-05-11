package models.entities.entity;

import models.entities.items.Item;
import models.exception.EntityException;
import models.exception.MonsterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Monster extends Entity {
    private int level;
    private List<Item> loots = new ArrayList<>();

    public Monster(String name, int level) throws EntityException, MonsterException {
        super.setName(name);
        setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) throws MonsterException {
        if (level < 1 || level > 100) {
            throw new MonsterException("Le level du monstre doit être compris entre 1 et 100.");
        }

        this.level = level;
    }

    public List<Item> getItemList() {
        return Collections.unmodifiableList(loots);
    }

    void setLoots(List<Item> loots) {
        if (loots == null) {
            this.loots = new ArrayList<>();
        } else {
            this.loots = new ArrayList<>(loots);
        }
    }

    public void addLoot(Item item) throws MonsterException {
        if (item == null) {
            throw new MonsterException("Impossible d'ajouter un loot null.");
        }

        loots.add(item);
    }

    public void removeLoot(Item item) throws MonsterException {
        if (item == null) {
            throw new MonsterException("Impossible de supprimer un loot null.");
        }

        loots.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Monster monster = (Monster) o;
        return level == monster.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), level);
    }

    @Override
    public String sePresenter() {
        return """
                Monstre de niveau %d s'appelant %s
                """.formatted(
                getLevel(),
                getName()
        );
    }
}