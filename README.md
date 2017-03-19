Wendy's horse pension
=====================

Using `maven` we can compile, package and even run the given application.

Testing
-----

In order to run the tests, just use `mvn test`. No need for setting up a
database here, as the tests use an embedded database to test its functionality. 

Running
-------

This application uses a database with an URL connection hardcoded into the
DataSourceConfiguration class (can be extended to read from properties files).

The H2 database needs to be started first. Then the database scheme needs to be
created. The database SQL files are located at `src/main/resources/db`.
`create-db.sql` should be used to create the tables. The `insert.sql` can be
optionally called to get test data.

Afterwards we just run the following maven command:

``` 
mvn exec:java -Dexec.mainClass=sepm.ss2017.e1625772.gui.JavaFXTest
```

Packaging
--------

The application can be packaged to a JAR file with the following command:

```
mvn package
```
