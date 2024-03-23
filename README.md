# HepsiBuradaCase

This is a demo project for case given by HepsiBurada.

### Stack:
- Language: Java
- Framework: Selenium
- Build Tool: Maven, Gradle
- Test Framework: TestNG
- Reporting: Allure
- CI/CD: GitHub Actions

### How to publish project into your S3 bucket ?

1. Create a S3 bucket in AWS and put its URL into gradle.properties file as `AWS_S3_URL`.
2. Create a IAM user and give permission to access S3 bucket.
3. Create a AWS access key and set the values of IAM user and put it into gradle.properties file as `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`.
4. Run the command `mvn install` to compile all needed components in the project.
5. Run the command `mvn package -DskipTests` to create jar of compiled code. (Excluding the test package)
6. Run the command `gradle publish ` to publish the project into your S3 bucket as a jar file with its dependencies. 

![readme_img_1.png](images%2Freadme_img_1.png)