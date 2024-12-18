﻿# task-management-system v.1 - система управления задачами


## Возможности:
- Система обеспечивает создание, редактирование, удаление и просмотр задач.
- Система поддерживает аутентификацию и авторизацию пользователей
- Доступ к API аутентифицирован с помощью JWT токена.
- Пользователи могут управлять своими задачами: создавать новые, редактировать существующие, просматривать и удалять, менять статус и назначать исполнителей задачи
- Пользователи могут просматривать задачи других пользователей, а исполнители задачи могут менять статус своих задач *
- API позволяет получать задачи конкретного автора или исполнителя, а также все комментарии к ним.
- Организован поиск задач по различным критериям (по части названия или описания задачи, по статусу, приоритету и т.д.)
- Обеспечена фильтрация и пагинация вывода.
- Сервис обрабатывает ошибки, а также валидирует входящие данные. 
---
## Технологии:
### Проект выполнен с использованием фреймфорка Spring Boot
- Защита от несанкционированного доступа выполнена на Spring Security
- В процессе мапинга сущностей использован механизим Spring
- Обработка ошибок производится на уровне контроллеров
- Используются профили для тестирования, разработки и продакшена 
- Для хранения информации использована база данных Postgresql
- Для кеширования Refresh token использована база данных Redis 
- Рабочая среда подготавливается с помощью Docker
  - контейнер для базы данных postgresql;
  - контейнер для pgAdmin;
  - контейнер для самой программы
  - контейнер для базы данных redis


---
#### Запуск приложения и его окружения (локально)
- программа контейнеризиации Docker должна быть запущена
- в терминале выпонить команду: docker network create tsm-network (созданиe сети для связи между контейнерами)
- в терминале из корневой директории проекта выпонить команду: docker compose up (запуск програмы и окружения - docker-compose.yml)

---
#### C помощью Github Actions приложение развернуто на тестовом сервере

API доступно удаленно по адресу - http://31.128.41.84:8181

на сервере программа обновляется при push в ветку main репозитория на Github
  

---

#### Выполнены модульные и интеграционные тесты

---







