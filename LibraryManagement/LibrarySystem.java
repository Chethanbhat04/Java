package LibraryManagement;

import java.util.Scanner;
import java.io.*;


class Book implements Serializable{
    public int sNo;
    public String bookName;
    public String authorName;
    public int bookQty;
    public int bookQtyCopy;

    Scanner input = new Scanner(System.in);

    public Book() {
        System.out.println("Enter Serial No of Book:");
        this.sNo = input.nextInt();
        input.nextLine();

        System.out.println("Enter Book Name:");
        this.bookName = input.nextLine();

        System.out.println("Enter Author Name:");
        this.authorName = input.nextLine();

        System.out.println("Enter Quantity of Books:");
        this.bookQty = input.nextInt();
        bookQtyCopy = this.bookQty;
    }

}

class Student implements Serializable{
    String studentName;
    String regNum;

    Book borrowedBooks[] = new Book[3];
    public int booksCount = 0;

    Scanner input = new Scanner(System.in);

    public Student() {
        System.out.println("Enter Student Name:");
        this.studentName = input.nextLine();

        System.out.println("Enter Registration Number:");
        this.regNum = input.nextLine();
    }
}

class Books implements Serializable{
    private Book[] theBooks = new Book[50];
    private int count;
    private Scanner input = new Scanner(System.in);

    public int compareBookObjects(Book b1, Book b2) {
        if (b1.bookName.equalsIgnoreCase(b2.bookName)) {
            System.out.println("Book of this Name Already Exists.");
            return 0;
        }
        if (b1.sNo == b2.sNo) {
            System.out.println("Book of this Serial No Already Exists.");
            return 0;
        }
        return 1;
    }

    public void addBook(Book b) {
        for (int i = 0; i < count; i++) {
            if (compareBookObjects(b, theBooks[i]) == 0)
                return;
        }
        if (count < 50) {
            theBooks[count] = b;
            count++;
        } else {
            System.out.println("No Space to Add More Books.");
        }
    }

    public void searchBySno() {
        System.out.println("\t\t\t\tSEARCH BY SERIAL NUMBER\n");
        System.out.println("Enter Serial No of Book:");
        int sNo = input.nextInt();
        int flag = 0;
        System.out.println("S.No\t\tName\t\tAuthor\t\tAvailable Qty\t\tTotal Qty");
        for (int i = 0; i < count; i++) {
            if (sNo == theBooks[i].sNo) {
                System.out.println(
                    theBooks[i].sNo + "\t\t"
                    + theBooks[i].bookName + "\t\t"
                    + theBooks[i].authorName + "\t\t"
                    + theBooks[i].bookQtyCopy + "\t\t"
                    + theBooks[i].bookQty);
                flag++;
                return;
            }
        }
        if (flag == 0)
            System.out.println("No Book for Serial No " + sNo + " Found.");
    }

    public void showAllBooks() {
        System.out.println("\t\t\t\tSHOWING ALL BOOKS\n");
        System.out.println("S.No\t\tName\t\tAuthor\t\tAvailable Qty\t\tTotal Qty");
        for (int i = 0; i < count; i++) {
            System.out.println(
                theBooks[i].sNo + "\t\t"
                + theBooks[i].bookName + "\t\t"
                + theBooks[i].authorName + "\t\t"
                + theBooks[i].bookQtyCopy + "\t\t"
                + theBooks[i].bookQty);
        }
    }

    public int isAvailable(int sNo) {
        for (int i = 0; i < count; i++) {
            if (sNo == theBooks[i].sNo) {
                if (theBooks[i].bookQtyCopy > 0) {
                    System.out.println("Book is Available.");
                    return i;
                }
                System.out.println("Book is Unavailable");
                return -1;
            }
        }
        System.out.println("No Book of Serial Number Available in Library.");
        return -1;
    }
}

class Students implements Serializable{
    Scanner input = new Scanner(System.in);
    Student theStudents[] = new Student[50];
    public static int count = 0;

    public void addStudent(Student s) {
        for (int i = 0; i < count; i++) {
            if (s.regNum.equalsIgnoreCase(theStudents[i].regNum)) {
                System.out.println("Student of Reg Num " + s.regNum + " is Already Registered.");
                return;
            }
        }

        if (count <= 50) {
            theStudents[count] = s;
            count++;
        }
    }

    public void showAllStudents() {
        System.out.println("Student Name\t\tReg Number");
        for (int i = 0; i < count; i++) {
            System.out.println(theStudents[i].studentName + "\t\t" + theStudents[i].regNum);
        }
    }

    public int isStudent() {
        System.out.println("Enter Reg Number:");
        String regNum = input.nextLine();

        for (int i = 0; i < count; i++) {
            if (theStudents[i].regNum.equalsIgnoreCase(regNum)) {
                return i;
            }
        }

        System.out.println("Student is not Registered.");
        System.out.println("Get Registered First.");
        return -1;
    }
}

class DataHandler implements Serializable{
    private static final String FILE_NAME = "LibraryData.dat";

    public static void saveData(Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}


public class LibrarySystem {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Books books = new Books();
            Students students = new Students();

            Books savedBooks = (Books) DataHandler.loadData();
            if (savedBooks != null) {
                books = savedBooks;
                System.out.println("Existing data loaded.");
            }

            int choice;

            do {
                System.out.println(" ");
                System.out.println("Library System Menu:");
                System.out.println("1. Add Student");
                System.out.println("2. Add Book");
                System.out.println("3. Search Book by Serial Number");
                System.out.println("4. Show All Books");
                System.out.println("5. Show All Students");
                System.out.println("0. Exit");

                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();  // Consume the newline

                switch (choice) {
                    case 1:
                        Student newStudent = new Student();
                        students.addStudent(newStudent);
                        break;
                    case 2:
                        Book newBook = new Book();
                        books.addBook(newBook);
                        break;
                    case 3:
                        books.searchBySno();
                        break;
                    case 4:
                        books.showAllBooks();
                        break;
                    case 5:
                        students.showAllStudents();
                        break;
                    case 0:
                        // Save data before exiting
                        DataHandler.saveData(books);
                        System.out.println("Exiting Library System.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 0);
        }
    }

}
