package models.facades;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModelImpl implements IModel {
    private static final List<Hunter> HUNTERS = new ArrayList<>();
    private static final List<Monster> MONSTERS = new ArrayList<>();
    private static final List<Dungeon> DUNGEONS = new ArrayList<>();

    public ModelImpl() {
        try {
            init();
        } catch (EntityException | MonsterException | DungeonException e) {
            throw new RuntimeException("Erreur lors de l'initialisation du modèle : " + e.getMessage(), e);
        }
    }

    private static void init() throws EntityException, MonsterException, DungeonException {
        HUNTERS.clear();
        MONSTERS.clear();
        DUNGEONS.clear();

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

        HUNTERS.add(hunter);

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

        MONSTERS.add(monarque);
        MONSTERS.add(igris);

        // DONJONS
        DUNGEONS.add(FactoryFields.createDungeon(
                "Hapjeong Subway Station",
                monarque,
                Map.of(monarque, 1, igris, 3),
                Rank.E
        ));
    }

    @Override
    public List<Hunter> recupererHunters() {
        return Collections.unmodifiableList(HUNTERS);
    }

    @Override
    public List<Monster> recupererMonsters() {
        return Collections.unmodifiableList(MONSTERS);
    }

    @Override
    public List<Dungeon> recupererDungeons() {
        return Collections.unmodifiableList(DUNGEONS);
    }

    @Override
    public void ajouterHunter(Hunter hunter) {
        HUNTERS.add(hunter);
    }

    @Override
    public void ajouterMonster(Monster monster) {
        MONSTERS.add(monster);
    }

    @Override
    public void ajouterDungeon(Dungeon dungeon) {
        DUNGEONS.add(dungeon);
    }

    @Override
    public void supprimerHunter(Hunter hunter) {
        HUNTERS.remove(hunter);
    }

    @Override
    public void supprimerMonster(Monster monster) {
        MONSTERS.remove(monster);
    }

    @Override
    public void supprimerDungeon(Dungeon dungeon) {
        DUNGEONS.remove(dungeon);
    }
}