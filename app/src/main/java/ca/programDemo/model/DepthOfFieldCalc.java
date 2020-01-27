package ca.programDemo.model;

public class DepthOfFieldCalc {
    private Lens lens;
    private double distanceOfObjInMM;
    private double aperture;
    public static final double CIRCLE_OF_CONFUSION_IN_MM = 0.029;
    private double hyperFocalDistInMM;

    public DepthOfFieldCalc(Lens lens, double distanceOfObjInMM, double aperture) {
        this.lens = lens;
        this.distanceOfObjInMM = distanceOfObjInMM;
        this.aperture = aperture;
        this.hyperFocalDistInMM = Math.pow(this.lens.getFocalLengthInMM(), 2) /
                (this.aperture*CIRCLE_OF_CONFUSION_IN_MM);
    }

    public Lens getLens() {
        return lens;
    }

    public double getDistanceOfObjInMM() {
        return distanceOfObjInMM;
    }

    public double getHyperFocalDistInMM(){
        return hyperFocalDistInMM;
    }

    public double getNearFocalPoint() {
        return (getHyperFocalDistInMM() * getDistanceOfObjInMM()) /
                (getHyperFocalDistInMM() + (getDistanceOfObjInMM() - getLens().getFocalLengthInMM()));
    }

    public double getFarFocalPoint() {
        double farFP = (getHyperFocalDistInMM() * getDistanceOfObjInMM()) /
                (getHyperFocalDistInMM() - (getDistanceOfObjInMM() - getLens().getFocalLengthInMM()));

        if(getDistanceOfObjInMM() > getHyperFocalDistInMM())
            farFP = Double.POSITIVE_INFINITY;

        return farFP;
    }

    public double getDepthOfFieldInMM() {
        return getFarFocalPoint() - getNearFocalPoint();
    }
}
