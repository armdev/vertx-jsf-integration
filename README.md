# vertx-jsf-integration
vertx backend as REST/Message bus, JSF frontend under Apache Tomcat 8

Just demo application.

1. Run vertx backend jar
2. Run JSF web application under Apache Tomcat 8 / 8585 port
3. Send Message with username from different browsers like different users
4. In the main page  you will receive messages and messages log from messga bus. messages will automatically update via primefaces poll.
5. java script client listener in header_main.xhtml, have a look at middleBus configuration, it receive message from bus and send it runtime to servlet, which is save it and show in the page.:)))
