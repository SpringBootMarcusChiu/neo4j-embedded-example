- download Neo4j
- go to directory containing all jar files
- create /tmp directory and cd into it
- find .. -name "*.jar" -exec jar -zf {} \;
- `jar -cf neo4j-4.2.3-super.jar .`
- add the jar to ${project.basedir}\system-scope\neo4j-4.2.3-super.jar
- then add the following in pom.xml
```
<dependency>
    <groupId>org.neo4j</groupId>
    <artifactId>neo4j</artifactId>
    <version>4.2.3</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/system-scope/neo4j-super-4.2.3.jar</systemPath>
</dependency>
```