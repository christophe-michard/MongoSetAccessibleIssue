# Requirements before running the application

Java 17 is used to run the application.

Please run MongoDB instance on localhost, using port 27017.

The provided docker-compose.yml configuration can be used if Mongo is not installed in local.

```bash
docker-compose up
```

# Run the application

Simply use gradlew to run the application:

```bash
./gradlew bootRun
```

# Crash

The application run will fail with the exception below.

It is likely related to the use of ReflectionUtils.makeAccessible(ReflectionUtils.java:201) from spring-core-6.0.9.jar:6.0.9.

Background: https://mail.openjdk.org/pipermail/jdk9-dev/2016-November/005276.html

```log
Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2023-06-14T15:05:01.708+09:00 ERROR 30041 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'mongoSetAccessibleIssueApplication': Failed to instantiate [com.example.mongosetaccessibleissue.MongoSetAccessibleIssueApplication$$SpringCGLIB$$0]: Constructor threw exception
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateBean(AbstractAutowireCapableBeanFactory.java:1314) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1199) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:560) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:520) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:973) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:941) ~[spring-context-6.0.9.jar:6.0.9]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:608) ~[spring-context-6.0.9.jar:6.0.9]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:733) ~[spring-boot-3.1.0.jar:3.1.0]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:435) ~[spring-boot-3.1.0.jar:3.1.0]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:311) ~[spring-boot-3.1.0.jar:3.1.0]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1305) ~[spring-boot-3.1.0.jar:3.1.0]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1294) ~[spring-boot-3.1.0.jar:3.1.0]
        at com.example.mongosetaccessibleissue.MongoSetAccessibleIssueApplication.main(MongoSetAccessibleIssueApplication.java:19) ~[main/:na]
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.example.mongosetaccessibleissue.MongoSetAccessibleIssueApplication$$SpringCGLIB$$0]: Constructor threw exception
        at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:224) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:87) ~[spring-beans-6.0.9.jar:6.0.9]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateBean(AbstractAutowireCapableBeanFactory.java:1308) ~[spring-beans-6.0.9.jar:6.0.9]
        ... 16 common frames omitted
Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make private java.time.Instant(long,int) accessible: module java.base does not "opens java.time" to unnamed module @58ecb515
        at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354) ~[na:na]
        at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:297) ~[na:na]
        at java.base/java.lang.reflect.Constructor.checkCanSetAccessible(Constructor.java:188) ~[na:na]
        at java.base/java.lang.reflect.Constructor.setAccessible(Constructor.java:181) ~[na:na]
        at org.springframework.util.ReflectionUtils.makeAccessible(ReflectionUtils.java:201) ~[spring-core-6.0.9.jar:6.0.9]
        at org.springframework.data.mapping.PreferredConstructor.<init>(PreferredConstructor.java:56) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.model.PreferredConstructorDiscoverer$Discoverers.buildPreferredConstructor(PreferredConstructorDiscoverer.java:250) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.model.PreferredConstructorDiscoverer$Discoverers$1.discover(PreferredConstructorDiscoverer.java:137) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.model.PreferredConstructorDiscoverer.discover(PreferredConstructorDiscoverer.java:82) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.model.InstanceCreatorMetadataDiscoverer.discover(InstanceCreatorMetadataDiscoverer.java:81) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.model.BasicPersistentEntity.<init>(BasicPersistentEntity.java:113) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity.<init>(BasicMongoPersistentEntity.java:84) ~[spring-data-mongodb-4.1.0.jar:4.1.0]
        at org.springframework.data.mongodb.core.mapping.MongoMappingContext.createPersistentEntity(MongoMappingContext.java:88) ~[spring-data-mongodb-4.1.0.jar:4.1.0]
        at org.springframework.data.mongodb.core.mapping.MongoMappingContext.createPersistentEntity(MongoMappingContext.java:41) ~[spring-data-mongodb-4.1.0.jar:4.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext.doAddPersistentEntity(AbstractMappingContext.java:403) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext.addPersistentEntity(AbstractMappingContext.java:379) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext$PersistentPropertyCreator.lambda$createAndRegisterProperty$3(AbstractMappingContext.java:591) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at java.base/java.lang.Iterable.forEach(Iterable.java:75) ~[na:na]
        at org.springframework.data.mapping.context.AbstractMappingContext$PersistentPropertyCreator.createAndRegisterProperty(AbstractMappingContext.java:588) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext$PersistentPropertyCreator.doWith(AbstractMappingContext.java:542) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.util.ReflectionUtils.doWithFields(ReflectionUtils.java:711) ~[spring-core-6.0.9.jar:6.0.9]
        at org.springframework.data.mapping.context.AbstractMappingContext.doAddPersistentEntity(AbstractMappingContext.java:422) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext.addPersistentEntity(AbstractMappingContext.java:379) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext.getPersistentEntity(AbstractMappingContext.java:280) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext.getPersistentEntity(AbstractMappingContext.java:206) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mapping.context.AbstractMappingContext.getPersistentEntity(AbstractMappingContext.java:92) ~[spring-data-commons-3.1.0.jar:3.1.0]
        at org.springframework.data.mongodb.core.EntityOperations.determineCollectionName(EntityOperations.java:187) ~[spring-data-mongodb-4.1.0.jar:4.1.0]
        at org.springframework.data.mongodb.core.MongoTemplate.getCollectionName(MongoTemplate.java:504) ~[spring-data-mongodb-4.1.0.jar:4.1.0]
        at org.springframework.data.mongodb.core.MongoTemplate.save(MongoTemplate.java:1447) ~[spring-data-mongodb-4.1.0.jar:4.1.0]
        at com.example.mongosetaccessibleissue.MongoSetAccessibleIssueApplication.<init>(MongoSetAccessibleIssueApplication.java:30) ~[main/:na]
        at com.example.mongosetaccessibleissue.MongoSetAccessibleIssueApplication$$SpringCGLIB$$0.<init>(<generated>) ~[main/:na]
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45) ~[na:na]
        at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499) ~[na:na]
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480) ~[na:na]
        at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:198) ~[spring-beans-6.0.9.jar:6.0.9]
        ... 18 common frames omitted


FAILURE: Build failed with an exception.
```