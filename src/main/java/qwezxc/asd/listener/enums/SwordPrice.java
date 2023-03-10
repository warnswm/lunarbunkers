package qwezxc.asd.listener.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SwordPrice {
    ShapnessI(1, 125),
    ShapnessII(2, 175),
    ShapnessIII(3, 235),
    ShapnessIV(4, 310),
    ShapnessV(5, 435);
    int enchantlevel;
    int price;

    public static final SwordPrice[] ALL = values();
    public static int getSwordEnchantByInt(int enchantlevel) {

        for (SwordPrice swordPrice : ALL)
            if (swordPrice.getEnchantlevel() == enchantlevel)
                return swordPrice.getPrice();

        return 0;

    }

}
