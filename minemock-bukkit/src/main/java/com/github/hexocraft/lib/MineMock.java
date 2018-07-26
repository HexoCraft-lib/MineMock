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

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;


public class MineMock {

    // Server instance
    private static MServer server = null;


    // Do not instantiate
    // Use static method instead.
    private MineMock() {
        throw new IllegalAccessError();
    }


    /**
     * Start the server
     *
     * @return {@link MServer} instance
     */
    public static MServer start() {

        // Do not call start if the server already exist
        if (server != null) {
            throw new IllegalStateException("MServer instance already exist");
        }

        // New server instance
        server = new MServer();

        // Set server instance
        return MBukkit.setServer(server);
    }

    /**
     * Stop the server
     */
    public static void stop() {

        // Disable all plugins while shutting down
        if (isRunning() && server.getPluginManager() != null) {
            server.getPluginManager().clearPlugins();
        }

        // Reset Bukkit server
        server = MBukkit.setServer(null);
    }

    /**
     * restart the server
     *
     * @return {@link MServer} instance
     */
    public static MServer restart() {
        if (isRunning())
            stop();
        return start();
    }

    /**
     * @return true if server is running (mocked)
     */
    public static boolean isRunning() {
        return server != null;
    }

    /**
     * @return {@link MServer} instance
     */
    public static MServer getServer() {
        return server;
    }

    /**
     * Create a fake plugin
     *
     * @param description The {@link PluginDescriptionFile} which contains information about the plugin
     * @param dataFolder The folder which contains plugin data's files
     * @param file The file which contains this plugin
     *
     * @return {@link MPlugin} or null
     */
    public static MPlugin createFakePlugin(PluginDescriptionFile description, File dataFolder, File file) {
        try {
            if (isRunning()) {
                MServer server = MineMock.getServer();
                JavaPlugin plugin = null;

                plugin = server.getPluginManager().loadPlugin(MPlugin.class, description, dataFolder, file);
                server.getPluginManager().enablePlugin(plugin);
                return (MPlugin) plugin;
            } else {
                throw new IllegalStateException("Not mocking");
            }
        }
        catch (InvalidPluginException e) {
            MineMock.getServer().getLogger().log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    /**
     * @param pluginName Name of this plugin
     * @param pluginVersion Version of this plugin
     * @param mainClass Full location of the main class of this plugin
     * @param dataFolder The folder which contains plugin data's files
     * @param file The file which contains this plugin
     *
     * @return {@link MPlugin}
     */
    public static MPlugin createFakePlugin(String pluginName, String pluginVersion, String mainClass, File dataFolder, File file) {
        return createFakePlugin(new PluginDescriptionFile(pluginName, pluginVersion, mainClass), dataFolder, file);
    }

    /**
     * @param pluginName Name of this plugin
     * @param pluginVersion Version of this plugin
     * @param mainClass Full location of the main class of this plugin
     *
     * @return {@link MPlugin}
     */
    public static MPlugin createFakePlugin(String pluginName, String pluginVersion, String mainClass) {
        return createFakePlugin(
            new PluginDescriptionFile(pluginName, pluginVersion, mainClass)
            , new File("./target/test/" + pluginName.replace(" ", "_"))
            , new File("./target/test/" + pluginName.replace(" ", "_²²") + ".jar"));
    }

    /**
     * @param pluginName Name of this plugin
     * @param pluginVersion Version of this plugin
     *
     * @return {@link MPlugin}
     */
    public static MPlugin createFakePlugin(String pluginName, String pluginVersion) {
        return createFakePlugin(pluginName, pluginVersion, "");
    }
}
