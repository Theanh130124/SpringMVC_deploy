FROM tomcat:11-jdk20
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]