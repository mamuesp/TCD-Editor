/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Controller.DataIO.DataIOController;
import Editor.View.Skin.IControlSkin;
import Editor.Model.TcdControl;
import Editor.Utils;
import com.mykong.core.OSValidator;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import org.controlsfx.control.PropertySheet;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mmspaeth
 */
public class Controller implements Initializable {

    final Logger logger = Logger.getLogger("MainLogger");

    @FXML
    public Menu mnuSpecMac;
    @FXML
    public MenuItem mnuFileSepWin;
    @FXML
    public MenuItem mnuFileQuitWin;
    @FXML
    public Rectangle panelShader;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Pane basePane;
    @FXML
    public GridPane itemGrid;
    @FXML
    public AnchorPane propEditor;
    @FXML
    public VBox propVBox;

    @FXML
    private void openGuiProject(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File controlsFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        if (controlsFile != null) {
            DataIOController.getInstance().loadControlDataFromFile(controlsFile);
            Utils.setControlsFilePath(controlsFile);
        }
        logger.log(Level.INFO, "Open an existing GUI project ...");
    }

    @FXML
    private void saveGuiProject(ActionEvent event) {

        File controlsFile = Utils.getControlsFilePath();
        if (controlsFile != null) {
            DataIOController.getInstance().saveControlDataToFile(controlsFile);
        } else {
            saveAsGuiProject(event);
        }
        logger.log(Level.INFO, "Save the current GUI project ...");
    }

    @FXML
    private void saveAsGuiProject(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File controlsFile = fileChooser.showSaveDialog(rootPane.getScene().getWindow());

        if (controlsFile != null) {
            DataIOController.getInstance().saveControlDataToFile(controlsFile);
            Utils.setControlsFilePath(controlsFile);
        }
        logger.log(Level.INFO, "Save the current GUI project ...");
    }

    @FXML
    private void infoAbout(ActionEvent event) {
        logger.log(Level.INFO, "Open the 'About' dialog ...");
    }

    @FXML
    private void openPreferences(ActionEvent event) {
        logger.log(Level.INFO, "Open the preferences dialog ...");
    }

    @FXML
    private void newGuiProject(ActionEvent event) {
        logger.log(Level.INFO, "Create and open a new GUI project ...");
    }

    @FXML
    private void closeGuiProject(ActionEvent event) {
        basePane.getChildren().clear();
        logger.log(Level.INFO, "Close the current GUI project ...");
    }

    @FXML
    private void cutSelection(ActionEvent event) {
        logger.log(Level.INFO, "Cut selection to clipboard ...");
    }

    @FXML
    private void copySelection(ActionEvent event) {
        logger.log(Level.INFO, "Copy selection to clipboard ...");
    }

    @FXML
    private void pasteSelection(ActionEvent event) {
        logger.log(Level.INFO, "Paste selection from clipboard ...");
    }

    @FXML
    private void performUndo(ActionEvent event) {
        logger.log(Level.INFO, "Perform Undo (last step) ...");
    }

    @FXML
    private void performRedo(ActionEvent event) {
        logger.log(Level.INFO, "Perform Redo (last step) ...");
    }

    @FXML
    private void performZoomIn(ActionEvent event) {
        logger.log(Level.INFO, "Perform zoom in ...");
    }

    @FXML
    private void performZoomOut(ActionEvent event) {
        logger.log(Level.INFO, "Perform zoom out ...");
    }

    @FXML
    private void performDelete(ActionEvent event) {
        if (! ItemSelection.getInstance().isEmpty()) {
            Alert confDlg = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected items?", ButtonType.YES, ButtonType.NO);
            confDlg.showAndWait();
            if (confDlg.getResult() == ButtonType.YES) {
                ItemSelection.getInstance().deleteAll();
            }
        }
        logger.log(Level.INFO, "Perform deletion of selected objects ...");
    }

    @FXML
    private void performUpload(ActionEvent event) {
        logger.log(Level.INFO, "Perform upload to Teensy ...");
    }

    @FXML
    private void performDownload(ActionEvent event) {
        logger.log(Level.INFO, "Perform download from Teensy ...");
    }

    @FXML
    private void quitApplication(ActionEvent event) {
        logger.log(Level.INFO, "Quit the TCD editor now ...");
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareMenus();
        DataIOController.getInstance().setRoot(basePane);
        setEventHandlers();
        ItemSelection.getInstance().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                final ObservableList selectedNodes = change.getList();
                if (selectedNodes.size() == 1) {
                    TcdControl ctrl = (TcdControl) selectedNodes.get(0);
                    PropertySheet ps = new PropertySheet();
                    PropertySheet propertySheet = new PropertySheet(((IControlSkin) ctrl.getSkin()).getProperties().getItems("all"));
                    propVBox.setVgrow(propertySheet, Priority.ALWAYS);
                    propVBox.getChildren().add(ps);
                }
            }
        });
    }

    private void setEventHandlers() {
        ObservableList<Node> items = itemGrid.getChildren();
        for (Node curr : items) {
            if (curr instanceof Button) {
                curr.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        logger.log(Level.INFO, "Drag detected ...");
                        Node source = (Node) event.getSource();
                        if (source instanceof Button) {
                            // drag was detected, start a drag-and-drop gesture
                            // allow any transfer mode
                            Button srcButton = (Button) source;
                            Dragboard db = srcButton.startDragAndDrop(TransferMode.COPY_OR_MOVE);

                            TcdControl newItem = ItemSelection.getInstance().generateNewItem(srcButton.getText(), basePane, true);
                            logger.log(Level.INFO, "ID: " + newItem.getId());
                            db.setDragView(newItem.getImage());
                            newItem.setVisible(false);
                            ClipboardContent content = new ClipboardContent();
                            content.putString(newItem.getId());
                            db.setContent(content);
                        }
                        event.consume();
                    }
                });
                basePane.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        // data is dragged over the target
                        // accept it only if it is not dragged from the same node
                        // and if it has a string data
                        logger.log(Level.INFO, "Drag over ... Target: " + event.getTarget().getClass().getName());
                        Node target = (Node) event.getTarget();
                        Dragboard db = event.getDragboard();
                        Node gestSrc = (Node) event.getGestureSource();
                        if (gestSrc != target && db.hasString()) {
                            String itemId = db.getString();
                            event.acceptTransferModes(TransferMode.MOVE);
                            Control draggedItem = (Control) basePane.lookup("#" + itemId);
                            if (gestSrc instanceof TcdControl) {
                                Point2D delta = ((TcdControl) draggedItem).calcDelta(event);
                                ItemSelection.getInstance().moveSelected(delta);
                            }
                            ((TcdControl) draggedItem).move(event);
                        }
                    }
                });
                basePane.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        // the drag-and-drop gesture entered the target
                        // show to the user that it is an actual gesture target
                        logger.log(Level.INFO, "Drag entered ...");
                        Node target = (Node) event.getTarget();
                        Dragboard db = event.getDragboard();
                        if (event.getGestureSource() != target && db.hasString()) {
                            panelShader.setOpacity(0.85f);
                        }
                        event.consume();
                    }
                });
                basePane.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        logger.log(Level.INFO, "Drag exited ...");
                        // mouse moved away, remove the graphical cues
                        panelShader.setOpacity(0.0f);
                        event.consume();
                    }
                });
                basePane.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        logger.log(Level.INFO, "Drag dropped ... Target: " + event.getTarget().getClass().getName());
                        Node target = (Node) event.getTarget();
                        Dragboard db = event.getDragboard();
                        String itemId = "";

                        boolean success = false;
                        if (db.hasString()) {
                            itemId = db.getString();
                            success = true;
                            Control newItem = (Control) basePane.lookup("#" + itemId);
                            Bounds pos = newItem.getBoundsInParent();
                            Bounds frame = basePane.getBoundsInLocal();
                            if (target.equals(basePane) && frame.contains(pos)) {
                                ((TcdControl) newItem).move(event);
                                newItem.setVisible(true);
                                event.setDropCompleted(success);
                            } else {
                                Node gestSrc = (Node) event.getGestureSource();
                                if (gestSrc instanceof TcdControl) {
                                    newItem.setVisible(true);
                                    newItem = null;
                                } else {
                                    basePane.getChildren().remove(newItem);
                                    newItem = null;
                                }
                            }
                        }
                        panelShader.setOpacity(0.0f);
                        db.clear();
                        event.consume();
                    }
                });
                basePane.setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        logger.log(Level.INFO, "Drag done ...");
                        event.consume();
                    }
                });
            }
        }
    }

    private void prepareMenus() {
        mnuFileSepWin.setVisible(OSValidator.isWindows());
        mnuFileSepWin.setDisable(!OSValidator.isWindows());
        mnuFileQuitWin.setVisible(OSValidator.isWindows());
        mnuFileQuitWin.setDisable(!OSValidator.isWindows());
        if (OSValidator.isMac()) {
            //AquaFx.style();
            mnuSpecMac.setVisible(true);
            mnuSpecMac.setDisable(false);
        }
    }
}
