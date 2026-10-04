import org.mongodb.scala._
import org.mongodb.scala.model._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import scala.concurrent.Await
import scala.concurrent.duration._

class RestaurantDB extends RestaurantOperations {

  private val connectionString =
    sys.env.getOrElse(
      "MONGO_URI",
      "mongodb://localhost:27017"
    )

  private val client =
    MongoClient(connectionString)

  private val database =
    client.getDatabase("sample_restaurants")

  private val collection =
    database.getCollection("restaurants")

  private def wait[T](
    result: Observable[T]
  ): Seq[T] = {
    Await.result(
      result.toFuture(),
      30.seconds
    )
  }

  override def addRestaurant(
    restaurant: Restaurant
  ): Unit = {

    try {

      val document = Document(
        "restaurant_id" -> restaurant.restaurantId,
        "name" -> restaurant.name,
        "cuisine" -> restaurant.cuisine,
        "borough" -> restaurant.borough,
        "address" -> Document(
          "zipcode" -> restaurant.zipcode
        ),
        "grades" -> Seq(
          Document(
            "score" -> restaurant.score
          )
        )
      )

      wait(
        collection.insertOne(document)
      )

      println("Restaurant added successfully.")

    } catch {
      case e: Exception =>
        println(
          s"Error while adding restaurant: ${e.getMessage}"
        )
    }
  }

  def getAllRestaurants(
    limit: Int = 10
  ): Unit = {

    try {

      val restaurants = wait(
        collection
          .find()
          .projection(
            Projections.exclude("_id")
          )
          .limit(limit)
      )

      if (restaurants.isEmpty) {
        println("No restaurants found.")
      } else {

        println(
          s"\nShowing ${restaurants.size} restaurants:\n"
        )

        restaurants.foreach { r =>
          println(r.toJson())
        }
      }

    } catch {
      case e: Exception =>
        println(
          s"Error: ${e.getMessage}"
        )
    }
  }

  def searchByName(
    name: String
  ): Unit = {

    try {

      val results = wait(
        collection
          .find(
            regex(
              "name",
              name,
              "i"
            )
          )
          .projection(
            Projections.exclude("_id")
          )
      )

      println(
        s"\nRestaurants matching name: $name"
      )

      if (results.isEmpty) {
        println("No restaurants found.")
      } else {
        results.foreach(
          r => println(r.toJson())
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Search error: ${e.getMessage}"
        )
    }
  }

  def searchByCuisine(
    cuisine: String
  ): Unit = {

    try {

      val results = wait(
        collection
          .find(
            regex(
              "cuisine",
              cuisine,
              "i"
            )
          )
          .projection(
            Projections.exclude("_id")
          )
      )

      println(
        s"\nRestaurants with cuisine: $cuisine"
      )

      if (results.isEmpty) {
        println("No restaurants found.")
      } else {
        results.foreach(
          r => println(r.toJson())
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Search error: ${e.getMessage}"
        )
    }
  }

  def searchByBorough(
    borough: String
  ): Unit = {

    try {

      val results = wait(
        collection
          .find(
            regex(
              "borough",
              borough,
              "i"
            )
          )
          .projection(
            Projections.exclude("_id")
          )
      )

      println(
        s"\nRestaurants in borough: $borough"
      )

      if (results.isEmpty) {
        println("No restaurants found.")
      } else {
        results.foreach(
          r => println(r.toJson())
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Search error: ${e.getMessage}"
        )
    }
  }

  def searchByZip(
    zip: String
  ): Unit = {

    try {

      val results = wait(
        collection
          .find(
            equal(
              "address.zipcode",
              zip
            )
          )
          .projection(
            Projections.exclude("_id")
          )
      )

      println(
        s"\nRestaurants with ZIP code: $zip"
      )

      if (results.isEmpty) {
        println("No restaurants found.")
      } else {
        results.foreach(
          r => println(r.toJson())
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Search error: ${e.getMessage}"
        )
    }
  }

  def searchByScore(
    minScore: Int
  ): Unit = {

    try {

      val results = wait(
        collection
          .find(
            elemMatch(
              "grades",
              gte(
                "score",
                minScore
              )
            )
          )
          .projection(
            Projections.exclude("_id")
          )
      )

      println(
        s"\nRestaurants having score >= $minScore"
      )

      if (results.isEmpty) {
        println("No restaurants found.")
      } else {
        results.foreach(
          r => println(r.toJson())
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Search error: ${e.getMessage}"
        )
    }
  }

  override def updateCuisine(
    id: String,
    newCuisine: String
  ): Unit = {

    val filter =
      Filters.equal(
        "restaurant_id",
        id
      )

    val update =
      Updates.set(
        "cuisine",
        newCuisine
      )

    try {

      val result = Await.result(
        collection
          .updateOne(
            filter,
            update
          )
          .toFuture(),
        30.seconds
      )

      if (result.getMatchedCount > 0) {
        println(
          "Cuisine updated successfully!"
        )
      } else {
        println(
          "Restaurant ID not found!"
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Cuisine update failed: ${e.getMessage}"
        )
    }
  }

  override def deleteRestaurant(
    id: String
  ): Unit = {

    try {

      val result = Await.result(
        collection
          .deleteOne(
            Filters.equal(
              "restaurant_id",
              id
            )
          )
          .toFuture(),
        30.seconds
      )

      if (result.getDeletedCount > 0) {
        println(
          "Restaurant deleted successfully."
        )
      } else {
        println(
          "Restaurant ID not found."
        )
      }

    } catch {
      case e: Exception =>
        println(
          s"Delete error: ${e.getMessage}"
        )
    }
  }

  def createIndexes(): Unit = {

    try {

      wait(
        collection.createIndex(
          Indexes.ascending("name")
        )
      )

      wait(
        collection.createIndex(
          Indexes.ascending("cuisine")
        )
      )

      wait(
        collection.createIndex(
          Indexes.compoundIndex(
            Indexes.ascending("borough"),
            Indexes.ascending("cuisine")
          )
        )
      )

      println(
        "Indexes created successfully."
      )

    } catch {
      case e: Exception =>
        println(
          s"Index error: ${e.getMessage}"
        )
    }
  }

  def showIndexes(): Unit = {

    try {

      val indexes =
        wait(
          collection.listIndexes()
        )

      println(
        "\n===== INDEX INFORMATION ====="
      )

      indexes.foreach { index =>
        println(index.toJson())
      }

    } catch {
      case e: Exception =>
        println(
          s"Index display error: ${e.getMessage}"
        )
    }
  }

  def collectionDemo(): Unit = {

    val data = Seq(
      "Indian",
      "Chinese",
      "Italian",
      "Indian",
      "Mexican"
    )

    val filtered =
      data.filter(
        _.length > 5
      )

    val mapped =
      data.map(
        _.toUpperCase
      )

    val grouped =
      data.groupBy(identity)

    println(
      "\n===== SCALA COLLECTION DEMO ====="
    )

    println("Original:")
    println(data)

    println("Filter:")
    println(filtered)

    println("Map:")
    println(mapped)

    println("GroupBy:")
    println(grouped)
  }

  def optionDemo(): Unit = {

    val restaurantName: Option[String] =
      Some("Sample Restaurant")

    restaurantName match {

      case Some(name) =>
        println(
          s"Restaurant found: $name"
        )

      case None =>
        println(
          "Restaurant name not available."
        )
    }
  }

  def close(): Unit = {
    client.close()
  }
}