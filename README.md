* About me *
Author: Rosana Fagundes
IDE: STS - Version: 3.8.4.RELEASE
Version control: GIT - GitHub

* Main challenge *
I was not sure if I was supposed to use relationships or not so I used only one time as per the test note "each newsletter relates to a subscriber".
CategoryPaths calculation was the main challenge to check level per level, in recursive mode, to find all possible paths for the books founds.

* Next Steps *
I could create one more layer (Service layer) in order to abstract code from rest controllers. For the sake of time I decided to do the most straighfoward.

* Feedback *
Interesting exercise, with different levels of challenges and very complete in terms of evaluation. Many concepts tested and relevant subject.

* Steps to run the application test: *
After cloning the source code, please follow the steps in root directory:

- build package:
./mvnw package

- run the application:
java -jar target/springer-0.0.1-SNAPSHOT.jar

- POST a subscriber using cUrl (book and category imported already as per the example of the test):
curl --data "email=test@test.com&categoryCodes=science" http://localhost:8080/subscribers
curl --data "email=rosana@test.com&categoryCodes=engineering" http://localhost:8080/subscribers

- Check newsletters in browser:
http://localhost:8080/newsletters
