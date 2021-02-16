package pl.com.harta;

public enum Category {
    HEAVY_METAL("Heavy Metal"),
    ROCK("Rock"),
    RB("R&B"),
    POP("Pop"),
    JAZZ("Jazz"),
    ALTERNATIVE("Alternative"),
    DISCO("Disco"),
    OTHER("Other");

public final String label;

    Category(String label) {
        this.label = label;
    }

    public static Category valueOfLabel(String label) {
        for (Category category : values()) {
            if (category.label.equals(label)) {
                return category;
            }
        }
        return null;
    }

    public String toString() {
        return label;
    }
}
