package ca.programDemo.model;

/**
 * Lens class
 */
public class Lens {
    private String make; //Make of camera. eg: Canon, Nikon, etc
    private double maxAperture; //F-number for lens
    private int focalLengthInMM;

    //Constructor
    public Lens(String make, double maxAperture, int focalLengthInMM) {
        this.make = make;
        this.maxAperture = maxAperture;
        this.focalLengthInMM = focalLengthInMM;
    }

    public String getMake() {
        return make;
    }

    public double getMaxAperture() {
        return maxAperture;
    }

    public int getFocalLengthInMM() {
        return focalLengthInMM;
    }

    @Override
    public String toString() {
        return make + "  " + focalLengthInMM +
                "mm  F" + maxAperture ;
    }
}
