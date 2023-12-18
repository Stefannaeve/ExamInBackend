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
        - [x] Update
            - [ ] Address
        - [ ] Delete
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

- [ ] README have all necessary information, GitHub.
- [ ] Deliver Zip to wise-flow
- [ ] Format
- [ ] Tested the application for obvious errors

## How to run the project
To run this project simply run the ***ExamInBackendApplication*** which can be found under ***Exam -> src -> main -> java -> com.example.examinbackend -> ExamInBackendApplication***.

We have class named ***DatabaseInitializer*** which will populate the database with some ***parts, subassemblies, and machines.***

We included scratch files, which allows you to test the CRUD operations of our application without having to use an API client such as Postman.
Simply run the application and open the scratch files, and you can click on the green arrow next to the request you want to test.

In the scratch files you will find a POST request on the top, this is due to some other API calls requiring the database to be populated.



## Information about the project

#### --- Reasoning behind the design of the domain models ---
We've chosen to design the parts with an understanding of their corresponding subassemblies. This approach simplifies the 
process of connecting a single part to various subassemblies. Initially, we were concerned this might not be in line with 
the exam guidelines. However, after a closer look, we're confident it meets the exam's criteria. The subassembly has been 
designed independently of the machines, following the instructions outlined in the exam.

## Testing ##
Our tests can be found under ***Exam -> src -> test -> java -> com.example.examinbackend***. <br/>
You can simply run all our tests by right-clicking on the ***java*** folder and click ***Run 'All Tests'***. <br/>

We have created comprehensive tests through unit testing, integration testing, and end-to-end testing. <br/>
We have tested all our controllers, services, and repositories, leaving us with **90% test coverage**. <br/>

## Plagiarism
All the code in this project is written by us, or provided from various repositories from the course GitHub. <br/>
Link to repository: https://github.com/jlwcrews2
## Necessary links
Link to this projects GitHub repository: https://github.com/Stefannaeve/ExamInBackend