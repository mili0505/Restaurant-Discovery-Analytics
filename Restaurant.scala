case class Restaurant(
  restaurantId: String,
  name: String,
  cuisine: String,
  borough: String,
  zipcode: String,
  score: Double
)

trait RestaurantOperations {
  def addRestaurant(restaurant: Restaurant): Unit

  def updateCuisine(
    id: String,
    newCuisine: String
  ): Unit

  def deleteRestaurant(
    id: String
  ): Unit
}