
package SW9.presentations;

import SW9.utility.colors.Color;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXSpinner;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import static java.lang.Thread.State.TERMINATED;

public class BackgroundThreadEntryPresentation extends AnchorPane {

    private final Color color = Color.GREY;
    private final Color.Intensity colorIntensity = Color.Intensity.I400;
    private final Thread thread;

    public BackgroundThreadEntryPresentation(final Thread thread) {
        this.thread = thread;

        new EcdarFXMLLoader().loadAndGetController("BackgroundThreadEntryPresentation.fxml", this);

        initializeRippler();
        initializeBackground();
        initializeLabel();
        initializeThreadRunningCheck();
    }

    private void initializeThreadRunningCheck() {
        final Runnable check = () -> {
            final JFXSpinner spinner = (JFXSpinner) lookup("#spinner");

            if (thread.isAlive()) {
                spinner.setOpacity(1);
            } else {
                spinner.setOpacity(0);
            }
        };

        new Thread(() -> {
            while (thread != null && !thread.getState().equals(TERMINATED)) {
                check.run();
                Thread.yield();
            }
        }).start();
    }

    private void initializeLabel() {
        final Label label = (Label) lookup("#label");

        label.setTextFill(color.getTextColor(colorIntensity.next(-5)));

        if (thread != null) {
            label.setText(thread.getName());
        }
    }

    private void initializeRippler() {
        final JFXRippler rippler = (JFXRippler) lookup("#rippler");

        rippler.setMaskType(JFXRippler.RipplerMask.RECT);
        rippler.setRipplerFill(color.getColor(colorIntensity));
        rippler.setPosition(JFXRippler.RipplerPos.BACK);
    }

    private void initializeBackground() {
        setBackground(new Background(new BackgroundFill(
                color.getColor(colorIntensity.next(-5)),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        setBorder(new Border(new BorderStroke(
                color.getColor(colorIntensity.next(-5).next(2)),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0, 0, 1, 0)
        )));
    }

}
