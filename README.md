# Known Kotlin <-> Obj-c <-> Swift interopability issues
* Obj-c Arrays are not typed => `Observable<List<T>>` will lose it's `T` type, when compiled to **Swift**
* Missing `struct` value types in *Kotlin* and *Obj-c* => No `copy-on_write` capability
# Project set-up
##### BE
- Run `./gradlew backend:run` from terminal (Additionally you can set up a **Gradle** configuration for this command)
##### iOS
1) Import the `iosApp` from Xcode
2) Run `./gradlew common:podspec` to generate/update the **common.framework**
3) Run the app from Xcode
# Resources
##### Multiplatform
- [Kotlin Multiplatform libraries](https://github.com/AAkira/Kotlin-Multiplatform-Libraries)
- [Jetbrains KMP project collection](https://www.jetbrains.com/lp/mobilecrossplatform/?_ga=2.202856727.765867490.1593685697-1840297874.1578984105)
- [KaMPKit from Touchlab](https://github.com/touchlab/KaMPKit)
- [Urbantz KMP networking example](https://gitlab.com/halcyonmobile/urbantz-kmp-networking)
- [Kotlinlang official multiplatform documentation](https://kotlinlang.org/docs/reference/multiplatform.html?_ga=2.5643317.765867490.1593685697-1840297874.1578984105)
##### KTor
- [Official KTor documentation](https://ktor.io)
##### iOS