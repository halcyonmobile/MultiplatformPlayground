# Todos / Pending Issues
* Solve multithreading -> [Check out the issue](https://github.com/Kotlin/kotlinx.coroutines/issues/462)
* Come up with a solution for exposing `Flow`s and `suspend fun`ctions
* Separate the **common module** into two parts
    1. commonClient - contains the common code for Frontend, like :requests, repositories, usecases, viewmodels
    2. common -> mainly contains the models and other dependencies that all the platforms need, backend included