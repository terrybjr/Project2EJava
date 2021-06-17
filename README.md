<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Project2E</h3>

  <p align="center">
    Java application supporting the Project2E web application.
    <!-- 
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a>
  -->
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
<!--
[![Product Name Screen Shot][product-screenshot]](https://example.com)
-->

PLACE HOLDER ABOUT
### Built With

* [JDK 8u291](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
* [Eclipse IDE 2021‑06](http://www.eclipse.org/downloads/packages/release/helios/sr1/eclipse-ide-java-ee-developers)
* [Wildfly 23.0.2](https://www.wildfly.org/downloads)



<!-- GETTING STARTED -->
## Getting Started

To get the application up and running follow these steps.

### Prerequisites
* Install Java and run the following command to check the Java Version and ensure a good install.
  ```sh
  java -version
  ```
* Install Eclipse.
* Download mysql-connector-java-8.0.24.jar

### Installation
1. Create workspace for project.

2. Initialize repo in workspace
  ```sh
  git init
  ```
3. Clone the repo
   ```sh
   git clone https://github.com/terrybjr/Project2EJava.git
   ```
4. Open workspace in Eclipse
5. Import existing maven projects. (Browse for the workspace directory)
6. Add wildfly 23 server via Eclipse server view (Under Jboss community, will have you install)
7. Run the server in eclipse.
8. Navigate to Server-Install/bin in cli
9. run add-user, follow the prompts to add a user.
  ```language
    ./add-user
  ```
10. Navigate to http://127.0.0.1:9990 in a web-browser and login using the credentials you created in step 9.
11. Once logged in, navigate to Deployments and click the + button, and add the mysql-connector-java-8.0.24.jar as a deployment
12. Navigate to Configuration -> Datasources & Drivers -> Datasources -> and click the + button.
13. Select Mysql hit next.
14. Keep the default name and JNDI Name and hit next
15. Select mysql-connector-java-8.0.24.jar as the driver name, keep the rest and hit next.
16. Use jdbc:mysql://24.242.87.134:3306/project2e as the connection url
17. Message Brian Terry, to get a unique username/password. 
18. Test the connection... if successful you are done configuring the server.
19. In Eclipse, right click trunk and maven-> install. 
20. Drill down into the ear project and refresh, mark ear as deployable. You should be able to watch the console to see if any errors occur. 

