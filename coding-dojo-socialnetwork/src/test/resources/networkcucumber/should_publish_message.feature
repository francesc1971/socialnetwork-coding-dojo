Feature: We should publish a message
  We should publish a message and receive the same message from server

  Scenario: Alice publish a message and receives the same message
    Given Alice writes in console Hello
    When Alice sends Hello to server and it is published
    Then Server sends to Alice Hello to her timeline

