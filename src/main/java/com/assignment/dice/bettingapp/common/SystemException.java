package com.assignment.dice.bettingapp.common;

/** Defines the custom exception. */
public class SystemException extends RuntimeException {

  private static final long serialVersionUID = -8185112895647610575L;

  /**
   * constructor
   *
   * @param errorMessage
   */
  public SystemException(String errorMessage) {
    super(errorMessage);
  }
}
