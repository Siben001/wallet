# How to start?
### Docker
You can start docker in IDEA from docker-compose itself or from terminal:
```
docker-compose -f ./docker/docker-compose.yml up -d postgres
```
###Project
Load gradle project and then start application/bootRun gradle task
# About project
## Main idea
Currently, I'm working with **kotlin / vertx / micronaut / jooq / postgres**.
Spring is very popular among developers, therefore  I wanted to try **kotlin / spring / jooq / postgres** with **r2dbc**.
So the main idea was to make all these technologies work together, but I encountered some troubles with this stack.
Time was limited and if I had made workaround I wouldn't have had time to do the main logic of app.
That why I decided to get step back and make application on **JDBC** driver.
Still didn't have time for tests :c
#### Where is R2DBC?
You can find my code with R2DBC in branch feature/r2dbc. It works fine until you try to make get request, post - ok :)