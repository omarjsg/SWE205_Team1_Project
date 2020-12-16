import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Person {
  private Payment payment;
  private Profile profile;
  private ArrayList < Rental > reservations = new ArrayList < >();

  public Customer(String firstName, String lastName, String email, String password, String location, int phoneNum, int age, int nationalID) throws FileNotFoundException {
    super(firstName, lastName, email, password, location, phoneNum, age, nationalID);
    payment = new Payment();
	
	//to load the payment cards.
	File paymentsFile = new File("Payments.txt");
    Scanner pScanner = new Scanner(paymentsFile);
    while (pScanner.hasNext()) {
      String name = pScanner.next();
      String expDate = pScanner.next();
      long cardNum = pScanner.nextLong();
      int cvv = pScanner.nextInt();
      int cardPhoneNum = pScanner.nextInt();
      if (cardPhoneNum == getPhoneNum()) { // it will add the payment card that have the same phone number.
        payment.addPayment(name, expDate, cardNum, cvv, cardPhoneNum);
      }
	}
	

	//to load the reservations
    pScanner.close();
    File reservationsFile = new File("Rentals.txt"); 
    Scanner rScanner = new Scanner(reservationsFile);
    while (rScanner.hasNext()) {
      int i = 0;
      String status = rScanner.next();
      int carID = rScanner.nextInt();
      int reservationID = rScanner.nextInt();
      int phoneNumber = rScanner.nextInt();
      String dateRented = rScanner.next();
      String returnDate = rScanner.next();
      double cost = rScanner.nextDouble();
      if (getReservations().size() > 0 && getPhoneNum() == phoneNumber) { // it will add the reservations that have the same phone number.
        if (reservationID != getReservations().get(i).getReservationID()) {
          getReservations().add(new Rental(this, status, carID, reservationID, dateRented, returnDate, cost));
          getReservations().get(getReservations().size() - 1).setIDIncrementer(reservationID);
        }
      } else if (getReservations().size() == 0 && getPhoneNum() == phoneNumber) {
        getReservations().add(new Rental(this, status, carID, reservationID, dateRented, returnDate, cost));
      }
      i++;
    }
    rScanner.close();
  }

  public ArrayList < Rental > getReservations() {
    return reservations;
  }

  public void setReservations(ArrayList < Rental > reservations) {
    this.reservations = reservations;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Object clone() throws CloneNotSupportedException {
    Customer copy = (Customer) super.clone();

    return copy;
  }

}