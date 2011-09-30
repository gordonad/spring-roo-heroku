Heroku with Spring Roo


Recommend Reading:
 - Java Workbook for Heroku: https://github.com/heroku/java-workbook (Do Not configure Roo from this one, use the link below)
 - http://devcenter.heroku.com/articles/spring-mvc-hibernate 

Start Roo 1.2

This demo was started using the included "herooku120.roo" script.

Add REPO environment variable pointing to your Maven Repository
Add DATABASE_URL environment variable pointing to local Postgres database

In Roo shell type:
   script --file herooku120.roo

# ################
# AFTER SCRIPT
# ################

# ################
# Change Database Config
# Add the following Bean to your applicationContext.xml

	<bean class="java.net.URI" id="dbUrl">
	    <constructor-arg value="${DATABASE_URL}"/>
	</bean>

# ################
# Change the Database Config in applicationContext.xml to use the new "dbUrl" bean
 	<bean class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close" id="dataSource">
  	    <property name="driverClassName" value="${database.driverClassName}"/>
 	    <property name="url" value="#{ 'jdbc:postgresql://' + @dbUrl.getHost() + @dbUrl.getPath() }"/>
 	    <property name="username" value="#{ @dbUrl.getUserInfo().split(':')[0] }"/>
 	    <property name="password" value="#{ @dbUrl.getUserInfo().split(':')[1] }"/>
    ...

# ################
# For local testing install the Heroku Toolkit: http://devcenter.heroku.com/articles/java#local_workstation_setup

# ################
# Create the runner file for Heroku
# MUST be named "Procfile" uppercase "P"
# MUST be in the root of the project directory
# MUST only contain the following line (cr at end is ok)
# ################
web: java $JAVA_OPTS -jar target/dependency/jetty-runner.jar --port $PORT target/*.war


# ################
# Changes to pom.xml
# Add the dependency plugin with the Jetty Runner 
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-dependency-plugin</artifactId>
  <version>2.3</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>copy</goal>
      </goals>
      <configuration>
        <artifactItems>
          <artifactItem>
            <groupId>org.mortbay.jetty</groupId>
            <artifactId>jetty-runner</artifactId>
            <version>8.0.1.v20110908</version>
            <destFileName>jetty-runner.jar</destFileName>
          </artifactItem>
        </artifactItems>
      </configuration>
    </execution>
  </executions>
</plugin>


# ################
# Set those Environment Variables
# ################

# NOTE: "DATABASE_URL" is used for local testing and requires a valid postgres db exists
# ################
export DATABASE_URL=postgres://stewie:rupert@localhost/herooku

# NOTE: "REPO" MUST be the PHYSICAL location of the Maven jars (not dereferenced through a settings.xml file)
# ################
export REPO=~/.m2/repository

export JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home
export JAVA_OPTS='-Xmx1536m -XX:PermSize=256m -XX:MaxPermSize=256m'
export MAVEN_HOME=/opt/apache-maven-3.0.3
export MAVEN_OPTS='-Xms512m -Xmx1536m -XX:PermSize=256m -XX:MaxPermSize=256m -Dmaven.artifact.threads=5'


# ################
# Add to Git for deployment:
# ################
$ git init
$ git add .
$ git commit -m "Ready to deploy"
$ heroku create --stack cedar
$ git push heroku master
$ heroku open


# ################
# Need Help on Heroku App
# ################
$ heroku help

# ################
#  (to see running processes)
# ################
$ heroku ps

# ################
# Showing the logs - after 'heroku open'
# ################
$ heroku logs -n 50 -t
