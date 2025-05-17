FROM tomcat:11-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
RUN chmod -R 755 /usr/local/tomcat/webapps
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
