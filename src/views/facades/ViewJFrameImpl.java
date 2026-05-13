package views.facades;

import models.entities.entity.Hunter;
import models.entities.entity.Monster;
import models.entities.fields.Dungeon;
import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypePotion;
import views.utils.Jframe.AffichageJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ViewJFrameImpl extends AffichageJFrame implements IView {
    private List<String> dernierMenu;
    private String dernierTitreMenu;

    public ViewJFrameImpl() {
        super();
    }

    @Override
    public void afficherMenuPrincipal(List<String> menu) {
        memoriserMenu("MENU PRINCIPAL", menu);
    }

    @Override
    public void afficherMenuRegister(List<String> menuReg) {
        memoriserMenu("ENREGISTREMENT", menuReg);
    }

    @Override
    public void afficherMenuAffichage(List<String> menuAff) {
        memoriserMenu("AFFICHAGE", menuAff);
    }

    @Override
    public void afficherMenuModification(List<String> menuMod, String titre) {
        memoriserMenu(titre, menuMod);
    }

    private void memoriserMenu(String titre, List<String> menu) {
        dernierTitreMenu = titre;
        dernierMenu = menu;

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText(titre);
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            afficherHeader(titre);
            zoneAffichage.append("\n");
            zoneAffichage.append("  Sélectionne une action avec les boutons en bas.\n\n");

            for (int i = 0; i < menu.size(); i++) {
                zoneAffichage.append("  [" + (i + 1) + "] " + menu.get(i) + "\n");
            }

            zoneAffichage.append("  [0] Retour / Sortir\n\n");

            rafraichirPanels();
        });
    }

    @Override
    public int saisirChoixMenu(int tailleMenu) {
        AtomicInteger choixUtilisateur = new AtomicInteger(0);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText(dernierTitreMenu);
            menuPanel.removeAll();
            saisiePanel.removeAll();

            for (int i = 0; i < tailleMenu; i++) {
                int choix = i + 1;
                JButton bouton = creerBoutonMenu(choix + " - " + dernierMenu.get(i));

                bouton.addActionListener(e -> {
                    choixUtilisateur.set(choix);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            JButton boutonSortie;

            if ("MENU PRINCIPAL".equals(dernierTitreMenu)) {
                boutonSortie = creerBoutonMenu("0 - Quitter");

                boutonSortie.addActionListener(e -> {
                    dispose();
                    System.exit(0);
                });

            } else {
                boutonSortie = creerBoutonMenu("0 - Retour");

                boutonSortie.addActionListener(e -> {
                    choixUtilisateur.set(0);
                    attente.countDown();
                });
            }

            menuPanel.add(boutonSortie);

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public String saisirNom() {
        return demanderTexte("NOM", "Entrez le nom :");
    }

    @Override
    public double saisirPower() {
        while (true) {
            String saisie = demanderTexte("POWER", "Entrez le power :");

            try {
                return Double.parseDouble(saisie);
            } catch (NumberFormatException e) {
                afficherMessage("Erreur : veuillez entrer un nombre valide pour le power.");
            }
        }
    }

    @Override
    public int saisirLevel() {
        while (true) {
            String saisie = demanderTexte("LEVEL", "Entrez le level entre 1 et 100 :");

            try {
                int level = Integer.parseInt(saisie);

                if (level >= 1 && level <= 100) {
                    return level;
                }

                afficherMessage("Erreur : le level doit être compris entre 1 et 100.");
            } catch (NumberFormatException e) {
                afficherMessage("Erreur : veuillez entrer un entier valide pour le level.");
            }
        }
    }

    @Override
    public int saisirCapacity() {
        while (true) {
            String saisie = demanderTexte("CAPACITÉ", "Entrez la capacité de l'équipement :");

            try {
                return Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                afficherMessage("Erreur : veuillez entrer un entier valide pour la capacité.");
            }
        }
    }

    @Override
    public Rank choisirRank() {
        AtomicReference<Rank> choixUtilisateur = new AtomicReference<>(Rank.E);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("CHOIX DU RANG");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            zoneAffichage.append("╔══════════════════════════════════════════════╗\n");
            zoneAffichage.append("        SÉLECTION DU RANG DU HUNTER\n");
            zoneAffichage.append("╚══════════════════════════════════════════════╝\n\n");
            zoneAffichage.append("Choisis le rang du personnage :\n\n");

            for (Rank rank : Rank.values()) {
                zoneAffichage.append("  ◆ Rang %-9s | Niveau de puissance : %d\n"
                        .formatted(rank.name(), rank.getPowerLevel()));

                JButton bouton = creerBoutonMenu("Rang " + rank.name());
                bouton.addActionListener(e -> {
                    choixUtilisateur.set(rank);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public int choisirHunter(List<Hunter> hunters) {
        AtomicInteger choixUtilisateur = new AtomicInteger(1);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("CHOIX DU HUNTER");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            zoneAffichage.append("╔══════════════════════════════════════════════╗\n");
            zoneAffichage.append("              CHOISIS UN HUNTER\n");
            zoneAffichage.append("╚══════════════════════════════════════════════╝\n\n");

            for (int i = 0; i < hunters.size(); i++) {
                int choix = i + 1;
                Hunter hunter = hunters.get(i);

                zoneAffichage.append("[" + choix + "] " + hunter.getName() + "\n");
                zoneAffichage.append("    Power : " + hunter.getPower() + "\n");
                zoneAffichage.append("    Rang  : " + hunter.getRang() + "\n\n");

                JButton bouton = creerBoutonMenu(choix + " - " + hunter.getName());
                bouton.addActionListener(e -> {
                    choixUtilisateur.set(choix);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public int choisirMonster(List<Monster> monsters) {
        AtomicInteger choixUtilisateur = new AtomicInteger(1);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("CHOIX DU MONSTRE");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            zoneAffichage.append("╔══════════════════════════════════════════════╗\n");
            zoneAffichage.append("              CHOISIS UN MONSTRE\n");
            zoneAffichage.append("╚══════════════════════════════════════════════╝\n\n");

            for (int i = 0; i < monsters.size(); i++) {
                int choix = i + 1;
                Monster monster = monsters.get(i);

                zoneAffichage.append("[" + choix + "] " + monster.getName() + "\n");
                zoneAffichage.append("    Level : " + monster.getLevel() + "\n\n");

                JButton bouton = creerBoutonMenu(choix + " - " + monster.getName());
                bouton.addActionListener(e -> {
                    choixUtilisateur.set(choix);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public int choisirDungeon(List<Dungeon> dungeons) {
        AtomicInteger choixUtilisateur = new AtomicInteger(1);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("CHOIX DU DONJON");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            zoneAffichage.append("╔══════════════════════════════════════════════╗\n");
            zoneAffichage.append("               CHOISIS UN DONJON\n");
            zoneAffichage.append("╚══════════════════════════════════════════════╝\n\n");

            for (int i = 0; i < dungeons.size(); i++) {
                int choix = i + 1;
                Dungeon dungeon = dungeons.get(i);

                zoneAffichage.append("[" + choix + "] " + dungeon.getName() + "\n");
                zoneAffichage.append("    Rang : " + dungeon.getRang() + "\n");

                if (dungeon.getBoss() == null) {
                    zoneAffichage.append("    Boss : Aucun\n\n");
                } else {
                    zoneAffichage.append("    Boss : " + dungeon.getBoss().getName() + "\n\n");
                }

                JButton bouton = creerBoutonMenu(choix + " - " + dungeon.getName());
                bouton.addActionListener(e -> {
                    choixUtilisateur.set(choix);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public int choisirTypeEquipement() {
        AtomicInteger choixUtilisateur = new AtomicInteger(1);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("TYPE D'ÉQUIPEMENT");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("=== TYPE D'ÉQUIPEMENT ===\n\n");

            TypeEquipement[] equipements = TypeEquipement.values();

            for (int i = 0; i < equipements.length; i++) {
                int choix = i + 1;
                TypeEquipement typeEquipement = equipements[i];

                zoneAffichage.append(choix + " - " + typeEquipement.name() + "\n");

                JButton bouton = creerBoutonMenu(choix + " - " + typeEquipement.name());
                bouton.addActionListener(e -> {
                    choixUtilisateur.set(choix);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public int choisirTypePotion() {
        AtomicInteger choixUtilisateur = new AtomicInteger(1);
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("TYPE DE POTION");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("=== TYPE DE POTION ===\n\n");

            TypePotion[] potions = TypePotion.values();

            for (int i = 0; i < potions.length; i++) {
                int choix = i + 1;
                TypePotion typePotion = potions[i];

                zoneAffichage.append(choix + " - " + typePotion.name() + "\n");

                JButton bouton = creerBoutonMenu(choix + " - " + typePotion.name());
                bouton.addActionListener(e -> {
                    choixUtilisateur.set(choix);
                    attente.countDown();
                });

                menuPanel.add(bouton);
            }

            rafraichirPanels();
        });

        attendre(attente);
        return choixUtilisateur.get();
    }

    @Override
    public void afficherMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            if (message != null && message.startsWith("Erreur")) {
                afficherPopupErreur(message);
                return;
            }

            titreLabel.setText("INFORMATION");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            afficherHeader("INFORMATION");
            zoneAffichage.append("\n");
            zoneAffichage.append("  ✓ Action terminée\n\n");
            zoneAffichage.append(indenterTexte(message));
            zoneAffichage.append("\n\n");
            zoneAffichage.append("──────────────────────────────────────────────\n");
            zoneAffichage.append("Utilise le menu en bas pour continuer.\n");

            rafraichirPanels();
        });
    }

    private void afficherPopupErreur(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void afficherLesHunters(List<Hunter> hunters) {
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("HUNTERS");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            afficherHeader("LISTE DES HUNTERS");
            zoneAffichage.append("\n");
            afficherLigneInfo("Total hunters", hunters.size());

            int index = 1;
            for (Hunter hunter : hunters) {
                afficherCarteDebut("HUNTER #" + index + " - " + hunter.getName());

                afficherLigneInfo("Nom", hunter.getName());
                afficherLigneInfo("Power", hunter.getPower());
                afficherLigneInfo("Rang", hunter.getRang());

                afficherSection("Equipements");
                if (hunter.getEquipments().isEmpty()) {
                    zoneAffichage.append("    Aucun equipement\n");
                } else {
                    hunter.getEquipments().forEach(equipment ->
                            zoneAffichage.append("    - " + equipment.sePresenter() + "\n")
                    );
                }

                afficherSection("Items");
                if (hunter.getItems().isEmpty()) {
                    zoneAffichage.append("    Aucun item\n");
                } else {
                    hunter.getItems().forEach(item ->
                            zoneAffichage.append("    - " + item.sePresenter() + "\n")
                    );
                }

                afficherCarteFin();
                index++;
            }

            menuPanel.add(creerBoutonRetour(attente, "Retour au menu affichage"));

            rafraichirPanels();
        });

        attendre(attente);
    }

    @Override
    public void afficherLesMonstres(List<Monster> monsters) {
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("MONSTRES");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            afficherHeader("LISTE DES MONSTRES");
            zoneAffichage.append("\n");
            afficherLigneInfo("Total monstres", monsters.size());

            int index = 1;
            for (Monster monster : monsters) {
                afficherCarteDebut("MONSTRE #" + index + " - " + monster.getName());

                afficherLigneInfo("Nom", monster.getName());
                afficherLigneInfo("Level", monster.getLevel());

                afficherSection("Loots");
                if (monster.getItemList() == null || monster.getItemList().isEmpty()) {
                    zoneAffichage.append("    Aucun loot\n");
                } else {
                    monster.getItemList().forEach(item ->
                            zoneAffichage.append("    - " + item.sePresenter() + "\n")
                    );
                }

                afficherCarteFin();
                index++;
            }

            menuPanel.add(creerBoutonRetour(attente, "Retour au menu affichage"));

            rafraichirPanels();
        });

        attendre(attente);
    }

    @Override
    public void afficherLesDungeons(List<Dungeon> dungeons) {
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText("DUNGEONS");
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            afficherHeader("LISTE DES DUNGEONS");
            zoneAffichage.append("\n");
            afficherLigneInfo("Total donjons", dungeons.size());

            int index = 1;
            for (Dungeon dungeon : dungeons) {
                afficherCarteDebut("DUNGEON #" + index + " - " + dungeon.getName());

                afficherLigneInfo("Nom", dungeon.getName());
                afficherLigneInfo("Rang", dungeon.getRang());
                afficherLigneInfo("Total monstres", dungeon.getNombreTotalMonstres());

                afficherSection("Boss");
                if (dungeon.getBoss() == null) {
                    zoneAffichage.append("    Aucun boss\n");
                } else {
                    Monster boss = dungeon.getBoss();

                    zoneAffichage.append("    Nom   : " + boss.getName() + "\n");
                    zoneAffichage.append("    Level : " + boss.getLevel() + "\n");

                    zoneAffichage.append("    Loots :\n");
                    if (boss.getItemList() == null || boss.getItemList().isEmpty()) {
                        zoneAffichage.append("      Aucun loot\n");
                    } else {
                        boss.getItemList().forEach(item ->
                                zoneAffichage.append("      - " + item.sePresenter() + "\n")
                        );
                    }
                }

                afficherSection("Monstres");
                if (dungeon.getMonsters().isEmpty()) {
                    zoneAffichage.append("    Aucun monstre\n");
                } else {
                    dungeon.getMonsters().forEach((monster, nombre) -> {
                        zoneAffichage.append("    " + monster.getName() + " x" + nombre + "\n");
                        zoneAffichage.append("      Level : " + monster.getLevel() + "\n");

                        zoneAffichage.append("      Loots :\n");
                        if (monster.getItemList() == null || monster.getItemList().isEmpty()) {
                            zoneAffichage.append("        Aucun loot\n");
                        } else {
                            monster.getItemList().forEach(item ->
                                    zoneAffichage.append("        - " + item.sePresenter() + "\n")
                            );
                        }

                        zoneAffichage.append("\n");
                    });
                }

                afficherCarteFin();
                index++;
            }

            menuPanel.add(creerBoutonRetour(attente, "Retour au menu affichage"));

            rafraichirPanels();
        });

        attendre(attente);
    }


    private String indenterTexte(String texte) {
        if (texte == null || texte.isBlank()) {
            return "  Aucun message.";
        }

        String[] lignes = texte.split("\\R");
        StringBuilder resultat = new StringBuilder();

        for (String ligne : lignes) {
            if (!ligne.isBlank()) {
                resultat.append("  ").append(ligne).append("\n");
            }
        }

        return resultat.toString();
    }

    private JButton creerBoutonRetour(CountDownLatch attente, String texte) {
        JButton boutonRetour = creerBoutonMenu(texte);
        boutonRetour.addActionListener(e -> attente.countDown());
        return boutonRetour;
    }

    private String demanderTexte(String titre, String message) {
        AtomicReference<String> valeur = new AtomicReference<>("");
        CountDownLatch attente = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            titreLabel.setText(titre);
            menuPanel.removeAll();
            saisiePanel.removeAll();

            zoneAffichage.setText("");
            afficherHeader(titre);
            zoneAffichage.append("\n");
            zoneAffichage.append("  " + message + "\n\n");
            zoneAffichage.append("──────────────────────────────────────────────\n");
            zoneAffichage.append("Valide avec le bouton ou la touche Entrée.\n");

            JLabel label = new JLabel(message);
            label.setForeground(COULEUR_TEXTE);
            label.setFont(new Font("Arial", Font.BOLD, 15));

            JTextField champ = new JTextField(28);
            champ.setBackground(new Color(20, 24, 55));
            champ.setForeground(COULEUR_TEXTE);
            champ.setCaretColor(COULEUR_ACCENT);
            champ.setFont(new Font("Arial", Font.PLAIN, 16));
            champ.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COULEUR_ACCENT, 2),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            JButton boutonValider = creerBoutonMenu("Valider");

            boutonValider.addActionListener(e -> {
                valeur.set(champ.getText());
                attente.countDown();
            });

            champ.addActionListener(e -> {
                valeur.set(champ.getText());
                attente.countDown();
            });

            saisiePanel.add(label);
            saisiePanel.add(champ);
            saisiePanel.add(boutonValider);

            rafraichirPanels();
            champ.requestFocusInWindow();
        });

        attendre(attente);
        return valeur.get();
    }

    private void attendre(CountDownLatch attente) {
        try {
            attente.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}