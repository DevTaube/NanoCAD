package devtaube.nanocad.editor;

public enum Axis {

    X, Y, Z;

    public static Axis parseAxis(CharSequence input) throws IllegalArgumentException {
        switch(input.charAt(0)) {
            case 'x': case 'X': return X;
            case 'y': case 'Y': return Y;
            case 'z': case 'Z': return Z;
        }
        throw new IllegalArgumentException("Unable to parse input");
    }

    @Override
    public String toString() {
        switch(this) {
            case X: return "x";
            case Y: return "y";
            case Z: return "z";
        }
        return name();
    }

}
