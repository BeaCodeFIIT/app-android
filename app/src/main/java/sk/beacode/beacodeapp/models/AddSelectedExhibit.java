package sk.beacode.beacodeapp.models;

public class AddSelectedExhibit {
	private int exhibitId;

	public AddSelectedExhibit(int exhibitId) {
		setExhibitId(exhibitId);
	}

	public int getExhibitId() {
		return exhibitId;
	}

	public void setExhibitId(int exhibitId) {
		this.exhibitId = exhibitId;
	}
}
