# Dùng base image có JDK 23
FROM eclipse-temurin:23-jdk

# Cài curl và tar để tải Tomcat
RUN apt-get update && apt-get install -y curl tar

# Tạo thư mục cài đặt Tomcat
WORKDIR /opt

# Tải và giải nén Tomcat 11.0.7
RUN curl -O https://downloads.apache.org/tomcat/tomcat-11/v11.0.7/bin/apache-tomcat-11.0.7.tar.gz && \
    tar -xvzf apache-tomcat-11.0.7.tar.gz && \
    rm apache-tomcat-11.0.7.tar.gz && \
    mv apache-tomcat-11.0.7 tomcat

# Sao chép WAR vào Tomcat
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /opt/tomcat/webapps/ROOT.war

# Mở port 8080
EXPOSE 8080

# Khởi chạy Tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
