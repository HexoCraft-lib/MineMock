package examples;

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
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakePlugin {


    @BeforeEach
    void setUp() {
        MineMock.start();
    }

    @AfterEach
    void tearDown() {
        MineMock.stop();
    }

    @Test
    void FakePlugin_all_args() {

        String pluginName = "Fake plugin";
        String pluginVersion = "1.0.0";
        String mainClass = "FakePlugin.class.getName()";
        File dataFolder = new File(".\\target\\test\\Fake-Plugin");
        File file= new File(".\\target\\test\\Fake-Plugin\\fake-plugin.jar");

        // Create a fake plugin
        MPlugin plugin = MineMock.createFakePlugin(pluginName, pluginVersion, mainClass, dataFolder, file);

        // Make sure the plugin is enable
        assertTrue(plugin.isEnabled());

        // Disable the plugins
        MineMock.getServer().getPluginManager().disablePlugin(plugin);

        // Make sure the plugin is disable
        assertFalse(plugin.isEnabled());
    }

    @Test
    void FakePlugin_description_file() {

        String pluginName = "Fake plugin";
        String pluginVersion = "1.0.0";
        String mainClass = "FakePlugin.class.getName()";

        PluginDescriptionFile pdf = new PluginDescriptionFile(pluginName, pluginVersion, mainClass);
        File dataFolder = new File(".\\target\\test\\Fake-Plugin");
        File file= new File(".\\target\\test\\Fake-Plugin\\fake-plugin.jar");

        MPlugin plugin = MineMock.createFakePlugin(pdf, dataFolder, file);

        // Make sure the plugin is enable
        assertTrue(plugin.isEnabled());

        // Disable the plugins
        MineMock.getServer().getPluginManager().disablePlugins();

        // Make sure the plugin is disable
        assertFalse(plugin.isEnabled());
    }
}
