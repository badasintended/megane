package badasintended.megane.api;

public interface MeganeApi {

    default String[] modDependencies() {
        return new String[0];
    }

    /**
     * Register your tooltips here plz.
     */
    void initialize();

}
