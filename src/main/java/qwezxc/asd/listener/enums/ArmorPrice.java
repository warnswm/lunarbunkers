package qwezxc.asd.listener.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public
enum ArmorPrice {
    ProtectionI(1, 125),
    ProtectionII(2, 375),
    ProtectionIII(3, 415),
    ProtectionIV(4, 475);
    int enchantlevel;
    int price;

    public static final ArmorPrice[] ALL = values();
    public static int getArmorEnchantByInt(int enchantlevel) {

        for (ArmorPrice armorPrice : ALL)
            if (armorPrice.getEnchantlevel() == enchantlevel)
                return armorPrice.getPrice();

        return 0;

    }


}
