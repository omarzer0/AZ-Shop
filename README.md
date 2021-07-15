

# AZ-Shop

<img src="https://github.com/omarzer0/AZ-Shop/blob/main/assets/AZ%20shpe%20video.gif" alt="app preview"  width="250" />

## What is the idea?
An e-commerce app with modern UI and Tech but the main focus is to use best practices and focus on implementing it in a real world app (not in just a dummy app)
and solve conflicts between what should be done and what can be done. For example:

- Fragments should only be responsible of updating the UI and should not decide whether show toast, navigate to another screen or even handle clicks.

- The ViewModel should have some sort of mechanism to tell the fragment to do some action but the fragment should not communicate back (one way communication)
For that I used flow with channels.

- The app should have only one activity and any number of fragments that means you can have action bar for ex shown or hidden for the whole app but modern UI
demands having a screen with an action bar and another without it (full screen) and this becomes a little tricky and causes a lot of pain. The App tries to make the
of best of the both worlds so action bar and status bar should be handled probably.

The app may look a bit overwhelming but using a proper architecture is worth it.

## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) together with [Flow](https://developer.android.com/kotlin/flow) for asynchronous streams 
and one side viewModel to fragment communication.
- Dagger Hilt for dependency injection.
- [Glide](https://github.com/bumptech/glide) for loading images.
- JetPack
  - Lifecycle - Dispose of observing data when the lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - ViewBinding - Interact with XML views in safeway and avoid findViewById() 
  - Room - Persistence library that provides an abstraction layer over SQLite database
  - Navigation Component - Make it easy to navigate between different screens and pass data in type-safe way
- Architecture
  - MVVM Architecture (View - ViewModel - Model)
  - Repository pattern
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like cardView
