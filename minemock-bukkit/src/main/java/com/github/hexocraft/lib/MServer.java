package com.github.hexocraft.lib;

/*

 Copyright 2018 hexosse

 Licensed under the Apache License, Version 2.0 (the "License")
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

import com.github.hexocraft.lib.assumptions.AssumeImplementedException;
import com.github.hexocraft.lib.plugin.MPluginManager;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class MServer implements Server {

    // Server customisation
    // -------------------------------------------------------------------------
    private String   name            = "MServer";
    private String   version         = "0.0.1";
    private String   bukkitVersion   = "1.12.2";
    private GameMode defaultGameMode = GameMode.SURVIVAL;

    // Server logger
    // -------------------------------------------------------------------------
    private final Logger logger;

    // Server data
    // -------------------------------------------------------------------------
    private final MPluginManager pluginManager = new MPluginManager(this);

    public MServer() {

        // Init logger
        logger = Logger.getLogger("MineMock Server");

        // Load configuration from file
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("logger.properties");
            LogManager.getLogManager().readConfiguration(is);
        } catch(IOException e) {
            logger.warning("Could not load file logger.properties");
        }

        // Log everything
        logger.setLevel(Level.ALL);
    }


    // Server customisation
    // -------------------------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setBukkitVersion(String bukkitVersion) {
        this.bukkitVersion = bukkitVersion;
    }

    @Override
    public void setDefaultGameMode(GameMode gameMode) {
        this.defaultGameMode = gameMode;
    }


    // Overridden functions from {@link Server}
    // -------------------------------------------------------------------------

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getBukkitVersion() {
        return this.bukkitVersion;
    }

    @Override
    public boolean isPrimaryThread() {
        return true;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public MPluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public GameMode getDefaultGameMode() {
        return this.defaultGameMode;
    }

    // Not implemented functions
    // -------------------------------------------------------------------------

    @Override
    public Collection<? extends Player> getOnlinePlayers() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getMaxPlayers() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getPort() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getViewDistance() {
        throw new AssumeImplementedException();
    }

    @Override
    public String getIp() {
        throw new AssumeImplementedException();
    }

    @Override
    public String getServerName() {
        throw new AssumeImplementedException();
    }

    @Override
    public String getServerId() {
        throw new AssumeImplementedException();
    }

    @Override
    public String getWorldType() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean getGenerateStructures() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean getAllowEnd() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean getAllowNether() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean hasWhitelist() {
        throw new AssumeImplementedException();
    }

    @Override
    public void setWhitelist(boolean b) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        throw new AssumeImplementedException();
    }

    @Override
    public void reloadWhitelist() {
        throw new AssumeImplementedException();
    }

    @Override
    public int broadcastMessage(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public String getUpdateFolder() {
        throw new AssumeImplementedException();
    }

    @Override
    public File getUpdateFolderFile() {
        throw new AssumeImplementedException();
    }

    @Override
    public long getConnectionThrottle() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        throw new AssumeImplementedException();
    }

    @Override
    public Player getPlayer(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public Player getPlayerExact(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public List<Player> matchPlayer(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public Player getPlayer(UUID uuid) {
        throw new AssumeImplementedException();
    }

    @Override
    public BukkitScheduler getScheduler() {
        throw new AssumeImplementedException();
    }

    @Override
    public ServicesManager getServicesManager() {
        throw new AssumeImplementedException();
    }

    @Override
    public List<World> getWorlds() {
        throw new AssumeImplementedException();
    }

    @Override
    public World createWorld(WorldCreator worldCreator) {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean unloadWorld(String s, boolean b) {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean unloadWorld(World world, boolean b) {
        throw new AssumeImplementedException();
    }

    @Override
    public World getWorld(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public World getWorld(UUID uuid) {
        throw new AssumeImplementedException();
    }

    @Override
    public MapView getMap(short i) {
        throw new AssumeImplementedException();
    }

    @Override
    public MapView createMap(World world) {
        throw new AssumeImplementedException();
    }

    @Override
    public void reload() {
        throw new AssumeImplementedException();
    }

    @Override
    public void reloadData() {
        throw new AssumeImplementedException();
    }

    @Override
    public PluginCommand getPluginCommand(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public void savePlayers() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean dispatchCommand(CommandSender commandSender, String s) throws CommandException {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        throw new AssumeImplementedException();
    }

    @Override
    public List<Recipe> getRecipesFor(ItemStack itemStack) {
        throw new AssumeImplementedException();
    }

    @Override
    public Iterator<Recipe> recipeIterator() {
        throw new AssumeImplementedException();
    }

    @Override
    public void clearRecipes() {
        throw new AssumeImplementedException();
    }

    @Override
    public void resetRecipes() {
        throw new AssumeImplementedException();
    }

    @Override
    public Map<String, String[]> getCommandAliases() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getSpawnRadius() {
        throw new AssumeImplementedException();
    }

    @Override
    public void setSpawnRadius(int i) {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean getOnlineMode() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean getAllowFlight() {
        throw new AssumeImplementedException();
    }

    @Override
    public boolean isHardcore() {
        throw new AssumeImplementedException();
    }

    @Override
    public void shutdown() {
        throw new AssumeImplementedException();
    }

    @Override
    public int broadcast(String s, String s1) {
        throw new AssumeImplementedException();
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<String> getIPBans() {
        throw new AssumeImplementedException();
    }

    @Override
    public void banIP(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public void unbanIP(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers() {
        throw new AssumeImplementedException();
    }

    @Override
    public BanList getBanList(BanList.Type type) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<OfflinePlayer> getOperators() {
        throw new AssumeImplementedException();
    }

    @Override
    public ConsoleCommandSender getConsoleSender() {
        throw new AssumeImplementedException();
    }

    @Override
    public File getWorldContainer() {
        throw new AssumeImplementedException();
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers() {
        throw new AssumeImplementedException();
    }

    @Override
    public Messenger getMessenger() {
        throw new AssumeImplementedException();
    }

    @Override
    public HelpMap getHelpMap() {
        throw new AssumeImplementedException();
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, InventoryType inventoryType) {
        throw new AssumeImplementedException();
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, InventoryType inventoryType, String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, int i) throws IllegalArgumentException {
        throw new AssumeImplementedException();
    }

    @Override
    public Inventory createInventory(InventoryHolder inventoryHolder, int i, String s) throws IllegalArgumentException {
        throw new AssumeImplementedException();
    }

    @Override
    public Merchant createMerchant(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public int getMonsterSpawnLimit() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getAnimalSpawnLimit() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        throw new AssumeImplementedException();
    }

    @Override
    public int getAmbientSpawnLimit() {
        throw new AssumeImplementedException();
    }

    @Override
    public String getMotd() {
        throw new AssumeImplementedException();
    }

    @Override
    public String getShutdownMessage() {
        throw new AssumeImplementedException();
    }

    @Override
    public Warning.WarningState getWarningState() {
        throw new AssumeImplementedException();
    }

    @Override
    public ItemFactory getItemFactory() {
        throw new AssumeImplementedException();
    }

    @Override
    public ScoreboardManager getScoreboardManager() {
        throw new AssumeImplementedException();
    }

    @Override
    public CachedServerIcon getServerIcon() {
        throw new AssumeImplementedException();
    }

    @Override
    public CachedServerIcon loadServerIcon(File file) throws IllegalArgumentException, Exception {
        throw new AssumeImplementedException();
    }

    @Override
    public CachedServerIcon loadServerIcon(BufferedImage bufferedImage) throws IllegalArgumentException, Exception {
        throw new AssumeImplementedException();
    }

    @Override
    public void setIdleTimeout(int i) {
        throw new AssumeImplementedException();
    }

    @Override
    public int getIdleTimeout() {
        throw new AssumeImplementedException();
    }

    @Override
    public ChunkGenerator.ChunkData createChunkData(World world) {
        throw new AssumeImplementedException();
    }

    @Override
    public BossBar createBossBar(String s, BarColor barColor, BarStyle barStyle, BarFlag... barFlags) {
        throw new AssumeImplementedException();
    }

    @Override
    public Entity getEntity(UUID uuid) {
        throw new AssumeImplementedException();
    }

    @Override
    public Advancement getAdvancement(NamespacedKey namespacedKey) {
        throw new AssumeImplementedException();
    }

    @Override
    public Iterator<Advancement> advancementIterator() {
        throw new AssumeImplementedException();
    }

    @Override
    public UnsafeValues getUnsafe() {
        throw new AssumeImplementedException();
    }

    @Override
    public void sendPluginMessage(Plugin plugin, String s, byte[] bytes) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        throw new AssumeImplementedException();
    }
}
