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

  Scenario: Scenario described in documentation has ambiguous name
    When I get "/scenarios/Adding"
    Then the response should be UNPROCESSABLE ENTITY
    And the response content should be JSON:
    """
    {
      "result":"ambiguous",
      "name":"Too many scenarios matching searched phrase",
      "path":"ABSOLUTE_PATH_TO_FEATURES_DIR/Adding.feature,ABSOLUTE_PATH_TO_FEATURES_DIR/Adding.feature",
      "content": []
    }
    """