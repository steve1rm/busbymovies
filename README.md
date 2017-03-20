# busbymovies
Shows all the movies that are considered the most popular and top rated

## User Stories:

* [x] Shows a list of popular movies
* [x] Shows a list of top rated movies
* [x] Show the detail of a selected popular or top rated movie
* [x] Caches movies for offline viewing for a period of 7 days
* [x] Only requests fresh movies after a 120 second period has expired

## Libraries used
* [x] Dagger2 - Dependency Injection
* [x] Butterknife - View Injection
* [x] Retrofit2 - parsing the JSON into java objects
* [x] RxJava - reactive programming for downloading the content from the service 
* [x] OkHttp3 - Logging interceptor, caching 120 seconds to avoid requesting from the server, offline cache enabled for 7 days and will use this if not connected to the internet
* [x] Palette - Creating dynamic vibrant colors based on the image poster
* [x] mockito - mocking classes for running tests using junit4 running on JVM
* [x] espresso - running instrumentation UI tests
* [x] blurkit - creating a blur effect
* [x] chrome custom tabs - opening the movie home page

## Architecture 
* [x] MVP - Model View Presenter

## API KEY INPUT
* Enter your API Key in utils/Constants.java file

## Video Walkthrough
Here's a walkthrough of implemented user stories:
<img src='https://github.com/steve1rm/busbymovies/blob/master/busbymoviepart1.gif' title='Video Walkthrough' alt='Video Walkthrough' />

