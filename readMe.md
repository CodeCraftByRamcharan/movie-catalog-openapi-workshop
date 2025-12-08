#H2 Console
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:moviesdb
User: sa (no password)

#Localhost Urls
1. Create
curl -X POST http://localhost:8080/api/movies \
-H "Content-Type: application/json" \
-d '{"title":"Fight Club","genre":"Drama","rating":8.8,"releaseYear":1999,"description":"An insomniac office worker..."}'

2. GetAll
   curl http://localhost:8080/api/movies

3. Get By Id:
   curl http://localhost:8080/api/movies/1

4. Filter By Genre
   curl "http://localhost:8080/api/movies?genre=Sci-Fi"

5. Search by partial title
   curl "http://localhost:8080/api/movies?search=dark"

6. Sort by rating
   curl "http://localhost:8080/api/movies?sortByRating=true"

7. Update
   curl -X PUT http://localhost:8080/api/movies/1 \
   -H "Content-Type: application/json" \
   -d '{"title":"The Shawshank Redemption (Edited)","genre":"Drama","rating":9.4,"releaseYear":1994,"description":"Updated"}'

8. Delete
   curl -X DELETE http://localhost:8080/api/movies/1


#Commits
1. add manually written OpenAPI spec and Swagger UI via CDN
- Created openapi.yml with manual OpenAPI 3.0 specification
- Added docs.html to load Swagger UI using CDN
- Configured project to serve static API documentation(http://localhost:8080/docs.html)

