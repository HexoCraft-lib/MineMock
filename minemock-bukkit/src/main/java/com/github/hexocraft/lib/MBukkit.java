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

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;


public class MBukkit {

    // Do not instantiate
    // Use static method instead.
    private MBukkit() {
        throw new IllegalAccessError();
    }

    //
    static MServer setServer(Server server) {
        if (server != null) {
            Bukkit.setServer(server);
        } else {
            return setNullServer();
        }

        return (MServer) Bukkit.getServer();
    }

    private static MServer setNullServer() {
        try {
            Field server = Bukkit.class.getDeclaredField("server");
            server.setAccessible(true);
            server.set(null, null);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (MServer) Bukkit.getServer();
    }
}
