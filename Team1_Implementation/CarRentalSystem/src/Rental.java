import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rental {
  private LocalDate dateRented,
  returnDate;
  String status;
  private int reservationID;
  private Car car;
  private Customer customer;
  private static int IDincrementer;
  private double cost;
  Inventory inventory = new Inventory();

  // for creating new rental requests.
  public Rental(Customer customer, Car car, LocalDate dateRented, LocalDate returnDate, double cost) {
    setCustomer(customer);
    setCar(car);
    setDateRented(dateRented);
    setReturnDate(returnDate);
    setReservationID(++IDincrementer);
    setStatus("Pending");
    car.setAvailable("Not-Available");
    setCost(cost);
  }

  // for loading rentals requests
  public Rental(Customer customer, String status, int carID, int reservationID, String dateRented, String returnDate, double cost) {
    for (Car car: inventory.getCars()) {
      if (carID == car.getCarID()) {
        setCar(car);
      }
    }
    setCustomer(customer);
    setStatus(status);
    setReservationID(reservationID);
    if (reservationID > IDincrementer) {
      IDincrementer = carID;
    }
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    setReturnDate(LocalDate.parse(returnDate, dtf));
    setDateRented(LocalDate.parse(dateRented, dtf));
    setCost(cost);
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void search() {

}

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public void manageRentalRequest() {

}

  public void cancelRentalRequest() {

}

  public void viewRentalRequest() {

}

  public LocalDate getDateRented() {
    return dateRented;
  }

  public void setDateRented(LocalDate dateRented) {
    this.dateRented = dateRented;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public int getReservationID() {
    return reservationID;
  }

  public void setReservationID(int reservationID) {
    this.reservationID = reservationID;
  }
  public void setIDIncrementer(int reservationID) {
    Rental.IDincrementer = reservationID;
  }
  public double getCost() {
    return this.cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }
  public void cancelReservations(Customer customer, int index, Inventory inventory) {
    customer.getReservations().get(index).setStatus("Canceled");
    int carID = customer.getReservations().get(index).getCar().getCarID();
    for (Car car: inventory.getCars()) {
      if (carID == car.getCarID()) {
        car.setAvailable("Available");
        break;
      }
    }
  }

  @Override
  public String toString() {
    return String.format("%nName: %s %s, Reservation ID: %d, Status: %s\n Car: %s, Car ID: %d\n Color: %s\n Milage: %d, Year: %d\nDate rented: %s\nReturn Date: %s\nprice: %.2f%n", customer.getFirstName(), customer.getLastName(), getReservationID(), getStatus(), car.getName(), car.getCarID(), car.getColor(), car.getMilage(), car.getReleaseYear(), getDateRented(), getReturnDate(), getCost());
  }
}