Feature: App first time launch
  I am able to launch the app first time

  Scenario: I am able to launch the app first time
    Given I open the App first time
    When I add permissions
    And I close introduction screen
    Then Dashboard page is opened

  Scenario: I can see all required Actions in Actions on click pop up
    Given I open the App
    And I close introduction screen
    When I click on Action on Click tab
    Then All Actions should be shown

  Scenario: I can set up vibration strength to 100
    Given I open the App
    And I close introduction screen
    When I click on Vibration strength tab
    And I put vibration strength to 100
    Then Vibration strength should be 100 in the Dashboard

  Scenario Outline: I can set up button color
    Given I open the App
    And I close introduction screen
    When I click on Button color tab
    And I click on "<color>" circle
    Then Color button should be "<color>" in the Dashboard
    Examples:
      | color  |
      | violet |
 #     | red |

  Scenario: I can check Notification checkbox
    Given I open the App
    And I close introduction screen
    When I click on Show Notification checkbox
    Then Show Notification checkbox should be unchecked

