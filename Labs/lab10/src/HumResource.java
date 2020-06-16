
public class HumResource {
	// data
	int		hrID;				// humanitarian resource ID
	String	hrName;				// name of resource
	String  hrAddressString;	// resource address string
	String  hrPhoneNumber;		// hr phone number
	double  hrLatitude;			// hr latitude
	double 	hrLongitude;		// hr longitude
	String	hrType;				// hr type (MedicalCenter, Water, Food)
	String  hrDesc;				// hr description
	String  hrOpenHoursString;	// hr open hours string
	
	// methods
	// all-arg constructor
	public HumResource(int hrID, String hrName, String hrAddressString, String hrPhoneNumber, double hrLatitude,
			double hrLongitude, String hrType, String hrDesc, String hrOpenHoursString) {
		super();
		this.hrID = hrID;
		this.hrName = hrName;
		this.hrAddressString = hrAddressString;
		this.hrPhoneNumber = hrPhoneNumber;
		this.hrLatitude = hrLatitude;
		this.hrLongitude = hrLongitude;
		this.hrType = hrType;
		this.hrDesc = hrDesc;
		this.hrOpenHoursString = hrOpenHoursString;
	}

	// default constructor
	public HumResource () {
		this(0, "default", null, null, 0.0, 0.0, null, null, null);
	}

	// getters and setters
	public int getHrID() {
		return hrID;
	}

	public void setHrID(int hrID) {
		this.hrID = hrID;
	}

	public String getHrName() {
		return hrName;
	}

	public void setHrName(String hrName) {
		this.hrName = hrName;
	}

	public String getHrAddressString() {
		return hrAddressString;
	}

	public void setHrAddressString(String hrAddressString) {
		this.hrAddressString = hrAddressString;
	}

	public String getHrPhoneNumber() {
		return hrPhoneNumber;
	}

	public void setHrPhoneNumber(String hrPhoneNumber) {
		this.hrPhoneNumber = hrPhoneNumber;
	}

	public double getHrLatitude() {
		return hrLatitude;
	}

	public void setHrLatitude(double hrLatitude) {
		this.hrLatitude = hrLatitude;
	}

	public double getHrLongitude() {
		return hrLongitude;
	}

	public void setHrLongitude(double hrLongitude) {
		this.hrLongitude = hrLongitude;
	}

	public String getHrType() {
		return hrType;
	}

	public void setHrType(String hrType) {
		this.hrType = hrType;
	}







	public String getHrDesc() {
		return hrDesc;
	}

	public void setHrDesc(String hrDesc) {
		this.hrDesc = hrDesc;
	}

	public String getHrOpenHoursString() {
		return hrOpenHoursString;
	}

	public void setHrOpenHoursString(String hrOpenHoursString) {
		this.hrOpenHoursString = hrOpenHoursString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hrAddressString == null) ? 0 : hrAddressString.hashCode());
		result = prime * result + ((hrDesc == null) ? 0 : hrDesc.hashCode());
		result = prime * result + hrID;
		long temp;
		temp = Double.doubleToLongBits(hrLatitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hrLongitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((hrName == null) ? 0 : hrName.hashCode());
		result = prime * result + ((hrOpenHoursString == null) ? 0 : hrOpenHoursString.hashCode());
		result = prime * result + ((hrPhoneNumber == null) ? 0 : hrPhoneNumber.hashCode());
		result = prime * result + ((hrType == null) ? 0 : hrType.hashCode());
		return result;
	}	// end - method hashCode

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HumResource other = (HumResource) obj;
		if (hrAddressString == null) {
			if (other.hrAddressString != null)
				return false;
		} else if (!hrAddressString.equals(other.hrAddressString))
			return false;
		if (hrDesc == null) {
			if (other.hrDesc != null)
				return false;
		} else if (!hrDesc.equals(other.hrDesc))
			return false;
		if (hrID != other.hrID)
			return false;
		if (Double.doubleToLongBits(hrLatitude) != Double.doubleToLongBits(other.hrLatitude))
			return false;
		if (Double.doubleToLongBits(hrLongitude) != Double.doubleToLongBits(other.hrLongitude))
			return false;
		if (hrName == null) {
			if (other.hrName != null)
				return false;
		} else if (!hrName.equals(other.hrName))
			return false;
		if (hrOpenHoursString == null) {
			if (other.hrOpenHoursString != null)
				return false;
		} else if (!hrOpenHoursString.equals(other.hrOpenHoursString))
			return false;
		if (hrPhoneNumber == null) {
			if (other.hrPhoneNumber != null)
				return false;
		} else if (!hrPhoneNumber.equals(other.hrPhoneNumber))
			return false;
		if (hrType == null) {
			if (other.hrType != null)
				return false;
		} else if (!hrType.equals(other.hrType))
			return false;
		return true;
	}	// 	end - method equals

	@Override
	public String toString() {
		return "HumResource [hrID=" + hrID + ", hrName=" + hrName + ", hrAddressString=" + hrAddressString
				+ ", hrPhoneNumber=" + hrPhoneNumber + ", hrLatitude=" + hrLatitude + ", hrLongitude=" + hrLongitude
				+ ", hrType=" + hrType + ", hrDesc=" + hrDesc + ", hrOpenHoursString=" + hrOpenHoursString + "]";
	}	// end - method toString
	
}	// end - class HumResource
