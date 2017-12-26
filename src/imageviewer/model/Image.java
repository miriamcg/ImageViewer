package imageviewer.model;

public interface Image {
    byte[] bitmap();

    public Image next();
    public Image prev();
}
