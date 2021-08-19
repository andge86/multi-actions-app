## Framework description:

- Java + Maven + Appium + Cucumber test automation framework
- POM 
- CI integration including GitHub Actions report publishing
- Cucumber simple report in /target folder
- Attaching screenshots to the report and the possibility to attach videos
- Saving screenshots and videos in /target folder with tests scenario names
- The tests could be run for Android 10 and 11 (tested)
- GitHub link: https://github.com/andge86/multi-actions-app
- Latest Cucumber report through GitHub Pages link: https://andge86.github.io/multi-actions-app


## How to run Locally (verified on macOS Big Sur 11.5.1):

- Setup properly java (use JDK 15.0.2, because JDK 16.0.2 is throwing errors on my machine), maven, Android Studio with all proper env variables
- Install Appium for desktop (possibly appuim service should also work) with proper env variables
- Using Android Studio AVD manager create Android Emulators for Android 11 -> Pixel 4 API 30 (I have used it for my tests)
- git clone https://github.com/andge86/multi-actions-app.git
- Run Appum Desktop and launch Android Emulator
- cd multi-actions-app
- mvn clean test
- Or use IntelliJ IDEA or other IDE, open as maven project and run through IDE.
- Enjoy!


Note: One test if failing intentionally to illustrate attaching screenshots to failed test cases in the report



## How to run in CI using GitHub Actions:

- Create a separate branch in multi-actions-app test project repo and create a pull request
- Or commit to master directly
- The build is triggered, enjoy!


Build with test cases would be triggered automatically and the new report would be published to GitHub Pages.

I am using continue-on-error: true in .yml file to publish the report even if some test failures occur.

To debug CI tests use Android Emulator for Android 10 -> Nexus 6 API 29 and run mvn clean test -DPLATFORM_VERSION=10



## Next Steps:

- CI with real project repo (tests triggered on creating a pull request to app master branch)
- Or cron run every day, week, etc.
- Local run improvements to run everything using one command
- Dockerization and running using simple docker-compose up command (I will try to implement it also)
- Possible Slack integration or sending reports through email
- Choosing some better and more readable extensions for report generation  
- Integration with Selenium Hub
- Taking application apk file from some remote directory (updated after new app build)
