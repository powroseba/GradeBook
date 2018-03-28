package com.pdfsender.pdf;

import com.domain.ExerciseAndGrades;
import com.entities.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.repositories.UserModelRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PDFGenerator {

    private UserModelRepository userModelRepository;

    public PDFGenerator(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    public byte[] createPDF(ExerciseAndGrades exerciseAndGrades) {

        Student student = (Student) userModelRepository.findByEmail(exerciseAndGrades.getEmail()).get().getUserModelDetails();
        Document document = new Document();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();

            PdfPTable tableOne = new PdfPTable(new float[]{2,2});
            tableOne.setSpacingAfter(20f);

            String firstName = student.getFirstName();
            String lastName  = student.getLastName();
            String schoolClass = student.getSchoolClass().getName();
            PdfPCell cellLeft = new PdfPCell(new Paragraph("First and Last Name: " + firstName + " " + lastName+",\n" +
                    "School Class: " + schoolClass));
            cellLeft.setColspan(1);
            cellLeft.setBorder(0);


            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            PdfPCell cellRight = new PdfPCell(new Paragraph(date));
            cellRight.setColspan(1);
            cellRight.setBorder(0);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);


            tableOne.addCell(cellLeft);
            tableOne.addCell(cellRight);

            PdfPTable table = new PdfPTable(new float[]{2,4});

            PdfPCell cell = new PdfPCell(new Paragraph("Oceny"));
            cell.setColspan(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(206, 206, 206));
            cell.setPadding(5f);
            table.addCell(cell);

            exerciseAndGrades.getExerciseAndGradeList().forEach((exercises, integers) -> {
                table.addCell(exercises.name().toLowerCase());
                table.addCell(integers.toString().replace("[", "").replace("]",""));
            });

            document.add(tableOne);
            document.add(table);



            document.close();
            writer.close();

            return baos.toByteArray();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
