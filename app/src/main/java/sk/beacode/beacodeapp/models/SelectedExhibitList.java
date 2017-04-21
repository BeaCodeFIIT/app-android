package sk.beacode.beacodeapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedExhibitList {
	private List<SelectedExhibit> selectedExhibits;

	@JsonProperty("data")
	public List<SelectedExhibit> getSelectedExhibits() {
		return selectedExhibits;
	}

	public void setSelectedExhibits(List<SelectedExhibit> selectedExhibits) {
		this.selectedExhibits = selectedExhibits;
	}
}
