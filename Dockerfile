FROM tomcat:11-jdk21  # hoặc jdk23 nếu có (tùy image trên DockerHub)

# Xóa ứng dụng mặc định trong Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR vào thư mục ROOT
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Cấp quyền đầy đủ (phòng trường hợp Tomcat không đọc được file)
RUN chmod -R 755 /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]
