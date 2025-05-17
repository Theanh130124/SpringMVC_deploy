FROM tomcat:11-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/SpringMVC_Health_Schedule.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
