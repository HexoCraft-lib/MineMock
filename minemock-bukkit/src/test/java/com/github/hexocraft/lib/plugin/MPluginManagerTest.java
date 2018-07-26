package com.github.hexocraft.lib.plugin;

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

import com.github.hexocraft.lib.MPlugin;
import com.github.hexocraft.lib.MineMock;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MPluginManagerTest {

    //
    public class PluginListener implements Listener {

        public boolean enable  = false;

        public PluginListener(JavaPlugin plugin) {
            super();
        }

        @EventHandler()
        public void onPluginEnable(PluginEnableEvent event) {
            enable = true;
        }

        @EventHandler()
        public void onPluginDisable(PluginDisableEvent event) {
            enable = false;
        }
    }




    //
    private Server         server;
    private MPluginManager manager;
    private MPlugin        plugin;

    @BeforeEach
    void setUp() {
        MineMock.start();

        server = MineMock.getServer();
        manager = (MPluginManager) server.getPluginManager();
        plugin = MineMock.createFakePlugin("Fake Plugin", "1.0.0");
    }

    @AfterEach
    void tearDown() {
        MineMock.stop();
    }

    @Test
    void plugins() {

        assertEquals(plugin, manager.getPlugin("Fake_Plugin"));
        assertTrue(manager.isPluginEnabled(plugin));
        assertTrue(manager.isPluginEnabled("Fake_Plugin"));
        assertTrue(manager.getPlugins().length == 1);
    }

    @Test
    void events() {

        PluginListener listener = new PluginListener(plugin);
        PluginEnableEvent event = new PluginEnableEvent(plugin);

        manager.registerEvents(listener, plugin);
        assertTrue(!listener.enable);

        manager.callEvent(new PluginEnableEvent(plugin));
        assertTrue(listener.enable);

        manager.callEvent(new PluginDisableEvent(plugin));
        assertTrue(!listener.enable);
    }
}
