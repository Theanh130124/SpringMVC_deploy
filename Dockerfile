FROM tomcat:11-jdk17
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
