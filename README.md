Дипломная работа. Мобильное приложение для ОС Android для местного заповедника Красноярские Столбы.
Поскольку в заповеднике очень плохая связь, была необходимость сохранить весь функционал оффлайновым с минимальным выходом в сеть, например, для обновления справочных данных, что и требовалось по ТЗ.
Серверный код на PHP для этого проекта, к сожалению, утерян.
Изначально проект должен был быть построен на API Mapbox, однако позже было принято решение перейти на OSMDroid, в том числе потому, что были опробованы API от GoogleMaps и Яндекса, но они не подошли. OpenStreetMaps оказался более подходящим для моих нужд.
Также, на момент начала разработки Google уже объявила, что основным языком разработки теперь будет Kotlin, однако еще не переписала документацию под него. Выход AndroidX попал примерно на этот же период, поэтому в проекте он используется не везде.
Тем не менее, во время работы над проектом и некоторое время после я изучал Kotlin.
