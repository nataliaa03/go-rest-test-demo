# Requirements
To run the tests there must be installed and running docker on your machine.

# Running tests
To run tests you need to:
- register, login and get your API token from: https://gorest.co.in/
- set your system variable (or configuration environment variable in IDE): `GOREST_TOKEN=your_api_token`

- in your terminal use commands:
`docker compose up -d`
`docker-compose run --rm rest-assured bash`
`cd /app`
`ls`
`mvn test`

# Running tests - to see events in kafka:
http://localhost:9000
