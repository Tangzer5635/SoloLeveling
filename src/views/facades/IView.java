package views.facades;

import models.entities.entity.Hunter;
import models.entities.entity.Monster;
import models.entities.fields.Dungeon;
import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypePotion;

import java.util.List;

public interface IView {
    void afficherMenuPrincipal(List<String> menu);

    void afficherMenuRegister(List<String> menuReg);

    void afficherMenuAffichage(List<String> menuAff);

    void afficherMenuModification(List<String> menuMod, String titre);

    int saisirChoixMenu(int tailleMenu);

    String saisirNom();

    double saisirPower();

    int saisirLevel();

    int saisirCapacity();

    Rank choisirRank();

    int choisirHunter(List<Hunter> hunters);

    int choisirMonster(List<Monster> monsters);

    int choisirDungeon(List<Dungeon> dungeons);

    int choisirTypeEquipement();

    int choisirTypePotion();

    void afficherMessage(String message);

    void afficherLesHunters(List<Hunter> hunters);

    void afficherLesMonstres(List<Monster> monsters);

    void afficherLesDungeons(List<Dungeon> dungeons);
}