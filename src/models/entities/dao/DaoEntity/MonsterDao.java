package models.entities.dao.DaoEntity;

import models.entities.entity.Monster;

import java.util.List;

public interface MonsterDao extends DaoEntity<Monster>{

    List<Monster> recupererMonsters();


}
