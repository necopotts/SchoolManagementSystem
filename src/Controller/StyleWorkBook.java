package Controller;

import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.WorkbookDependentFormula;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.stress.SpreadsheetHandler;

public class StyleWorkBook {

    private HashMap<String, CellStyle> cellStyles;
    private HSSFWorkbook workbook;

    public StyleWorkBook(HashMap<String, CellStyle> cellStyles, HSSFWorkbook workbook) {
        this.cellStyles = cellStyles;
        this.workbook = workbook;
        cellStyleCreate();
    }

    public HashMap<String, CellStyle> getCellStyles() {
        return cellStyles;
    }

    private void cellStyleCreate() {
        CellStyle cellStyle = this.workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        HSSFFont normalFont = this.workbook.createFont();
        normalFont.setFontHeightInPoints((short) 11);
        cellStyle.setFont(normalFont);
        // cellStyle.setShrinkToFit(true);
        cellStyles.put("Normal cell", cellStyle);

        // date format
        CellStyle dateFormatStyle = this.workbook.createCellStyle();
        CreationHelper createHelper = this.workbook.getCreationHelper();
        dateFormatStyle.cloneStyleFrom(cellStyle);
        dateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
        cellStyles.put("Date cell", dateFormatStyle);

        // title cell
        CellStyle titleStyle = this.workbook.createCellStyle();
        titleStyle.cloneStyleFrom(cellStyle);
        HSSFFont titleFont = this.workbook.createFont();
        titleFont.setBold(true);
        // titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        cellStyles.put("Title cell", titleStyle);

        // School cell
        CellStyle schoolStyle = this.workbook.createCellStyle();
        HSSFFont schoolFont = this.workbook.createFont();
        schoolFont.setFontHeightInPoints((short) 11);
        schoolStyle.setFont(schoolFont);
        cellStyles.put("School cell", schoolStyle);

        // Room cell
        CellStyle roomStyle = this.workbook.createCellStyle();
        HSSFFont roomFont = this.workbook.createFont();
        roomFont.setBold(true);
        roomFont.setUnderline(HSSFFont.U_SINGLE);
        roomStyle.cloneStyleFrom(schoolStyle);
        roomStyle.setFont(roomFont);
        cellStyles.put("Room cell", roomStyle);

        // Header cell
        CellStyle headerStyle = this.workbook.createCellStyle();
        HSSFFont headerFont = this.workbook.createFont();
        headerFont.setBold(true);
        headerStyle.cloneStyleFrom(schoolStyle);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setWrapText(true);
        headerStyle.setFont(headerFont);
        cellStyles.put("Header cell", headerStyle);

    }

}
