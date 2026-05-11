package models.facades;

import models.entities.fields.Dungeon;
import models.entities.entity.Hunter;
import models.entities.entity.Monster;

import java.util.List;

public interface IModel {
    List<Hunter> recupererHunters();

    List<Monster> recupererMonsters();

    List<Dungeon> recupererDungeons();

    void ajouterHunter(Hunter hunter);

    void ajouterMonster(Monster monster);

    void ajouterDungeon(Dungeon dungeon);

    void supprimerHunter(Hunter hunter);

    void supprimerMonster(Monster monster);

    void supprimerDungeon(Dungeon dungeon);
}