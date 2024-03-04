# Requirement
- Data bank in memory is H2.
- Build process by Maven.
- Persistence with JPA.
- SpringBoot Framework.
- Java 17
- Delivered to a public repository (github) with the source code and script
DB creation.
- Readme explaining how to try it.
- Solution diagram.

# Commands

## Analyze used ports 🔎

```shell
sudo lsof -i -P -n | grep 8085
```

```shell
kill -9 $(lsof -ti:8085)
```

## Stop & Remove all containers ❌

```shell
docker stop $(docker ps -a -q)
```

```shell
docker system prune --all
```

## Build & run ▶️️ 🆙

```shell
docker-compose -f global-logic.yml up -d --force-recreate --build
```

## Check & Logs ✅ 📃

```shell
docker ps -a
```

```shell
docker logs [NAME/CONTAINER ID]
```
# Steps to Run

Before running the commands, ensure your system meets the following requirements:

- **Docker**
- **Maven and Java JDK** installed. Verify by running the command:
    ```shell
    mvn -v
    ```
  Ensure Java version is 17 and Apache Maven version is 3.6.3.

## Running the Application:

Choose one of the following methods:

1. **Using Docker:**
    ```shell
    docker-compose -f global-logic.yml up -d --force-recreate --build
    ```

2. **Using Java:**
    ```shell
    # global_logic_ms/target$
    java -jar user-0.0.1-SNAPSHOT.jar
    ```

## Testing the API

To test the API, utilize the provided **GlobalLogic.postman_collection.json**.

### Postman Setup:

1. Import the collection into Postman.
2. The collection includes two requests:
   - **Register:** To create a new user.
   - **Validate Token:** To validate the token obtained in the first request.

3. In the first request, you can verify errors such as repeated user mail or invalid user mail/password.

### cURL Commands:

If you prefer working with cURL in your console, we provide the following commands:

#### Register a new user:

```bash
curl --location --request POST 'localhost:8085/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}
'
```
### Response
```bash
{
	"id": 3,
	"name": "Juan Rodriguez",
	"email": "juan@rodriguez.org",
	"phones": [{
		"number": "1234567",
		"cityCode": "1",
		"countryCode": "57"
	}],
	"created": "2024-03-04T15:50:12.517947197-03:00",
	"modified": "2024-03-04T15:50:12.517947197-03:00",
	"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE3MDk1NzgyMTIsImV4cCI6MTcwOTU3ODUxMn0.jugAuoXIBquBe5t2iHWLnupFcJxMsfWd4d5ctx12hs0",
	"last_login": "2024-03-04T15:50:12.517947197-03:00",
	"isactive": true
}
```
--------------------------------------------------
### Request
```bash
# Validate token (replace 'your-token' with the actual token obtained from the registration request)
curl --location --request GET 'localhost:8085/api/v1/user/hello' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJpYXQiOjE3MDk1NzgyMTIsImV4cCI6MTcwOTU3ODUxMn0.jugAuoXIBquBe5t2iHWLnupFcJxMsfWd4d5ctx12hs0'
```

### Response
```bash
{
    "status": "AUTHENTICATED"
}
```

