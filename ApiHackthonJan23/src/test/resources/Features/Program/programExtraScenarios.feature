Feature: Program

  @ignore
  Scenario: Authenticate user
    Given Add payload with valid "username" with valid "password"
    When user call login api with get method
    Then user is logged to system with success code 200

  @ignore
  Scenario: Search programs by latest N number of records
    Given Add payload with integer limit "20"
    When user calls getAllPrograms with get method
    Then latest "20" number of records should display

  @ignore
  Scenario Outline: Search programs by given key
    Given add payload with "key" "<search>"
    When user calls getProgramByName with get method
    Then All programs with given description must display with success code

    Examples: 
      | key                | search     |
      | programName        | SDET       |
      | programDescription | SDET DESC  |
      | creationTime       | 2023-01-17 |
