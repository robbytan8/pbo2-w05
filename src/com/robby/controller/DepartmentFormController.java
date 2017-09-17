package com.robby.controller;

import com.robby.entity.Department;
import com.robby.utility.TextUtil;
import com.robby.utility.ViewUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Robby
 */
public class DepartmentFormController implements Initializable {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Department, String> colCode;
    @FXML
    private TableColumn<Department, String> colName;
    private MainFormController mainController;
    @FXML
    private TableView<Department> tableDepartment;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    private Department selectedDepartment;

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setTitle("Delete Confirmation");
        alertConfirmation.setContentText("Are you sure want to delete?");
        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                mainController.getDepartmentDao().deleteData(selectedDepartment);
                mainController.getDepartments().clear();
                mainController.getDepartments().addAll(mainController.getDepartmentDao().showAllData());
                txtCode.setDisable(false);
                txtCode.clear();
                txtName.clear();
                btnDelete.setDisable(true);
                btnSave.setDisable(false);
                btnUpdate.setDisable(true);
            }
        });
    }

    @FXML
    private void btnResetAction(ActionEvent event) {
        txtCode.setDisable(false);
        txtCode.clear();
        txtName.clear();
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (!TextUtil.isEmptyField(txtCode, txtName)) {
            Department department = new Department();
            department.setCode(txtCode.getText().trim());
            department.setName(txtName.getText().trim());
            mainController.getDepartmentDao().addData(department);
            mainController.getDepartments().clear();
            mainController.getDepartments().addAll(mainController.getDepartmentDao().showAllData());
            txtCode.clear();
            txtName.clear();
        } else {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error", "Please fill all field");
        }
    }

    @FXML
    private void btnUpdateAction(ActionEvent event) {
        if (!TextUtil.isEmptyField(txtName)) {
            selectedDepartment.setName(txtName.getText().trim());
            mainController.getDepartmentDao().updateData(selectedDepartment);
            mainController.getDepartments().clear();
            mainController.getDepartments().addAll(mainController.getDepartmentDao().showAllData());
            txtCode.setDisable(false);
            txtCode.clear();
            txtName.clear();
            btnDelete.setDisable(true);
            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
        } else {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error", "Please fill all field");
        }
    }

    public void setMainController(MainFormController mainController) {
        this.mainController = mainController;
        this.tableDepartment.setItems(mainController.getDepartments());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    private void tableDepartmentMouseClicked(MouseEvent event) {
        selectedDepartment = tableDepartment.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            txtCode.setDisable(true);
            txtCode.setText(selectedDepartment.getCode());
            txtName.setText(selectedDepartment.getName());
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
        }
    }
}
