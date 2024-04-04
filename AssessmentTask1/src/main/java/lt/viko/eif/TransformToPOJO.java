package lt.viko.eif;
import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
public class PojoConverter {
        public static void convertXmlToPojo() {
        try {
            String xmlFilePath = "student.xml";

            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Student student = (Student) unmarshaller.unmarshal(new File(xmlFilePath));

            System.out.println("Name: " + student.getName());
            System.out.println("Student ID: " + student.getStudentId());

//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            marshaller.marshal(student, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
