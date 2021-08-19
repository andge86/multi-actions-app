Feature: Base interactions with app menu
  I am able to interact with the app menu

  Background: I am on the Dashboard page
    Given I open the App
    And I close tutorial screen and rate pop-up if present

  @Regression @Actions
  Scenario: I can see all required Actions in Actions on click pop up
    When I click on Action on Click tab
    Then All Actions should be shown

  @Regression @Behavior
  Scenario: I can set up vibration strength to 100
    When I click on Vibration strength tab
    And I put vibration strength to 100
    Then Vibration strength should be 100 in the Dashboard

  @Regression @Appearance
  Scenario Outline: I can set up button color
    When I click on Button color tab
    And I click on "<color>" circle
    Then Color button should be "<color>" in the Dashboard
    Examples:
      | color  |
      | violet |
 #     | red |

  @Regression @Notification
  Scenario: I can check Notification checkbox
    When I click on Show Notification checkbox
    Then Show Notification checkbox should be unchecked

  @ForTesting
  Scenario: Intentionally failed test scenario to see attached screenshot
    When I open the App
    Then Color button should be "white" in the Dashboard

