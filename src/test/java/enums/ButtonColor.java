package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonColor {
    // quickly checked RBG and corresponding colors here: https://www.w3schools.com/colors/colors_converter.asp
    RED(1, 224, 0, 50),
    BLUE(2, 0, 145, 234),
    GREEN(3, 18, 199, 0),
    VIOLET(4, 156, 39, 176),
    WHITE(5, 255, 255, 255),
    GREY(6, 97, 97, 97),
    UNDEFINED(0, 0, 0, 0);

    private int order; // order in the app
    private int red;  // RGB red
    private int green; // RGB green
    private int blue; // RGB blue
}
