package com.github.hexocraft.lib.assumptions;

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


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(Lifecycle.PER_CLASS)
class AssumeImplementedMethodTest {


    private TestClass testClass;

    @BeforeAll
    void init() {
        testClass = new TestClass();
    }

    @Test
    void implemented() {
        assertTrue(testClass.implemented());
    }

    /** Not the use of {@link AssumeImplemented} */
    @Test
    @AssumeImplemented()
    void notImplemented() {
        assertTrue(testClass.notImplemented());
    }

    @Test
    void exception() {
        assertThrows(RuntimeException.class, () -> testClass.exception());
    }

}
