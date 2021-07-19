package fr.kinjer.kinomod.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModConfigProperty {

    String name();

    String category();

    String comment() default "";

    boolean requiresMCRestart() default false;

    boolean requiresWorldRestart() default false;

    boolean requiresSync() default false;

    boolean autoSync() default false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MinMax {
        String min() default "-2147483648";
        String max() default "2147483647";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface ValidValues {

    	String[] values();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface ListRestrictions {

        int maxLength() default 2147483647;

        boolean fixedLength() default false;
    }
}