package cyanogenoid.portablechests;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Database {

    private final FileConfiguration database;
    private final File dataFile;
    private int taskId = -1;

    public Database() {
        String dataPath = PortableChests.instance.getDataFolder() + File.separator + "Data" + File.separator + "inventories.yml";
        this.dataFile = new File(dataPath);

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException er) {
                er.printStackTrace();
            }
        }

        this.database = YamlConfiguration.loadConfiguration(dataFile);

        this.taskId = new BukkitRunnable() {
            @Override
            public void run() {
                save();
            }
        }.runTaskTimerAsynchronously(PortableChests.instance, 0, 10 * 20).getTaskId();
    }

    public void saveContent(UUID uuid, String content) {
        database.set(uuid.toString(), content);
    }

    public String loadContent(String uuid) {
        return this.database.getString(uuid);
    }

    public void save() {
        try {
            database.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTaskId() {
        return taskId;
    }
}
