package models.facades;

import models.entities.dao.Dao;
import models.entities.dao.DaoEntity.*;
import models.entities.dao.DaoFields.DaoFields;
import models.entities.dao.DaoFields.DaoFieldsImpl;
import models.entities.entity.FactoryEntity;
import models.entities.entity.Hunter;
import models.entities.entity.Monster;
import models.entities.fields.Dungeon;
import models.entities.fields.FactoryFields;
import models.entities.items.Equipment;
import models.entities.items.FactoryItem;
import models.entities.items.Item;
import models.exception.DungeonException;
import models.exception.EntityException;
import models.exception.MonsterException;
import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypePotion;

import java.util.*;

public class ModelImpl implements IModel {
    private static final List<Hunter> HUNTERS = new ArrayList<>();
    private static final List<Monster> MONSTERS = new ArrayList<>();
    private static final List<Dungeon> DUNGEONS = new ArrayList<>();

    DaoEntity daoEntity = new DaoEntityImpl();
    DaoHunter daoHunter = new DaoHunterImpl();
    MonsterDao daoMonster = new MonsterDaoImpl();
    DaoFields daoFields = new DaoFieldsImpl();

    public ModelImpl() {
        try {
            init();
        } catch (EntityException | MonsterException | DungeonException e) {
            throw new RuntimeException("Erreur lors de l'initialisation du modèle : " + e.getMessage(), e);
        }
    }

    private void init() throws EntityException, MonsterException, DungeonException {
        // HUNTERS
        Hunter hunter = FactoryEntity.createHunter("Sung Jin-Woo", 9999, Rank.SS);

        Equipment dagueKasaka = (Equipment) FactoryItem.createEquipment(
                "Dague empoisonnée de Kasaka",
                Rank.S,
                TypeEquipement.ARME,
                250
        );

        Equipment armureOmbre = (Equipment) FactoryItem.createEquipment(
                "Armure du monarque des ombres",
                Rank.SS,
                TypeEquipement.ARMURE,
                500
        );

        Equipment anneauMana = (Equipment) FactoryItem.createEquipment(
                "Anneau de mana noir",
                Rank.A,
                TypeEquipement.ANNEAU,
                120
        );

        Item potionSoin = FactoryItem.createPotion(
                "Potion de soin supérieure",
                Rank.B,
                TypePotion.HEALING
        );

        Item potionMana = FactoryItem.createPotion(
                "Potion de mana supérieure",
                Rank.B,
                TypePotion.MANA
        );

        hunter.addNewEquipments(dagueKasaka);
        hunter.addNewEquipments(armureOmbre);
        hunter.addNewEquipments(anneauMana);
        hunter.addNewItems(potionSoin);
        hunter.addNewItems(potionMana);

        daoHunter.create(hunter);
//        HUNTERS.add(hunter);

        // MONSTRES
        Monster monarque = FactoryEntity.createMonster(
                "Monarque",
                50,
                List.of(potionMana)
        );

        Monster igris = FactoryEntity.createMonster(
                "Igris le Rouge Sang",
                35,
                List.of(dagueKasaka)
        );

//        MONSTERS.add(monarque);
//        MONSTERS.add(igris);

        daoMonster.create(monarque);
        daoMonster.create(igris);
        // DONJONS
        Dungeon dungeon = FactoryFields.createDungeon(
                "Hapjeong Subway Station",
                monarque,
                Map.of(monarque, 1, igris, 3),
                Rank.E
        );

        daoFields.create(dungeon);
//        DUNGEONS.add(dungeon);


    }

    @Override
    public List<Hunter> recupererHunters() {
        return daoHunter.recupererHunters();
//        return Collections.unmodifiableList(HUNTERS);
    }

    @Override
    public List<Monster> recupererMonsters() {
        return daoMonster.recupererMonsters();
//        return Collections.unmodifiableList(MONSTERS);
    }

    @Override
    public List<Dungeon> recupererDungeons() {
        return daoFields.recupererDungeons();
//        return Collections.unmodifiableList(DUNGEONS);
    }

    @Override
    public void ajouterHunter(Hunter hunter) {
        daoHunter.create(hunter);
    }

    @Override
    public void ajouterMonster(Monster monster) {
        daoMonster.create(monster);
    }

    @Override
    public void ajouterDungeon(Dungeon dungeon) {
        daoFields.create(dungeon);
    }

    @Override
    public void supprimerHunter(Hunter hunter) {
        daoHunter.delete(hunter.getId());
    }

    @Override
    public void supprimerMonster(Monster monster) {
        daoMonster.delete(monster.getId());
    }

    @Override
    public void supprimerDungeon(Dungeon dungeon) {
        daoFields.delete(dungeon.getId());
    }
}