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
Install all necessary tools using instructions on [Wiki](https://github.com/KaidanAlenko/frms-backend/wiki/Upute-za-instalaciju-potrebnih-alata) pages of backend project.

### Clone repository
Clone this repository using HTTPS or SSH and import it to IDEA as Maven project.

### Create schema for production and test databases
Position yourself to root folder of **frms-core** project and run script *init-database.sh*.

### Run migrations using Flyway

Position terminal to root of **frms-core** project and run *recreate-database.sh* script.

### Run tests
After you complete all previous steps, before project is ready to develop, you must run all tests and verify that everything is in order. It is done by right clicking on project *frms-backend* in Project view in IntelliJ IDEA. Right click -> Run all tests.

### Runing project 
Project is run with command *mvn jetty:run* in root of the folder

#### You are ready to go
For all additional information, contact me on `ahrga93@gmail.com` or `alen.hrga@eestec-zg.hr`

Good luck! :)


## Inportant information

When you want to change domain to local, you must change  src/main/resources/application.properties property to `frontend.web.url=http://localhost:4200` port 4200 is because ng is serving on default port 4200
