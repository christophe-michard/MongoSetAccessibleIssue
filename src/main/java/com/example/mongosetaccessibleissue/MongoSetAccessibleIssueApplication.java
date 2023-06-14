package com.example.mongosetaccessibleissue;

import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.time.Instant;

@SpringBootApplication
public class MongoSetAccessibleIssueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoSetAccessibleIssueApplication.class, args);
    }

    public MongoSetAccessibleIssueApplication() {
        // Create a custom mongoTemplate
        // Crash happens only if we create a template with the mappingMongoConverter as second argument
        MongoDatabaseFactory mongoDbFactory = new SimpleMongoClientDatabaseFactory(MongoClients.create(), "db");
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, mappingMongoConverter);

        // Crash happens when this line is executed
        mongoTemplate.save(new MongoEntity());
    }
}

class MongoEntity {

    // We need a field that is a class from java.time package, for instance Instant, to provoke a crash.
    // Background: https://mail.openjdk.org/pipermail/jdk9-dev/2016-November/005276.html
    // Crash sample:
    //   Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make private java.time.Instant(long,int) accessible: module java.base does not "opens java.time" to unnamed module @1afd44cb
    // 	   at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354) ~[na:na]
    //	   at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:297) ~[na:na]
    //	   at java.base/java.lang.reflect.Constructor.checkCanSetAccessible(Constructor.java:188) ~[na:na]
    //	   at java.base/java.lang.reflect.Constructor.setAccessible(Constructor.java:181) ~[na:na]
    //	   at org.springframework.util.ReflectionUtils.makeAccessible(ReflectionUtils.java:201) ~[spring-core-6.0.9.jar:6.0.9]
    //     at org.springframework.data.mapping.PreferredConstructor.<init>(PreferredConstructor.java:56) ~[spring-data-commons-3.1.0.jar:3.1.0]
    public Instant createdDateTime;
}
