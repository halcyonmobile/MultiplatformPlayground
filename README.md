# Known Kotlin <-> Obj-c <-> Swift interopability issues
* Obj-c Arrays are not typed => `Observable<List<T>>` will lose it's `T` type, when compiled to **Swift**
* Missing `struct` value types in *Kotlin* and *Obj-c* => No `copy-on_write` capability
# Resources
- [Kotlin Multiplatform libraries](https://github.com/AAkira/Kotlin-Multiplatform-Libraries)
