# Discover Movies
Sample Android Application written in Kotlin to show a list of movies by using the [TMDb API](https://www.themoviedb.org/documentation/api)
The MVVM pattern is used to encapsulate App specific domain logic into ViewModels that can be unit tested in isolation.
The use of [Android architecture ViewModel](The use https://developer.android.com/topic/libraries/architecture/viewmodel) allows ui state preserving on orientation change.
The internals of the ViewModel are based on explicit ui states that are inspired by the talk [Managing State with RxJava by Jake Wharton](https://www.youtube.com/watch?v=0IKHxjkgop4).
The ViewModel is backed by a repository which hides the underlying API service and allows later extension by a database backed cache.
The communication between Activity and (Dialog-)Fragment is done using a shared ViewModel described in the Android developers documentation section [Share data between fragments](https://developer.android.com/topic/libraries/architecture/viewmodel#sharing_data_between_fragments)

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
* Create intermediate domain model of MovieListResult to be passed to MovieItem that handles pre formatting
* Add option for changing until date filter failure state
* Add an empty result state