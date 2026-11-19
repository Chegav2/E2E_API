# language: en
Feature: Compra E2E en Demoblaze
  Como usuario
  Quiero poder agregar productos al carrito y completar la compra
  Para finalizar exitosamente una orden

  @e2e @purchase @demoblaze
  Scenario Outline: Compra exitosa de dos productos
    Given que abro la tienda
    When agrego los productos del caso:
      | caso   |
      | <caseId> |
    And voy al carrito
    And realizo la compra con los datos del caso:
      | caso   |
      | <caseId> |
    Then veo la confirmación de compra
    And el total de compra coincide con la suma del carrito

    Examples:
      | caseId            |
      | standard-purchase |
