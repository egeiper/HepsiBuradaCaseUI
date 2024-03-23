# HepsiBuradaCase

This is a demo project for case given by HepsiBurada.

### Stack:
- Language: Java
- Framework: Selenium
- Build Tool: Maven, Gradle
- Test Framework: TestNG
- Reporting: Allure
- CI/CD: GitHub Actions

### How to run the project ?
- To run project on Selenium Grid:
  - Set gridURL in test.properties file.
  - Execute command `./gradlew clean test -Pserver=grid`


- To run project in local with Gradle: `./gradlew clean test -Pserver=local`
- To run project in local with TestNG: `mvn clean test -Dsurefire.suiteXmlFiles=tests.xml`

### How to run project on GitHub Actions ?
- Go to Actions tab and run the "Run with Gradle" workflow. Workflow will setup java and latest version of chrome in ubuntu machine. Then it will build project with Gradle. At the end it will create report artifact in execution.


- <b>Info</b>: Because HepsiBurada doesn't allow to run browsers on headless mode, this implementation is built just for demonstration.

![readme_img_2.png](images%2Freadme_img_2.png)

### How to publish project into your S3 bucket ?

1. Create a S3 bucket in AWS and put its URL into gradle.properties file as `AWS_S3_URL`.
2. Create a IAM user and give permission to access S3 bucket.
3. Create a AWS access key and set the values of IAM user and put it into gradle.properties file as `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`.
4. Run the command `mvn install -DskipTests` to compile all needed components in the project.
5. Run the command `mvn package -DskipTests` to create jar of compiled code. (Excluding the test package)
6. Run the command `gradle publish ` to publish the project into your S3 bucket as a jar file with its dependencies. 

![readme_img_1.png](images%2Freadme_img_1.png)