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
    And file "testfixture-cucumberjvm-result.json" with content from "com/michaelszymczak/speccare/specminer/specificationprovider/testfixture-cucumber-result.json"

  Scenario: Scenario exists and passes
    Given OK

