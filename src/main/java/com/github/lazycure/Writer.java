package main.java.com.github.lazycure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.java.com.github.lazycure.activities.Activity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Writer {

	public static Context context = LazyCureApplication.getAppContext();

	public static Boolean checkSDCard() {
		String auxSDCardStatus = Environment.getExternalStorageState();

		if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED))
			return true;
		else if (auxSDCardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			Toast.makeText(
					context,
					"Warning, the SDCard it's only in read mode.\nthis does not result in malfunction"
							+ " of the read aplication", Toast.LENGTH_LONG)
					.show();
			return true;
		} else if (auxSDCardStatus.equals(Environment.MEDIA_NOFS)) {
			Toast.makeText(
					context,
					"Error, the SDCard can be used, it has not a corret format or "
							+ "is not formated.", Toast.LENGTH_LONG).show();
			return false;
		} else if (auxSDCardStatus.equals(Environment.MEDIA_REMOVED)) {
			Toast.makeText(
					context,
					"Error, the SDCard is not found, to use the reader you need "
							+ "insert a SDCard on the device.",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (auxSDCardStatus.equals(Environment.MEDIA_SHARED)) {
			Toast.makeText(
					context,
					"Error, the SDCard is not mounted beacuse is using "
							+ "connected by USB. Plug out and try again.",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTABLE)) {
			Toast.makeText(
					context,
					"Error, the SDCard cant be mounted.\nThe may be happend when the SDCard is corrupted "
							+ "or crashed.", Toast.LENGTH_LONG).show();
			return false;
		} else if (auxSDCardStatus.equals(Environment.MEDIA_UNMOUNTED)) {
			Toast.makeText(
					context,
					"Error, the SDCArd is on the device but is not mounted."
							+ "Mount it before use the app.", Toast.LENGTH_LONG)
					.show();
			return false;
		}

		return true;
	}

	public static boolean writeFile(String directoryName, String filename,
			String data) {
		boolean operationStatus = false;
		File root = new File(Environment.getExternalStorageDirectory(),
				directoryName);
		if (!root.exists()) {
			root.mkdirs();
		}
		File file = new File(root, filename);
		try {
			if (checkSDCard() && root.canWrite()) {
				FileWriter filewriter = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(filewriter);
				out.write(data);
				out.close();
				operationStatus = true;
			}
		} catch (IOException e) {
			Log.e("Writer", "Could not write text file " + e.getMessage());
		}
		return operationStatus;
	}

	public static boolean writeActivitiesInXLS(String directoryName,
			String filename, List<Activity> activities) {
		boolean operationStatus = false;

		// New Workbook
		Workbook wb = new HSSFWorkbook();

		Cell c = null;

		// Cell style for header row
		CellStyle header = wb.createCellStyle();
		header.setFillForegroundColor(HSSFColor.LIME.index);
		header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// Cell style for header row
		CellStyle activityStyle = wb.createCellStyle();
		activityStyle.setFillForegroundColor(HSSFColor.AUTOMATIC.index);
		activityStyle.setFillPattern(HSSFCellStyle.NO_FILL);

		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet("Activities");

		// Generate column headings
		Row row = sheet1.createRow(0);

		c = row.createCell(0);
		c.setCellValue("Start Time");
		c.setCellStyle(header);

		c = row.createCell(1);
		c.setCellValue("Name");
		c.setCellStyle(header);

		c = row.createCell(2);
		c.setCellValue("Duration");
		c.setCellStyle(header);

		c = row.createCell(3);
		c.setCellValue("Finish Time");
		c.setCellStyle(header);

		for (int i = 0; i < activities.size(); i++) {
			Row activityRow = sheet1.createRow(i + 1);
			// Start Time
			c = activityRow.createCell(0);
			c.setCellValue(Time.formatWithDefaultTimeZone(activities.get(i)
					.getStartTime()));
			c.setCellStyle(activityStyle);
			// Name
			c = activityRow.createCell(1);
			c.setCellValue(activities.get(i).getName());
			c.setCellStyle(activityStyle);
			// Duration
			c = activityRow.createCell(2);
			c.setCellValue(Time.formatWithDay(activities.get(i).getDuration()));
			c.setCellStyle(activityStyle);
			// Finish Time
			c = activityRow.createCell(3);
			c.setCellValue(Time.formatWithDefaultTimeZone(activities.get(i)
					.getFinishTime()));
			c.setCellStyle(activityStyle);
		}

		sheet1.setColumnWidth(0, (15 * 500));
		sheet1.setColumnWidth(1, (15 * 500));
		sheet1.setColumnWidth(2, (15 * 500));
		sheet1.setColumnWidth(3, (15 * 500));

		File root = new File(Environment.getExternalStorageDirectory(),
				directoryName);
		if (!root.exists()) {
			root.mkdirs();
		}
		File file = new File(root, filename);
		FileOutputStream os = null;
		try {
			if (checkSDCard() && root.canWrite()) {
				os = new FileOutputStream(file);
				wb.write(os);
				operationStatus = true;
			}
		} catch (IOException e) {
			Log.e("Writer", "Could not write XLS file " + e.getMessage());
		}
		return operationStatus;
	}

}
