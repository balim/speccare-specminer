Feature:
  As a stakeholder
  I want to have my features in the documentation checked against their presence in the developed application
  So that I know if the documentation does not lie by describing something that does not exist

  Background:
    Given feature file "Adding.feature" in the developed application:
    """
    Feature: Adding calculator

      Scenario: Adding two numbers
        Given I have 2 and 3
        When  I add these numbers
        Then the result should be 5

      Scenario: Adding three numbers
        Given I have 2, 3 and 4
        When  I add these numbers
        Then the result should be 9
    """
    And feature file "Subtracting.feature" in the developed application:
    """
    Feature: Subtracting Calculator

      Scenario: Subtracting two numbers
        Given I have 2 and 3
        When  I subtract these numbers
        Then the result should be -1
    """

  Scenario: Scenario described in documentation exists in the actual feature files
    When I get "/scenarios/Adding two numbers"
    Then the response should be OK
    And the response content should be JSON:
    """
    {
      "result":"found",
      "name":"Adding two numbers",
      "path":"ABSOLUTE_PATH_TO_FEATURES_DIR/Adding.feature",
      "content": [
        "  Scenario: Adding two numbers",
        "    Given I have 2 and 3",
        "    When  I add these numbers",
        "    Then the result should be 5",
        ""
      ]
    }
    """

  Scenario: Scenario described in documentation not present in the actual feature files
    When I get "/scenarios/Multiplying two numbers"
    Then the response should be NOT FOUND
    And the response content should be JSON:
    """
    {
      "result":"notfound",
      "name":"Scenario not found",
      "path":"",
      "content": []
    }
    """
