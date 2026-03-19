package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Simple test demonstrating that DriverCommand implementations are decoupled
 * from concrete Job2dDriver implementations and can be reused with different receivers.
 */
public class DriverCommandTest {

    public static void main(String[] args) {
        Job2dDriver driver1 = new Job2dDriver() {
            @Override
            public void setPosition(int x, int y) {
                System.out.printf("[driver1] Set position to %d, %d%n", x, y);
            }

            @Override
            public void operateTo(int x, int y) {
                System.out.printf("[driver1] Operate to %d, %d%n", x, y);
            }
        };

        Job2dDriver driver2 = new Job2dDriver() {
            @Override
            public void setPosition(int x, int y) {
                System.out.printf("[driver2] Set position to %d, %d%n", x, y);
            }

            @Override
            public void operateTo(int x, int y) {
                System.out.printf("[driver2] Operate to %d, %d%n", x, y);
            }
        };

        SetPositionCommand setPos = new SetPositionCommand(10, 20);
        OperateToCommand opTo = new OperateToCommand(30, 40);

        setPos.setDriver(driver1);
        opTo.setDriver(driver1);
        setPos.execute();
        opTo.execute();

        setPos.setDriver(driver2);
        opTo.setDriver(driver2);
        setPos.execute();
        opTo.execute();

        SetPositionCommand setPosWithDriver = new SetPositionCommand(5, 6, driver1);
        setPosWithDriver.execute();
    }
}

