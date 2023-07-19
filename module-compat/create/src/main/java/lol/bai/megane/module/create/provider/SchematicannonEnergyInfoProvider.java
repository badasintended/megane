package lol.bai.megane.module.create.provider;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import lol.bai.megane.api.provider.EnergyInfoProvider;
import lol.bai.megane.api.util.BarFormat;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SchematicannonEnergyInfoProvider extends EnergyInfoProvider<SchematicannonBlockEntity> {

    @Override
    public Text getName() {
        return Items.GUNPOWDER.getName();
    }

    @Override
    public BarFormat getFormat() {
        return BarFormat.PERCENT;
    }

    @Override
    public int getColor() {
        return 0x2D2D2D;
    }

    @Nullable
    @Override
    public String getUnit() {
        return "";
    }

}
