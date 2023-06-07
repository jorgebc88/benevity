
This project i decided to do the Backend in Java, using Java17 and Spring boot 3.


This application is composed by 2 repositories, the server and the client:

- In the server repository you can find a spring boot server that connect to a local postgreSQL database and expose the api.
In order to make it easier the starting, a docker-compose was created, with the benevity-service app and a PostgreSQL image for the database. 


To start the server the first thing is to generate the jar 
```$xslt
    cd benevityServer
    .\gradlew.bat clean build
```
After you create the build, you have to execute the docker-compose
```
    cd benevityServer
    docker compose up
```
Now that you have the Server UP and Running. The server has their swagger page http://localhost:8080/swagger-ui/index.html
Now you can start the Client. 


- In the client repository, you have the Front-end code of the blog that uses React and Redux.
To start the Front-end
```
    cd client
    npm i
    npm start
```
The url will be localhost:3000

The following features were created:

- "User creation": Going to the tab "Create User", you can create an user to log in to the app.
- "Login": After creating the user, you can go to "Login" and log in to unblock the other features.
- "User Management": From the Users tabs, you can see the users that the system has. You can delete an user just it's not the logged-in one, and it doesn't have any posts created.
- "Posts": Here you will be able to create a new post and see the posts list.
        - "Post creation": Here you can add the author, title, and content of the post (you can write as much content as you want). After you submit, you will see it automatically in the post list.
        - "Post listing": Here you will see all the posts ordered by date. You have the title with a hyperlink to the post page, the date, content, author, and if you are the owner of the post, you can delete the post or Upload/Remove image. If the post is not yours. Those options will not appear.
        - "Post": If you enter in some post, you will see the post detailed on its own page.
- "Logout": If you do an hover the logout you will see the user logged. And clicking you logout.