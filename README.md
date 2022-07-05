# BasicMoviesApp
An android app built using Kotlin to display movies and its details. The movies are dislayed in form of a list that is fetched from the API.

The app consits of three major screens/activities : 
- Upcoming Movies 
- Popular Movies 
- Top-rated Movies

Each particular screen has 10 – 20 movies with an image/poster, title, genres and a brief overview. The movies are arranged in the form of a responsive grid.

## Functioning of the app -
The landing page of the app has a 'drop-down' menu where the user selects the type of movie he/she wants to view the details about. 

- #### [The Upcoming movies](https://developers.themoviedb.org/3/movies/get-upcoming) : this section lists down all the movies that are going to get released and shows the release date. 

- #### [The Popular movies](https://developers.themoviedb.org/3/movies/get-popular-movies) : this section lists down all the movies according to the popularity of the movies in the moviedb. 

- #### [The Top-rated movies](https://developers.themoviedb.org/3/movies/get-top-rated-movies) : this section lists down all the movies according to the vote count and vote average. 

> The list in every section is sorted according to the release dates, populairty and vote count respectively and is fetched from the API accordingly.

- #### [Movie Detail Screen](https://developers.themoviedb.org/3/movies/get-movie-details) : Then if the user clicks on the movie item he/she is redirected to the movie detail screen which shows all the details of the movie like : language, title, overview, origin_country, budget, revenue etc. Then there is a button at the end of the screen which links the user to the homepage of the movie.

## Built using -
- Android Studio 
- Kotlin 
- [Movie DB API](https://developers.themoviedb.org/3/getting-started/introduction) 
> BASE-URL = https://api.themoviedb.org/3/

> IMAGE BASE-URL = https://image.tmdb.org/

## Concepts used - 
- Material Design concepts 
- Contraint and Linear Layouts
- RecyclerView : to list the movies
- viewBinding
- MVVM architecture : using viewModels and LiveData 
- Retrofit Library : to fetch data from the movieDB api.
- Glide Library : to display images from the api.

## Demo 
<img src ="https://github.com/Dhruv-194/BasicMoviesApp/blob/master/basicmoviesapp-android.gif" width ="200" height="400"/>

## What next? 
- Updating the app by using fragments instead of activities.
- Updating the UI of the app. Using a tablayout instead of the 'drop-down' menu.
- Implementing a searching functionality by having a search bar to search among the list of the movies.
- Use dependency injection to make app more stable.


