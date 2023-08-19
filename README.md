# PetStore API Testing Framework

This framework is designed for API testing. It provides utilities and methodologies to make REST API testing easier, more effective, and more organized.

Features

Supports multiple HTTP methods: GET, POST, PUT.
Uses Java combined with Maven for dependency management.
Utilizes TestNG for test organization and execution.
Detailed logging for debugging and traceability.
Allows data-driven testing using JSON files.

## Prerequisites

Java JDK (Version recommended: 8 or above).
Maven (If not installed, refer to the installation guide below).
Your favorite IDE (e.g., IntelliJ IDEA, Eclipse).

## Getting Started

### Clone the Repository:
git clone <repository_url>

### Navigate to the Project Directory:
cd path/to/directory

### Install Dependencies:
mvn install

### Run Tests:
mvn test


## Directory Structure
src/main/java: Contains the main utilities and classes for the framework.
src/test/java: Houses the actual test cases and the test utilities.
src/test/resources: Place your JSON files and other resources here.
pom.xml: Maven configuration file.
testng.xml: TestNG configuration file.

## Logging

Logging is facilitated using java.util.logging.Logger. This allows for detailed test execution logs, aiding in debugging and traceability.

## Reporting

After executing the tests via mvn test, reports can be found in the test-output directory.

## Contact

Email - [vijaykumarkhl@gmail.com]

Project Link: https://github.com/your_username/repo_name
