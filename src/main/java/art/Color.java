package art;

public enum Color {
    // Basic colors
    WHITE(1, 1, 1),
    BLACK(0, 0, 0),
    RED(1, 0, 0),
    GREEN(0, 1, 0),
    BLUE(0, 0, 1),

    // Secondary colors
    MAGENTA(1, 0, 1),
    CYAN(0, 1, 1),
    YELLOW(1, 1, 0),
    GREY(0.5, 0.5, 0.5),

    // Other colors
    LIGHT_GREY(0.8, 0.8, 0.8);


    public final double red;
    public final double green;
    public final double blue;
    private Color(double red, double green, double blue) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }
}
