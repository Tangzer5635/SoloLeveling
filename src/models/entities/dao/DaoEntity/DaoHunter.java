package models.entities.dao.DaoEntity;

import models.entities.entity.Hunter;
import models.entities.entity.Monster;

import java.util.List;

public interface DaoHunter extends DaoEntity<Hunter>{

    List<Hunter> recupererHunters();
}
