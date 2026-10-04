import org.mongodb.scala._
import org.mongodb.scala.model._
import scala.concurrent.Await
import scala.concurrent.duration._

class Analytics(db: RestaurantDB) {
  private val connectionString =
    sys.env.getOrElse("MONGO_URI","mongodb://localhost:27017")
  private val client = MongoClient(connectionString)
  private val database = client.getDatabase("sample_restaurants")
  private val collection = database.getCollection("restaurants")
  private def wait[T](
    result: Observable[T]
  ): Seq[T] = {

    Await.result(
      result.toFuture(),
      30.seconds
    )
  }

  def restaurantsByCuisine(): Unit = {
    println("\n===== RESTAURANTS BY CUISINE =====")

    try {
      val pipeline = Seq(
        Aggregates.group(
          "$cuisine",
          Accumulators.sum(
            "count",
            1
          )
        ),

        Aggregates.sort(
          Sorts.descending(
            "count"
          )
        )
      )

      val results =
        wait(
          collection.aggregate(
            pipeline
          )
        )

      results.foreach { r =>
        println(
          r.toJson()
        )
      }

    } catch {

      case e: Exception =>
        println(
          s"Aggregation error: ${e.getMessage}"
        )
    }
  }

  def restaurantsByBorough(): Unit = {

    println(
      "\n===== RESTAURANTS BY BOROUGH ====="
    )

    try {

      val pipeline = Seq(

        Aggregates.group(
          "$borough",
          Accumulators.sum(
            "count",
            1
          )
        ),

        Aggregates.sort(
          Sorts.descending(
            "count"
          )
        )
      )

      val results =
        wait(
          collection.aggregate(
            pipeline
          )
        )

      results.foreach { r =>
        println(
          r.toJson()
        )
      }

    } catch {

      case e: Exception =>
        println(
          s"Aggregation error: ${e.getMessage}"
        )
    }
  }

  def averageScoreByCuisine(): Unit = {

    println(
      "\n===== AVERAGE SCORE BY CUISINE ====="
    )

    try {

      val pipeline = Seq(

        Aggregates.unwind(
          "$grades"
        ),

        Aggregates.group(
          "$cuisine",
          Accumulators.avg(
            "averageScore",
            "$grades.score"
          )
        ),

        Aggregates.sort(
          Sorts.ascending(
            "averageScore"
          )
        )
      )

      val results =
        wait(
          collection.aggregate(
            pipeline
          )
        )

      results.foreach { r =>
        println(
          r.toJson()
        )
      }

    } catch {

      case e: Exception =>
        println(
          s"Aggregation error: ${e.getMessage}"
        )
    }
  }

  def topRatedRestaurants(): Unit = {

    println(
      "\n===== TOP RATED RESTAURANTS ====="
    )

    try {

      val pipeline = Seq(

        Aggregates.unwind(
          "$grades"
        ),

        Aggregates.sort(
          Sorts.descending(
            "grades.score"
          )
        ),

        Aggregates.limit(10),

        Aggregates.project(
          Projections.fields(
            Projections.include(
              "name",
              "borough",
              "cuisine",
              "grades"
            ),
            Projections.excludeId()
          )
        )
      )

      val results =
        wait(
          collection.aggregate(
            pipeline
          )
        )

      results.foreach { r =>
        println(
          r.toJson()
        )
      }

    } catch {

      case e: Exception =>
        println(
          s"Aggregation error: ${e.getMessage}"
        )
    }
  }

  def close(): Unit = {
    client.close()
  }
}