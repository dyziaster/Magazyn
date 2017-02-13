package Model;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class JTable2Pdf extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = -4220791480343841284L;

@SuppressWarnings("deprecation")
public static void print(JTable table) {
    Document doc = new Document();
    try {
        PdfWriter.getInstance(doc, new FileOutputStream("table.pdf"));
        doc.open();
        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        //adding table headers
        for (int i = 0; i < table.getColumnCount(); i++) {
            pdfTable.addCell(table.getColumnName(i));
        }
        //extracting data from the JTable and inserting it to PdfPTable
        for (int rows = 0; rows < table.getRowCount() - 1; rows++) {
            for (int cols = 0; cols < table.getColumnCount(); cols++) {
                pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());

            }
        }
        doc.add(pdfTable);
        doc.close();
        System.out.println("done");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    doc.close();
  }
}