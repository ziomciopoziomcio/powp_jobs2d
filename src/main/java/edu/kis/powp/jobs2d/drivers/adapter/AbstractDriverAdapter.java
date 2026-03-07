package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class AbstractDriverAdapter extends edu.kis.powp.jobs2d.AbstractDriver {

    public AbstractDriverAdapter() {
        super(0, 0);
    }

    @Override
    public void operateTo(int x, int y) {
        Job2dDriver current = DriverFeature.getDriverManager().getCurrentDriver();
        int prevX = getX();
        int prevY = getY();

        current.setPosition(prevX, prevY);
        current.operateTo(x, y);

        super.setPosition(x, y);
    }
}

