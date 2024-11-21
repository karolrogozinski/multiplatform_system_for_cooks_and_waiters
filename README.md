# Requirements and assumptions of the project

### The task is to write a system to operate a restaurant.

We plan to create the project in a client-server architecture. We will have one main server that will communicate with the database and mobile and desktop applications communicating with the server.

### As part of the project we will create:

## Desktop application

A desktop application for cooks written in Java with a graphical interface written using Swing or JavaFX library.

### Functionality:

- for cooks

- display of pending orders
- ability to assign oneself to the execution of a dish
- ability to edit the status of a dish
- display of information about dishes (ingredients, recipe, category, etc.)
- editing the amount of ingredients in stock after an order has been made (we can potentially do this automatically at the database level)
- ability to display orders assigned to a particular cook
- logging in as a cook
- several cooks can be on the same device at the same time (we do not require each cook to have his own computer)
- initially only selection from the list of cooks, and if there is enough time => implementation of full login with password (probably auth0)
- display of dishes to be prepared by cooks in several windows / split window
- or
- ability to quickly switch logged-in users

## Mobile application

Mobile application for waiters written in Java for Android phones.

### Functionality:

- for waiters
- adding a new bill
- browsing through dish categories
- viewing available dishes within a category
- placing orders for dishes within a given account
- logging in as a waiter
- initially only selection from the list of waiters, and if there is enough time => implementation of full login with password (probably auth0)
- only one user can be logged in on one device (we assume that each waiter has his own phone)
- display of an open account assigned to a given waiter
- closing the account - displaying the amount to be paid

## Api

Server written in Java using Spring Boot framework.

### Functionality:

- is to serve as an intermediary between applications used by employees and the database
- main task is to map objects from relational database to java objects and vice versa

## Database

Oracle central database

### Initial database and relationship design:
![Relational model](./diagrams/relational_model_draft.png)

## Optional functionalities that we plan to introduce, only after the critical functionalities are in place:

(These functionalities can be replaced by direct access of these people to the database)

## Desktop application

Desktop application for procurement and admin written in Java with a graphical interface written using Swing or JavaFX library

- for inventory

- display of inventory data
- displaying what is in short supply
- updating the number of components when supplies arrive

- for admin

- crud ingredients / products
- crud dishes
- crud employees
- crud tables
- crud categories
