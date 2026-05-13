package views.utils.Jframe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AffichageJFrame extends JFrame {

    protected static final String TITRE_APPLICATION = "Solo Levelling";

    // Palette moderne
    protected static final Color COULEUR_FOND = new Color(8, 12, 22);
    protected static final Color COULEUR_NAV = new Color(15, 23, 42);
    protected static final Color COULEUR_CARD = new Color(30, 41, 59);
    protected static final Color COULEUR_CARD_HOVER = new Color(51, 65, 85);
    protected static final Color COULEUR_ACCENT = new Color(0, 191, 255);
    protected static final Color COULEUR_TEXTE = new Color(248, 250, 252);
    protected static final Color COULEUR_SECONDAIRE = new Color(148, 163, 184);
    protected static final Color COULEUR_BORDURE = new Color(51, 65, 85);

    protected final JLabel titreLabel = new JLabel("MENU PRINCIPAL");
    protected final JLabel sousTitreLabel = new JLabel("Gérez vos données avec une interface moderne");

    protected final JPanel menuPanel = new JPanel();
    protected final JPanel saisiePanel = new JPanel();
    protected final JPanel actionsPanel = new JPanel();

    protected final JTextArea zoneAffichage = new JTextArea();

    public AffichageJFrame() {
        initialiserFenetre();
    }

    private void initialiserFenetre() {
        setTitle(TITRE_APPLICATION);
        setSize(1500, 900);
        setMinimumSize(new Dimension(1200, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COULEUR_FOND);
        setContentPane(root);

        root.add(creerNavbar(), BorderLayout.NORTH);
        root.add(creerCentre(), BorderLayout.CENTER);
        root.add(creerFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel creerNavbar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(COULEUR_NAV);
        navbar.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        gauche.setOpaque(false);

        JLabel logo = new JLabel("⚔");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 34));
        logo.setForeground(COULEUR_ACCENT);

        JPanel textes = new JPanel();
        textes.setLayout(new BoxLayout(textes, BoxLayout.Y_AXIS));
        textes.setOpaque(false);

        titreLabel.setForeground(COULEUR_TEXTE);
        titreLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        sousTitreLabel.setForeground(COULEUR_SECONDAIRE);
        sousTitreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        textes.add(titreLabel);
        textes.add(sousTitreLabel);

        gauche.add(logo);
        gauche.add(textes);

        JLabel badge = new JLabel("Niveau S");
        badge.setOpaque(true);
        badge.setBackground(COULEUR_ACCENT);
        badge.setForeground(Color.BLACK);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setBorder(new EmptyBorder(8, 14, 8, 14));

        navbar.add(gauche, BorderLayout.WEST);
        navbar.add(badge, BorderLayout.EAST);

        return navbar;
    }

    private JPanel creerCentre() {
        JPanel centre = new JPanel(new BorderLayout(25, 25));
        centre.setBackground(COULEUR_FOND);
        centre.setBorder(new EmptyBorder(25, 25, 25, 25));

        initialiserMenuPanel();
        initialiserZoneAffichage();

        JScrollPane scroll = new JScrollPane(zoneAffichage);
        scroll.setBorder(new LineBorder(COULEUR_BORDURE, 1, true));
        scroll.getViewport().setBackground(COULEUR_CARD);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        JPanel contenu = new JPanel(new BorderLayout(0, 25));
        contenu.setOpaque(false);
        contenu.add(menuPanel, BorderLayout.NORTH);
        contenu.add(scroll, BorderLayout.CENTER);

        centre.add(contenu, BorderLayout.CENTER);

        return centre;
    }

    private JPanel creerFooter() {
        actionsPanel.setLayout(new BorderLayout());
        actionsPanel.setBackground(COULEUR_NAV);
        actionsPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        initialiserSaisiePanel();

        JLabel footer = new JLabel("Solo Levelling © 2026", SwingConstants.CENTER);
        footer.setForeground(COULEUR_SECONDAIRE);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        actionsPanel.add(saisiePanel, BorderLayout.NORTH);
        actionsPanel.add(footer, BorderLayout.SOUTH);

        return actionsPanel;
    }

    private void initialiserMenuPanel() {
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
        menuPanel.setOpaque(false);
    }

    private void initialiserSaisiePanel() {
        saisiePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));
        saisiePanel.setOpaque(false);
    }

    private void initialiserZoneAffichage() {
        zoneAffichage.setEditable(false);
        zoneAffichage.setLineWrap(true);
        zoneAffichage.setWrapStyleWord(true);
        zoneAffichage.setFont(new Font("Consolas", Font.PLAIN, 15));
        zoneAffichage.setBackground(COULEUR_CARD);
        zoneAffichage.setForeground(COULEUR_TEXTE);
        zoneAffichage.setMargin(new Insets(30, 30, 30, 30));
        zoneAffichage.setBorder(null);

        // Aucune sortie console affichée au démarrage
        zoneAffichage.setText("");
    }

    protected JButton creerCarteMenu(String titre, String description, String emoji) {
        JButton bouton = new JButton();
        bouton.setLayout(new BorderLayout());
        bouton.setPreferredSize(new Dimension(280, 180));
        bouton.setBorder(new LineBorder(COULEUR_BORDURE, 1, true));
        bouton.setBackground(COULEUR_CARD);
        bouton.setFocusPainted(false);
        bouton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bouton.setContentAreaFilled(false);
        bouton.setOpaque(false);

        JLabel imageLabel = new JLabel(emoji, SwingConstants.CENTER);
        imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        imageLabel.setForeground(COULEUR_ACCENT);
        imageLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel texte = new JLabel(
                "<html><div style='text-align:center;'>" +
                        "<div style='font-size:18px;font-weight:bold;color:white;'>" + titre + "</div>" +
                        "<div style='font-size:12px;color:#94A3B8;margin-top:8px;'>" + description + "</div>" +
                        "</div></html>",
                SwingConstants.CENTER
        );
        texte.setBorder(new EmptyBorder(0, 15, 20, 15));

        bouton.add(imageLabel, BorderLayout.CENTER);
        bouton.add(texte, BorderLayout.SOUTH);

        bouton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bouton.setBorder(new LineBorder(COULEUR_ACCENT, 2, true));
                bouton.setBackground(COULEUR_CARD_HOVER);
                bouton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bouton.setBorder(new LineBorder(COULEUR_BORDURE, 1, true));
                bouton.setBackground(COULEUR_CARD);
                bouton.repaint();
            }
        });

        return bouton;
    }

    protected JButton creerBoutonMenu(String texte) {
        return creerCarteMenu(texte, "Cliquez pour ouvrir cette section", "⚡");
    }

    protected void nettoyerInterface() {
        menuPanel.removeAll();
        saisiePanel.removeAll();
        zoneAffichage.setText("");
    }

    protected void afficherHeader(String titre) {
        zoneAffichage.append("══════════════════════════════════════════════════════════\n");
        zoneAffichage.append("  " + titre.toUpperCase() + "\n");
        zoneAffichage.append("══════════════════════════════════════════════════════════\n\n");
    }

    protected void afficherSection(String titre) {
        zoneAffichage.append("▶ " + titre + "\n");
        zoneAffichage.append("────────────────────────────────────────────────────────\n");
    }

    protected void afficherLigneInfo(String label, Object valeur) {
        zoneAffichage.append(String.format("%-25s : %s%n", label, valeur));
    }

    protected void afficherCarteDebut(String titre) {
        zoneAffichage.append("┌────────────────────────────────────────────────────────┐\n");
        zoneAffichage.append("│ " + titre + "\n");
        zoneAffichage.append("├────────────────────────────────────────────────────────┤\n");
    }

    protected void afficherCarteFin() {
        zoneAffichage.append("└────────────────────────────────────────────────────────┘\n\n");
    }

    protected void rafraichirPanels() {
        menuPanel.revalidate();
        menuPanel.repaint();
        saisiePanel.revalidate();
        saisiePanel.repaint();
        actionsPanel.revalidate();
        actionsPanel.repaint();
        zoneAffichage.revalidate();
        zoneAffichage.repaint();
    }
}
