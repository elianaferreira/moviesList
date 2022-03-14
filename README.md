# Movies List
## Description
Android application to list movies and TV shows.

- Popular and Top Rated Movies.
- Popular and Top Rated TV Shows.

## Tech
- Kotlin as Language.
- The pattern used is MVP.
- API for lists: https://api.themoviedb.org/3/
- [Volley](https://developer.android.com/training/volley) for HTTP Requests.
- [Picasso](https://square.github.io/picasso/) and [RoundedImageView](https://github.com/vinc3m1/RoundedImageView) for image managment.
- [GenericViewHolder](https://github.com/elianaferreira/genericviewholder) for RecyclerView Holder.
- [unDraw](https://undraw.co/illustrations) for cool images.
- [Espresso](https://developer.android.com/training/testing/espresso) for few basic UI Tests.
- [Dagger](https://developer.android.com/training/dependency-injection/dagger-android) for Dependency Injection.
- [Mockito](https://github.com/mockito/mockito-kotlin) for few basic Unit Tests.
 

## Could improve
### Transitions
When the transition is made from the home to any detail view (A -> B -> C), in the goBack action from B to A, the transition is again the default.
