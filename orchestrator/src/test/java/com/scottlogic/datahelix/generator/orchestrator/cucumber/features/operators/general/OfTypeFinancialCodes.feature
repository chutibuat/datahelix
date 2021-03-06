# Copyright 2019 Scott Logic Ltd
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
Feature: User can specify that a field must be a financial code type

  Background:
    Given the generation strategy is full
    And there is a non nullable field foo

  Scenario: An ofType constraint with the value "ISIN" generates valid ISINs
    Given foo has type "ISIN"
    And foo is in set:
      | "GB0002634946" |
    Then the following data should be generated:
      | foo            |
      | "GB0002634946" |

  Scenario: Sequential isins are generated uniquely
    Given foo has type "ISIN"
    And the generator can generate at most 4 rows
    Then the following data should be generated:
      | foo            |
      | "AD0000000003" |
      | "AD0000000011" |
      | "AD0000000029" |
      | "AD0000000037" |

  Scenario: An ofType constraint with the value "SEDOL" generates valid SEDOLs
    Given foo has type "SEDOL"
    And foo is in set:
      | "0263494" |
    Then the following data should be generated:
      | foo       |
      | "0263494" |

  Scenario: An ofType constraint with the value "CUSIP" generates valid CUSIPs
    Given foo has type "CUSIP"
    And foo is in set:
      | "38259P508" |
    Then the following data should be generated:
      | foo         |
      | "38259P508" |

  Scenario: Two ISIN constraints combined generate valid ISINs
    Given foo has type "ISIN"
    And foo has type "ISIN"
    And foo is in set:
      | "GB0002634946" |
    Then the following data should be generated:
      | foo            |
      | "GB0002634946" |

  Scenario: A SEDOL constraint combined with another SEDOL constraint generates valid SEDOLs
    Given foo has type "SEDOL"
    And foo has type "SEDOL"
    And foo is in set:
      | "0263494" |
    Then the following data should be generated:
      | foo       |
      | "0263494" |

  Scenario: A CUSIP constraint combined with a second CUSIP constraint generates valid CUSIPs
    Given foo has type "CUSIP"
    And foo has type "CUSIP"
    And foo is in set:
      | "38259P508" |
    Then the following data should be generated:
      | foo         |
      | "38259P508" |

  Scenario: A RIC constraint combined with a not null constraint generates valid RICs
    Given foo has type "RIC"
    And foo is in set:
      | "AB.PQ" |
    Then the following data should be generated:
      | foo     |
      | "AB.PQ" |

  Scenario: A RIC constraint combined with a not null constraint and an in set constraint that does not contain any valid RICs generates no data
    Given foo has type "RIC"
    And foo is in set:
      | "NOPE" |
    Then the following data should be generated:
      | foo |

  Scenario: A RIC constraint combined with an of length constraint returns valid RICs of the specified length
    Given foo has type "RIC"
    And foo is of length 6
    And foo is in set:
      | "AB.PQ"   |
      | "ABC.PQ"  |
      | "ABCD.PQ" |
    Then the following data should be generated:
      | foo      |
      | "ABC.PQ" |
