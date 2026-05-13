package models.entities.dao.DaoEntity;

import models.entities.entity.Hunter;
import models.entities.entity.Monster;

import java.util.List;

public class DaoHunterImpl extends DaoEntityImpl<Hunter> implements DaoHunter{

    public List<Hunter> recupererHunters(){
        return persist.values().stream().toList();
    }
}
