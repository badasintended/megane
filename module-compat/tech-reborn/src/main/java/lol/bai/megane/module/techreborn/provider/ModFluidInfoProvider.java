package lol.bai.megane.module.techreborn.provider;

import lol.bai.megane.api.registry.ClientRegistrar;
import techreborn.init.ModFluids;

import static techreborn.init.ModFluids.BERYLLIUM;
import static techreborn.init.ModFluids.BIOFUEL;
import static techreborn.init.ModFluids.CALCIUM;
import static techreborn.init.ModFluids.CALCIUM_CARBONATE;
import static techreborn.init.ModFluids.CARBON;
import static techreborn.init.ModFluids.CARBON_FIBER;
import static techreborn.init.ModFluids.CHLORITE;
import static techreborn.init.ModFluids.COMPRESSED_AIR;
import static techreborn.init.ModFluids.DEUTERIUM;
import static techreborn.init.ModFluids.DIESEL;
import static techreborn.init.ModFluids.ELECTROLYZED_WATER;
import static techreborn.init.ModFluids.GLYCERYL;
import static techreborn.init.ModFluids.HELIUM;
import static techreborn.init.ModFluids.HELIUM3;
import static techreborn.init.ModFluids.HELIUMPLASMA;
import static techreborn.init.ModFluids.HYDROGEN;
import static techreborn.init.ModFluids.LITHIUM;
import static techreborn.init.ModFluids.MERCURY;
import static techreborn.init.ModFluids.METHANE;
import static techreborn.init.ModFluids.NITROCOAL_FUEL;
import static techreborn.init.ModFluids.NITROFUEL;
import static techreborn.init.ModFluids.NITROGEN;
import static techreborn.init.ModFluids.NITROGEN_DIOXIDE;
import static techreborn.init.ModFluids.NITRO_CARBON;
import static techreborn.init.ModFluids.NITRO_DIESEL;
import static techreborn.init.ModFluids.OIL;
import static techreborn.init.ModFluids.POTASSIUM;
import static techreborn.init.ModFluids.SILICON;
import static techreborn.init.ModFluids.SODIUM;
import static techreborn.init.ModFluids.SODIUM_PERSULFATE;
import static techreborn.init.ModFluids.SODIUM_SULFIDE;
import static techreborn.init.ModFluids.SULFUR;
import static techreborn.init.ModFluids.SULFURIC_ACID;
import static techreborn.init.ModFluids.TRITIUM;
import static techreborn.init.ModFluids.WOLFRAMIUM;

public class ModFluidInfoProvider {

    private final ClientRegistrar registrar;

    public ModFluidInfoProvider(ClientRegistrar registrar) {
        this.registrar = registrar;
    }

    public void registerAll() {
        register(BERYLLIUM, 0x082507);
        register(CALCIUM, 0x6D4539);
        register(CALCIUM_CARBONATE, 0x5A250C);
        register(CARBON, 0x0D0F0B);
        register(CARBON_FIBER, 0x0A0B0D);
        register(CHLORITE, 0x255D5F);
        register(COMPRESSED_AIR, 0x393D42);
        register(DEUTERIUM, 0xB5BA16);
        register(DIESEL, 0x97681D);
        register(ELECTROLYZED_WATER, 0x0A1546);
        register(GLYCERYL, 0x084942);
        register(HELIUM, 0xB6B850);
        register(HELIUM3, 0xB8BA5A);
        register(HELIUMPLASMA, 0xDFDB95);
        register(HYDROGEN, 0x09174E);
        register(LITHIUM, 0x41739C);
        register(MERCURY, 0x858688);
        register(METHANE, 0x881258);
        register(NITRO_CARBON, 0x431E1A);
        register(NITRO_DIESEL, 0xD1553B);
        register(NITROCOAL_FUEL, 0x0A201D);
        register(NITROFUEL, 0xA2B30C);
        register(NITROGEN, 0x1F8A81);
        register(NITROGEN_DIOXIDE, 0x5FBDB7);
        register(OIL, 0x171717);
        register(POTASSIUM, 0x748A8B);
        register(SILICON, 0x180A24);
        register(SODIUM, 0x0A2276);
        register(SODIUM_SULFIDE, 0x825F0C);
        register(SODIUM_PERSULFATE, 0x0B4345);
        register(SULFUR, 0x843030);
        register(SULFURIC_ACID, 0x86888B);
        register(TRITIUM, 0xAF1618);
        register(WOLFRAMIUM, 0x291F35);
        register(BIOFUEL, 0x156718);
    }

    private void register(ModFluids modFluid, int color) {
        registrar.addFluidInfo(modFluid.getFluid(), color, modFluid.getBlock().getName());
        registrar.addFluidInfo(modFluid.getFlowingFluid(), color, modFluid.getBlock().getName());
    }

}
