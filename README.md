
# MineMock
MineMock is a mocking framework for Bukkit plugins with JUnit5.  
It tries to make make unit testing of Bukkit plugins a whole lot easier.  

MineMock is not a complete mock implementation yet, but it will be expanded over time.


## Disclaimer
MineMock is mainly inspired from [MockBukkit](https://github.com/seeseemelk/MockBukkit), but built with maven and [JUnit 5](https://junit.org/junit5/).


## Usage
Like all HexoCraft-lib libs, it can be found on my repository:
``` xml
    <repository>
        <id>hexocraft-repo</id>
        <url>http://repo.hexocube.fr/artifactory/public</url>
    </repository>
    
    ...

    <dependency>
        <groupId>com.github.hexocraft-lib</groupId>
        <artifactId>minemock-bukkit</artifactId>
        <scope>test</scope>
    </dependency>    
```

You are now ready to use it.   

```java
    @BeforeAll
    public static void init() {
        // Start the mocked server
        MineMock.start();
    }

    @AfterAll
    public static void cleanUp() {
        // Stop the mocked server
        MineMock.stop();
    }


    @Test
    void MyTest() {

        // Create a fake plugin
        final JavaPlugin plugin = MineMock.createFakePlugin("Fake-Plugin", "1.0.0");
        
        ...
    }

```

For more use cases, check the [examples folder](https://github.com/HexoCraft-lib/MineMock/tree/master/minemock-bukkit/src/test/java/examples).
