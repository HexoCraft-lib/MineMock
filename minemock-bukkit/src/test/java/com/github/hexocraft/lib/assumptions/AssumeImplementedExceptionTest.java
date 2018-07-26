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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssumeImplementedExceptionTest {

    @Test
    void Constructor_without_message() {
        try {
            throw new AssumeImplementedException();
        } catch(AssumeImplementedException e) {
            assertEquals(e.getMessage(), "Not implemented yet");
        }
    }

    @Test
    void Constructor_with_message() {
        String message = "This is a custum message";
        try {
            throw new AssumeImplementedException(message);
        } catch(AssumeImplementedException e) {
            assertEquals(e.getMessage(), message);
        }
    }
}
