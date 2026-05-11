package models.entities.fields;

import models.entities.entity.Monster;
import models.exception.DungeonException;
import models.referencies.Rank;

import java.util.Map;

public final class FactoryFields {
    private FactoryFields() {
    }

    public static Dungeon createDungeon(String nom, Monster boss, Map<Monster, Integer> monsters, Rank rang) throws DungeonException {
        return new Dungeon(nom, boss, monsters, rang);
    }
}