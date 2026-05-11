package views.facades;

import models.entities.entity.Hunter;
import models.entities.entity.Monster;
import models.entities.fields.Dungeon;
import models.referencies.Rank;
import models.referencies.TypeEquipement;
import models.referencies.TypePotion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ViewJFrameImpl extends JFrame implements IView {
    private static final String TITRE_APPLICATION = "Solo Levelling";

    private static final Color COULEUR_FOND = new Color(8, 10, 24);
    private static final Color COULEUR_PANNEAU = new Color(15, 18, 42);
    private static final Color COULEUR_BOUTON = new Color(42, 36, 110);
    private static final Color COULEUR_BOUTON_SURVOL = new Color(82, 67, 190);
    private static final Color COULEUR_TEXTE = new Color(230, 235, 255);
    private static final Color COULEUR_ACCENT = new Color(95, 175, 255);
    private static final Color COULEUR_VIOLET = new Color(155, 90, 255);

    private final JLabel titreLabel = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
    private final JPanel menuPanel = new JPanel();
    private final JTextArea zoneAffichage = new JTextArea();
    private final JPanel saisiePanel = new JPanel();
    private final JPanel actionsPanel = new JPanel(new BorderLayout(10, 10));

    private List<String> dernierMenu;
    private String dernierTitreMenu;

    public ViewJFrameImpl() {
        initialiserFenetre();
    }

    private void initialiserFenetre() {
        setTitle(TITRE_APPLICATION);
        setSize(1050, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(COULEUR_FOND);

        titreLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titreLabel.setForeground(COULEUR_ACCENT);
        titreLabel.setOpaque(true);
        titreLabel.setBackground(COULEUR_FOND);
        titreLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, COULEUR_VIOLET),
                BorderFactory.createEmptyBorder(18, 10, 18, 10)
        ));

        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));
        menuPanel.setBackground(COULEUR_PANNEAU);
        menuPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COULEUR_ACCENT, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        zoneAffichage.setEditable(false);
        zoneAffichage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        zoneAffichage.setLineWrap(true);
        zoneAffichage.setWrapStyleWord(true);
        zoneAffichage.setBackground(new Color(5, 7, 18));
        zoneAffichage.setForeground(COULEUR_TEXTE);
        zoneAffichage.setCaretColor(COULEUR_TEXTE);
        zoneAffichage.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JScrollPane scrollPane = new JScrollPane(zoneAffichage);
        scrollPane.setBorder(BorderFactory.createLineBorder(COULEUR_VIOLET, 2));
        scrollPane.getViewport().setBackground(new Color(5, 7, 18));

        saisiePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));
        saisiePanel.setBackground(COULEUR_PANNEAU);
        saisiePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COULEUR_VIOLET, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        actionsPanel.setBackground(COULEUR_FOND);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        actionsPanel.add(menuPanel, BorderLayout.CENTER);
        actionsPanel.add(saisiePanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(COULEUR_FOND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(titreLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

        setVisible(true);
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

    private void afficherHeader(String titre) {
        zoneAffichage.append("============================================================\n");
        zoneAffichage.append("  " + titre + "\n");
        zoneAffichage.append("============================================================\n");
    }

    private void afficherCarteDebut(String titre) {
        zoneAffichage.append("\n");
        zoneAffichage.append("------------------------------------------------------------\n");
        zoneAffichage.append("  " + titre + "\n");
        zoneAffichage.append("------------------------------------------------------------\n");
    }

    private void afficherCarteFin() {
        zoneAffichage.append("------------------------------------------------------------\n\n");
    }

    private void afficherLigneInfo(String label, Object valeur) {
        zoneAffichage.append("  " + String.format("%-16s", label) + ": " + valeur + "\n");
    }

    private void afficherSection(String titre) {
        zoneAffichage.append("\n");
        zoneAffichage.append("  " + titre.toUpperCase() + "\n");
        zoneAffichage.append("  ------------------------------\n");
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

    private JButton creerBoutonMenu(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bouton.setFont(new Font("Arial", Font.BOLD, 15));
        bouton.setForeground(COULEUR_TEXTE);
        bouton.setBackground(COULEUR_BOUTON);
        bouton.setPreferredSize(new Dimension(210, 46));
        bouton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COULEUR_ACCENT, 2),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));

        bouton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                bouton.setBackground(COULEUR_BOUTON_SURVOL);
                bouton.setForeground(Color.WHITE);
                bouton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COULEUR_VIOLET, 2),
                        BorderFactory.createEmptyBorder(10, 18, 10, 18)
                ));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                bouton.setBackground(COULEUR_BOUTON);
                bouton.setForeground(COULEUR_TEXTE);
                bouton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COULEUR_ACCENT, 2),
                        BorderFactory.createEmptyBorder(10, 18, 10, 18)
                ));
            }
        });

        return bouton;
    }

    private void rafraichirPanels() {
        menuPanel.revalidate();
        menuPanel.repaint();
        saisiePanel.revalidate();
        saisiePanel.repaint();
        zoneAffichage.revalidate();
        zoneAffichage.repaint();
    }

    private void attendre(CountDownLatch attente) {
        try {
            attente.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}