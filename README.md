QA Technical Challenge - ProductStore

This project implements automated tests for the site https://demoblaze.com/index.html using Selenium Webdriver with Java and testNG as test framework.


**Prerequisites**
Make sure to install:
- Java:
  - brew install openjdk@17
- Maven
  - brew install maven
- Chromedriver (for Mac):
  - brew install --cask chromedriver
**How to setup using the github repo**
- Clone the repo
  - git@github.com:jremaggi87/arionkoder.git
- Configure Chromedriver
  - sudo xattr -r -d com.apple.quarantine /opt/homebrew/bin/chromedriver
  - sudo chmod +x /opt/homebrew/bin/chromedriver
- Install dependencies
  - mvn clean install
**How to setup with the zip file**
- Extract the zip
- Open up a terminal and go to the project folder
- Install dependencies by doing:
  -  mvn clean install
**How to execute tests**
- Execute all tests
  - mvn clean test
- Execute one specific test
  - mvn test -Dtest=LoginTest
**How to generate report**
- Install allure
  - brew install allure
- Generate allure report
  - mvn allure:report
- Open generated report
  - allure serve target/allure-results

