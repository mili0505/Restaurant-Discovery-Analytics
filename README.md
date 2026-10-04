# Restaurant Discovery & Analytics System

A Scala-based CLI application for restaurant discovery and analytics using MongoDB.

The application uses MongoDB's official `sample_restaurants` dataset as the initial data source and performs database operations through Scala.


## Database
* Database: `sample_restaurants`
* Collection: `restaurants`


### 1. Technologies Used

* Scala 2.13.16
* Scala CLI
* MongoDB Atlas
* MongoDB Scala Driver
* MongoDB Compass
* Visual Studio Code

`sbt` is not required for this project because Scala CLI is used to build and run the application.


### 2. Project Structure
RestaurantAnalytics/
│
├── Main.scala
├── Restaurant.scala
├── RestaurantDB.scala
├── Analytics.scala
└── README.md


### Description
* `Main.scala` - Menu-driven CLI application and user input.
* `Restaurant.scala` - Restaurant case class and operations trait.
* `RestaurantDB.scala` - MongoDB connection and CRUD operations.
* `Analytics.scala` - Search, indexing and aggregation operations.
* `README.md` - Project setup and execution instructions.


### 3. Prerequisites
Install the following before running the project:

1. Java JDK 17 or later
2. Scala CLI
3. MongoDB Atlas account
4. MongoDB Compass (optional, for viewing the database)


### 4. MongoDB Setup

## Step 1: Create MongoDB Atlas Cluster
- Create a MongoDB Atlas cluster and load the official MongoDB sample dataset.

The application requires:
    Database: sample_restaurants
    Collection: restaurants

Make sure the restaurants collection contains the official sample restaurant data.

## Step 2: Get MongoDB Connection String
- From MongoDB Atlas, copy the connection string for the cluster.

The connection string is used by the Scala application to connect to MongoDB.

Example format:
   mongodb+srv://<username>:<password>@<cluster-url>/

## Step 3: Configure the Application
- Open the MongoDB connection configuration in:

RestaurantDB.scala
Add your MongoDB connection string where required.

**Important:** Do not upload your real MongoDB password or private connection string to a public GitHub repository.



### 5. Running the Application

- Open PowerShell/Terminal inside the project folder:

Run the application using:  scala-cli run .

The application starts with a menu-driven interface.

Example:
1. Add Restaurant
2. Search/View Restaurants
3. Update Restaurant
4. Delete Restaurant
5. Restaurant Analytics
6. Index Information
7. Exit


### 6. CRUD Operations
The application performs all required MongoDB CRUD operations through Scala.

- Create
Two new restaurant records can be added using the application.

- Read
Restaurant records can be viewed and searched.

- Update
An existing restaurant can be updated using its restaurant ID.

- Delete
A restaurant can be deleted using its restaurant ID.


### 7. Search and Filtering

The application supports multiple search/filter operations, including:

* Search by restaurant name
* Search by cuisine
* Search by borough
* Search by ZIP code
* Search/filter using restaurant score

These operations are performed through the Scala application.



### 8. Indexing

The application creates and uses MongoDB indexes relevant to the implemented queries.

It includes:
* At least two indexes
* At least one compound index


### 9. Aggregation and Analytics
The application performs MongoDB aggregation operations for restaurant analytics.

Implemented analytics include:
- Restaurants by Cuisine
Groups restaurants according to their cuisine.

- Restaurants by Borough
Groups restaurants according to their borough.

- Average Score by Cuisine
Calculates the average restaurant score for different cuisines.

- Top Rated Restaurants
Displays restaurants having the highest scores.


### 10. Scala Concepts Demonstrated
The project demonstrates the following Scala concepts:

* Variables and data types
* Conditions
* Functions and methods
* Scala collections
* `map`
* `filter`
* `groupBy`
* `Option`
* Pattern matching
* Exception/error handling
* Classes and case classes
* Objects
* Encapsulation
* Trait
* MongoDB CRUD operations


### 11. Execution Flow

MongoDB Atlas
      |
      v
sample_restaurants
      |
      v
restaurants collection
      |
      v
Scala CLI Application
      |
      +---- CRUD Operations
      |
      +---- Search & Filtering
      |
      +---- Indexing
      |
      +---- Aggregation & Analytics



## 13. Notes
* The application starts with MongoDB's official `sample_restaurants` dataset.
* All required database operations are performed through Scala.
* MongoDB Compass can be used to verify the database records and indexes.
* Scala CLI is used to compile and execute the project.
* The MongoDB connection string should not be shared publicly.