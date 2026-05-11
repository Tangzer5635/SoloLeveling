package models.entities.entity;

import models.entities.items.Equipment;
import models.entities.items.Item;
import models.exception.EntityException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hunter extends Entity {
    private List<Equipment> equipments = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public List<Equipment> getEquipments() {
        return Collections.unmodifiableList(equipments);
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addNewEquipments(Equipment equipment) throws EntityException {
        if (equipment == null) {
            throw new EntityException("Impossible d'ajouter un équipement null.");
        }

        equipments.add(equipment);
    }

    public void addNewItems(Item item) throws EntityException {
        if (item == null) {
            throw new EntityException("Impossible d'ajouter un item null.");
        }

        items.add(item);
    }

    public void removeEquipment(Equipment equipment) throws EntityException {
        if (equipment == null) {
            throw new EntityException("Impossible de supprimer un équipement null.");
        }

        equipments.remove(equipment);
    }

    public void removeItem(Item item) throws EntityException {
        if (item == null) {
            throw new EntityException("Impossible de supprimer un item null.");
        }

        items.remove(item);
    }

    public Hunter(String name) throws EntityException {
        super.setName(name);
    }

    @Override
    public String sePresenter() {
        String itemsText = Item.presenterListeItem(items);
        String equipmentsText = Equipment.presenterListeEquip(equipments);

        if (itemsText.isBlank() && equipmentsText.isBlank()) {
            itemsText = " aucun ";
            equipmentsText = " aucun ";
        }

        return """
                [Hunter] Je suis %s, j'ai%sitems et%sequipments, mon power est de %s et mon rang est de %s
                """.formatted(
                getName(),
                itemsText,
                equipmentsText,
                getPower(),
                getRang()
        );
    }
}