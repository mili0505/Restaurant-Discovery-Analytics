//> using scala "2.13.16"
//> using dep "org.mongodb.scala::mongo-scala-driver:5.13.0"

import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {

    println("==============================================")
    println("   RESTAURANT DISCOVERY & ANALYTICS SYSTEM")
    println("==============================================")

    val db = new RestaurantDB()
    val analytics = new Analytics(db)

    var running = true

    while (running) {

      println()
      println("============== MAIN MENU ==============")
      println("1. Add Restaurant")
      println("2. Search / View Restaurants")
      println("3. Update Restaurant")
      println("4. Delete Restaurant")
      println("5. Restaurant Analytics")
      println("6. Index Information")
      println("7. Scala Features Demo")
      println("8. Exit")
      println("========================================")

      print("Enter your choice: ")

      val choice = StdIn.readLine()

      choice match {

        case "1" =>

          println(
            "\n===== ADD RESTAURANT ====="
          )

          print("Restaurant ID: ")
          val restaurantId =
            StdIn.readLine()

          print("Restaurant Name: ")
          val name =
            StdIn.readLine()

          print("Cuisine: ")
          val cuisine =
            StdIn.readLine()

          print("Borough: ")
          val borough =
            StdIn.readLine()

          print("ZIP Code: ")
          val zip =
            StdIn.readLine()

          print("Score: ")
          val scoreInput =
            StdIn.readLine()

          try {

            val score =
              scoreInput.toDouble

            val restaurant =
              Restaurant(
                restaurantId,
                name,
                cuisine,
                borough,
                zip,
                score
              )

            db.addRestaurant(
              restaurant
            )

          } catch {

            case _: NumberFormatException =>
              println(
                "Invalid score. Please enter a number."
              )
          }

        case "2" =>

          println(
            "\n===== SEARCH MENU ====="
          )

          println(
            "1. View Restaurants"
          )

          println(
            "2. Search by Name"
          )

          println(
            "3. Search by Cuisine"
          )

          println(
            "4. Search by Borough"
          )

          println(
            "5. Search by ZIP Code"
          )

          println(
            "6. Search by Score"
          )

          print("Enter choice: ")

          val searchChoice =
            StdIn.readLine()

          searchChoice match {

            case "1" =>
              db.getAllRestaurants()

            case "2" =>
              print(
                "Enter restaurant name: "
              )

              val name =
                StdIn.readLine()

              db.searchByName(name)

            case "3" =>
              print(
                "Enter cuisine: "
              )

              val cuisine =
                StdIn.readLine()

              db.searchByCuisine(
                cuisine
              )

            case "4" =>
              print(
                "Enter borough: "
              )

              val borough =
                StdIn.readLine()

              db.searchByBorough(
                borough
              )

            case "5" =>
              print(
                "Enter ZIP code: "
              )

              val zip =
                StdIn.readLine()

              db.searchByZip(zip)

            case "6" =>
              print(
                "Enter minimum score: "
              )

              try {

                val score =
                  StdIn
                    .readLine()
                    .toInt

                db.searchByScore(
                  score
                )

              } catch {

                case _: NumberFormatException =>
                  println(
                    "Invalid score."
                  )
              }

            case _ =>
              println(
                "Invalid search choice."
              )
          }

        case "3" =>

          println(
            "\n===== UPDATE RESTAURANT ====="
          )

          print(
            "Enter Restaurant ID: "
          )

          val id =
            StdIn.readLine()

          print(
            "Enter new cuisine: "
          )

          val cuisine =
            StdIn.readLine()

          db.updateCuisine(
            id,
            cuisine
          )

        case "4" =>

          println(
            "\n===== DELETE RESTAURANT ====="
          )

          print(
            "Enter Restaurant ID: "
          )

          val id =
            StdIn.readLine()

          db.deleteRestaurant(id)

        case "5" =>

          println(
            "\n===== RESTAURANT ANALYTICS ====="
          )

          println(
            "1. Restaurants by Cuisine"
          )

          println(
            "2. Restaurants by Borough"
          )

          println(
            "3. Average Score by Cuisine"
          )

          println(
            "4. Top Rated Restaurants"
          )

          print("Enter choice: ")

          val analyticsChoice =
            StdIn.readLine()

          analyticsChoice match {

            case "1" =>
              analytics
                .restaurantsByCuisine()

            case "2" =>
              analytics
                .restaurantsByBorough()

            case "3" =>
              analytics
                .averageScoreByCuisine()

            case "4" =>
              analytics
                .topRatedRestaurants()

            case _ =>
              println(
                "Invalid analytics choice."
              )
          }

        case "6" =>

          db.createIndexes()
          db.showIndexes()

        case "7" =>

          db.collectionDemo()
          db.optionDemo()

        case "8" =>

          println(
            "Closing application..."
          )

          analytics.close()
          db.close()

          running = false

          println(
            "Thank you!"
          )

        case _ =>

          println(
            "Invalid choice. Please enter 1-8."
          )
      }
    }
  }
}