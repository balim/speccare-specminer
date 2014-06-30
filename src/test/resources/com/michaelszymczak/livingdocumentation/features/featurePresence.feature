Feature:
  As a stakeholder
  I want to have my features in the documentation checked against their presence in the developed application
  So that I know if the documentation does not lie by describing something that does not exist

  Scenario: Scenario described in documentation exists in the actual feature files
    Given documentation that reads:
    """
      Scenario: Adding two numbers
        Given I have 2 and 3
        When  I add these numbers
        Then the result should be 5
    """
#    And feature file "Adding.feature" in the developed application:
#    """
#    Feature: Calculator
#
#      Scenario: Adding two numbers
#        Given I have 2 and 3
#        When  I add these numbers
#        Then I should have 5
#    """
#    Then "Adding two numbers" scenario should be marked as present
#
#
#
#  Scenario: Scenario described in documentation does not exist in the actual feature files
#    Given scenario "Subtracting two numbers" in the documentation:
#    """
#    Scenario: Subtracting two numbers
#      Given I have 2 and 3
#      When  I subtract these numbers
#      Then I should have -1
#    """
#    And feature file "Adding.feature" in the developed application:
#    """
#    Feature: Calculator
#
#      Scenario: Adding two numbers
#        Given I have 2 and 3
#        When  I add these numbers
#        Then I should have 5
#    """
#    Then "Subtracting two numbers" scenario should be marked as absent
