# weather-service
Test task.
Сервис принимает http запрос с параметрами "srvice" и "city" и возвращает JSON с погодой от выбранного погодного сервиса.
Реализованы сервисы: openweathermap, weatherbit, worldweatheronline. Данные кэшируются в БД. 
Время кэширования, ключи для доступа к погодным сервисам, параметры подключения к БД задаются в файле application.properties.
