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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MineMockTest {

    @Test
    void MineMock() {

        // Start the server
        MServer server = MineMock.start();

        // Check server instance
        assertNotNull(server);
        assertNotNull(MineMock.getServer());
        assertNotNull(Bukkit.getServer());
        assertEquals(server, MineMock.getServer());
        assertEquals(server, Bukkit.getServer());

        // Server must be running
        assertTrue(MineMock.isRunning());

        // Stop the server
        MineMock.stop();

        // Server must be null
        assertNull(MineMock.getServer());
        assertNull(Bukkit.getServer());

        // Start the server again
        server = MineMock.start();

        // Server must be running
        assertTrue(MineMock.isRunning());

        // Restart the server
        MServer serverRestarted = MineMock.restart();

        // Check new server instance
        assertNotNull(serverRestarted);
        assertNotNull(MineMock.getServer());
        assertNotNull(Bukkit.getServer());
        assertEquals(serverRestarted, MineMock.getServer());
        assertEquals(serverRestarted, Bukkit.getServer());

        // Server must be running
        assertTrue(MineMock.isRunning());

        // Server must be a new server
        assertNotEquals(server, serverRestarted);

        // Stop the server
        MineMock.stop();
    }
}
