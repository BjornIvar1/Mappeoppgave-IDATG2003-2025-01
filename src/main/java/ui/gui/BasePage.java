package ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;

/**
 * This class represents the base page for the application.
 * Including methods needed for all the pages.
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.2.0
 */
public class BasePage extends BorderPane {

  /**
   * Creates a new instance of BasePage.
   *
   * <p>This constructor initializes the top menu bar for the page.</p>
   */
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

  /**
   * Creates a top bar with return and game rules buttons.
   *
   * <p>This method creates a top bar with two buttons: one to return
   * to the previous page and another to show game rules.</p>
   *
   * @param returnButton the button to return to the previous page
   * @param gameRulesButton the button to show game rules
   * @return a HBox containing the buttons
   */
  protected HBox createTopBar(Button returnButton, Button gameRulesButton) {
    HBox topBar = new HBox();
    topBar.setPadding(new Insets(10));
    topBar.setSpacing(10);
    topBar.setAlignment(Pos.CENTER);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    returnButton.setMaxWidth(Double.MAX_VALUE);
    gameRulesButton.setMaxWidth(Double.MAX_VALUE);

    topBar.getChildren().addAll(returnButton, spacer, gameRulesButton);
    return topBar;
  }

  /**
   * Sets the content of the page.
   *
   * <p>This method sets the center of the page to the specified content.</p>
   *
   * @param content the content to be set in the center of the page.
   */
  protected void setPageContent(Pane content) {
    setCenter(content);
  }
}
