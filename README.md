# Known Kotlin <-> Obj-c <-> Swift interopability issues
* Obj-c Arrays are not typed => `Observable<List<T>>` will lose it's `T` type, when compiled to **Swift**
* Missing `struct` value types in *Kotlin* and *Obj-c* => No `copy-on_write` capability
# Project set-up
##### BE
1. Create a new Run Configuration using “Application” as a template
2. For the main class use the following engine: `io.ktor.server.netty.EngineMain`
3. Specify **backend** as a module
4. Give a name and save the to the configuration

See [Running the application from inside the IDE](https://ktor.io/servers/engine.html) for more information
##### iOS
- Run ./gradlew podspec
- Add the generated dependency snippet in the Podfile
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