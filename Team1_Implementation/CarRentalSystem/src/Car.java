public class Car {
	private String name,
	type,
	location,
	color,
	brand,
	Available;
	private int milage,
	releaseYear,
	carID;
	private static int carIDIncrementer;
	private double price;
  
	// When renting a car.
	public Car(String name, String type, String brand, String location, String color, int milage, int releaseYear, double price) {
	  setName(name);
	  setType(type);
	  setLocation(location);
	  setBrand(brand);
	  setColor(color);
	  setMilage(milage);
	  setReleaseYear(releaseYear);
	  setAvailable("Available");
	  setPrice(price);
	}
  
	// When adding a car.
	public Car(String name, String type, String brand, String location, String color, int milage, double price, int releaseYear) {
	  setName(name);
	  setType(type);
	  setLocation(location);
	  setBrand(brand);
	  setColor(color);
	  setMilage(milage);
	  setReleaseYear(releaseYear);
	  setCarID(++carIDIncrementer);
	  setAvailable("Available");
	  setPrice(price);
	}
  
	// When loading a car.
	public Car(String name, String type, String brand, String location, String color, double price, int milage, int releaseYear, int carID, String available) {
	  setName(name);
	  setType(type);
	  setLocation(location);
	  setBrand(brand);
	  setColor(color);
	  setMilage(milage);
	  setReleaseYear(releaseYear);
	  setCarID(carID);
	  if (carID > carIDIncrementer) {
		carIDIncrementer = carID;
	  }
	  setAvailable(available);
	  setPrice(price);
	}
  
	public static int getCarIDIncrementer() {
	  return carIDIncrementer;
	}
  
	public static void setCarIDIncrementer(int carIDIncrementer) {
	  Car.carIDIncrementer = carIDIncrementer;
	}
	public void setAvailable(String status) {
	  this.Available = status;
  
	}
	public String getAvailable() {
	  return Available;
	}
  
	public String getBrand() {
	  return brand;
	}
  
	public void setBrand(String brand) {
	  this.brand = brand;
	}
  
	public String getName() {
	  return name;
	}
  
	public void setName(String name) {
	  this.name = name;
	}
  
	public String getType() {
	  return type;
	}
  
	public void setType(String type) {
	  this.type = type;
	}
  
	public String getLocation() {
	  return location;
	}
  
	public void setLocation(String location) {
	  this.location = location;
	}
  
	public String getColor() {
	  return color;
	}
  
	public void setColor(String color) {
	  this.color = color;
	}
  
	public int getMilage() {
	  return milage;
	}
  
	public void setMilage(int milage) {
	  this.milage = milage;
	}
  
	public int getReleaseYear() {
	  return releaseYear;
	}
  
	public void setReleaseYear(int releaseYear) {
	  this.releaseYear = releaseYear;
	}
  
	public int getCarID() {
	  return carID;
	}
  
	public void setCarID(int carID) {
	  this.carID = carID;
	}
  
	public double getPrice() {
	  return price;
	}
  
	public void setPrice(double price) {
	  this.price = price;
	}
  
	@Override
	public String toString() {
	  return String.format("%nCar Name: %s, Car ID: %d,Color: %s, Milage: %d Km, Location: %s, Year: %d, Price: %s SAR/ mo, Status: %s%n", getName(), getCarID(), getColor(), getMilage(), getLocation(), getReleaseYear(), getPrice(), getAvailable());
	}
  
  }