
import java.util.ArrayList;

public class Payment {
	private ArrayList<PaymentMethod> paymentList = new ArrayList<>();

	public int getSize() {
		return paymentList.size();
	}

	// to add a payment method.
	public void addPayment(String name, String expDate, long cardNum, int cvv, int phoneNum) {
		paymentList.add(new PaymentMethod(name, expDate, cardNum, cvv, phoneNum));
	}

	public void setPaymentList(ArrayList<PaymentMethod> paymentList) {
		this.paymentList = paymentList;
	}

	public void remove(int index) {
		if (isEmpty()) {
			System.out.println("Error");
		} else {
			paymentList.remove(index);
		}
	}

	public ArrayList<PaymentMethod> getPaymentList() {
		return paymentList;
	}

	private boolean isEmpty() {

		return paymentList.isEmpty();
	}

	public String toString() {
		return "Payment methods list: " + paymentList.toString();
	}

	public class PaymentMethod {
		private String name;
		private String expDate;
		private long cardNum;
		private int phoneNum, cvv;

		public PaymentMethod(String name, String expDate, long cardNum, int cvv, int phoneNum) {
			setName(name);
			setExpDate(expDate);
			setCardNum(cardNum);
			setCvv(cvv);
			setPhoneNum(phoneNum);
		}

		public String getExpDate() {
			return expDate;
		}

		public void setExpDate(String expDate) {
			this.expDate = expDate;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public long getCardNum() {
			return cardNum;
		}

		public void setCardNum(long cardNum) {
			this.cardNum = cardNum;
		}

		public int getCvv() {
			return cvv;
		}

		public void setCvv(int cvv) {
			this.cvv = cvv;
		}

		public int getPhoneNum() {
			return phoneNum;
		}

		public void setPhoneNum(int phoneNum) {
			this.phoneNum = phoneNum;
		}

		@Override
		public String toString() {
			return String.format("Name: %S, Card number: %s************", getName(),
					String.valueOf(getCardNum()).substring(0, 4), getExpDate());
		}
	}
}
