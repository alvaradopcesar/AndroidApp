package iosco.app.model.entity;

import java.io.Serializable;

import iosco.app.utils.Helpers;

/**
 * Created by usuario on 01/03/2016.
 */
public class FlightEntity  implements Serializable {
    private String AirlineIn;
    private String FlightNumberIn;
    private String AirlineOut;
    private String FlightNumberOut;

    public String getAirlineIn() {
        return AirlineIn == null ? "" : AirlineIn;
    }

    public void setAirlineIn(String airlineIn) {
        AirlineIn = airlineIn;
    }

    public String getFlightNumberIn() {
        return FlightNumberIn;
    }

    public void setFlightNumberIn(String flightNumberIn) {
        FlightNumberIn = flightNumberIn;
    }

    public String getAirlineOut() {
        return AirlineOut;
    }

    public void setAirlineOut(String airlineOut) {
        AirlineOut = airlineOut;
    }

    public String getFlightNumberOut() {
        return FlightNumberOut;
    }

    public void setFlightNumberOut(String flightNumberOut) {
        FlightNumberOut = flightNumberOut;
    }
}
