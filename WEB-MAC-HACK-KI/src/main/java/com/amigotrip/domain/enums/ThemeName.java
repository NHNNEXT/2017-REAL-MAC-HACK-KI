package com.amigotrip.domain.enums;

/**
 * Created by Woohyeon on 2018. 1. 3..
 */
public enum ThemeName {
    Art {
        @Override
        public String toString() {
            return "Art & Culture";
        }
    },
    Nature,
    Healing,
    Foods {
        @Override
        public String toString() {
            return "Food & Drink";
        }
    },
    Sports,
    Music,
    Nightlife,
    Concert
}
