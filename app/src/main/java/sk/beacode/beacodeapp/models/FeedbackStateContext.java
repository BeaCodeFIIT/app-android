package sk.beacode.beacodeapp.models;

public class FeedbackStateContext {

	private FeedbackState state;
	private String feedback;

	public FeedbackStateContext() {
		state = null;
	}

	public FeedbackState getState() {
		return state;
	}

	public void setState(FeedbackState state) {
		this.state = state;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
