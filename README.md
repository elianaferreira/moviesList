# Movies List
## Description
Android application to list movies and TV shows.

- Popular and Top Rated Movies
- Popular and Top Rated TV Shows

## Tech
- Kotlin as Language
- The pattern used is MVP.
- API for lists: https://api.themoviedb.org/3/
- API for retrieve API_KEY: http://moviedbapikeyprovider.herokuapp.com
- [Volley](https://developer.android.com/training/volley) for HTTP Requests
- [Picasso](https://square.github.io/picasso/) and [RoundedImageView](https://github.com/vinc3m1/RoundedImageView) for image manage
- [GenericViewHolder](https://github.com/elianaferreira/genericviewholder) for RecyclerView Holder
- [unDraw](https://undraw.co/illustrations) for the cool images.
- [Espresso](https://developer.android.com/training/testing/espresso) for few basic UI tests.

## Pending
### Transitions
Define XMLs for transitions (in and out) and set them in the Theme of the app.
Start every new activity defining the animation.

### Inject dependencies
[Dagger](https://developer.android.com/training/dependency-injection/dagger-android) could be used for that.
