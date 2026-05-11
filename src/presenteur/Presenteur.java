package presenteur;

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
import models.facades.IModel;
import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypePotion;
import views.facades.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Presenteur {
    private IModel model;
    private IView view;

    private final int CHOIX_SORTIE = 0;

    public Presenteur(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    private final List<String> MENU_PRINCIPAL = new ArrayList<>(Arrays.asList(
            "Enregistrer",
            "Afficher",
            "Modifier"
    ));

    private final List<String> MENU_REG = new ArrayList<>(Arrays.asList(
            "Enregistrer un Hunteur",
            "Enregistrer un Monstre",
            "Enregistrer un Dungeon"
    ));

    private final List<String> MENU_AFF = new ArrayList<>(Arrays.asList(
            "Afficher un Hunteur",
            "Afficher un Monstre",
            "Afficher un Dungeon"
    ));

    private final List<String> MENU_MOD = new ArrayList<>(Arrays.asList(
            "Modifier un Hunteur",
            "Modifier un Monstre",
            "Modifier un Dungeon"
    ));

    private final int TAILLE_MENU = MENU_PRINCIPAL.size();
    private final int TAILLE_REG = MENU_REG.size();
    private final int TAILLE_AFF = MENU_AFF.size();
    private final int TAILLE_MOD = MENU_MOD.size();

    public void start() {
        int choix;
        do {
            view.afficherMenuPrincipal(MENU_PRINCIPAL);
            choix = view.saisirChoixMenu(TAILLE_MENU);
            gestionMenuPrincipal(choix);
        } while (choix != CHOIX_SORTIE);
    }

    private void gestionMenuPrincipal(int choix) {
        switch (choix) {
            case 1 -> registerSubMenu();
            case 2 -> showSubMenu();
            case 3 -> showModMenu();
            default -> {
            }
        }
    }

    private void registerSubMenu() {
        int choix;
        do {
            view.afficherMenuRegister(MENU_REG);
            choix = view.saisirChoixMenu(TAILLE_REG);
            gestionSubMenuRegister(choix);
        } while (choix != CHOIX_SORTIE);
    }

    private void gestionSubMenuRegister(int choix) {
        switch (choix) {
            case 1 -> registerHunter();
            case 2 -> registerMonster();
            case 3 -> registerDungeon();
            default -> {
            }
        }
    }

    private void registerHunter() {
        try {
            String nom = view.saisirNom();
            double power = view.saisirPower();
            Rank rank = view.choisirRank();

            Hunter hunter = FactoryEntity.createHunter(nom, power, rank);

            model.ajouterHunter(hunter);
            view.afficherMessage("""
                    Hunter ajouté avec succès !

                    Nom   : %s
                    Power : %.2f
                    Rang  : %s
                    """.formatted(nom, power, rank));
        } catch (EntityException e) {
            view.afficherMessage("Erreur lors de la création du hunter : " + e.getMessage());
        }
    }

    private void registerMonster() {
        try {
            String name = view.saisirNom();
            int level = view.saisirLevel();

            Monster monster = FactoryEntity.createMonster(name, level, List.of());

            model.ajouterMonster(monster);
            view.afficherMessage("""
                    Monstre ajouté avec succès !

                    Nom   : %s
                    Level : %d
                    """.formatted(name, level));
        } catch (EntityException | MonsterException e) {
            view.afficherMessage("Erreur lors de la création du monstre : " + e.getMessage());
        }
    }

    private void registerDungeon() {
        try {
            String nom = view.saisirNom();
            List<Monster> monsters = model.recupererMonsters();
            Monster boss = monsters.isEmpty() ? null : monsters.get(0);
            Rank rang = view.choisirRank();

            Dungeon dungeon = FactoryFields.createDungeon(nom, boss, Map.of(), rang);

            model.ajouterDungeon(dungeon);
            view.afficherMessage("""
                    Dungeon ajouté avec succès !

                    Nom  : %s
                    Rang : %s
                    Boss : %s
                    """.formatted(
                    nom,
                    rang,
                    boss != null ? boss.getName() : "Aucun"
            ));
        } catch (DungeonException e) {
            view.afficherMessage("Erreur lors de la création du donjon : " + e.getMessage());
        }
    }

    private void showSubMenu() {
        int choix;
        do {
            view.afficherMenuAffichage(MENU_AFF);
            choix = view.saisirChoixMenu(TAILLE_AFF);
            gestionSubMenuShow(choix);
        } while (choix != CHOIX_SORTIE);
    }

    private void gestionSubMenuShow(int choix) {
        switch (choix) {
            case 1 -> showHunter();
            case 2 -> showMonster();
            case 3 -> showDungeon();
            default -> {
            }
        }
    }

    private void showHunter() {
        List<Hunter> hunters = model.recupererHunters();
        view.afficherLesHunters(hunters);
    }

    private void showMonster() {
        List<Monster> monsters = model.recupererMonsters();
        view.afficherLesMonstres(monsters);
    }

    private void showDungeon() {
        List<Dungeon> dungeons = model.recupererDungeons();
        view.afficherLesDungeons(dungeons);
    }

    private void showModMenu() {
        int choix;
        do {
            view.afficherMenuModification(MENU_MOD, "MODIFICATION");
            choix = view.saisirChoixMenu(TAILLE_MOD);
            gestionSubMenuMod(choix);
        } while (choix != CHOIX_SORTIE);
    }

    private void gestionSubMenuMod(int choix) {
        switch (choix) {
            case 1 -> editHunter();
            case 2 -> editMonster();
            case 3 -> editDungeon();
            default -> {
            }
        }
    }

    private void editHunter() {
        List<Hunter> hunters = model.recupererHunters();

        if (hunters.isEmpty()) {
            view.afficherMessage("Aucun hunter disponible.");
            return;
        }

        int choixHunter = view.choisirHunter(hunters);
        Hunter hunter = hunters.get(choixHunter - 1);

        List<String> menuAction = List.of("Ajouter un équipement", "Ajouter un item");
        view.afficherMenuModification(menuAction, "MODIFIER " + hunter.getName());

        int choixAction = view.saisirChoixMenu(menuAction.size());

        switch (choixAction) {
            case 1 -> ajouterEquipementAuHunter(hunter);
            case 2 -> ajouterItemAuHunter(hunter);
            default -> {
            }
        }
    }

    private void ajouterEquipementAuHunter(Hunter hunter) {
        try {
            String nom = view.saisirNom();

            int choixType = view.choisirTypeEquipement();
            TypeEquipement typeEquipement = TypeEquipement.values()[choixType - 1];

            Rank rank = view.choisirRank();
            int capacity = view.saisirCapacity();

            Equipment equipment = (Equipment) FactoryItem.createEquipment(
                    nom,
                    rank,
                    typeEquipement,
                    capacity
            );

            hunter.addNewEquipments(equipment);
            view.afficherMessage("Équipement ajouté avec succès à " + hunter.getName() + ".");
        } catch (EntityException e) {
            view.afficherMessage("Erreur lors de l'ajout de l'équipement : " + e.getMessage());
        }
    }

    private void ajouterItemAuHunter(Hunter hunter) {
        try {
            String nom = view.saisirNom();

            int choixType = view.choisirTypePotion();
            TypePotion typePotion = TypePotion.values()[choixType - 1];

            Rank rank = view.choisirRank();

            Item item = FactoryItem.createPotion(nom, rank, typePotion);

            hunter.addNewItems(item);
            view.afficherMessage("Item ajouté avec succès à " + hunter.getName() + ".");
        } catch (EntityException e) {
            view.afficherMessage("Erreur lors de l'ajout de l'item : " + e.getMessage());
        }
    }

    private void editMonster() {
        List<Monster> monsters = model.recupererMonsters();

        if (monsters.isEmpty()) {
            view.afficherMessage("Aucun monstre disponible.");
            return;
        }

        int choixMonster = view.choisirMonster(monsters);
        Monster monster = monsters.get(choixMonster - 1);

        List<String> menuAction = List.of(
                "Changer le nom",
                "Changer le level",
                "Ajouter un loot potion",
                "Ajouter un loot équipement"
        );

        view.afficherMenuModification(menuAction, "MODIFIER " + monster.getName());
        int choixAction = view.saisirChoixMenu(menuAction.size());

        switch (choixAction) {
            case 1 -> changerNomMonster(monster);
            case 2 -> changerLevelMonster(monster);
            case 3 -> ajouterPotionAuMonster(monster);
            case 4 -> ajouterEquipementAuMonster(monster);
            default -> {
            }
        }
    }

    private void changerNomMonster(Monster monster) {
        try {
            String ancienNom = monster.getName();
            String nouveauNom = view.saisirNom();

            monster.setName(nouveauNom);

            view.afficherMessage("""
                    Nom du monstre modifié avec succès !

                    Ancien nom  : %s
                    Nouveau nom : %s
                    """.formatted(ancienNom, nouveauNom));
        } catch (EntityException e) {
            view.afficherMessage("Erreur lors de la modification du nom du monstre : " + e.getMessage());
        }
    }

    private void changerLevelMonster(Monster monster) {
        try {
            int ancienLevel = monster.getLevel();
            int nouveauLevel = view.saisirLevel();

            monster.setLevel(nouveauLevel);

            view.afficherMessage("""
                    Level du monstre modifié avec succès !

                    Monstre      : %s
                    Ancien level : %d
                    Nouveau level: %d
                    """.formatted(monster.getName(), ancienLevel, nouveauLevel));
        } catch (MonsterException e) {
            view.afficherMessage("Erreur lors de la modification du level : " + e.getMessage());
        }
    }

    private void ajouterPotionAuMonster(Monster monster) {
        try {
            String nom = view.saisirNom();

            int choixType = view.choisirTypePotion();
            TypePotion typePotion = TypePotion.values()[choixType - 1];

            Rank rank = view.choisirRank();

            Item item = FactoryItem.createPotion(nom, rank, typePotion);

            monster.addLoot(item);

            view.afficherMessage("""
                    Loot ajouté avec succès !

                    Monstre : %s
                    Loot    : %s
                    Type    : %s
                    Rang    : %s
                    """.formatted(monster.getName(), nom, typePotion, rank));
        } catch (MonsterException e) {
            view.afficherMessage("Erreur lors de l'ajout du loot : " + e.getMessage());
        }
    }

    private void ajouterEquipementAuMonster(Monster monster) {
        try {
            String nom = view.saisirNom();

            int choixType = view.choisirTypeEquipement();
            TypeEquipement typeEquipement = TypeEquipement.values()[choixType - 1];

            Rank rank = view.choisirRank();
            int capacity = view.saisirCapacity();

            Item item = FactoryItem.createEquipment(
                    nom,
                    rank,
                    typeEquipement,
                    capacity
            );

            monster.addLoot(item);

            view.afficherMessage("""
                    Équipement ajouté aux loots du monstre !

                    Monstre  : %s
                    Loot     : %s
                    Type     : %s
                    Rang     : %s
                    Capacité : %d
                    """.formatted(monster.getName(), nom, typeEquipement, rank, capacity));
        } catch (MonsterException e) {
            view.afficherMessage("Erreur lors de l'ajout de l'équipement au monstre : " + e.getMessage());
        }
    }

    private void editDungeon() {
        List<Dungeon> dungeons = model.recupererDungeons();

        if (dungeons.isEmpty()) {
            view.afficherMessage("Aucun dungeon disponible.");
            return;
        }

        int choixDungeon = view.choisirDungeon(dungeons);
        Dungeon dungeon = dungeons.get(choixDungeon - 1);

        List<String> menuAction = List.of(
                "Changer le nom",
                "Changer le boss",
                "Changer le rang"
        );

        view.afficherMenuModification(menuAction, "MODIFIER " + dungeon.getName());
        int choixAction = view.saisirChoixMenu(menuAction.size());

        switch (choixAction) {
            case 1 -> changerNomDungeon(dungeon);
            case 2 -> changerBossDungeon(dungeon);
            case 3 -> changerRangDungeon(dungeon);
            default -> {
            }
        }
    }

    private void changerNomDungeon(Dungeon dungeon) {
        try {
            String ancienNom = dungeon.getName();
            String nouveauNom = view.saisirNom();

            dungeon.setName(nouveauNom);

            view.afficherMessage("""
                    Nom du donjon modifié avec succès !

                    Ancien nom  : %s
                    Nouveau nom : %s
                    """.formatted(ancienNom, nouveauNom));
        } catch (DungeonException e) {
            view.afficherMessage("Erreur lors de la modification du nom du donjon : " + e.getMessage());
        }
    }

    private void changerBossDungeon(Dungeon dungeon) {
        List<Monster> monsters = model.recupererMonsters();

        if (monsters.isEmpty()) {
            view.afficherMessage("Aucun monstre disponible pour choisir un boss.");
            return;
        }

        int choixMonster = view.choisirMonster(monsters);
        Monster nouveauBoss = monsters.get(choixMonster - 1);

        dungeon.setBoss(nouveauBoss);

        view.afficherMessage("""
                Boss du donjon modifié avec succès !

                Donjon       : %s
                Nouveau boss : %s
                Level du boss: %d
                """.formatted(dungeon.getName(), nouveauBoss.getName(), nouveauBoss.getLevel()));
    }

    private void changerRangDungeon(Dungeon dungeon) {
        try {
            Rank ancienRang = dungeon.getRang();
            Rank nouveauRang = view.choisirRank();

            dungeon.setRang(nouveauRang);

            view.afficherMessage("""
                    Rang du donjon modifié avec succès !

                    Donjon       : %s
                    Ancien rang  : %s
                    Nouveau rang : %s
                    """.formatted(dungeon.getName(), ancienRang, nouveauRang));
        } catch (DungeonException e) {
            view.afficherMessage("Erreur lors de la modification du rang du donjon : " + e.getMessage());
        }
    }
}