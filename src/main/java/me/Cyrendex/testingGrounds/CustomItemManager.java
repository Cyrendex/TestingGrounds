package me.Cyrendex.testingGrounds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;

public class CustomItemManager {
    public static ItemStack cursor;

    public static void init() {
        createCursor();
        System.out.println("WOoOp! Initialized.");
    }

    private static void createCursor() {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);

        // Custom Meta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Cursor");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Drag and Drop");
        meta.setLore(lore);
        CustomModelDataComponent customData = meta.getCustomModelDataComponent();
        List<String> modelString = new ArrayList<>();
        modelString.add("cursor");
        customData.setStrings(modelString);
        System.out.println(customData.getStrings());
        meta.setCustomModelDataComponent(customData);

        item.setItemMeta(meta);
        cursor = item;
    }
}
