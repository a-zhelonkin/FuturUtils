# Futur Utils
Общие утилиты для написания JavaFX приложений

|Модуль                     |Описание
|:--------------------------|:-
|<a href="https://github.com/AlexM4Q/FuturUtils/tree/master/src/main/java/com/futur/ui">ui</a>          |Вспомогательные классы контролов интерфейса
|<a href="https://github.com/AlexM4Q/FuturUtils/tree/master/src/main/java/com/futur/common">common</a>  |Вспомогательные классы математики, работы со строками, файлами, ресурсами и т.д.


## Подключение
Прописать в build.gradle следующее
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile('com.github.alexm4q:futurutils:master-SNAPSHOT') { changing = true }
}
```
 Регулярное обновление
```
configurations.all {
    resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
}
 ```
