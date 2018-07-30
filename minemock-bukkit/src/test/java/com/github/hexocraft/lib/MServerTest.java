package com.github.hexocraft.lib;

/*

 Copyright 2018 hexosse

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

import org.bukkit.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
class MServerTest {

    private Server server;

    @BeforeAll
    void init() {
        MineMock.start();
        server = MineMock.getServer();
    }

    @AfterAll
    void cleanUp() {
        MineMock.stop();
    }


    @Test
    void updateFolder() {

        assertEquals("update", server.getUpdateFolder());
        assertEquals(new File(new File("plugins"), server.getUpdateFolder()).toString(), server.getUpdateFolderFile().toString());

    }

}
