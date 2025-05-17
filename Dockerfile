FROM eclipse-temurin:23-jdk

# Cài đặt curl và tar
RUN apt-get update && apt-get install -y curl tar

# Tải và giải nén Tomcat 11.0.7
RUN curl -O https://downloads.apache.org/tomcat/tomcat-11/v11.0.7/bin/apache-tomcat-11.0.7.tar.gz && \
    tar -xvzf apache-tomcat-11.0.7.tar.gz && \
    mv apache-tomcat-11.0.7 /opt/tomcat

# Sao chép WAR vào thư mục webapps
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /opt/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
