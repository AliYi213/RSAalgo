import javax.xml.transform.sax.SAXResult;
import java.util.List;

public class Student {
    List <Subject> subjects;
    private String constructSubject(){
        String results = "";
        for (Subject course : subjects){
        results += subjects;
        }
        return results;
    };
    private int studentId;
    private String name;
    private String lastName;
    private Account account;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("\t\tStudent \n" +
        "\tName %s\n" +
                "\tLastname %s\n" +
                "\tCode %s\n" +
                "\tAccount %s\n" +
                "\t\nSubject %s\n",
                this.name, this.lastName, this.studentId, this.account, this.constructSubject());

    }
}
