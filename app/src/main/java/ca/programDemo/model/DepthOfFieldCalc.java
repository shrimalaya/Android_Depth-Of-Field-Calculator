package ca.programDemo.model;

/**
 * Depth of Field class implementation
 *
 * Allows modification of Circle Of Confusion
 */
public class DepthOfFieldCalc {
    private Lens lens;
    private double distanceOfObjInMM;
    private double aperture;
    private double circleOfConfusionInM = 0.029;
    private double hyperFocalDistInMM;

    //Constructor
    public DepthOfFieldCalc(Lens lens, double distanceOfObjInMM, double aperture, double circleOfConfusionInM) {
        this.lens = lens;
        this.distanceOfObjInMM = distanceOfObjInMM;
        this.aperture = aperture;
        this.circleOfConfusionInM = circleOfConfusionInM;
        this.hyperFocalDistInMM = Math.pow(this.lens.getFocalLengthInMM(), 2) /
                (this.aperture* this.circleOfConfusionInM);
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

    /**
     * Calculation of Near and Far focal points.
     * Plus, Depth of Field
     *
     * Everything is done in MM
     */
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
