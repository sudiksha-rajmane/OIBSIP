package reservation;

/**
 * Model class representing a train reservation ticket.
 */
public class Reservation {
    private String pnrNumber;
    private String passengerName;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String from;
    private String to;

    public Reservation(String pnrNumber, String passengerName, String trainNumber,
                       String trainName, String classType, String dateOfJourney,
                       String from, String to) {
        this.pnrNumber    = pnrNumber;
        this.passengerName = passengerName;
        this.trainNumber  = trainNumber;
        this.trainName    = trainName;
        this.classType    = classType;
        this.dateOfJourney = dateOfJourney;
        this.from         = from;
        this.to           = to;
    }

    // Getters
    public String getPnrNumber()     { return pnrNumber; }
    public String getPassengerName() { return passengerName; }
    public String getTrainNumber()   { return trainNumber; }
    public String getTrainName()     { return trainName; }
    public String getClassType()     { return classType; }
    public String getDateOfJourney() { return dateOfJourney; }
    public String getFrom()          { return from; }
    public String getTo()            { return to; }

    @Override
    public String toString() {
        return String.format(
            "PNR: %s | Name: %s | Train: %s (%s) | Class: %s | Date: %s | %s → %s",
            pnrNumber, passengerName, trainNumber, trainName,
            classType, dateOfJourney, from, to
        );
    }
}
