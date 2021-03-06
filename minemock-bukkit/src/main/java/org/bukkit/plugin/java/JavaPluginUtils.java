package org.bukkit.plugin.java;

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




public class JavaPluginUtils {


    // Do not instantiate
    // Use static method instead.
    private JavaPluginUtils() {
        throw new IllegalAccessError("This is a private constructor");
    }


    /**
     * Sets the enabled status of a java plugin.
     *
     * @param plugin The plugin of which to set the state.
     * @param enabled The state to set it to.
     */
    public static void setEnabled(JavaPlugin plugin, boolean enabled) {
        plugin.setEnabled(enabled);
    }
}
