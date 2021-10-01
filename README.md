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

Example Response:

```json
{
  "user_name": "octocat",
  "display_name": "The Octocat",
  "avatar": "https://avatars.githubusercontent.com/u/583231?v=4",
  "geo_location": "San Francisco",
  "email": null,
  "url": "https://github.com/octocat",
  "created_at": "2011-01-25 18:44:36",
  "repos": [
    {
      "name": "boysenberry-repo-1",
      "url": "https://github.com/octocat/boysenberry-repo-1"
    }
    ...
  ]
}
```

### Architecture & Decisions

- Application architecture is pretty simple with a controller endpoint, and a couple of services to handle the mapping logic / API call
- Retrofit is used to make the API call to github
- Jackson is responsible for the mapping of json<>models to avoid a bunch of by-hand json parsing
- The `ObjectMapper` we use turns off `FAIL_ON_UNKNOWN_PROPERTIES`. The github API call fails without it since we aren't using all the fields. I personally think this defaulting to `true` is not a great decision.
- The `ObjectMapper` also uses the `kotlinModule()` to play nicely with kotlin

#### Technology
- Kotlin / Spring Boot - I am quite comfortable in both, and are generally scalable technologies. They are both well-supported / maintained, and there are many docs on how to do things
- Retrofit - Used for easy API calls, because `RestTemplate` is not fun to use.
- OkHttp - The underlying library for Retrofit, I pull in some extra OkHttp stuff for logging
- Kotest / mockk / kluent - Test frameworks and verification matching library
- Detekt - Static Analysis tool for good practices and consistent formatting

### Notes

- I would talk with the client about receiving the `created_at` field in the standard ISO-8601 datetime format. 
- Additionally, the requested datetime format by the client doesn't have any time zone information. Since this is a bad pattern, I will be making the assumption they want UTC time. Unfortunately, their requested pattern has no way to tell them this.

### Future Improvements
- Add a proper auth solution to avoid rate limiting
- Add externalized configuration for things like github api url, token information, api logging, etc
- Add proper functional tests where the Application Context starts, endpoints are hit, and results are expected. Maybe add a mock api call to github
- Add a bunch of stuff to the github API call to make it better:
    - More robust error handling
    - Retry logic
    - Self rate limiting?
- Containerize the application for easy deployments
