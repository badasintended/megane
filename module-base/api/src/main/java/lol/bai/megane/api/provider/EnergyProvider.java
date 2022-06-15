package lol.bai.megane.api.provider;

public abstract class EnergyProvider<T> extends AbstractProvider<T> {

    public boolean hasEnergy() {
        return true;
    }

    public abstract long getStored();

    public abstract long getMax();

}
