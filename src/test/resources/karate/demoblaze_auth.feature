@api @demoblaze
Feature: Demoblaze authentication API

  Background:
    * url 'https://api.demoblaze.com'
    * configure connectTimeout = 5000
    * configure readTimeout = 10000
    * def username = 'demoblaze_' + java.util.UUID.randomUUID()

  @signup @new-user
  Scenario Outline: Create a new user with signup
    Given path 'signup'
    And request { username: '#(username)', password: '<password>' }
    When method post
    Then status 200
    And match response == '#string'
    And match response contains '""'

    Examples:
      | read('classpath:data/api/signup_cases.json') |

  @signup @existing-user
  Scenario Outline: Try to create an existing user with signup
    Given path 'signup'
    And request { username: '#(username)', password: '<password>' }
    When method post
    Then status 200
    And match response == '#string'
    And match response contains '""'
    Given path 'signup'
    And request { username: '#(username)', password: '<password>' }
    When method post
    Then status 200
    And match response == { errorMessage: '<expectedMessage>' }

    Examples:
      | read('classpath:data/api/signup_duplicate_cases.csv') |

  @login @valid-credentials
  Scenario Outline: Login with valid credentials
    Given path 'signup'
    And request { username: '#(username)', password: '<password>' }
    When method post
    Then status 200
    Given path 'login'
    And request { username: '#(username)', password: '<password>' }
    When method post
    Then status 200
    And match response == '#string'
    And match response contains '<expectedMessage>'

    Examples:
      | read('classpath:data/api/login_valid_cases.csv') |

  @login @invalid-credentials
  Scenario Outline: Login with invalid credentials
    Given path 'signup'
    And request { username: '#(username)', password: 'Test12345!' }
    When method post
    Then status 200
    Given path 'login'
    And request { username: '#(username)', password: '<password>' }
    When method post
    Then status 200
    And match response == { errorMessage: '<expectedMessage>' }

    Examples:
      | read('classpath:data/api/login_invalid_cases.csv') |
