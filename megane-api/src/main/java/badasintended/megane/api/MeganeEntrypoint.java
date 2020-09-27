package badasintended.megane.api;

public interface MeganeEntrypoint {

    String[] EMPTY_DEP = new String[0];

    default String[] dependencies() {
        return EMPTY_DEP;
    }

    /**
     * Register your tooltips here plz.
     */
    void initialize();

}
