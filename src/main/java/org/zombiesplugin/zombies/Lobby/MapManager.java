package org.zombiesplugin.zombies.Lobby;

import org.bukkit.Bukkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;

public class MapManager {
    /**
     * Duplicates a world/map.
     *
     * @param mapName Name of the world/map in the server directory.
     * @return True if duplication is successful, false otherwise.
     */
    public static boolean duplicateMap(String mapName) {
        Path worldPath = Paths.get(String.format("./%s", mapName));

        if (!Files.isDirectory(worldPath)) {
            Bukkit.getLogger().log(Level.WARNING, "[MapManager] Map directory not found: " + mapName);
            return false;
        }

        UUID uuid = UUID.randomUUID();
        final String absPath = System.getProperty("user.dir");
        File original = new File(String.format("%s/%s", absPath, mapName));
        File copied = new File(String.format("%s/%s", absPath, uuid));

        try {
            Files.walk(Paths.get(original.getPath()))
                    .forEach(source -> {
                        Path destination = Paths.get(copied.getPath(), source.toString()
                                .substring(original.getPath().length()));
                        try {
                            Files.copy(source, destination);
                        } catch (IOException e) {
                            Bukkit.getLogger().log(Level.SEVERE, "[MapManager] Error copying file: " + e.getMessage());
                        }
                    });

            Bukkit.getLogger().log(Level.INFO, "[MapManager] Map duplicated successfully: " + uuid.toString());
            return true;
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[MapManager] Error duplicating map: " + e.getMessage());
            return false;
        }
    }
}
