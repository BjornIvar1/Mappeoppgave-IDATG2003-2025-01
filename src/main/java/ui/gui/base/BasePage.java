package ui.gui.base;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;

/**
 * This class includes methods needed for all the pages.
 *
 * <p>Provides a shared functionality such as a top menu bar</p>
 *
 * @author A. Sahoo, B.I. Høie
 * @since 0.0.1
 * @version 0.2.0
 */
public class BasePage extends BorderPane {

  /**
   * Constructs {@link BasePage} with a default top menu bar.
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
   * @param content the content to be set
   */
  protected void setPageContent(Pane content) {
    setCenter(content);
  }
}
