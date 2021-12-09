# Movies List
## Description
Android application to list movies and TV shows.

- Popular and Top Rated Movies
- Popular and Top Rated TV Shows

## Tech
- Kotlin as Language
- API for lists: https://api.themoviedb.org/3/
- API for retrieve API_KEY: http://moviedbapikeyprovider.herokuapp.com
- [Volley](https://developer.android.com/training/volley) for HTTP Requests
- [Picasso](https://square.github.io/picasso/) and [RoundedImageView](https://github.com/vinc3m1/RoundedImageView) for image manage
- [GenericViewHolder](https://github.com/elianaferreira/genericviewholder) for RecyclerView Holder
- [unDraw](https://undraw.co/illustrations) for the cool images.

## Pending
### Transitions
Define XMLs for transitions (in and out) and set them in the Theme of the app.
Start every new activity defining the animation.

### Inject dependencies
[Dagger](https://developer.android.com/training/dependency-injection/dagger-android) could be used for that.

### Pattern
The project use the simple MVC pattern. Now the application is simple with few user interactions; but if escalate, MVP could be a better approach.

### Unit tests
[Espresso](https://developer.android.com/training/testing/espresso) could be used for test the application UI.


