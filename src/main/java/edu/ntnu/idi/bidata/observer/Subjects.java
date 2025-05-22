package edu.ntnu.idi.bidata.observer;

/**
 * The interface {@code Subjects} defines the observer pattern.
 *
 * <p>It is used to notify observers about changes in the subject's state.</p>
 *
 * @since 0.0.1
 * @author A. Sahoo, B.I. Høie
 * @version 0.0.1
 */
public interface Subjects {
  /**
   * Adds an observer to the list of observers.
   *
   * @param observer the observer to be added
   */
  void addObserver(BoardGameObserver observer);

  /**
   * Removes an observer from the list of observers.
   *
   * @param observer the observer to be removed
   */
  void removeObserver(BoardGameObserver observer);

  /**
   * Notifies all registered observers about a change.
   */
  void notifyObservers();
}
