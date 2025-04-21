package gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class represents the base page for the application.
 * Including methods needed for all the pages.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.1.0
 */
public class BasePage extends BorderPane {

  public BasePage() {
    setTop(createMenuBar());
  }

  /**
   * Creates a menu bar with a "File" menu and a "Close" menu item.
   *
   * @return the created MenuBar
   */
  protected MenuBar createMenuBar() {
    MenuItem closeMenuItem = new MenuItem("Close");
    closeMenuItem.setOnAction(event ->
        System.exit(0));

    Menu fileMenu = new Menu("File");
    fileMenu.getItems().addAll(closeMenuItem);

    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().addAll(fileMenu);
    return menuBar;
  }

  protected void setPageContent(Pane content) {
    setCenter(content);
  }
}
