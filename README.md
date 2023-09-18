# Selenium-Java reference implementation project for Jupiter Prime

This project is incomplete, it is intended as a guide and template as well as training and assessment purposes.

It is highly recommended that Visual Studio Code with .devcontainer is used when developing Automation projects that will be executed in a CI/CD environment using docker. Devcontainers will give every person in a team the same dev environment as well as the same environment that CI/CD will use to run the code.

## Quick Start

* Make sure you have JDK 17 & Maven
* Crate a .env file in the src/test/resources folder with the following entires:

```text
SELENIUM_URL=http://jupiterprime-react-prod.s3-website.us-east-2.amazonaws.com
SELENIUM_BROWSER=chrome
SELENIUM_WAIT=3
SELENIUM_HEADLESS=true
```

* Run:

```bash
mvn test
```

## Visual Studio Code devcontainer requirements

* Install the Remote Containers extension
* Run the 'Remote-Containers: Reopen In Container' command
  * Be patient while the first build of .devcontainers docker-compose runs
* To configure environment variables for test execution from within vscode please open the file .vscode/setting.json and edit the env vars there

## Local Requirements

* OpenJDK 17
  * Make sure JAVA_HOME points to the OpenJDK 17 installation dir
  * Make sure that $JAVA_HOME/bin is the only java bin directory in your $PATH
* Maven
* Chromedriver and Geckodriver in your $PATH (%PATH% for Windows)
  * Install on Windows:
    * ```choco install selenium-gecko-driver```
    * ```choco install chromedriver```
  * Install on macOS:
    * ```brew cask install chromedriver```
    * ```brew install geckodriver```

## Environment setup

This template follows <https://12factor.net/config> and as a consequence it reads configuration information from environment variables.

These are the environment variables that can be set to configure the template:

* **SELENIUM_URL** -> Mandatory. The URL of the system under test
* **SELENIUM_BROWSER** -> Mandatory. 'chromium'|'firefox'
* **SELENIUM_WAIT** -> Mandatory. Implicit wait for the project in seconds. It is recommended that this is less than 5 and no more than 10.
* **SELENIUM_HEADLESS** -> Optional. 'true|false' defaults to true

## .env file

To assist development when setting or changing environment variables on the fly without having to set them at the OS level create a file with name `.env` inside the src/test/resources folder. This file can contain the environment variables that will be used during the creation of test cases.

|**DON'T ADD .env TO A GIT REPO.**
|--------------------------------------|
|*Make sure that .gitignore contains an entry for .env before creating the file*

## Test environments

### ReactJS site

<http://jupiterprime-react-prod.s3-website.us-east-2.amazonaws.com>
