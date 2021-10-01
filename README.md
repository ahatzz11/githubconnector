# githubconnector

[![forthebadge](https://forthebadge.com/images/badges/powered-by-electricity.svg)](https://forthebadge.com)

Welcome to the githubconnector application, it is responsible for grabbing information from github and mapping it to our customers expected object

### Build & Test

I am using gradle as my build tool.
Included in this repo is the gradle wrapper, so all the following commands are able to run without gradle as a local dependency.

To build the program:
```bash
./gradlew clean build
```

The `build` command also runs:
- `./gradlew detekt` to enforce code formatting, syntax, and many other rules. [Detekt Repo](https://github.com/detekt/detekt)
- `./gradlew test` to run all the tests

### Run & Hit

To run the application:

```bash
./gradlew clean bootRun
```

Example request:

```curl
curl "http://localhost:8080/github_connector/v1/users/octocat"
```

### Architecture & Decisions

### Technology
- Kotlin / Spring Boot - I am quite comfortable in both of these
- Retrofit - Used for easy API calls
- OkHttp - The underlying library for Retrofit, I pull in some extra OkHttp stuff for logging
- Kotest

### Notes

- I would talk with the client about receiving the `created_at` field in the standard ISO-8601 datetime format. 
- Additionally, the requested format by the client doesn't have any time zone information. Since this is a bad pattern, I will be making the assumption they want UTC time. Unfortunately, their requested pattern has no way to tell them this.

### Future Improvements
- Implement a proper auth solution to avoid rate limiting
- Add externalized configuration for things like github api url, token information, api logging, etc
