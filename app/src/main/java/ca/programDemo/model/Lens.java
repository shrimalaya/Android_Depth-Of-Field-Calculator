package ca.programDemo.model;

public class Lens {
    private String make; //Make of camera. eg: Canon, Nikon, etc
    private double maxAperture; //F-number for lens
    private int focalLengthInMM;

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

    public void setMake(String make) {
        this.make = make;
    }

    public void setMaxAperture(double maxAperture) {
        this.maxAperture = maxAperture;
    }

    public void setFocalLengthInMM(int focalLengthInMM) {
        this.focalLengthInMM = focalLengthInMM;
    }

    @Override
    public String toString() {
        return make + "  " + focalLengthInMM +
                "mm  F" + maxAperture ;
    }
}
