FROM centos
ENV JDK8TAR=OpenJDK8U-jdk_x64_linux_hotspot_8u322b06.tar.gz
ENV JDK17TAR=OpenJDK17U-jdk_x64_linux_hotspot_17.0.2_8.tar.gz
ENV TOMCATTAR=apache-tomcat-9.0.62.tar.gz
ENV VULNTAR=vuln-test.tar.gz
ENV JDK8=jdk8u322-b06
ENV JDK17=jdk-17.0.2+8
ENV TOMCAT=apache-tomcat-9.0.62
ENV SPRING2=vuln-springboot2-3.0.3.jar
ENV SRPING3=vuln-springboot3-3.0.3.jar
COPY tar/ /data
EXPOSE 8001 8002 8003
RUN cd /data/ \
    && tar -xvzf $JDK8TAR \
    && tar -xvzf $JDK17TAR \
    && tar -xvzf $TOMCATTAR \
    && tar -xvzf $VULNTAR \
    && mv vuln-test ROOT \
    && cd $TOMCAT \
    && chmod +x bin/*.sh \
    && sed -i 's/8080/8001/g' conf/server.xml \
    && sed -i '1a JAVA_HOME=\"/data/$JDK8/\"' bin/catalina.sh \
    && cd webapps \
    && mv ROOT/ tomcat \
    && mv /data/ROOT . \
    && mkdir /data/logs \
    && echo '#!/bin/sh' > /start.sh \
    && echo 'nohup /data/$JDK8/bin/java -jar /data/$SPRING2 >/data/logs/vuln-springboot2.log &' >> /start.sh \
    && echo 'nohup /data/$JDK17/bin/java -jar /data/$SRPING3 >/data/logs/vuln-springboot3.log &' >> /start.sh \
    && echo 'sh /data/$TOMCAT/bin/catalina.sh run' >> /start.sh \
    && echo '/etc/ntp.conf:my content' >/etc/ntp.conf \
    && chmod +x /start.sh
CMD /start.sh