# Getting Started

##### What am thought about task nature
    This is a simple wiki about the service, first of all I thought of simulating data creation and validation before listing of phone numbers with the right state (valid/not valid).
    Then listing and pagination will hit directly customer table after data creation and validation simulation, so no that lopping for all customers against countries every listing request.

##### Prerequisites
    Docker environment up and running.
    
##### Unit testing
    I try to show different flavor of testing Integration, Repositories and isolated unit tests.
    You will find me using isolated flavor of unit test by focusing on only the method I need to test and mock other dependencies or interacting methods.

##### Steps to get things ready
    Extract the zip folder
    cd phonenumber-service
    mvn clean install
    docker build -t phone-number-service-image .
    docker run -d -p 8080:8080 --name phone-service-container phone-number-service-image:latest
    cd ..
    cd phone-number-page
    docker build -t phone-number-page-image .
    docker run -d -p 4200:80  --link phone-service-container --name phone-page-container phone-number-page-image:latest
    Navigate to http://localhost:4200/
