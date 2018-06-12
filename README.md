# Discover Movies
Sample Android Application written in Kotlin to show a list of movies by using the [TMDb API](https://www.themoviedb.org/documentation/api)

## Build
The App uses the TMDb API which requires a registered API key for most of the API calls. To build the app the API key needs to be specified as a gradle property with the key "TheMovieDatabaseApiKey" that is accessible by the gradle build system. One possibility is to add it to the gradle.properties file in the user's .gradle folder  [USER_HOME]/.gradle/gradle.properties in the following way
````
TheMovieDatabaseApiKey="{API_KEY}"
```` 

## Dependencies
* [Retrofit 2](http://square.github.io/retrofit) HTTP client to query the API and map requests in a declarative way
* [Moshi](https://github.com/square/moshi) JSON library to parse custom Date attributes
* [RxJava 2](https://github.com/ReactiveX/RxJava) reactive extensions for the JVM to handle asynchronous events in a reactive/stream based way  
* [Koin](https://insert-koin.io/) lightweight Kotlin dependency injection framework that allows simple ViewHolder injection and without code generation
* [Groupie](https://github.com/lisawray/groupie) RecyclerView layout library that implements delegation pattern to easily add new item types   

##Improvements (potential next steps)
* Allow API key to be specified as environment variable
* Create intermediate model of MovieListResult to be passed to MovieItem that handles pre formatting