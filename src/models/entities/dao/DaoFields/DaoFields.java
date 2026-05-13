package models.entities.dao.DaoFields;

import models.entities.dao.Dao;
import models.entities.fields.Dungeon;

import java.util.List;

public interface DaoFields extends Dao<Dungeon> {
    List<Dungeon> recupererDungeons();
}
