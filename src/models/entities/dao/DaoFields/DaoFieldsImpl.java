package models.entities.dao.DaoFields;

import models.entities.dao.MemoireDao;
import models.entities.fields.Dungeon;

import java.util.List;

public class DaoFieldsImpl extends MemoireDao<Dungeon> implements DaoFields {

    @Override
    public List<Dungeon> recupererDungeons() {
        return persist.values().stream().toList();
    }
}
