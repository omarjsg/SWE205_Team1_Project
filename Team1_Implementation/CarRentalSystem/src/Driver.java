import java.io. * ;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util. * ;

public class Driver {
  static Profile profile = new Profile();
  static Inventory inventory = new Inventory();

  public static void main(String[] args) throws FileNotFoundException {
    Scanner KB = new Scanner(System. in );
    int startChoice = 0,
    carId = 0,
    goToPay = 0,
    searchChoice = 0,
    customerMenuChoice = 0,
    rentalManagerMenuChoice = 0,
    paymentCoice = 0,
    brandChoice = 0;
    do {
      //Block#1 loggin in: 
      try {
        System.out.println("\nEnter: \n\'1\' For Sign-in.\n\'2\' For Sign-up.\n\'3\' For Exit.");
        startChoice = KB.nextInt();
        switch (startChoice) {
          //Block#2 Sign in
        case 1:
          {
            String password = "";
            String phoneNumber;
            LocalDate dateRented,
            returnDate = LocalDate.of(1, 1, 1);
            // to repeat asking the sign in information.
            do {
              try {
                System.out.println("\nEnter your phone number:");
                phoneNumber = KB.next();
                if (numberOrNot(phoneNumber) && (phoneNumber.length() == 10)) { // to check if the phone number is valid.
                  break;
                } else {
                  throw new IllegalArgumentException();
                }
              } catch(IllegalArgumentException exception) {
                System.out.println("\nYou have entered invalid mobile number. Try again...");
              }
            } while ( true );
            System.out.println("\nEnter your password:");
            password = KB.next().trim();
            Person person = profile.signIn(Integer.parseInt(phoneNumber), password); // to sign , return of type person.

            //Block#3 customer sign in.
            if (person instanceof Customer) {
              Customer customer = (Customer) person; // to use the customer methods.
              System.out.println("Welcome " + customer.getFirstName() + " " + customer.getLastName());
              do { //Start to ask the costumer what he want to do
                try {
                  System.out.println("\nEnter:\n\'1\'- To rent a car.\n\'2\'- To view reservetions.\n\'3\'- To manage the account.\n\'4\'- To manage the payments.\n\'5\'- Sign out.\n");
                  customerMenuChoice = KB.nextInt();
                  //Block#4 main menu selection.
                  switch (customerMenuChoice) {

					//Block#5 rent a car menu. 
                  case 1:
                    {
                      System.out.println("\nEnter:- \n\'1\'- To Search by the brand.\n\'2\'- To Search by the type.\n\'3\'- To Search by the car's name." + "\n\'4\'- To Search by the price.\n");
                      searchChoice = KB.nextInt();
                      boolean search = inventory.search(searchChoice); //then the code will be continued in inventory search meathod.

                      if (search) {
                        boolean validCar = true;
                        System.out.println("Enter the car id");
                        carId = KB.nextInt();
                        for (Car car: inventory.getCars()) {
                          if (car.getCarID() == carId && car.getAvailable().equals("Available")) { // to check if the car is available to rent.
                            System.out.println("Selected car:" + car); //will show the selected car.
                            do {
                              try {
                                System.out.println("Enter the rent date day: \n");
                                int d = Integer.parseInt(KB.next());
                                System.out.println("Enter the rent date month: \n");
                                int m = Integer.parseInt(KB.next());
                                System.out.println("Enter the rent date year: \n");
                                int y = Integer.parseInt(KB.next());
                                dateRented = LocalDate.of(y, m, d); // generate a LocalDate object of the rent date.
                                break;
                              } catch(DateTimeException exception) {
                                System.out.println("Wrong date format. accepted format: (DD/ MM/ YYYY)");
                              }
                            } while ( true );
                            System.out.println("Enter return date:");
                            do {
                              try {
                                System.out.println("Enter the return date day: "); //prompt the return date day.
                                int d = Integer.parseInt(KB.next());
                                System.out.println("Enter the return date month: "); //prompt the return date month.
                                int m = Integer.parseInt(KB.next());
                                System.out.println("Enter the return date year: "); //prompt the return date year.
                                int y = Integer.parseInt(KB.next());
								returnDate = LocalDate.of(y, m, d); // generate a LocalDate object of the return date.
								
                                if (ChronoUnit.MONTHS.between(dateRented, returnDate) > 0) {
                                  double cost = inventory.calculateCost(car, dateRented, returnDate); // to get th cost of the rent for a specifec duration.
                                  System.out.println("Do you want to continue to pay?");
                                  while (goToPay != 1 && goToPay != 2) {
                                    System.out.println("Enter \'1\' For Yes or \'2\' For No");
									goToPay = KB.nextInt();
									//Block#6 confirm purchase.
                                    switch (goToPay) {  
                                    case 1:
                                      {

										  //if there is no payment method it will not cancel the reservation.
                                        if (customer.getPayment().getPaymentList().size() == 0) { 
                                          System.out.println("No payment method found, please add one.");
										  break;

										  // If there is one at least it will continue purchasing.
                                        } else { 
                                          System.out.println("Car has been rented.\n");
                                          customer.getReservations().add(new Rental(customer, car, dateRented, returnDate, cost));
                                          System.out.println("Reservation:\n" + customer.getReservations().get(customer.getReservations().size() - 1).toString());
                                          break;
                                        }
									  }
									  
									  //this will be activated if the costumer choose to not to pay.
                                    case 2:
                                      { 
                                        System.out.println("Your reservation has been canceled.");
                                        break;
                                      }
                                    default:
                                      System.out.println("You should choose either \'1\' or \'2\'\n");
                                      break;
                                    }
                                  }
                                  goToPay = 0;
                                } else {
                                  System.out.println("Invalid date input.");
                                }
                                break;
                              } catch(DateTimeException exception) {
                                System.out.println("Wrong date format. (DD/ MM/ YYYY)");
                              }

							} while ( true );
							
							//If the car is not available it will inform the customer that it is already rented.
                          } else if (carId == car.getCarID() && car.getAvailable().equalsIgnoreCase("Not-Available")) {
                            System.out.println("Car has been already rented.");
                          } else {
                            validCar = false; // It will get out of the loop if it the car is not available.
                          }
                        }
                        if (validCar) {
                          System.out.println("Car ID does not exist.");
                          validCar = true;
                        }
						break;
					  } 
					  //if there no such cars in the selected data.
					  else {
                        System.out.println("No cars found with the entered data.");
                      }
                      break;
					}
					//Block#5 ends here.
					
					//Block#6 view reservation menu
					/*
					there are two menues for the view reservation block. The first one will be excuted if there are previous
					reservation registered, and if there are no reservations registered, the second one will be excuted.
					*/

                  case 2:
                    { //this will be activated when the user choose to view reservetions.
                      boolean notFound = false,
                      validDate = false,
					  canceled = false;
                      if (customer.getReservations().size() > 0) { // first menu
                        System.out.println("Reservations list:");
                        for (Rental reservation: customer.getReservations()) { // to print the list of the reservations.
                          System.out.println(reservation);
                        }
                        System.out.println("\nEnter:\n\'1\' manage reservation.\n\'2\' Cancel reservetions.\n\'3\' Back.\n");
                        switch (KB.nextInt()) {

							//Block#7 manage reservation.
							/*
							In this part the user will change the return date. The cost of change will be as following:-
							- If the reservation is already canceled it will tell the customer that the reservation is already canceled
							and he cannot modify it.
							- If the customer wants to shorten the return date it will reduce the cost of the reservation.
							- If the customer wants to extend the return date it will increase the cost.
							*/
                        case 1:
                          {
                            System.out.println("Enter reservation id:\n");
                            int reservationID = KB.nextInt();
                            int rentalIndex = 0;
                            LocalDate newDate;
                            for (Rental reservation: customer.getReservations()) { // it will display the Pending and Accepted reservations.
                              if (reservationID == reservation.getReservationID() && !(reservation.getStatus().equalsIgnoreCase("Canceled"))) {
                                rentalIndex = customer.getReservations().indexOf(reservation);
                                do {
                                  try {
                                    System.out.println("Enter the new return date day: \n"); //it will prompt customer to enter the new return date day.
                                    int d = Integer.parseInt(KB.next());
                                    System.out.println("Enter the  new return date month: \n"); //it will prompt customer to enter the new return date month.
                                    int m = Integer.parseInt(KB.next());
                                    System.out.println("Enter the new return date year: \n"); //it will prompt customer to enter the new return date year.
                                    int y = Integer.parseInt(KB.next());
                                    newDate = LocalDate.of(y, m, d);
                                    if (ChronoUnit.MONTHS.between(customer.getReservations().get(rentalIndex).getReturnDate(), newDate) > 0 && ChronoUnit.MONTHS.between(customer.getReservations().get(rentalIndex).getDateRented(), newDate) > 0) { // extending the return date.
                                      double cost = inventory.calculateCost(
                                      customer.getReservations().get(rentalIndex).getCar(), customer.getReservations().get(rentalIndex).getReturnDate(), newDate); // Change the date in the reservation.
                                      customer.getReservations().get(rentalIndex).setCost(customer.getReservations().get(rentalIndex).getCost() + cost); // Change the cost in the reservation.
                                      customer.getReservations().get(rentalIndex).setReturnDate(newDate);
                                      System.out.println(
                                      customer.getReservations().get(rentalIndex));
                                      validDate = true;
                                    } else if (ChronoUnit.MONTHS.between(customer.getReservations().get(rentalIndex).getReturnDate(), newDate) < 0 && ChronoUnit.MONTHS.between(customer.getReservations().get(rentalIndex).getDateRented(), newDate) > 0) { // shortening the return date.
                                      double cost = inventory.calculateCost(
                                      customer.getReservations().get(rentalIndex).getCar(), customer.getReservations().get(rentalIndex).getReturnDate(), newDate); // Change the date in the reservation.
                                      customer.getReservations().get(rentalIndex).setCost((customer.getReservations().get(rentalIndex).getCost()) - cost);  // Change the cost in the reservation.
                                      customer.getReservations().get(rentalIndex).setReturnDate(newDate);
                                      System.out.println(
                                      customer.getReservations().get(rentalIndex));
                                      validDate = true;
                                    } else if (ChronoUnit.MONTHS.between(customer.getReservations().get(rentalIndex).getReturnDate(), newDate) == 0 || ChronoUnit.MONTHS.between(customer.getReservations().get(rentalIndex).getDateRented(), newDate) <= 0) { // it will be excuted if the return date came before or on the same day of the rent day. 
                                      System.out.println("Invalid date input.");
                                    }
                                  } catch(DateTimeException exception) {
                                    System.out.println("Wrong date format. (DD/ MM/ YYYY)\n");
                                  }
                                } while (! validDate );
                              } else if (reservationID == reservation.getReservationID() && reservation.getStatus().equalsIgnoreCase("Canceled")) { //it will not allow the customer to cancel the canceled reservation.
                                System.out.println("Cannot manage a canceled reservation.");
                                break;
                              } else {
                                notFound = true;
                              }
                            }
                            if (notFound && !validDate) {
                              System.out.println("Reservation with entered ID does not exist");
                            }
                            notFound = false;
                            validDate = false;
                            break;
						  }
						  //Block#7 ends here.

						  //Block#8 Canceling reservation.
                        case 2:
                          { //this will be activated when the user choose to cancel reservetions
                            System.out.println("Enter reservation id: \n");
                            int reservationID = KB.nextInt(); // it will prompt the user to enter the reservation ID.
                            int rentalIndex = 0;
                            for (Rental reservation: customer.getReservations()) { // it will display the Pending and Accepted reservations.
                              if (reservationID == reservation.getReservationID() && !(reservation.getStatus().equalsIgnoreCase("Canceled"))) {
                                rentalIndex = customer.getReservations().indexOf(reservation);
                                if (!reservation.getStatus().equalsIgnoreCase("Canceled")) { //If the reservation is not canceled canceled it will cancel it.
                                  reservation.cancelReservations(customer, rentalIndex, inventory);
                                  System.out.println(customer.getReservations().get(rentalIndex)); 
                                  canceled = true;
                                  break;
                                }
                              } else if (reservationID == reservation.getReservationID() && reservation.getStatus().equalsIgnoreCase("Canceled")) {
                                System.out.println("Cannot cancel a canceled reservation.");
                                break;
                              } else {
                                notFound = true;
                              }
                            }
                            if (notFound && !canceled) {
                              System.out.println("Reservation with entered ID does not exist");
                              break;
                            }
                            notFound = false;
                            canceled = false;
                            break;
						  }
						  //Block#8 ends here.

						  //Block#9 Back to the customer main menu.
                        case 3:
                          { //this will be activated when the user choose to go back
                            break;
						  }
						  //Block#9 ends here.
						}
						//If the reservation id entered does not exist.
                      } else {
                        System.out.println("No reservations are registered.");
                        break;
                      }
                      break;
					}
					//Block#6 ends here.


					//Block#10 manage account.
                  case 3:
                    { //this will be activated when the user choose to manage his account personal data.
                      boolean isDone = false;
                      do {
                        try {
                          System.out.println("Manage Account");
                          System.out.println("\nEnter:\n\'1\' To edit the first name.\n\'2\' To edit the last name." + "\n\'3\' To edit the email.\n\'4\' To edit the password.\n\'5\' To edit the phone number." + "\n\'6\' To edit the age.\n\'7\' To edit the national ID.\n\'8\' To edit the location.\n\'9\' To go back.\n");
						  int whatToEdit = KB.nextInt();
						  
						  //Block#11 edit first name.
                          if (whatToEdit == 1) {
                            System.out.println("Enter the new first name:\n");
                            String firstName = KB.next().trim();
                            customer.setFirstName(firstName);
                            System.out.println("The first name has been changed.\n");
                            isDone = true;
						  }
						  //Block#11 ends here.

						  //Block#12 edit last name.
						  else if (whatToEdit == 2) {
                            System.out.println("Enter the new last name:\n");
                            String lastName = KB.next().trim();
                            customer.setLastName(lastName);
                            System.out.println("The last name has been changed.\n");
							isDone = true;
						  } 
						  //Block#12 ends here.
						 
						  //Block#13 edit email.
						  else if (whatToEdit == 3) {
                            do {
                              try {
                                System.out.println("Enter the new email:\n");
                                String email = KB.next().trim();
                                if (isValidEmail(email)) {
                                  customer.setEmail(email);
                                  break;
                                }
                                System.out.println("Invalid email. Try again...\\n");
                              } catch(InputMismatchException exception) {
                                System.out.println("Invalid email. Try again...\n");
                              }
                            } while ( true );
                            System.out.println("The email has been changed.\n");
                            isDone = true;
						  } 
						  //Block#13 ends here.
						 

						  //Block#14 edit password.
						  /*
						  - The password and the verfication password should match.
						- isValid method will be excuted.
						  */
						  else if (whatToEdit == 4) { 
                            do {
                              try {

                                System.out.println("Enter the new password: \n");
                                String newPassword = KB.next();

                                boolean validPassword = isValidPassword(newPassword);
                                if (!validPassword) {
                                  throw new IllegalArgumentException();
                                }
                                System.out.println("verify your password: \n");
                                String password2 = KB.next();
                                if (newPassword.equals(password2)) {
                                  System.out.println(newPassword);
                                  customer.setPassword(newPassword);

                                  break;
                                } else if (! (newPassword == password2)) {
                                  System.out.println("password does not match try again\n");

                                }

                              } catch(IllegalArgumentException e) {
                                System.out.println("password is not valid\n");
                              }
                            } while ( true );
                            System.out.println("The password has been changed.\n");
                            isDone = true;
						  }
						  //Block#14 ends here.
						 

						  //Block#15 edit phone number.
						  else if (whatToEdit == 5) {
                            System.out.println("Enter the new phone number:\n");
                            do {
                              try {
                                boolean matched = false;
                                System.out.println("Enter the new phone number: for example: 0555005500\n");
                                phoneNumber = KB.next();
                                if (numberOrNot(phoneNumber) && (phoneNumber.length() == 10)) { //it will check if the entered number contains 10-digits without any letters.
                                  for (int index = 0; index < profile.getPersons().size(); index++) {
                                    if (profile.getPersons().get(index).getPhoneNum() == Integer.parseInt(phoneNumber) && Integer.parseInt(phoneNumber) != customer.getPhoneNum()) { //it will check if the phone number is registered in another account
                                      matched = true;
                                    }
                                  }
                                  if (!matched) {
                                    customer.setPhoneNum(Integer.parseInt(phoneNumber)); // if the phone number is not matched with any phone number it will be changed.
                                    matched = false;
                                    break;
                                  }
                                  System.out.println("Phone number entered has been already registred in system.");
                                } else {
                                  throw new IllegalArgumentException();
                                }
                              } catch(IllegalArgumentException exception) {
                                System.out.println("You have entered an invalid phone number. Try again...\n");
                              }
                            } while ( true );
                            System.out.println("The phone number has been changed.\n");
                            isDone = true;
						  }
						  //Block#15 ends here.
						 
						  //Block#16 edit age.
						  else if (whatToEdit == 6) {
                            do {
                              try {
                                System.out.println("Enter your age: \n");
                                int age = KB.nextInt();
                                if (age >= 18 && age <= 80) { //it will assaign the age if it is between 18 and 80.
                                  customer.setAge(age);
                                  break;
                                }
                                System.out.println("Age should be between 18 and 80");
                              } catch(InputMismatchException exception) {
                                System.out.println("Invalid age entry. Try again...\n");
                              }
                            } while ( true );
                            System.out.println("The age has been changed.\n");
                            isDone = true;
						  }
						  //Block#16 ends here.
						 

						  //Block#17 edit national ID
						  else if (whatToEdit == 7) { 
                            System.out.println("Enter the new national ID:\n");
                            do {
                              try {
                                boolean matched = false;
                                System.out.println("Enter your nation ID: \n");
                                String nationalID = KB.next();
                                if (String.valueOf(nationalID).length() == 10) { //
                                  for (int index = 0; index < profile.getPersons().size(); index++) {
                                    if (profile.getPersons().get(index).getNationalID() == Integer.parseInt(nationalID)) {
                                      matched = true;
                                    }
                                  }
                                  if (!matched) {
                                    customer.setNationalID(Integer.parseInt(nationalID));
                                    matched = false;
                                    break;
                                  }
                                  System.out.println("National ID entered has been already registred in system.");
                                } else {
                                  throw new IllegalArgumentException();
                                }
                              } catch(IllegalArgumentException exception) {
                                System.out.println("You have entered an invalid national ID. Try again...\n");
                              }
                            } while ( true );
                            System.out.println("The national ID has been changed.\n");
                            isDone = true;
						  } 
						  //Block#17 ends here.
						 
						  //Block#18 will be activated when the user choose to edit the location.
						  else if (whatToEdit == 8) { 
                            for (int i = 0; i < inventory.getLocations().size(); i++) { // it will show the list of the locations
                              System.out.printf("\'%d\'  - %s \n", i + 1, inventory.getLocations().get(i));
                            }
                            int locationNum;
                            do {
                              try {
                                System.out.println("Enter the location number:");
                                locationNum = KB.nextInt() - 1; // the customer will select a location.
                                if ((locationNum >= 0) && (locationNum < inventory.getLocations().size())) {
                                  System.out.println("Location selected: " + inventory.getLocations().get(locationNum));
                                  customer.setLocation(
                                  inventory.getLocations().get(locationNum));
                                  break;
                                }
                                System.out.println("You should choose betweeen \'1\' - \'" + inventory.getLocations().size() + "\'\n");
                              } catch(InputMismatchException exception) {
                                System.out.println("Invalid Input");
                              }
                            } while ( true );
						  } 
						  //Block#18 ends here.

						  //Block19 will be activated when the user choose to go back to the customer main menu.
						  else if (whatToEdit == 9) {
                            isDone = true;
						  }
						  //Block#19 ends here.
						 
						  else {
                            System.out.println("You should choose a number from \'1\' to \'9\'.\n");
                          }
                        } catch(InputMismatchException exception) {
                          System.out.println("Invalid Input");
                          KB.nextLine();
                        }
                      } while (! isDone );
                      break;
					}
					//Block#10 ends here.


					//Block#20 will be activated when the user choose to manage the payments.
					/*
					This part has two scenarios:
					1- if there are no payment card available: -
						-it will ask the customer to add apayment method or to return back to the customer main menu.
					2- if there is at least one payment card it will display all the payment cards will be displayed.
						- it will ask the customer to add a payment card, remove a payment card or to return back to the customer main menu.
					*/
                  case 4:
                    { 
                      System.out.println("Manage Payments: \n");
                      if (customer.getPayment().getPaymentList().size() > 0) { // if there is at least one payment card. 
                        do {
                          try {
                            System.out.println("Payment cards list: \n");
                            for (int index = 0; index < customer.getPayment().getPaymentList().size(); index++) { // display all the card list.
                              System.out.println((index + 1) + "- " + customer.getPayment().getPaymentList().get(index));
                            }
                            System.out.println("\nEnter:\n\'1\' Add payment card.\n\'2\' remove payment card.\n\'3\' Back.\n");
                            paymentCoice = KB.nextInt();
                            switch (paymentCoice) {

							//Block#21 add payment
                            case 1:
                              {
                                do {
                                  LocalDate expDate;
                                  try {
                                    System.out.println("Fill payment card information:\n");
                                    System.out.println("Enter the first name\n");
                                    String name = KB.next();
                                    if (!name.equalsIgnoreCase(customer.getFirstName())) { //to check if the card owner has the same name of the customer.
                                      throw new IllegalArgumentException();
                                    }
                                    System.out.println("Name: " + name);
                                    do {
                                      try {
                                        System.out.println("Enter expiration day: \n");
                                        int d = Integer.parseInt(KB.next());
                                        System.out.println("Enter the rexpiration month: \n");
                                        int m = Integer.parseInt(KB.next());
                                        System.out.println("Enter the expiration year: \n");
                                        int y = Integer.parseInt(KB.next());
                                        expDate = LocalDate.of(y, m, d);
                                        System.out.println("Expiration date: " + expDate);
                                        break;
                                      } catch(DateTimeException exception) {
                                        System.out.println("Wrong date format. accepted format: (DD/ MM/ YYYY)\n");
                                      }
                                    } while ( true );
                                    System.out.println("Enter the card number: (16-digits)\n");
                                    long cardNumber = KB.nextLong();
                                    if (String.valueOf(cardNumber).length() != 16) { //to check if the card number contains only 16-digits.
                                      throw new IllegalArgumentException();
                                    }
                                    System.out.println("Enter the security number: (3-digits)\n"); 
                                    int cvv = KB.nextInt();
                                    if (String.valueOf(cvv).length() != 3) {  //to check if the security number contains only 16-digits.
                                      throw new IllegalArgumentException();
                                    }
                                    customer.getPayment().addPayment(name, expDate.toString(), cardNumber, cvv, customer.getPhoneNum()); // assing payment card.
                                    break;
                                  } catch(InputMismatchException exception) {
                                    System.out.println("Data entered format is not correct.");
                                  } catch(IllegalArgumentException exception) {
                                    System.out.println("Data entered format is not correct.");
                                  }
                                } while ( true );
                                break;
							  }
							  //Block#21 ends here.


							  //Block#22 will be activated when the user choose to remove his payment card.
                            case 2:
                              {
                                System.out.println("Remove payment card: \n");

                                do {
                                  try {
                                    System.out.println("Select the card: \n");
                                    int selectedCard = KB.nextInt() - 1;
                                    if ((selectedCard >= 0) && (selectedCard < customer.getPayment().getPaymentList().size())) {
                                      System.out.println("Selected card with: " + customer.getPayment().getPaymentList().get(selectedCard) + " has been removed.");
									  customer.getPayment().remove(selectedCard);

									  //if the payments list is empty will return back to the customer main menu.
                                      if (customer.getPayment().getPaymentList().size() == 0) {
                                        paymentCoice = 3;
                                      }
                                      break;
                                    }
                                    System.out.println("You should choose betweeen \'1\' - \'" + inventory.getLocations().size() + "\'\n");
                                  } catch(InputMismatchException exception) {
                                    System.out.println("Invalid Input");
                                  }
                                } while ( true );
                                break;
							  }
							  //Block#22 ends here.

							  //Block#23 will be activated when the user choose to go back to the customer main menu.
                            case 3:
                              break;
							}
							//Block#23 ends here.


                          } catch(InputMismatchException exception) {
                            System.out.println("Invalid Input");
                            KB.nextLine();
                          }
                        } while ( paymentCoice != 3 );
                        break;
					  } 
					  //If there is no payment card registered.
					  else {
                        do {
                          try {
                            System.out.println("No payment methods exist.\n");
                            System.out.println("\nEnter:\n\'1\' Add payment card.\n\'2\' Back.\n");
                            paymentCoice = KB.nextInt();
                            switch (paymentCoice) {

							//Block#24 adding payment card.
                            case 1:
                              {
                                do {
                                  LocalDate expDate;
                                  try {
                                    System.out.println("Fill payment card information:\n");
                                    System.out.println("Enter the first name");
                                    String name = KB.next();
                                    if (!name.equalsIgnoreCase(customer.getFirstName())) {
                                      throw new IllegalArgumentException();
                                    }
                                    System.out.println("Name: " + name);
                                    do {
                                      try {
                                        System.out.println("Enter expiration day: \n");
                                        int d = Integer.parseInt(KB.next());
                                        System.out.println("Enter the rexpiration month: \n");
                                        int m = Integer.parseInt(KB.next());
                                        System.out.println("Enter the expiration year: \n");
                                        int y = Integer.parseInt(KB.next());
                                        expDate = LocalDate.of(y, m, d);
                                        System.out.println("Expiration date: " + expDate);
                                        break;
                                      } catch(DateTimeException exception) {
                                        System.out.println("Wrong date format. accepted format: (DD/ MM/ YYYY)");
                                      }
                                    } while ( true );
                                    System.out.println("Enter the card number: (16-digits)\n");
                                    long cardNumber = KB.nextLong();
                                    if (String.valueOf(cardNumber).length() != 16) {
                                      throw new IllegalArgumentException();
                                    }
                                    System.out.println("Enter the security number: (3-digits)\n");
                                    int cvv = KB.nextInt();
                                    if (String.valueOf(cvv).length() != 3) {
                                      throw new IllegalArgumentException();
                                    }
                                    customer.getPayment().addPayment(name, expDate.toString(), cardNumber, cvv, customer.getPhoneNum());
                                    paymentCoice = 2;
                                    break;
                                  } catch(InputMismatchException exception) {
                                    System.out.println("Data entered format is not correct.");
                                  } catch(IllegalArgumentException exception) {
                                    System.out.println("Data entered format is not correct.");
                                  }
                                } while ( true );
                                break;
							  }
							  //Block#24 ends here

							  //Block#25 will be activated when the user choose to go back to customer main menu.
                            case 2:
                              {
                                break;
							  }
							  //Block#25 ends here.


                            default:
                              {
                                System.out.println("You should choose either \'1\' or \'2\'\n");
                                break;
                              }
                            }
                          } catch(InputMismatchException exception) {
                            System.out.println("Invalid Input");
                            KB.nextLine();

                          }
                        } while ( paymentCoice != 2 );
                      }
                      break;
					}
					//Block#20 ends here.


                  case 5:
                    {
                      System.out.println("Signed out.\n");
                      break;
                    }
                  default:
                    System.out.println("You should choose between \'1\' - \'5\'\n");
                  }
                  //Block#4 ends here.

                } catch(InputMismatchException exception) {
                  System.out.println("Invalid Input");
                  KB.nextLine();
                }
              } while ( customerMenuChoice != 5 );
            }
            //Block#3 ends here.


			//Block#26 rental manager sign in.
            else if (person instanceof RentalManager) {
              System.out.println("\nWelcome Rental managar.");
              do {
                try {
                  System.out.println("\nEnter:\n\'1\'- To manage customers reservations.\n\'2\'- To manage customers accounts.\n\'3\'- To manage rental requests.\n\'4\'- To access the inventory.\n\'5\'- Sign out\n");
                  rentalManagerMenuChoice = KB.nextInt();
				  switch (rentalManagerMenuChoice) { // Rental manager main menu selection.
					//Block#27 mange customers reservstion menu.
                  case 1:
                    {
                      System.out.println("Manage customers reservations:");
                      System.out.println("Not implemented yet.");
                      break;
					}
					//Block#27 ends here.

					//Block#28 manage customers accounts menu.
                  case 2:
                    {
                      System.out.println("Manage customers accounts:");
                      System.out.println("Not implemented yet.");
                      break;
					}
					//Block28 ends here.


					//Block29 manage rental requests menu.
                  case 3:
                    {
                      System.out.println("Manage rental requests:");
                      System.out.println("Not implemented yet.");
                      break;
					}
					//Block#29 ends here.

					//Block#30 access inventory.
                  case 4:
                    {
                      System.out.println("Inventory: ");
                      System.out.println("Not implemented yet.");
                      break;
					}
					//Block#30 ends here.


					//Block#31 Rental manager sign out.
                  case 5:
                    {
                      System.out.println("Signed out.");
                      break;
					}
					//Block#31 ends here.

                  default:
                    {
                      System.out.println("You should choose between \'1\'- \'5\'\n");
                      break;
                    }
                  }
                } catch(InputMismatchException exception) {
                  System.out.println("Invalid Input");
                  KB.nextLine();
                }
              } while ( rentalManagerMenuChoice != 5 );
			}
			//Block#26 ends here.
			

			//Block#32 inventory manager sign in.
			else if (person instanceof InventoryManager) {
              System.out.println("\nWelcome Inventory managar.");
              do {
                try {
                  System.out.println("\nEnter:\n\'1\'- To manage inventory.\n\'2\'- To manage brands.\n\'3\'- To manage discounts.\n\'4\'- Sign out\n");
                  rentalManagerMenuChoice = KB.nextInt();
				  switch (rentalManagerMenuChoice) { //inventory manager menu selection.
					
					//Block#33 manage inventory menu.
                  case 1:
                    {
                      System.out.println("Manage inventory:");
                      System.out.println("Not implemented yet.");
                      break;
					}
					//Block#33 ends here.


					//Block#34 manage brands
					/*
					In this parts there are two senarios:- 
					1 - if there is no brand it will ask the inventory manager to either add a brand or to go return back to the inventory manager main menu.
					2 - if there is at least one brand it will display a list of brands and will ask the inventory manager to add a brand, remove a brand or to return back to the main menu.
					*/
                  case 2:
                    {
                      System.out.println("Manage brands:\n");
                      if (inventory.getBrands().size() > 0) { //If there at least one brand.
                        System.out.println("Brands list:\n");
                        for (Brand brand: inventory.getBrands()) { //display list of brands.
                          System.out.println(brand);
                        }
                        do {
                          System.out.println("\nEnter:\n\'1\'- To add a brand.\n\'2\'- To remove a brand.\n\'3\'- Back");
                          try {
                            brandChoice = KB.nextInt();
							switch (brandChoice) { //manage brands menu selection.
								

							//Block#35 adding a brand.
                            case 1:
                              {
                                int discount;
                                System.out.println("Enter the brand name: ");
                                String brandName = KB.next();
                                do {
                                  try {
                                    System.out.println("Enter the brand discount: example: 20 -> 20%");
                                    discount = KB.nextInt();
                                    if (discount >= 0 && discount < 100) { //it will check if the discount is from 0 to 99 integer
                                      break;
                                    }
                                  } catch(InputMismatchException exception) {
                                    System.out.println("\nInvalid Input. discount should be from 0% to 99%");
                                  }
                                } while ( true );
                                inventory.addBrand(brandName, discount);
                                break;
							  }
							  //Block#35 ends here.


							  //Block#36 removing a brand.
                            case 2:
                              {
                                System.out.println("Not implemented yet.");
                                break;
							  }
							  //Block#36 ends here.


							  //Bloc#k37 return back to inventory manager main menu.
                            case 3:
                              {
                                break;
							  }
							  //Block#37 ends here.
                            default:
                              {
                                System.out.println("\nYou should choose between \'1\' - \'3\'.");
                                break;
                              }
                            }
                          } catch(InputMismatchException exception) {
                            System.out.println("Invalid Input");
                          }

                        } while ( brandChoice != 3 );
                        break;
					  } 
					  
					  else { //if there is no brand registered.
                        System.out.println("Manage brands:\n");
                        System.out.println("No brands available");
                      }
                      do {
                        System.out.println("\nEnter:\n\'1\'- To add a brand.\n\'2\'- Back");
                        try {
                          brandChoice = KB.nextInt();
                          switch (brandChoice) { //manage brands menu selection.
						 
							//Block#38 adding a brand.
							case 1:
                            {
                              int discount;
                              System.out.println("Enter the brand name: ");
                              String brandName = KB.next();
                              do {
                                try {
                                  System.out.println("Enter the brand discount: example: 20 -> 20%");
                                  discount = KB.nextInt();
                                  if (discount >= 0 && discount < 100) {
                                    break;
                                  }
                                } catch(InputMismatchException exception) {
								  System.out.println("\nInvalid Input. discount should be from 0% to 99%");
								  KB.nextLine();
                                }
                              } while ( true );
                              inventory.addBrand(brandName, discount);
                              break;
							}
							//Block#38 ends here.


							//Block#39 return back to inventory manager main menu.
                          case 2:
                            {
                              break;
							}
							//Block#39 ends here

                          default:
                            {
                              System.out.println("\nYou should choose either \'1\' or \'2\'.");
                              break;
                            }
                          }
                        } catch(InputMismatchException exception) {
                          System.out.println("Invalid Input");
                        }

                      } while ( brandChoice != 2 );
                      break;
					}
					//Block#34 ends here.


					//Block#40 will be activated when the inventory manager choose to manage discounts.
                  case 3:
                    {
                      System.out.println("Manage discounts:");
                      System.out.println("Not implemented yet.");
                      break;
					}
					//Block#40 ends here.

					//Block#41 Inventory manager sign out.
                  case 4:
                    {
                      System.out.println("Signed out.");
                      break;
					}
					//Block#41 ends here.


                  default:
                    {
                      System.out.println("You should choose between \'1\'- \'4\'\n");
                      break;
                    }
                  }
                } catch(InputMismatchException exception) {
                  System.out.println("Invalid Input");
                  KB.nextLine();
                }
              } while ( rentalManagerMenuChoice != 4 );

			}
			//Block#32 ends here.
			else {
              System.out.println("Data entered are not available in the system.\nIf you do not have an account please sign up.\n");
            }
            break;
          }

          //Block#2 ends here.

		  //Block#42 customer sign up.
        case 2:
          {
            String nationalID = "",
            firstName = "",
            lastName = "",
            email = "",
            password = "",
            location = "",
            phoneNumber = "",
            veifyPassword = "";
            int age = 0;
            System.out.println("Enter your first name: \n");
            firstName = KB.next().trim();
            System.out.println("\nEnter your last name: \n");
            lastName = KB.next().trim();
            do {
              try {
                System.out.println("\nEnter your email: \n");
                email = KB.next();
                if (isValidEmail(email)) { //It will check if the email iss valid.
                  break;
                }
                System.out.println("Invalid email. Try again...\\n");
              } catch(InputMismatchException exception) {
                System.out.println("Invalid email. Try again...\n");
              }
            } while ( true );

            do {
              try {
                System.out.println("Enter your password: \n");
                password = KB.next();

                boolean validPassword = isValidPassword(password); // it will check if the password is valid.
                if (!validPassword) {
                  throw new IllegalArgumentException();
                }
                System.out.println("verify your password: \n");
                veifyPassword = KB.next();
                if (password.equals(veifyPassword)) { //check if the password match the verification.
                  System.out.println(password);
                  break;
                } else if (! (password == veifyPassword)) {
                  System.out.println("password does not match try again\n");

                }

              } catch(IllegalArgumentException e) {
                System.out.println("password is not valid\n");
              }
            } while ( true );
            for (int i = 0; i < inventory.getLocations().size(); i++) {
              System.out.printf("\'%d\'  - %s \n", i + 1, inventory.getLocations().get(i)); // it will display the locations available.
            }
            int locationNum;
            do {
              try {
                System.out.println("Enter the location number:");
                locationNum = KB.nextInt() - 1;
                if ((locationNum >= 0) && (locationNum < inventory.getLocations().size())) {
                  System.out.println("Location selected: " + inventory.getLocations().get(locationNum));
                  location = inventory.getLocations().get(locationNum); //it will assign the location
                  break;
                }
                System.out.println("You should choose betweeen \'1\' - \'" + inventory.getLocations().size() + "\'\n");
              } catch(InputMismatchException exception) {
                System.out.println("Invalid Input");
              }
            } while ( true );
            do {
              try {
                boolean matched = false;
                System.out.println("Enter your phone number: for example: 0555005500\n");
                phoneNumber = KB.next();
                if (numberOrNot(phoneNumber) && (phoneNumber.length() == 10)) { //it will check if the phone number contains 10-digits integer number only.
                  for (int index = 0; index < profile.getPersons().size(); index++) {
                    if (profile.getPersons().get(index).getPhoneNum() == Integer.parseInt(phoneNumber)) { // it will check if the phone number is matched with another registered phone number.
                      matched = true;
                    }
                  }
                  if (!matched) {
                    matched = false;
                    break;
                  }
                  System.out.println("Phone number entered has been already registred in system.");
                } else {
                  throw new IllegalArgumentException();
                }
              } catch(IllegalArgumentException exception) {
                System.out.println("You have entered an invalid phone number. Try again...\n");
              }
            } while ( true );
            do {
              try {
                System.out.println("Enter your age: \n");
                age = KB.nextInt();
                if (age >= 18 && age <= 80) { // it will check if the entered age is between 18 and 80.
                  break;
                }
                System.out.println("Age should be between 18 and 80");
              } catch(InputMismatchException exception) {
                System.out.println("Invalid age entry. Try again...\n");
              }
            } while ( true );
            do {
              try {
                boolean matched = false;
                System.out.println("Enter your nation ID: \n");
                nationalID = KB.next();
                if (String.valueOf(nationalID).length() == 10) { //it wil check if the national id contains 10-digits integer only.
                  for (int index = 0; index < profile.getPersons().size(); index++) {
                    if (profile.getPersons().get(index).getNationalID() == Integer.parseInt(nationalID)) { //it will check if it is matched with another national id registered.
                      matched = true;
                    }
                  }
                  if (!matched) {
                    matched = false;
                    break;
                  }
                  System.out.println("National ID entered has been already registred in system.");
                } else {
                  throw new IllegalArgumentException();
                }
              } catch(IllegalArgumentException exception) {
                System.out.println("You have entered an invalid national ID. Try again...\n");
              }
            } while ( true );
            profile.signUp(firstName, lastName, email, password, location, Integer.parseInt(phoneNumber), age, Integer.parseInt(nationalID)); //it will create a new customer object.
            break;
		  }
		  //Block#42 ends here.


		  //Block#43 exit from the 
        case 3:
          saveData();
          KB.close();
          System.out.println("Exit.");
		  System.exit(0);
		  //Block#43 ends here.


        default:
          System.out.println("You should choose between \'1\' - \'3\'\n");
        }
      } catch(InputMismatchException exception) {
        System.out.println("Invalid Input");
        KB.nextLine();
      }
    } while ( startChoice != 3 );
    //Block#1 ends here.

  }

  //it will check if the number contains only integer number.
  public static boolean numberOrNot(String phoneNumber) {
    try {
      Integer.parseInt(phoneNumber);
    } catch(NumberFormatException ex) {
      System.out.println("Wrong format phone number.\n");
      return false;
    }
    return true;
  }

  //this method is to check the password if it is strong enough.
  public static boolean isValidPassword(String password) {

    boolean isValid = true;
    if (password.length() > 15 || password.length() < 8) {
      System.out.println("Password must be less than 20 and more than 8 characters in length.");
      isValid = false;
    }

    String upperCaseChars = "(.*[A-Z].*)";
    if (!password.matches(upperCaseChars)) {
      System.out.println("Password must have atleast one uppercase character");
      isValid = false;
    }

    String lowerCaseChars = "(.*[a-z].*)";
    if (!password.matches(lowerCaseChars)) {
      System.out.println("Password must have atleast one lowercase character");
      isValid = false;
    }

    String numbers = "(.*[0-9].*)";
    if (!password.matches(numbers)) {
      System.out.println("Password must have atleast one number");
      isValid = false;
    }

    String specialChars = "(.*[@,#,$,%].*$)";
    if (!password.matches(specialChars)) {
      System.out.println("Password must have atleast one special character among @#$%");
      isValid = false;
    }
    return isValid;
  }

  //to check if the email entered is valid.
  public static boolean isValidEmail(String email) {
    if (Character.isLetter(email.charAt(0)) && email.contains("@")) {
      return true;
    }
    return false;
  }

//to save all the data in the file.
  public static void saveData() throws FileNotFoundException {
    PrintWriter cPrintWriter = new PrintWriter(new File("Customers.txt"));
    PrintWriter rmPrintWriter = new PrintWriter(new File("RentalManagers.txt"));
    PrintWriter imPrintWriter = new PrintWriter(new File("InventoryManagers.txt"));
    PrintWriter resPrintWriter = new PrintWriter(new File("Rentals.txt"));
    PrintWriter pPrintWriter = new PrintWriter(new File("Payments.txt"));
    PrintWriter carPrintWriter = new PrintWriter(new File("Cars.txt"));
    PrintWriter bPrintWriter = new PrintWriter(new File("Brands.txt"));
    for (Person person: profile.getPersons()) {
      if (person instanceof Customer) {
        Customer customer = (Customer) person;
        cPrintWriter.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail() + " " + customer.getPassword() + " " + customer.getLocation() + " " + customer.getPhoneNum() + " " + customer.getAge() + " " + customer.getNationalID());
        for (Rental reservation: customer.getReservations()) {
          resPrintWriter.println(reservation.getStatus() + " " + reservation.getCar().getCarID() + " " + reservation.getReservationID() + " " + customer.getPhoneNum() + " " + reservation.getDateRented() + " " + reservation.getReturnDate() + " " + reservation.getCost());
        }
        for (Payment.PaymentMethod payment: customer.getPayment().getPaymentList()) {
          pPrintWriter.println(payment.getName() + " " + payment.getExpDate() + " " + payment.getCardNum() + " " + payment.getCvv() + " " + payment.getPhoneNum());
        }
      } else if (person instanceof RentalManager) {
        rmPrintWriter.println(person.getFirstName() + " " + person.getLastName() + " " + person.getEmail() + " " + person.getPassword() + " " + person.getLocation() + " " + person.getPhoneNum() + " " + person.getAge() + " " + person.getNationalID());
      } else if (person instanceof InventoryManager) {
        imPrintWriter.println(person.getFirstName() + " " + person.getLastName() + " " + person.getEmail() + " " + person.getPassword() + " " + person.getLocation() + " " + person.getPhoneNum() + " " + person.getAge() + " " + person.getNationalID());
      }

    }
    for (Car car: inventory.getCars()) {
      carPrintWriter.printf("%s %s %s %s %s %,2f %d %d %d %s%n", car.getName(), car.getType(), car.getBrand(), car.getLocation(), car.getColor(), car.getPrice(), car.getMilage(), car.getReleaseYear(), car.getCarID(), car.getAvailable());
    }
    for (Brand brand: inventory.getBrands()) {
      bPrintWriter.printf("%s %d%n", brand.getBrandName(), brand.getDiscount());
    }
    cPrintWriter.close();
    rmPrintWriter.close();
    imPrintWriter.close();
    resPrintWriter.close();
    pPrintWriter.close();
    carPrintWriter.close();
    bPrintWriter.close();
  }
}