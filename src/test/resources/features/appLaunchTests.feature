Feature: App first time launch
  I am able to launch the app first time

  @AppStability @Smoke @Regression
  Scenario: I am able to launch the app first time
    Given I install new app
    And I open the App first time
    When I add permissions
    And I close tutorial screen by clicking on it
    Then Dashboard page is opened