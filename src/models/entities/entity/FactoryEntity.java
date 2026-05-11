package models.entities.entity;

import models.entities.items.Item;
import models.exception.EntityException;
import models.exception.MonsterException;
import models.referencies.Rank;

import java.util.List;

public final class FactoryEntity {
    private FactoryEntity() {
    }

    public static Hunter createHunter(String nom, double power, Rank rang) throws EntityException {
        Hunter hunter = new Hunter(nom);
        hunter.setPower(power);
        hunter.setRang(rang);
        return hunter;
    }

    public static Monster createMonster(String name, int level, List<Item> loots) throws EntityException, MonsterException {
        Monster monster = new Monster(name, level);
        monster.setLoots(loots);
        return monster;
    }
}