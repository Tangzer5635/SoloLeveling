package models.entities.fields;

import models.entities.entity.Monster;
import models.exception.DungeonException;
import models.referencies.Rank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Dungeon {
    private String name;
    private Monster boss;
    private Map<Monster, Integer> monsters = new HashMap<>();
    private Rank rang;

    public String getName() {
        return name;
    }

    public void setName(String name) throws DungeonException {
        if (name == null || name.isBlank()) {
            throw new DungeonException("Le nom du donjon ne peut pas être vide.");
        }

        this.name = name;
    }

    public Monster getBoss() {
        return boss;
    }

    public void setBoss(Monster boss) {
        this.boss = boss;
    }

    public Rank getRang() {
        return rang;
    }

    public void setRang(Rank rang) throws DungeonException {
        if (rang == null) {
            throw new DungeonException("Le rang du donjon ne peut pas être null.");
        }

        this.rang = rang;
    }

    public Map<Monster, Integer> getMonsters() {
        return Collections.unmodifiableMap(monsters);
    }

    public Dungeon(String name, Monster boss, Map<Monster, Integer> monsters, Rank rang) throws DungeonException {
        setName(name);
        setBoss(boss);
        setMonsters(monsters);
        setRang(rang);
    }

    public void setMonsters(Map<Monster, Integer> monsters) throws DungeonException {
        if (monsters == null) {
            this.monsters = new HashMap<>();
            return;
        }

        for (Map.Entry<Monster, Integer> entry : monsters.entrySet()) {
            if (entry.getKey() == null) {
                throw new DungeonException("Un donjon ne peut pas contenir un monstre null.");
            }

            if (entry.getValue() == null || entry.getValue() <= 0) {
                throw new DungeonException("Le nombre de monstres doit être supérieur à 0.");
            }
        }

        this.monsters = new HashMap<>(monsters);
    }

    public int getNombreTotalMonstres() {
        return monsters.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String afficherMonstreAvecDetails() {
        if (monsters.isEmpty()) {
            return "Aucun monstre";
        }

        return monsters.entrySet().stream()
                .map(entry -> entry.getKey().getName() + " x" + entry.getValue())
                .collect(Collectors.joining(", "));
    }

    public String sePresenter() {
        return """
                Dungeon : %s | Boss : %s | Rang : %s | Nombre total de monstres : %d | Détail : %s
                """.formatted(
                getName(),
                getBoss() != null ? getBoss().getName() : "Aucun",
                getRang(),
                getNombreTotalMonstres(),
                afficherMonstreAvecDetails()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        return Objects.equals(name, dungeon.name) && Objects.equals(boss, dungeon.boss) && rang == dungeon.rang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, boss, rang);
    }
}