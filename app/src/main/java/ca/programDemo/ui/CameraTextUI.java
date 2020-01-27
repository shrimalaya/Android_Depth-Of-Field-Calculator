package ca.programDemo.ui;

import ca.programDemo.model.DepthOfFieldCalc;
import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Sample (incomplete) text UI to interact with the user.
 * You may change any part of this!
 */
public class CameraTextUI {
    private static final double COC = 0.029;    // "Circle of Confusion" for a "Full Frame" camera
    private LensManager manager;
    private Scanner in = new Scanner(System.in);// Read from keyboard


    public CameraTextUI(LensManager manager) {
        // Accept and store a reference to the lens manager (the model)
        // (this design is called "dependency injection")
        this.manager = manager;

        // Populate lenses (Make, max aperture (smallest supported F number), focal length [mm]):
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }

    public void show() {
        // BEGIN SAMPLE USING SCREEN AND KEYBOARD:

        int isDone = 0;
        while(isDone!=-1) {
            System.out.println("Lenses to pick from: ");
            int count = 0;
            for(Lens lens: manager) {
                System.out.println("    " + (count++) + ". " + lens);
            }
            System.out.println("    (-1 to exit)");
            System.out.print(": ");
            int choice = in.nextInt();

            isDone = choice;

            if(isDone==-1)
            {
                //do nothing
                continue;
            }
            else if(choice<0 ||  choice> (manager.getManagerSize()-1))
            {
                System.out.println("ERROR: Invalid lens index");
            }
            else
            {
                double getAperture = takeAperture(choice);
                if(getAperture!=-1)
                {
                    double getDistance = takeDistance();
                    Lens DoF_lens = null;
                    int counter = 0;
                    for(Lens temp:manager){
                        if(counter==choice)
                            DoF_lens=temp;
                        counter++;
                    }
                    DepthOfFieldCalc object = new DepthOfFieldCalc(DoF_lens, getDistance*1000,
                                                                    getAperture);
                    String nearFP = formatM(object.getNearFocalPoint()/1000);
                    String farFP = formatM(object.getFarFocalPoint()/1000);
                    String hyperFocalP = formatM(object.getHyperFocalDistInMM()/1000);
                    String depthOfField = formatM(object.getDepthOfFieldInMM()/1000);

                    System.out.println("In focus: " + nearFP + "m to " + farFP + "m [DOF = " + depthOfField + "m]");
                    System.out.println("Hyperfocal point: " + hyperFocalP + "m");
                }
            }
        }
    }

    public double takeDistance() {
        System.out.print("Distance to subject [m]: ");
        double getDistance = in.nextDouble();
        while(getDistance<0)
        {
            System.out.println("WARNING! Please enter a positive distance.");
            System.out.print("Distance to subject [m]: ");
            getDistance = in.nextDouble();
        }
        return getDistance;
    }

    public double takeAperture(int choice) {
        System.out.print("Aperture [the F number]: ");
        Lens lens = null;
        int counter = 0;
        for(Lens temp:manager){
            if(counter==choice)
                lens=temp;
            counter++;
        }
        double F = in.nextDouble();
        if(F<0)
        {
            System.out.println("ERROR: Enter a positive value smaller than the maximum aperture");
            return -1;
        }
        else if(F < lens.getMaxAperture())
        {
            System.out.println("ERROR: This aperture is not possible with this lens");
            return -1;
        }
        else
        {
            return F;
        }
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}