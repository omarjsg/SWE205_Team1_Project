
public class Person implements Comparable<Person>, Cloneable {
	String firstName, lastName, email, password, location;
	int age, nationalID , phoneNum;

	public Person(String firstName, String lastName, String email, String password, String location, int phoneNum,
			int age, int nationalID) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setLocation(location);
		setPhoneNum(phoneNum);
		setAge(age);
		setNationalID(nationalID);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(int phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getNationalID() {
		return nationalID;
	}

	public void setNationalID(int nationalID) {
		this.nationalID = nationalID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int compareTo(Person person) {
		if (phoneNum < person.phoneNum) {
			return -1;
		} else if (phoneNum > person.phoneNum) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Person copy = (Person) super.clone();
		return copy;
	}

	public boolean equals(Object object) {
		if (object instanceof Person) {
			Person person = (Person) object;
			return compareTo(person) == 0;
		}
		return false;
	}
}
