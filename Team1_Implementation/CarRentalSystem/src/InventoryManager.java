
public class InventoryManager extends Person {

	public InventoryManager(String firstName, String lastName, String email, String password, String location,
			int phoneNum, int age, int nationalID) {
		super(firstName, lastName, email, password, location, phoneNum, age, nationalID);
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		InventoryManager copy = (InventoryManager) super.clone();
		
		return copy;
	}
}
