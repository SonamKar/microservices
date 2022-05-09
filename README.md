# MovieApp
* This is a very simple start-level microservice to show how to make Rest APIs.
* Basically it exposes four apis to demonstrate the CRUD (Create, Retrieve, Update and Delete) operations on movie related information.
  * For creating a new movie details entry, a POST api has been constructed.
  * For retrieving or fetching movie details, two GET apis are made available. Now this can return all the movie related details through '/movies' endpoint.         Else, we can also fetch a particular movie detail by movie id through '/movie/{id}' or '/movie?id=<some_num>' endpoint. If no movie resource is found with       the given movie id then a custom MovieNotFounException (extends RuntimeException) with message 'Movie is not present' is thrown. 
  * A PUT method is used for updating an existing movie by id. If not present, new movie details are created.
  * DeleteMapping has been used for deleting an existent movie info.
  * There is one more API which facilitates search of movie details by movie title and genre, i.e., only if a movie entry matches the inputted title and genre       then only it should be returned. It is again a GET api exposed at '/moviesByTitleAndGenre'.  
* ControllerAdvice Usage too is shown for defining my own fields for exception body.
* The in-memory H2 database has been used.
* JPA hibernate with custom methods too are covered. (JDBC template applications will come in subsequent projects) 
* Test cases are not written for this project.
* Packing structure is maintained for easy understanding.
