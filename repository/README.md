- download Neo4j
- go to directory containing all jar files
- create /tmp directory and cd into it
- find .. -name "*.jar" -exec jar -zf {} \;
- optional (for Static Local Repository):
    - remove from META-INF the . ecplipse.* files
- jar -cf neo4j-4.2.3-super.jar .
- mvn install:install-file -DlocalRepositoryPath=./repository -DcreateChecksum=false -Dpackaging=jar -Dfile=neo4j-super-4.2.3.jar -DgroupId=local -DartifactId=neo4j-super -Dversion=4.2.3

- then add the following in pom.xml
```
  <dependency>
      <groupId>local</groupId>
      <artifactId>neo4j-super</artifactId>
      <version>4.2.3</version>
      <scope>compile</scope>
  </dependency>
```
```
  <repositories>
      <repository>
          <id>ProjectRepo</id>
          <url>file://${project.basedir}/repository</url>
      </repository>
  </repositories>
```