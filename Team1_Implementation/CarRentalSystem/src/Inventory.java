import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Inventory {

  private ArrayList < Car > cars = new ArrayList < >();
  private ArrayList < Brand > brands = new ArrayList < >();
  private ArrayList < String > types = new ArrayList < >();
  private ArrayList < String > locations = new ArrayList < >();

  public Inventory() {
    try {
      File brandsFile = new File("Brands.txt"); //load car brands.
      Scanner bScanner = new Scanner(brandsFile);
      while (bScanner.hasNext()) {
        brands.add(new Brand(bScanner.next(), bScanner.nextInt()));

      }
      bScanner.close();
    } catch(FileNotFoundException exception) {
      System.out.println("Error: brands file does not exist.");
    }

    try {
      File carsFile = new File("Cars.txt"); //load cars.
      Scanner cScanner = new Scanner(carsFile);
      while (cScanner.hasNext()) {
        cars.add(new Car(cScanner.next(), cScanner.next(), cScanner.next(), cScanner.next(), cScanner.next(), cScanner.nextDouble(), cScanner.nextInt(), cScanner.nextInt(), cScanner.nextInt(), cScanner.next()));
      }
      cScanner.close();
    } catch(FileNotFoundException exception) {
      System.out.println("Error: cars file does not exist.");
    }
    try {
      File typesFile = new File("Types.txt"); //load car types
      Scanner tScanner = new Scanner(typesFile);
      while (tScanner.hasNext()) {
        types.add(tScanner.next());
      }
      tScanner.close();
    } catch(FileNotFoundException exception) {
      System.out.println("Error: types file does not exist.");
    }
    try {
      File locationsFile = new File("Locations.txt"); //load locations.
      Scanner lScanner = new Scanner(locationsFile);
      while (lScanner.hasNext()) {
        locations.add(lScanner.next());
      }
      lScanner.close();
    } catch(FileNotFoundException exception) {
      System.out.println("Error: locations file does not exist.");
    }
  }
  //to remove a car.
  public void removeACar() {
    Scanner KB = new Scanner(System. in );
    System.out.println("Cars list: ");
    for (Car car: cars) {
      System.out.println(car);
    }
    System.out.println("Enter the car id that you want to remove: ");
    int carId = KB.nextInt();
    for (Car car: cars) {
      if (carId == car.getCarID()) {
        int choice = 0;
        System.out.println("You are about to remove the selected car? Car ID: " + carId + "\n\'1\' - Confirm.\n\'2\' - Cancel");
        do {
          choice = KB.nextInt();
          switch (choice) {
          case 1:
            {
              cars.remove(car);
              break;
            }
          case 2:
            {
              System.out.println("Car removal has been canceled.");
              break;
            }
          default:
            {
              System.out.println("Please select between 1 or 2.");
              break;
            }
          }
        } while ( choice != 1 || choice != 2 );
      }
    }
    KB.close();
  }

  //adding a car.
  public void addACar() {
    Scanner kb = new Scanner(System. in );
    String carName,
    carColor,
    carBrand,
    cartype,
    location;
    int carMilage,
    releaseYear,
    choice = 0;
    double monthlyPrice = 0;
    System.out.println("Enter the car name: "); 
    carName = kb.nextLine();
    System.out.println("Are you sure About the car name? \nCar name is: " + carName + "\n\'1\' - Yes, to confirm.\n\'2\' - No, to change"); 
    do {
      choice = kb.nextInt();
      switch (choice) {
      case 1:
        {
          System.out.println("Car name: " + carName + ", has been assigned.");
          break;
        }
      case 2:
        {
          System.out.println("Please enter the car name again: ");
          carName = kb.nextLine();
          break;
        }
      default:
        {
          System.out.println("Please select between 1 or 2.");
          break;
        }
      }
    } while ( choice != 1 || choice != 2 );
    choice = 0;
    System.out.println("Enter the car color: ");
    carColor = kb.nextLine();
    System.out.println("Are you sure About the car color? \nCar color is: " + carColor + "\n\'1\' - Yes, to confirm.\n\'2\' - No, to change");
    do {
      choice = kb.nextInt();
      switch (choice) {
      case 1:
        {
          System.out.println("Car color: " + carColor + ", has been assigned.");
          break;
        }
      case 2:
        {
          System.out.println("Please enter the car color again: ");
          carColor = kb.nextLine();
          break;
        }
      default:
        {
          System.out.println("Please select between 1 or 2.");
          break;
        }
      }
    } while ( choice != 1 || choice != 2 );
    choice = 0;
    System.out.println("Select the Car brand: -");
    int index = 0;
    for (Brand brand: brands) {
      System.out.printf("%d - %s.", ++index, brand.getBrandName());
    }
    System.out.println("Enter the brand number:");
    do {
      choice = kb.nextInt();

    } while ( choice > 0 && choice < brands . size ());
    carBrand = brands.get(choice - 1).getBrandName();
    choice = 0;
    System.out.println("Select the Car type: -");
    index = 0;
    for (String type: types) {
      System.out.printf("%d - %s.", ++index, type);
    }
    System.out.println("Enter the type number:");
    do {
      choice = kb.nextInt();

    } while ( choice > 0 && choice < types . size ());
    cartype = types.get(choice - 1);
    choice = 0;
    System.out.println("Enter the car milage: ");
    carMilage = kb.nextInt();
    System.out.println("Are you sure About the car milage? \nCar milage is: " + carMilage + "\n\'1\' - Yes, to confirm.\n\'2\' - No, to change");
    do {
      choice = kb.nextInt();
      switch (choice) {
      case 1:
        {
          System.out.println("Car milage: " + carMilage + ", has been assigned.");
          break;
        }
      case 2:
        {
          System.out.println("Please enter the car milage again: ");
          carMilage = kb.nextInt();
          break;
        }
      default:
        {
          System.out.println("Please select between 1 or 2.");
          break;
        }
      }
    } while ( choice != 1 || choice != 2 );
    choice = 0;
    System.out.println("Select the Car location: -");
    index = 0;
    for (String location1: locations) {
      System.out.printf("%d - %s.", ++index, location1);
    }
    System.out.println("Enter the location number:");
    do {
      choice = kb.nextInt();

    } while ( choice > 0 && choice < locations . size ());
    location = locations.get(choice - 1);
    choice = 0;
    System.out.println("Enter the car release year: ");
    releaseYear = kb.nextInt();
    System.out.println("Are you sure About the car year? \nCar release year is: " + releaseYear + "\n\'1\' - Yes, to confirm.\n\'2\' - No, to change");
    do {
      choice = kb.nextInt();
      switch (choice) {
      case 1:
        {
          System.out.println("Car release year: " + releaseYear + ", has been assigned.");
          break;
        }
      case 2:
        {
          System.out.println("Please enter the year again: ");
          releaseYear = kb.nextInt();
          break;
        }
      default:
        {
          System.out.println("Please select between 1 or 2.");
          break;
        }
      }
    } while ( choice != 1 || choice != 2 );
    choice = 0;
    System.out.println("Enter the car monthly price in saudi riyals: ");
    monthlyPrice = kb.nextDouble();
    System.out.println("Are you sure About the car price? \nCar release year is: " + monthlyPrice + "SAR/ mo\n\'1\' - Yes, to confirm.\n\'2\' - No, to change");
    do {
      choice = kb.nextInt();
      switch (choice) {
      case 1:
        {
          System.out.println("Car price: " + monthlyPrice + " SAR/ mo, has been assigned.");
          break;
        }
      case 2:
        {
          System.out.println("Please enter the price again: ");
          monthlyPrice = kb.nextInt();
          break;
        }
      default:
        {
          System.out.println("Please select between 1 or 2.");
          break;
        }
      }
    } while ( choice != 1 || choice != 2 );
    choice = 0;
    cars.add(new Car(carName, cartype, carBrand, location, carColor, carMilage, releaseYear, monthlyPrice)); //creating new car object and add it to the car list.
    System.out.println("New car has been added:\n" + cars.get(cars.size() - 1));
    kb.close();
  }
  //adding a brand with its discount.
  public void addBrand(String name, int discount) {
    brands.add(new Brand(name, discount));
    System.out.println("Brand: " + name + ", Disount: " + discount + "% has been added.");
  }

  //it will calculate the cost of the reservation.
  public double calculateCost(Car car, LocalDate firstDate, LocalDate SecondDate) {
    long daysBetween = ChronoUnit.DAYS.between(firstDate, SecondDate); //to calculate the reservation period in days.
    long monthsBetwwen = ChronoUnit.MONTHS.between(firstDate, SecondDate); //to calculate the reservation period in months.
    if (daysBetween != 0) {
      System.out.println("Car price: " + car.getPrice() + " SAR.\n");

      int brandIndex = 0;
      for (Brand brand: brands) {
        if (car.getBrand().equals(brand.getBrandName())) { //display the brands.
          brandIndex = brands.indexOf(brand);
        }
      }
      double averageDaysInMonth = (daysBetween / monthsBetwwen * 1.0);
      double defaultPrice = (daysBetween / averageDaysInMonth) * car.getPrice(); //to calculate the price by multiplying the months with the car price.
      double discount = ((daysBetween / averageDaysInMonth) * (brands.get(brandIndex).getDiscount() / 100.0) * car.getPrice()); //to calculate the discount of a brand on the reservation.
      if (daysBetween > 0) { // if it is an original or extended reservation.
        if (brands.get(brandIndex).getDiscount() > 0) { // if there is a discount.
          System.out.printf("Price before discount: %.2f SAR.%n", (daysBetween / averageDaysInMonth) * car.getPrice());
          System.out.printf("Discount: %.2f SAR.%n", discount);
          System.out.printf("Price after discount: %.2f SAR.%n", (defaultPrice - discount));
          return defaultPrice - discount; //return price after discount.
        } else { //if there is not a discount available.
          System.out.printf("Price: %.2f SAR.%n", defaultPrice);
          return defaultPrice; //return the base price.
        }
      } else if (daysBetween < 0) { //if it is a shortened reservation.
        if (brands.get(brandIndex).getDiscount() > 0) { // if there is a discount.
          System.out.printf("Refund after discount: %.2f SAR.%n", Math.abs(defaultPrice - discount));
          return Math.abs(defaultPrice - discount); //return price after discount.
        } else { //if there is not a discount available.
          System.out.printf("Refund: %.2f SAR.%n", Math.abs(defaultPrice));
          return Math.abs(defaultPrice); //return the base price.
        }
      }
    }
    return 0;
  }
  // it will display the list of cars based on the search number.
  public boolean search(int choice) {
    boolean found = false;
    Scanner KB = new Scanner(System. in );
	
	// to search by brands.
	if (choice == 1) { 

      for (int i = 0; i < brands.size(); i++) {
        System.out.printf("\'%d\'  - %s \n", i + 1, brands.get(i).getBrandName());
      }
      System.out.println("Enter the brand number:");
      int brandNum;
      do {
        brandNum = KB.nextInt() - 1;

      } while (!( brandNum > 0 ) && !(brandNum < brands.size()));
      if (brands.size() > 0) {
        for (Car car: cars) { // to check if there are cars available under selected brand and it is not rented.
          if (car.getBrand().equals(brands.get(brandNum).getBrandName()) && car.getAvailable().equals("Available")) {
            System.out.println(car.toString());
            found = true;
          }
        }
      }
	} 


	//to search by car types.
	else if (choice == 2) {
      for (int i = 0; i < types.size(); i++) {
        System.out.printf("\'%d\'  - %s \n", i + 1, types.get(i));
      }
      System.out.println("Enter the type number:");
      int typeNum;
      do {
        typeNum = KB.nextInt() - 1;

      } while (!( typeNum > 0 ) && !(typeNum < types.size()));
      if (types.size() > 0) {
        for (Car car: cars) { // to check if there are cars available under selected type and it is not rented.
          if (car.getType().equals(types.get(typeNum)) && car.getAvailable().equals("Available")) {
            System.out.println(car.toString());
            found = true;
          }
        }
      }
    } else if (choice == 3) {
      System.out.println("\nEnter the car name: ");
      String carName = KB.next().trim();
      for (Car car: cars) { // to check if the car name match the cars in the inventory.
        if (car.getAvailable().equals("Available") && car.getName().equalsIgnoreCase(carName)) {
          System.out.println(car);
          found = true;
        }
	  }

	  // to search for cars based on their prices.
    } else if (choice == 4) {
      do {
        System.out.println("\nChoose the price range: \'1\'- 1000 -> 2500 SAR/ mo\n\'2\'- 2500 -> 5000 SAR/mo\n\'3\'- 5000 -> 7500 SAR/mo\n\'4\'- 7500 -> 10000 SAR/mo\n\'5\'- 10000+ SAR/mo");
        try {
          double priceChoicee = KB.nextInt();
          if (priceChoicee == 1) {
            for (Car car: cars) {
              if (car.getPrice() >= 1000 && car.getPrice() < 2500) {
                System.out.println(car);
                found = true;
              }
            }
            break;
          } else if (priceChoicee == 2) {
            for (Car car: cars) {
              if (car.getPrice() >= 2500 && car.getPrice() < 5000) {
                System.out.println(car);
                found = true;
              }
            }
            break;
          } else if (priceChoicee == 3) {
            for (Car car: cars) {
              if (car.getPrice() >= 5000 && car.getPrice() < 7500) {
                System.out.println(car);
                found = true;
              }
            }
            break;
          } else if (priceChoicee == 4) {
            for (Car car: cars) {
              if (car.getPrice() >= 7500 && car.getPrice() < 10000) {
                System.out.println(car);
                found = true;
              }
            }
            break;
          } else if (priceChoicee == 5) {
            for (Car car: cars) {
              if (car.getPrice() >= 10000) {
                System.out.println(car);
                found = true;
              }
            }
            break;
          } else {
            System.out.println("Please select from 1 to 5");
          }
        } catch(InputMismatchException exception) {
          System.out.println("Invalid input.");
        }
      } while ( true );
    }
    return found;
  }

  public ArrayList < Car > getCars() {
    return cars;
  }

  public void setCars(ArrayList < Car > cars) {
    this.cars = cars;
  }

  public ArrayList < Brand > getBrands() {
    return this.brands;
  }

  public void setBrands(ArrayList < Brand > brands) {
    this.brands = brands;
  }

  public ArrayList < String > getTypes() {
    return this.types;
  }

  public void setTypes(ArrayList < String > types) {
    this.types = types;
  }

  public ArrayList < String > getLocations() {
    return this.locations;
  }

  public void setLocations(ArrayList < String > locations) {
    this.locations = locations;
  }

}