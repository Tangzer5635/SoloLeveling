package views.facades;

import models.entities.entity.Hunter;
import models.entities.entity.Monster;
import models.entities.fields.Dungeon;
import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypePotion;
import views.utils.AffichageConsole;
import views.utils.LectureConsole;

import java.util.ArrayList;
import java.util.List;

public class ViewConsoleImpl implements IView {
    private final String MENU_PRINCIPAL = "MENU PRINCIPAL";

    @Override
    public void afficherMenuPrincipal(List<String> menu) {
        AffichageConsole.afficherMenuEntoureAvecOptionSortie(menu, MENU_PRINCIPAL);
    }

    @Override
    public void afficherMenuRegister(List<String> menuReg) {
        AffichageConsole.afficherMenuEntoureAvecOptionSortie(menuReg, "Retour au menu principal");
    }

    @Override
    public void afficherMenuAffichage(List<String> menuAff) {
        AffichageConsole.afficherMenuEntoureAvecOptionSortie(menuAff, "Retour au menu principal");
    }

    @Override
    public void afficherMenuModification(List<String> menuMod, String titre) {
        AffichageConsole.afficherMenuEntoureAvecOptionSortie(menuMod, titre);
    }

    @Override
    public int saisirChoixMenu(int tailleMenu) {
        return LectureConsole.lectureChoixInt(0, tailleMenu);
    }

    @Override
    public String saisirNom() {
        return LectureConsole.lectureChaineCaracteres("Entrez le nom : ");
    }

    @Override
    public double saisirPower() {
        return LectureConsole.lectureDouble("Entrez le power : ");
    }

    @Override
    public int saisirLevel() {
        AffichageConsole.afficherMessageSansSautLigne("Entrer le level : ");
        return LectureConsole.lectureChoixInt(1, 100);
    }

    @Override
    public int saisirCapacity() {
        return LectureConsole.lectureEntier("Capacité : ");
    }

    @Override
    public Rank choisirRank() {
        Rank[] ranks = Rank.values();
        List<String> menuRank = new ArrayList<>();

        for (Rank rank : ranks) {
            menuRank.add(rank.name());
        }

        AffichageConsole.afficherMenuSimple(menuRank);
        int choix = LectureConsole.lectureChoixInt(1, menuRank.size());
        return ranks[choix - 1];
    }

    @Override
    public int choisirHunter(List<Hunter> hunters) {
        AffichageConsole.afficherMessageAvecSautLigne("Choisissez un hunter :");
        afficherLesHunters(hunters);
        AffichageConsole.afficherMessageSansSautLigne("CHOIX : ");
        return LectureConsole.lectureChoixInt(1, hunters.size());
    }

    @Override
    public int choisirMonster(List<Monster> monsters) {
        AffichageConsole.afficherMessageAvecSautLigne("Choisissez un monstre :");
        afficherLesMonstres(monsters);
        AffichageConsole.afficherMessageSansSautLigne("CHOIX : ");
        return LectureConsole.lectureChoixInt(1, monsters.size());
    }

    @Override
    public int choisirDungeon(List<Dungeon> dungeons) {
        AffichageConsole.afficherMessageAvecSautLigne("Choisissez un donjon :");
        afficherLesDungeons(dungeons);
        AffichageConsole.afficherMessageSansSautLigne("CHOIX : ");
        return LectureConsole.lectureChoixInt(1, dungeons.size());
    }

    @Override
    public int choisirTypeEquipement() {
        TypeEquipement[] values = TypeEquipement.values();
        List<String> menu = new ArrayList<>();

        for (TypeEquipement value : values) {
            menu.add(value.name());
        }

        AffichageConsole.afficherMenuSimple(menu);
        return LectureConsole.lectureChoixInt(1, values.length);
    }

    @Override
    public int choisirTypePotion() {
        TypePotion[] values = TypePotion.values();
        List<String> menu = new ArrayList<>();

        for (TypePotion value : values) {
            menu.add(value.name());
        }

        AffichageConsole.afficherMenuSimple(menu);
        AffichageConsole.afficherMessageSansSautLigne("CHOIX : ");
        return LectureConsole.lectureChoixInt(1, values.length);
    }

    @Override
    public void afficherMessage(String message) {
        AffichageConsole.afficherMessageAvecSautLigne(message);
    }

    @Override
    public void afficherLesHunters(List<Hunter> hunters) {
        AffichageConsole.afficherMessageAvecSautLigne("Nombre de hunters : " + hunters.size());

        int index = 1;
        for (Hunter hunter : hunters) {
            AffichageConsole.afficherMessageAvecSautLigne(index + " | " + hunter.sePresenter());
            index++;
        }
    }

    @Override
    public void afficherLesMonstres(List<Monster> monsters) {
        AffichageConsole.afficherMessageAvecSautLigne("Nombre de monstres : " + monsters.size());

        int index = 1;
        for (Monster monster : monsters) {
            AffichageConsole.afficherMessageAvecSautLigne(index + " | " + monster.sePresenter());
            index++;
        }
    }

    @Override
    public void afficherLesDungeons(List<Dungeon> dungeons) {
        AffichageConsole.afficherMessageAvecSautLigne("Nombre de dungeons : " + dungeons.size());

        int index = 1;
        for (Dungeon dungeon : dungeons) {
            AffichageConsole.afficherMessageAvecSautLigne(index + " | " + dungeon.sePresenter());
            index++;
        }
    }
}