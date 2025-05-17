FROM eclipse-temurin:23-jdk

# Cài curl và tar
RUN apt-get update && apt-get install -y curl tar

# Cài Tomcat
RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.0-M17/bin/apache-tomcat-11.0.0-M17.tar.gz && \
    tar -xvzf apache-tomcat-11.0.0-M17.tar.gz && \
    mv apache-tomcat-11.0.0-M17 /opt/tomcat

# Copy WAR vào Tomcat
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /opt/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
