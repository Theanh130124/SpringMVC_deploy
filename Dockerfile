# Dùng Tomcat 11 kèm JDK 21
FROM tomcat:11-jdk21

# Xóa các ứng dụng mặc định để tránh bị lẫn
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR của bạn và đổi tên thành ROOT.war để chạy tại http://localhost:8080/
COPY target/SpringMVC_Health_Schedule-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 8080
EXPOSE 8080

# Chạy Tomcat
CMD ["catalina.sh", "run"]
