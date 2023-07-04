# OompaLoompasApp

## Libraries:

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419). Dependency injection. This reduces code for instantiating classes with all its dependencies as well as makes easier to mock in testing.
* [Retrofit + Gson](https://square.github.io/retrofit/). HTTP client with serialization/deserialization to easily communicate with API.
* [Mockito](https://site.mockito.org/). Easily create test doubles to limit scope of tests. This allows to set any method with returning data 
* [Coil](https://github.com/coil-kt/coil#jetpack-compose). Loading and rendering images from internet. Fits for cases where only the network URL of the image is available. Includes optimizations like disk and memory caching.
* [Coroutines Test Library](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/): This allows the usage of coroutines in tests.

Remaining libraries used (Junit, compose, etc) are included by default
