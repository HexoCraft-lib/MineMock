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

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.Optional;

import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;


public class AssumeImplementedHandler implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        Optional<AssumeImplemented> annotationMethod = findAnnotation(context.getTestMethod(), AssumeImplemented.class);
        Optional<AssumeImplemented> annotationClass = findAnnotation(context.getTestClass(), AssumeImplemented.class);

        if (!(annotationMethod.isPresent() || annotationClass.isPresent())) {
            throw throwable;
        }

        if (!(throwable instanceof AssumeImplementedException)) {
            throw throwable;
        }
    }
}
