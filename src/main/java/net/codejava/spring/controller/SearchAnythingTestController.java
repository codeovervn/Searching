package net.codejava.spring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import net.codejava.spring.dao.SearchAnythingTestDAO;
import net.codejava.spring.model.PDFCreator;
import net.codejava.spring.model.PersonInfoDetail;
import net.codejava.spring.model.SearchAnythingTest;

/**
 * This controller routes accesses to the application to the appropriate hanlder methods. 
 * @author Sakekun
 *
 */
@Controller
public class SearchAnythingTestController {

	/** Create */
	// Sakekun ADD 2018/08/18 Start
	@Autowired
	SearchAnythingTestDAO searchAnythingTestDAO;

	/**
	 * Display result from list search.
	 */
	@RequestMapping(value="/collectData")
	public ModelAndView init(ModelAndView model) throws IOException {

		// Get main data list.
		List<SearchAnythingTest> listSearchAnythingTest = searchAnythingTestDAO.list();
		model.addObject("listSearchAnythingTest", listSearchAnythingTest);

		// Get backlog data list.
		List<SearchAnythingTest> listSearchAnythingTestBk = searchAnythingTestDAO.listBk();
		model.addObject("listSearchAnythingTestBk", listSearchAnythingTestBk);
		model.setViewName("SearchAnythingTest");
		SearchAnythingTest searchAnythingTest = new SearchAnythingTest();
		model.addObject("searchAnythingTest", searchAnythingTest);

		return model;
	}

	/**
	 * Save data end user when them to search.
	 */
	@RequestMapping(value = "/saveSearchAnythingTest", method = RequestMethod.POST)
	public ModelAndView saveSearchAnythingTest(@ModelAttribute SearchAnythingTest searchAnythingTest) {
		searchAnythingTestDAO.saveOrUpdate(searchAnythingTest);
		return new ModelAndView("redirect:/collectData");
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/searchContensDisplayTest", method = RequestMethod.GET)
	public ModelAndView searchContensDisplayTest(@ModelAttribute SearchAnythingTest searchAnythingTest, ModelAndView model) {
		List<SearchAnythingTest> searchAnythingTestList = searchAnythingTestDAO.searchContensDisplayTest(searchAnythingTest);
		model.setViewName("SearchAnythingTest");
		model.addObject("searchAnythingTestList", searchAnythingTestList);

		return model;
	}

	/**
	 * Get Data Json and Display to screen.
	 */
	@RequestMapping(value = "/getJsondata", method = RequestMethod.GET)
	public ModelAndView getJsondata(ModelAndView model) {
		List<PersonInfoDetail> jsondataList = searchAnythingTestDAO.getJsonPersonInfo();
		model.setViewName("SearchAnythingTest");
		model.addObject("jsondataList", jsondataList);

		return model;
	}

	/** Defind constant */
	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	// CSV file header
	private static final String FILE_HEADER = "ID,NAME,EMAIL,BIRTHDAY,YEAROLD,JOB,COUNTRY,REMARKS";

	/**
	 * Export CSV Data
	 */
	@RequestMapping(value = "/exportDataCSV", method = RequestMethod.GET)
	public ModelAndView exportDataCSV() throws Exception {

		String strPathFileCsv ="D:\\Projects\\20180801\\TestExportCsv\\TestExportCsv.csv";
		List<PersonInfoDetail> jsonDataExportCsv = searchAnythingTestDAO.getJsonPersonInfo();
		FileWriter fileWriter = new FileWriter(strPathFileCsv);

		// Add header to file.
		fileWriter.append(FILE_HEADER.toString());
		// Add a new line separator after the header
		fileWriter.append(NEW_LINE_SEPARATOR);

		// Write data to file line by line
		for (PersonInfoDetail personInfoDetail : jsonDataExportCsv) {
			fileWriter.append(String.valueOf(personInfoDetail.getID()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(personInfoDetail.getNAME());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(personInfoDetail.getEMAIL());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(personInfoDetail.getBIRTHDAY());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(personInfoDetail.getYEAR_OLD()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(personInfoDetail.getJOB());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(personInfoDetail.getCOUNTRY());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(personInfoDetail.getREMARKS());
			fileWriter.append(NEW_LINE_SEPARATOR);
		}

		// Write data to file.
		fileWriter.flush();
		// After finish write data to file will be closed that file.
		fileWriter.close();

		return new ModelAndView("redirect:/getJsondata");
	}

	/**
	 * Export PDF Data
	 */
	private static final String TITLE = "TestReport";
	public static final String PDF_EXTENSION = ".pdf";
	@RequestMapping(value = "/exportDataPdf", method = RequestMethod.GET)
	public ModelAndView exportDataPdf() throws Exception {

		// Get data Json format from DAO
		List<PersonInfoDetail> jsonDataExportPdf = searchAnythingTestDAO.getJsonPersonInfo();

		Document document = new Document(PageSize.A4);
		HeaderFooter event = new HeaderFooter();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("D:\\Projects\\20180801\\TestExportCsv\\TestExportCsv.pdf")));
		event.setHeader("Test Report");
		writer.setPageEvent(event);

		document.open();
		PDFCreator.addMetaData(document, TITLE);
		PDFCreator.addTitlePage(document, TITLE);
		PDFCreator.addContent(document, jsonDataExportPdf);

		// After write data success will be closed this transaction.
		if(null != document) {
			document.close();
		}

		return new ModelAndView("redirect:/getJsondata");
	}
	// Sakekun ADD 2018/08/18 End
}
