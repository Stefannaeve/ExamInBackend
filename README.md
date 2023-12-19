# Exam in BackEnd Programming 2023

## Task List

### Priority 1

- [x] Create domain models including fields
  - [x] Customer
  - [x] Address
  - [x] Order
  - [x] Machine
  - [x] Subassembly
  - [x] Part
  

- [x] Create domain controllers and service with working methods
    - [x] ***Customer***
        - [x] Create
        - [x] Add
          - [x] Address        
        - [x] Get
          - [x] All
          - [x] All with pagination
          - [x] One by id
        - [x] Update
          - [x] Name
          - [x] Email
          - [x] Phone
          - [x] Address
        - [x] Delete
    - [x] ***Address***
        - [x] Create
        - [x] Get
            - [x] All
            - [x] All with pagination
            - [x] One by id
        - [x] Update
            - [x] Address
        - [x] Delete
    - [x] ***Order***
      - [x] Create
      - [x] Get
          - [x] All
          - [x] All with pagination
          - [x] One by id
      - [x] Update
          - [x] Machines
      - [x] Delete
    - [x] ***Machine***
      - [x] Create
      - [x] Get
          - [x] All
          - [x] All with pagination
          - [x] One by id
      - [x] Update
          - [x] Name
      - [x] Delete
    - [x] ***Subassembly***
        - [x] Create
        - [x] Get
            - [x] All
            - [x] All with pagination
            - [x] One by id
        - [x] Update
            - [x] Name
        - [x] Delete
    - [x] ***Part***
        - [x] Create
        - [x] Get
            - [x] All
            - [x] All with pagination
            - [x] One by id
        - [x] Update
            - [x] Name
        - [x] Delete

### Priority 2
- [x] Comprehensive testing
    - [x] Unit testing service classes
    - [x] Integration testing
    - [x] End-to-end testing

### Priority 3

- [x] Additional features
- [x] Test additional features
  - [x] Unit testing service classes
  - [x] Integration testing
  - [x] End-to-end testing

### Before delivering the exam

- [x] README have all necessary information.
- [x] Deliver Zip to wise-flow
- [x] Format
- [x] Tested the application for obvious errors

## How to run the project
To run this project simply run the ***ExamInBackendApplication*** which can be found under ***Exam -> src -> main -> java -> com.example.examinbackend -> ExamInBackendApplication***.

We have a class named ***DatabaseInitializer*** which will populate the database with some ***parts, subassemblies, and machines.***

We included scratch files, which allows you to test the CRUD operations of our application without having to use an API client such as Postman.
Simply run the application and open the scratch files, and you can click on the green arrow next to the request you want to test.

In the scratch files you will find a POST request on the top, this is due to some other API calls requiring the database to be populated.

This project is run on JDK 17, so make sure you have the correct JDK installed.

#### NOTE
You will have to manually update the ids in some of the API paths to match the ids of the objects you have updated.


## Information about the project

#### --- Reasoning behind the design of the domain models ---
We've chosen to design the parts with an understanding of their corresponding subassemblies. This approach simplifies the 
process of connecting a single part to various subassemblies. Initially, we were concerned this might not be in line with 
the exam guidelines. However, after a closer look, we're confident it meets the exam's criteria. The subassembly has been 
designed independently of the machines, following the instructions outlined in the exam.

#### --- Pagination ---
We used pagination for all possible endpoints that return lists as this was stated under the section, *implement the following functionality*
We also decided to include a getAll() method that would return everything that was in the repository.
Based off of the exam guidelines we assumed that it would not negatively affect us to implement both, it was also useful
for us when testing certain aspects of the application.


## Testing
Our tests can be found under ***Exam -> src -> test -> java -> com.example.examinbackend***. <br/>
You can simply run all our tests by right-clicking on the ***java*** folder and click ***Run 'All Tests'***. <br/>

We have created comprehensive tests through unit testing, integration testing, and end-to-end testing. <br/>
We have tested all our controllers, services, and repositories, leaving us with **89% test coverage**. <br/>

## Issues/Bugs
Towards the end of our exam we discovered an issue with updating order with machines. While trying to reproduce the problem
and trying to debug, we managed to update it once, but when trying to update it again the id of the order was incremented.
This eventually led to the application crashing. One of the issues surrounding this problem was due to a Fetch problem, so
we applied Fetch.EAGER to certain fields instead of Fetch.LAZY which seemed sort of resolve the problem. Based off of our
understanding Fetch.EAGER can become problematic with larger applications, however we decided it was alright to implement it
in such a small project.

## Plagiarism
All the code in this project is written by us, or provided from various repositories from the course GitHub. <br/>
Link to repository: https://github.com/jlwcrews2 <br/>
We found this website to be very helpful when it came to debugging certain error codes: https://www.h2database.com/javadoc/org/h2/api/ErrorCode.html
