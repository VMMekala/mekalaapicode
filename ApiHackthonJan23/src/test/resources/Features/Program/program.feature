Feature: Program

	
  Scenario Outline: create program
    Given create new program payload with "<name>" "<description>" "<status>" with POST request
    When user calls "AddProgram" api with post request
    Then program is created with success code
    And verify program_id created is mapped to "<name>" using "GetProgramById"

    Examples: 
      | name                        | description                 | status |
      | Jan23-TechWarrior-SDET-019- | Jan23-TechWarrior-SDET-Desc | Active |
      | Jan23-TechWarrior-SDET-019- | Jan23-TechWarrior-SDET-Desc | Active |
      | Jan23-TechWarrior-SDET-019- | Jan23-TechWarrior-SDET-Desc | Active |

  Scenario: get program by Id
    Given a program id with api "GetProgramById"
    When user requests with get method
    Then programs are displayed with success code
    And response has id

  Scenario: delete program by Id
    Given a program id with api "DeleteProgramById"
    When user requests with delete method
    Then program id is deleted with success code

  @updateProgram
  Scenario: Update program by program name
    Given Sets the request payload data with program "<description>" "<status>"
    When User gives PUT Request to the update program api "UpdateProgramByName"
    Then Program should be updated with status code 200
    And validate response body and schema

  Scenario: delete program by programname
    Given program payload with in GET request
    When user calls deletebyprogname api with in DELETE request
    Then program is deleted by program name with success code 200

  Scenario Outline: Update program by Id
    Given Update existing program payload with "<ID>" "<name>" "<description>" with Put request
    When user calls "putprogram" api with "<ID>" request
    Then program is updated with success code 200
    And verify "<name>" exists in response

    Examples: 
      | ID   | name             | description | status |
      | 4715 | Updated at night | RPA Desc    | Active |

  Scenario: Get All Programs
    Given API URL to fetch all programs
    When User sends GET request to "GetAllProgram"
    Then All programs are displayed with success code 
    And validate header in response
