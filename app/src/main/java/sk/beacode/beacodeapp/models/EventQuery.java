package sk.beacode.beacodeapp.models;

public class EventQuery {

    private String namePart;

    public EventQuery(String namePart) {
        this.namePart = namePart;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }
}
