package models.entities.dao.DaoEntity;

import models.entities.entity.Monster;

import java.util.List;

public class MonsterDaoImpl extends DaoEntityImpl<Monster> implements MonsterDao {
    @Override
    public List<Monster> recupererMonsters() {
        return persist.values().stream().toList();
    }
}
