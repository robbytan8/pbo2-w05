package com.robby.controller;

import com.robby.entity.Department;
import com.robby.entity.Student;
import com.robby.utility.TextUtil;
import com.robby.utility.ViewUtil;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Robby
 */
public class StudentFormController implements Initializable {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Student, String> col01;
    @FXML
    private TableColumn<Student, String> col02;
    @FXML
    private TableColumn<Student, Date> col03;
    @FXML
    private TableColumn<Student, String> col04;
    @FXML
    private ComboBox<Department> comboDepartment;
    private MainFormController mainController;
    @FXML
    private TableView<Student> tableStudent;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtLastName;
    private Student selectedStudent;

    @FXML
    private void deleteAction(ActionEvent event) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setContentText("Are you sure want to delete?");
        deleteConfirmation.showAndWait();
        if (deleteConfirmation.getResult() == ButtonType.YES) {
            mainController.getStudentDao().deleteData(selectedStudent);
            mainController.getStudents().clear();
            mainController.getStudents().addAll(mainController.getStudentDao().showAllData());
        }
    }

    public void setMainController(MainFormController mainController) {
        this.mainController = mainController;
        this.comboDepartment.setItems(mainController.getDepartments());
        this.tableStudent.setItems(mainController.getStudents());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col01.setCellValueFactory(data -> {
            Student s = data.getValue();
            return new SimpleStringProperty(s.getId());
        });
        col02.setCellValueFactory(data -> {
            Student s = data.getValue();
            return new SimpleStringProperty(s.getFirstName() + " " + s.getLastName());
        });
        col03.setCellValueFactory(data -> {
            Student s = data.getValue();
            return new SimpleObjectProperty<>(s.getBirthDate());
        });
        col04.setCellValueFactory(data -> {
            Student s = data.getValue();
            return new SimpleStringProperty(s.getDepartment().getName());
        });
    }

    @FXML
    private void resetAction(ActionEvent event) {
        this.resetForm();
    }

    @FXML
    private void saveAction(ActionEvent event) {
        if (TextUtil.isEmptyField(txtId, txtFirstName, txtLastName) || txtDate.getValue() == null || comboDepartment.
                getValue() == null) {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error", "Please fill all field");
        } else {
            Student student = new Student();
            student.setId(txtId.getText().trim());
            student.setFirstName(txtFirstName.getText().trim());
            student.setLastName(txtLastName.getText().trim());
            Date studentBirthDate = Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            student.setBirthDate(studentBirthDate);
            student.setDepartment(comboDepartment.getValue());
            mainController.getStudents().clear();
            mainController.getStudentDao().addData(student);
            mainController.getStudents().addAll(mainController.getStudentDao().showAllData());
            this.resetForm();
        }
    }

    @FXML
    private void tableClicked(MouseEvent event) {
        selectedStudent = tableStudent.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            txtId.setText(selectedStudent.getId());
            txtFirstName.setText(selectedStudent.getFirstName());
            txtLastName.setText(selectedStudent.getLastName());
            txtDate.setValue(((java.sql.Date) selectedStudent.getBirthDate()).toLocalDate());
            comboDepartment.setValue(selectedStudent.getDepartment());
            txtId.setDisable(true);
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    private void updateAction(ActionEvent event) {
        if (TextUtil.isEmptyField(txtFirstName, txtLastName) || txtDate.getValue() == null || comboDepartment.
                getValue() == null) {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error",
                    "Please fill first name, last name, date, and department");
        } else {
            selectedStudent.setId(txtId.getText().trim());
            selectedStudent.setFirstName(txtFirstName.getText().trim());
            selectedStudent.setLastName(txtLastName.getText().trim());
            Date selectedStudentBirthDate = Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).
                    toInstant());
            selectedStudent.setBirthDate(selectedStudentBirthDate);
            selectedStudent.setDepartment(comboDepartment.getValue());
            mainController.getStudents().clear();
            mainController.getStudentDao().updateData(selectedStudent);
            mainController.getStudents().addAll(mainController.getStudentDao().showAllData());
            this.resetForm();
        }
    }

    private void resetForm() {
        txtId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtId.setDisable(false);
        selectedStudent = null;
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
}
