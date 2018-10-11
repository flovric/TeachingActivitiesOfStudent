package hr.fer.view;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import hr.fer.models.AcademicYear;
import hr.fer.models.Activity;
import hr.fer.models.SubjectTemp;
import hr.fer.models.User;
 
 
 
public class ExcelReportView extends AbstractXlsView {
 
	 @Override
	 protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
	 HttpServletResponse response) throws Exception {
	  
		 
		 
		 List<Activity> activities = (List<Activity>) model.get("activities");
		 int suma = (int) model.get("suma");
		 User user = (User) model.get("student");
		 SubjectTemp subject = (SubjectTemp) model.get("subject");
		 AcademicYear year = (AcademicYear) model.get("year");
		 
		 String fileName = subject.getName() +", "+subject.getCourse() +", " 
		 +year.getSemester() +" "+year.getAcademicYear();
		 
		 
		 response.setContentType("application/ms-excel; charset=UTF-8");
		 response.setCharacterEncoding("UTF-8");
		 response.setHeader("Content-Disposition", "attachment;filename=\""+ fileName+ ".xls\"");
		 
		 Sheet sheet = workbook.createSheet(user.getName() +" "+ user.getLastName());
		 Row header = sheet.createRow(0);
		 header.createCell(0).setCellValue("Opis aktivnosti");
		 header.createCell(1).setCellValue("Trajanje (u satima)");
		 header.createCell(2).setCellValue("Vrijeme nastanka");
		 header.createCell(4).setCellValue("SUMA (u satima)");
		  
		 int rowNum = 1;
		 
		 for(Activity activity : activities){
			 Row row = sheet.createRow(rowNum++);
			 row.createCell(0).setCellValue(activity.getText());
			 row.createCell(1).setCellValue(activity.getDuration());
			 row.createCell(2).setCellValue(activity.getCreationTime().toString());
			 
			 if(rowNum == 2)
				 row.createCell(4).setCellValue(suma);	 
		 }
	 }
}