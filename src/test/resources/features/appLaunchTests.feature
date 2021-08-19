Feature: App first time launch
  I am able to launch the app first time

  Scenario: I am able to launch the app first time
    Given I open the App first time
    When I add permissions
    And I close tutorial screen and rate pop-up if present
    Then Dashboard page is opened