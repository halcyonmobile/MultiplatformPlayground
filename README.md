# Todos without any blocker
* Finish the client-side networking in ktor
   - [ ] File support in multiplatform should be added.   [Okio](https://github.com/square/okio) can be a solution
* Finish backend Api's
    - [ ] Upload files to some File sharing api
    - [ ] Handle files and `partMap`s
* [ ] Separate the **common module** into two parts
    1. **commonClient** - contains the common code for Frontend, like :requests, repositories, usecases, viewmodels
    2. **common** -> mainly contains the models and other dependencies that all the platforms need, backend included
* [ ] Refactor common repository logic and add use-cases according to  [Arch Apportfolio](https://gitlab.com/halcyonmobile/android-technical/architecture-appportfolio)
* [ ] Add DI to backend
* [ ] Add DI to mobile apps
* [ ] Move UI from  [Arch Apportfolio](https://gitlab.com/halcyonmobile/android-technical/architecture-appportfolio) to the android app
* [ ] Make UI for the **iOS** app
# Todos with pending issue
* Solve multithreading -> [Check out the issue](https://github.com/Kotlin/kotlinx.coroutines/issues/462)
* Come up with a solution for exposing `Flow`s and `suspend` functions

# Resources
- [Kotlin Multiplatform libraries](https://github.com/AAkira/Kotlin-Multiplatform-Libraries)
