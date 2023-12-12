# Exam in BackEnd Programming 2023

## Motivation

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
    - [ ] **Customer**
        - [x] Create
        - [ ] Update
          - [x] Name
          - [x] Phone
          - [x] Email
        - [ ] Delete
  - [ ] **Address**
      - [x] Create
      - [ ] Update
          - [ ] Address
      - [ ] Delete
    - [ ] **Order**
        - [ ] Create
        - [ ] Update
            - [ ] Machines
        - [ ] Delete
    - [x] Machine
        - [x] Create
        - [x] Update
            - [ ] Name
        - [x] Delete
    - [x] Subassembly
        - [x] Create
        - [x] Update
            - [] Parts
    - [x] Part
        - [x] Create
        - [x] Update
        - [x] Delete
            - [x] Name

### Priority 2

- [ ] Add pagination to all controllers
- [ ] Comprehensive testing
    - [ ] Unit testing service classes
    - [ ] Integration testing
    - [ ] End-to-end testing
- [ ] Implement flyway (additional feature)

### Priority 3

- [ ] Additional features
- [ ] Test additional features
  - [ ] Unit testing service classes
  - [ ] Integration testing
  - [ ] End-to-end testing

### Before delivering the exam

- [ ] README have all necessary information, GitHub.
- [ ] Deliver Zip to wise-flow
- [ ] Format
- [ ] Tested the application for obvious errors

## Information about the project
We've chosen to design the parts with an understanding of their corresponding subassemblies. This approach simplifies the 
process of connecting a single part to various subassemblies. Initially, we were concerned this might not be in line with 
the exam guidelines. However, after a closer look, we're confident it meets the exam's criteria. The subassembly has been 
designed independently of the machines, following the instructions outlined in the exam.

While we were creating our controllers and services, we started discussing whether we should implement a controller for Address. Our reasoning for not including it was that it seemed counterproductive to have two controllers which can accomplish the same task. According to the exam, under the additional functionality segment it read:
1.    Create a customer and add an address to it.
2.    Create an address and add it to a customer.
3.    Add an address to a customer.
We concluded that it seemed more reasonable to let a customer controller handle this type of functionality, as the address is tied to a customer.

## Plagiarism

## Necessary links
