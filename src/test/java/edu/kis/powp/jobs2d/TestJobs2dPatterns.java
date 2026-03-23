package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.DriverToPanelAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener figure1Listener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 1);

		SelectTestFigureOptionListener figure2Listener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), 2);

		application.addTest("Figure Joe 1", figure1Listener);
		application.addTest("Figure Joe 2", figure2Listener);

		application.addTest("Figure Jane demo", (ActionEvent e) -> {
			edu.kis.powp.jobs2d.drivers.adapter.AbstractDriverAdapter adapter = new edu.kis.powp.jobs2d.drivers.adapter.AbstractDriverAdapter();
			edu.kis.powp.jobs2d.magicpresets.FiguresJane.figureScript(adapter);
		});

		application.addTest("Draw shapes (ShapeFactory)", (ActionEvent e) -> {
			Job2dDriver current = DriverFeature.getDriverManager().getCurrentDriver();
			if (current == null) return;
			edu.kis.powp.command.ComplexCommand rect = edu.kis.powp.command.ShapeFactory.createRectangle(current, 10, 10, 80, 40);
			rect.execute();
			edu.kis.powp.command.ComplexCommand tri = edu.kis.powp.command.ShapeFactory.createTriangle(current, 150, 20, 200, 80, 100, 80);
			tri.execute();
		});

		application.addTest("Record FiguresJoe and replay", (ActionEvent e) -> {
			Job2dDriver current = DriverFeature.getDriverManager().getCurrentDriver();
			if (current == null) return;
			edu.kis.powp.command.CommandRecordingDriver recorder = new edu.kis.powp.command.CommandRecordingDriver(current);
			try {
				edu.kis.powp.jobs2d.magicpresets.FiguresJoe.figureScript1(recorder);
			} catch (Throwable ex) {
				recorder.setPosition(0, 0);
				recorder.operateTo(10, 10);
				recorder.operateTo(20, 0);
				recorder.operateTo(0, 0);
			}
			edu.kis.powp.command.ComplexCommand recorded = recorder.getRecordedCommand();
			recorded.execute();
		});
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DriverToPanelAdapter();
		DriverFeature.addDriver("Buggy Simulator", testDriver);

		Job2dDriver basicLineAdapter = new LineDrawerAdapter(LineFactory::getBasicLine);
		DriverFeature.addDriver("Line Adapter - Basic", basicLineAdapter);
		
		Job2dDriver specialLineAdapter = new LineDrawerAdapter(LineFactory::getSpecialLine);
		DriverFeature.addDriver("Line Adapter - Special", specialLineAdapter);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
//	private static void setupDefaultDrawerVisibilityManagement(Application application) {
//		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
//		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
//			new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
//		defaultDrawerWindow.setVisible(true);
//	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
//				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
