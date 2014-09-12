Feature:
  As a stakeholder
  I want to have my features in the documentation checked if they pass
  So that I know if the documentation does not describe something that does notwork

  Background:
    Given feature file "featureA.feature" in the developed application:
    """
    Feature:
      As foo
      I want feature A
      So that bar

      Background:
        Given OK
        And OK

      Scenario: Feature A Scenario passed
        When OK
        Then OK

      Scenario: Feature A Scenario failed
        When OK
        Then FAIL


      Scenario: Feature A Scenario ignored
        Given OK
        When NOTFOUND
        Then OK
    """
    And feature file "featureB.feature" in the developed application:
    """
    Feature:
      As foo
      I want feature B
      So that bar

      Scenario: feature B Scenario passed
        When OK
        Then OK

      Scenario: feature B Scenario failed
        When OK
        Then FAIL


      Scenario: feature B Scenario ignored
        Given OK
        When NOTFOUND
        Then OK
    """
    And result file with content from "com/michaelszymczak/speccare/specminer/featurefiles/testfixture-cucumber-result.json"

  Scenario: Passing scenario found by the scenario name fragment
    Given result file with one "Feature A Scenario passed" passing scenario
    When I get "/scenarios/A Scenario passed"
    Then the response should be OK
    And the response content should be JSON:
    """
    {
      "result":"passed",
      "name":"Feature A Scenario passed",
      "path":"ABSOLUTE_PATH_TO_FEATURES_DIR/featureA.feature",
      "content": [
        "  Scenario: Feature A Scenario passed",
        "    When OK",
        "    Then OK"
      ]
    }
    """

  Scenario: Existing but failing scenario found by the scenario name fragment
    Given result file with one "feature B Scenario failed" failed scenario
    When I get "/scenarios/B Scenario failed"
    Then the response should be OK
    And the response content should be JSON:
    """
    {
      "result":"failed",
      "name":"feature B Scenario failed",
      "path":"ABSOLUTE_PATH_TO_FEATURES_DIR/featureB.feature",
      "content": [
        "  Scenario: feature B Scenario failed",
        "    When OK",
        "    Then FAIL"
      ]
    }
    """

