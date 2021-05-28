# kotlin_synthetic_undepreciated

https://android-developers.googleblog.com/2020/11/the-future-of-kotlin-android-extensions.html
"Kotlin android extensions" which generates Kotlin snythteic code is depreceated. 
Depreceated means, it will show error when you try to use "Kotlin android extensions" plugin in kotlin version 1.4.20 and above. 

Without "kotlin android extensions", kotlin synthetics references will throw compilation error.

The best solution is **Migrate to ViewBinding**.

But if the project has huge number of synthetic references and you want to postpone migration and want to use latest kotlin version, use this gradle task.
It will generate synthetic like classes.

Note: 
* Views are non nullable. That means, it will crash when there is no view with matching id (Original kotlin synthetic won't crash if you use it in null safe way)
* It requires ViewBinding to be enabled. Since the script depends on the ViewBinding generated classes.
* Generated files are placed inside src/main folder. You need to push these files to repository.
* You need to run the task manually
