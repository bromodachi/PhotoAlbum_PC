package cs213.photoalbum.model;

public enum TagType {
	LOCATION, PEOPLE, DATE;

	@Override
	public String toString() {
		switch (this) {
		case LOCATION:
			return "Location";
		case PEOPLE:
			return "People";
		case DATE:
			return "Date";
		default:
			return "";
		}
	}
}
