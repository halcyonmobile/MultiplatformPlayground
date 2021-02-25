# Multiplatform Playground (AppPortfolio)

A **Kotlin Multiplatform** project with the purpose of experimenting with and providing a possible direction to newcomers having questions about *tech stack*, *project setup*, *architectural decisions* in a KMP environment.

#### Note:

Its primary purpose is to showcase sharing every layer except the UI layer in between multiple platforms, so shared **Presentation layer** using the MVVM pattern, shared **Domain** and **Data layers** (UseCase, Repositories, Entities, etc.).

 If sharing ViewModel logic is out of scope for you, then consider checking out the following repositories:

- https://github.com/joreilly/PeopleInSpace
- https://github.com/touchlab/KaMPKit
- https://github.com/joreilly/BikeShare

### Shared pieces

- Every layer except UI (so ViewModel, UseCase, Repository layers)
- Localization
- DI
- Shared DTOs (between Backend & Clients)

### Tech stack

- Declarative UI with `Jetpack Compose` and `SwiftUI`
- [Koin](https://github.com/InsertKoinIO/koin)
- [Ktor](https://ktor.io/) (Backend + Client logic)
- [Kotlinx-serialization](https://github.com/Kotlin/kotlinx.serialization)
- [SqlDelight](https://github.com/cashapp/sqldelight)
- [Moko-Resources](https://github.com/icerockdev/moko-resources) (Localization)

### Targets

- JVM (Backend)
- Android
- iOS
- macOS (Coming soon)
- desktop (planned)
- web (planned)

### Module Hierarchy
![module-hierarchy](img/modules.png)

### Further Development

- Shared testing (currently android only, [Mockk K/N support](https://github.com/mockk/mockk/issues/58))
- Supporting more targets

##### The icons are from: https://icons8.com
##
### License

    Copyright 2021 HalcyonMobile https://www.halcyonmobile.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
