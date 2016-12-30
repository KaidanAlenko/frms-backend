# EESTEC LC Zagreb
## Fund raising management system (FRMS)

Main function of FRMS is to facilitate the management of events organized by EESTEC LC Zagreb. FRMS web application backend provides functionality of storing information about every year's events, contests and workshops who are in need of donations from sponsors. Sponsors are companies of some profession related to organizing event.

Application is used to maintain information about FR team members which use this software to track their tasks of contacting companies about some workshop.

Glossary:

* TASK - main term in this software. It is task given to some user to contact certain company related to certain event.
* USER - FR team member which has system role to track progress of given tasks
* EVENT - contest or workshop who is in need of material or financial donations from sponsor
* COMPANY - organization which have interest in making donation to event.

## Environment setup (Linux)

### Prerequisites
Install all necessary tools using instructions on [Wiki]([Wiki](https://github.com/KaidanAlenko/frms-backend/wiki/Upute-za-instalaciju-potrebnih-alata) pages of backend project.

### Clone repository
Clone this repository using HTTPS or SSH and import it to IDEA as Maven project.

### Create schema for production and test databases
Run this script using `psql` command line tool or `pgAdmin` application as database admin user:

    CREATE DATABASE frmsbackend;
    CREATE USER frmsbackend WITH PASSWORD 'frmsbackend';
    GRANT ALL PRIVILEGES ON DATABASE frmsbackend to frmsbackend;

    CREATE DATABASE frmsbackendutest;
    CREATE USER frmsbackendutest WITH PASSWORD 'frmsbackendutest';
    GRANT ALL PRIVILEGES ON DATABASE frmsbackendutest to frmsbackendutest;

### Run migrations using Flyway

Position terminal to your flyway tool which and configure it to read migrations from *frms-backend* Maven project which you previously cloned from this GitHub repository. In your flyway folders named **frmsbackend** and **frmsbackendutest**, edit `conf/flyway.conf` file to contain these lines respectively:

File: `frmsbackend/conf/flyway.conf`

    flyway.url=jdbc:postgresql://localhost:5432/frmsbackend
    flyway.user=frmsbackend
    flyway.password=frmsbackend
    flyway.locations=filesystem:<project_home_dir>/src/main/resources/migrations

File: `frmsbackendutest/conf/flyway.conf`

    flyway.url=jdbc:postgresql://localhost:5432/frmsbackendutest
    flyway.user=frmsbackendutest
    flyway.password=frmsbackendutest
    flyway.locations=filesystem:<project_home_dir>/src/main/resources/migrations

Where **project_home_dir** can be something like: `/home/alen/EESTEC/frms-backend`

After that, you run migrations in both folders with commands:
* `./flyway clean` - to clean database
* `./flyway migrate` - to run all unperformed migrations

### Run tests
After you complete all previous steps, before project is ready to develop, you must run all tests and verify that everything is in order. It is done by right clicking on project *frms-backend* in Project view in IntelliJ IDEA. Right click -> Run all tests.

#### You are ready to go
For all additional information, contact me on `ahrga93@gmail.com` or `alen.hrga@eestec-zg.hr`

Good luck! :)
