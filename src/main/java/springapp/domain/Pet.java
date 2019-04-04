package springapp.domain;

public class Pet {
	private final Integer id;
	private final String name;
	private final Gender gender;
	private final boolean altered;
	private final Integer clientId;
	
	public Pet(Integer id, String name, Gender gender, boolean altered, Integer clientId ){
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.altered = altered;
		this.clientId = clientId;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	public boolean isAltered() {
		return altered;
	}

	public Integer getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", gender=" + gender + ", altered=" + altered + ", clientId="
				+ clientId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (altered ? 1231 : 1237);
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (altered != other.altered)
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}

