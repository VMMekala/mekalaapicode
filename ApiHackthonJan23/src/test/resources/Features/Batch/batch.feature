Feature: Create batch

  Scenario Outline: create batch
    Given create new batch  with "<batchName>" "<batchDescription>" "<batchStaus>" <batchNoOfClasses> <programId> with POST request
    When user calls "AddBatch" api with post method
    Then batch is created with success code 201
    And verify batch_id created is mapped to given programId using PosttBatchAPI

    Examples: 
      | batchName                   | batchDescription | batchStaus | batchNoOfClasses | programId |
      | Jan23-TechWarriors-SDET019- | Python           | Active     |               12 |      4317 |
      | Jan23-TechWarriors-SDET019- | Python           | Active     |               12 |      4029 |
      | Jan23-TechWarriors-SDET019- | Python           | Active     |               12 |      4030 |

  Scenario: get batches By ID
    Given get batch with get request by Id 
    When user calls get batch api with get request
    Then get batch details with success code 200
    Then verify the given batch_id

  Scenario: delete batch
    Given delete batch with delete request
    When user calls delete batch api with Delete request
    Then batch is deleted with success code 200
    Then verify the given batch_id deleted
 
  Scenario: get all batches
    Given a api url 
    When user calls "GetAllBatches" with get method
    Then resquest is successfull with  status code 200
    And verify response size is greater than zero
    
    Scenario Outline: Update batch by Id
    Given Update existing batch payload with "<name>" "<description>" "<status>" with Put request
    When user calls "UpdateBatchById" api with "<ID>"
    Then verify batch is updated with "<name>" "<description>"

    Examples: 
      |ID			| name | description | status |
      |196 	| RPA  | RPA Desc    | Active |
