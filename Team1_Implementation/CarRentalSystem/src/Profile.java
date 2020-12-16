import java.io. * ;
import java.util. * ;

public class Profile {
  private ArrayList < Person > people = new ArrayList < >();
  private boolean studentDiscount;
  private boolean signedIn;

  public Profile() {
    try {
      File customersFile = new File("Customers.txt");
      Scanner cScanner = new Scanner(customersFile);
      while (cScanner.hasNext()) {
        people.add(new Customer(cScanner.next(), cScanner.next(), cScanner.next(), cScanner.next(), cScanner.next(), cScanner.nextInt(), cScanner.nextInt(), cScanner.nextInt()));
      }
      cScanner.close();
    } catch(FileNotFoundException exception) {
      System.err.println("Error: customers file does not exist.");
    }
    try {
      File rmFile = new File("RentalManagers.txt");
      Scanner rmScanner = new Scanner(rmFile);
      while (rmScanner.hasNext()) {
        //if (rmScanner.hasNext()) {
        people.add(new RentalManager(rmScanner.next(), rmScanner.next(), rmScanner.next(), rmScanner.next(), rmScanner.next(), rmScanner.nextInt(), rmScanner.nextInt(), rmScanner.nextInt()));
        //}
      }
      rmScanner.close();
    } catch(FileNotFoundException exception) {
      System.err.println("Error: rental managers file does not exist.");
    }
    try {
      File imFile = new File("InventoryManagers.txt");
      Scanner imScanner = new Scanner(imFile);
      while (imScanner.hasNext()) {
        //if (imScanner.hasNext()) {
        people.add(new InventoryManager(imScanner.next(), imScanner.next(), imScanner.next(), imScanner.next(), imScanner.next(), imScanner.nextInt(), imScanner.nextInt(), imScanner.nextInt()));
        //}
      }
      imScanner.close();
    } catch(FileNotFoundException exception) {
      System.err.println("Error: inventory managers file does not exist.");
    }
  }

  public Person signIn(int phoneNumber, String password) {
    if (!people.isEmpty()) {
      for (Person person: people) {
        if (phoneNumber == person.getPhoneNum() && password.equals(person.getPassword())) {
          return person;
        }
      }
    }
    return null;
  }

  public void signOut() {
    System.out.println("Signed out.");
    System.exit(0);
  }

  public void signUp(String firstName, String lastName, String email, String password, String location, int phoneNum, int age, int nationalID) {
    try {
      people.add(new Customer(firstName, lastName, email, password, location, phoneNum, age, nationalID));
    } catch(FileNotFoundException e) {
      System.err.println("Payments file does not exist.");
    }
  }

  public Customer search(int choice) {
    return null;

  }

  public ArrayList < Person > getPersons() {
    return people;
  }

  public void setPeople(ArrayList < Person > persons) {
    this.people = persons;
  }

  public boolean isSignedIn() {
    return signedIn;
  }

  public void manageAccount() {

}

  public void setSignedIn(boolean signedIn) {
    this.signedIn = signedIn;
  }

  public boolean isStudentDiscount() {
    return studentDiscount;
  }

  public void setStudentDiscount(boolean studentDiscount) {
    this.studentDiscount = studentDiscount;
  }
}