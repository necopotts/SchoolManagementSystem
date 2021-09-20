package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.stress.SpreadsheetHandler;

import DAO.Student;
import Model.DataModel;
import Model.DatabaseConnection;
import Model.StudentModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;

public class StudentTableController extends Controller implements Initializable {

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentIdColumn;
    @FXML
    private TableColumn<Student, String> fullnameColumn;
    @FXML
    private TableColumn<Student, String> dateOfBirthColumn;
    @FXML
    private TableColumn<Student, String> majorColumn;
    @FXML
    private TableColumn<Student, String> programColumn;
    @FXML
    private TableColumn<Student, Void> transcriptButtonColumn;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button editStudentButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Text studentIDText;
    @FXML
    private Text fullnameText;
    @FXML
    private Text dateOfBirthText;
    @FXML
    private Text majorText;
    @FXML
    private Text programText;
    @FXML
    private TextField searchTextField;
    @FXML
    private TilePane infoStudentTilePane;

    private Student currentStudent;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private StudentModel studentModel;

    private HashMap<String, CellStyle> cellStyles;

    @FXML
    public void studentClicked(MouseEvent event) {
        currentStudent = studentTable.getSelectionModel().getSelectedItem();
        studentIDText.setText(currentStudent.getStudentID().toString());
        fullnameText.setText(currentStudent.getFullname());
        dateOfBirthText.setText(currentStudent.getDateOfBirth().toString());
        majorText.setText(currentStudent.getMajor());
        programText.setText(currentStudent.getProgram());
        studentModel.setCurrentPerson(currentStudent);
        editStudentButton.setDisable(false);
        deleteStudentButton.setDisable(false);
        infoStudentTilePane.setVisible(true);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        editStudentButton.setDisable(true);
        deleteStudentButton.setDisable(true);
        infoStudentTilePane.setVisible(false);

    }

    public void initModel(StudentModel studentModel) {
        // TODO Auto-generated method stub
        if (this.studentModel != null) {
            throw new IllegalStateException("Model can only initialize once");
        }
        this.studentModel = studentModel;
        loadData();
    }

    public void openScene(String URL) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URL));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnHidden(evt -> {
                studentTable.getItems().clear();
                loadData();
            });
            stage.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addStudentButtonOnAction(ActionEvent event) {
        openScene("/View/AddStudentStage.fxml");

    }

    public void editStudentButtonOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditStudentStage.fxml"));
            Parent root = loader.load();
            EditStudentController editStudentController = (EditStudentController) loader.getController();
            editStudentController.initData(currentStudent);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnHidden(evt -> {
                studentTable.getItems().clear();
                loadData();
            });
            stage.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void searchButtonOnAction(ActionEvent event) {
        studentTable.getItems().clear();
        if (searchTextField.getText().isBlank()) {
            loadData();
            exportButton.setDisable(false);
        } else {
            String searchKeyword = searchTextField.getText();
            studentList = studentModel.searchData(searchKeyword);
            exportButton.setDisable(true);
        }

    }

    public void deleteStudentButtonOnAction(ActionEvent event) {
        if (currentStudent != null) {
            Integer studentID = currentStudent.getStudentID();
            studentModel.deleteData(studentID);
            studentTable.getItems().clear();
            loadData();
        } else {

        }
    }

    public void exportButtonOnAction(ActionEvent event) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get current stage
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory == null) {
            System.out.println("No path choose");
        } else {
            String URL = convertFromBackslashToSlash(selectedDirectory.getAbsolutePath());
            exportToExcel(workbook, URL);
        }

    }

    private void exportToExcel(HSSFWorkbook workbook, String URL) {
        HSSFSheet spreadsheet = workbook.createSheet("Sheet1");
        StyleWorkBook styleWorkBook = new StyleWorkBook(new HashMap<String, CellStyle>(), workbook);
        cellStyles = styleWorkBook.getCellStyles();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Set header
        createHeaders(spreadsheet);

        HSSFRow row = spreadsheet.createRow(3); // table start at row 3
        // create No column
        row.createCell(0).setCellValue("No");
        row.getCell(0).setCellStyle(cellStyles.get("Title cell"));

        // Set titles of column
        for (int i = 0; i < studentTable.getColumns().size(); i++) {
            row.createCell(i + 1).setCellValue(studentTable.getColumns().get(i).getText());
            row.getCell(i + 1).setCellStyle(cellStyles.get("Title cell"));

        }
        // Set data to column
        for (int i = 0; i < studentTable.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 4);
            row.createCell(0).setCellValue(i + 1);
            row.getCell(0).setCellStyle(cellStyles.get("Normal cell"));
            for (int j = 0; j < studentTable.getColumns().size(); j++) {
                Object cellValue = studentTable.getColumns().get(j).getCellObservableValue(i).getValue();
                if (cellValue != null && isNumeric(cellValue.toString()) == true) {
                    row.createCell(j + 1).setCellValue(Integer.parseInt(cellValue.toString()));
                    row.getCell(j + 1).setCellStyle(cellStyles.get("Normal cell"));
                } else {
                    if (isDate(cellValue.toString())) {
                        String dateString = LocalDate.parse(cellValue.toString()).format(dateFormatter);
                        LocalDate date = LocalDate.parse(dateString, dateFormatter);
                        row.createCell(j + 1).setCellValue(date); // cell type: Numeric
                        row.getCell(j + 1).setCellStyle(cellStyles.get("Date cell")); // convert to Date cell
                    } else {
                        row.createCell(j + 1).setCellValue(cellValue.toString());
                        row.getCell(j + 1).setCellStyle(cellStyles.get("Normal cell"));
                    }
                }
                spreadsheet.autoSizeColumn(j + 1);
            }

        }
        try {
            FileOutputStream fileOut = new FileOutputStream(URL + "/StudentIU.xls");
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            System.out.println("File exported");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void createHeaders(HSSFSheet spreadsheet) {
        HSSFRow row = spreadsheet.createRow(0);
        // Set header
        row.createCell(0).setCellValue("TRƯỜNG ĐẠI HỌC QUỐC TẾ");
        row.getCell(0).setCellStyle(cellStyles.get("School cell"));
        row = spreadsheet.createRow(1);
        row.createCell(0).setCellValue("PHÒNG HỢP TÁC ĐÀO TẠO");
        row.getCell(0).setCellStyle(cellStyles.get("Room cell"));
        row = spreadsheet.createRow(2);
        row.createCell(0).setCellValue("DANH SÁCH SINH VIÊN");
        // Merge cell for "DS SV"
        spreadsheet.addMergedRegion(
                new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, studentTable.getColumns().size()));
        row.getCell(0).setCellStyle(cellStyles.get("Header cell"));

    }

    private void loadData() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));

        studentList = this.studentModel.getStudentList();
        studentTable.setItems(studentList);
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactory = new Callback<TableColumn<Student, Void>, TableCell<Student, Void>>() {

            @Override
            public TableCell<Student, Void> call(TableColumn<Student, Void> arg0) {
                Image img = new Image("Resource/icons8-list-64.png");
                ImageView transcriptImageView = new ImageView(img);
                transcriptImageView.setFitHeight(13);
                transcriptImageView.setFitWidth(15);
                transcriptImageView.setPreserveRatio(true);
                final Button button = new Button("");
                button.setPrefWidth(30);
                button.setPrefHeight(20);
                button.setMinHeight(20);
                button.setMinWidth(30);
                button.setGraphic(transcriptImageView);

                // TODO Auto-generated method stub
                final TableCell<Student, Void> cell = new TableCell<Student, Void>() {
                    // Image img = new Image("Resource/icons8-list-64.png");
                    // ImageView transcriptImageView = new ImageView(img);
                    // transcriptImageView.setFitHeight(10);
                    // transcriptImageView.setFitWeight(10);
                    // transcriptImageView.setPreserveRatio(true);

                    // private final Button btn = new Button("");

                    // btn.setGraphic(transcriptImageView);

                    {
                        button.setOnAction((ActionEvent event) -> {
                            // Student data = getTableView().getItems().get(getIndex());
                            // System.out.println("selectedData: " + data.getStudentID());
                            openScene("/View/TranscriptStudentStage.fxml");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        transcriptButtonColumn.setCellFactory(cellFactory);

    }

    public String convertFromBackslashToSlash(String string) {
        return string.replaceAll("\\\\", "/");
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
    }

    public boolean isDate(String dateString) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateString, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

}
