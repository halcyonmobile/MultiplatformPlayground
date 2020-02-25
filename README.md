# Todos without any blocker
* Move UI from  [Arch Apportfolio](https://gitlab.com/halcyonmobile/android-technical/architecture-appportfolio) to the android app
* Make UI for the **iOS** app
* Fix Backend serialization issue with /POST category request
* Come up with a solution for resource ids in `ViewModel`s (either avoid or ResourceWrapper?)
# Todos with pending issue
* Add multithreading -> [Check out the issue](https://github.com/Kotlin/kotlinx.coroutines/issues/462) [and this](https://github.com/Kotlin/kotlinx.coroutines/pull/1648)
* Come up with a solution for exposing `Flow`s and `suspend` functions or use a 3rd party DB library for the LocalSource

# Known Kotlin <-> Obj-c <-> Swift interopability issues
* Obj-c Arrays are not typed => `Observable<List<T>>` will lose it's `T` type, when compiled to **Swift**
* Missing `struct` value types in *Kotlin* and *Obj-c* => No `copy-on_write` capability
# Resources
- [Kotlin Multiplatform libraries](https://github.com/AAkira/Kotlin-Multiplatform-Libraries)
