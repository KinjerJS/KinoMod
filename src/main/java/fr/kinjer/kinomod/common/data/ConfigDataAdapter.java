package fr.kinjer.kinomod.common.data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface ConfigDataAdapter<T extends ConfigDataAdapter.DataSet> {

    public Iterable<T> getDefaultDataSets();

    public String getDataFileName();

    public String getDescription();

    @Nullable
    public Optional<T> appendDataSet(String str);

    public void resetRegistry();

    default public LoadPhase getLoadPhase() {
        return LoadPhase.PRE_INIT;
    }

    @Nonnull
    default public String[] serializeDataSet() {
        List<String> defaultValueStrings = new LinkedList<>();
        for (T data : getDefaultDataSets()) {
            defaultValueStrings.add(data.serialize());
        }
        String[] out = new String[defaultValueStrings.size()];
        return defaultValueStrings.toArray(out);
    }

    public static enum LoadPhase {

        PRE_INIT,
        INIT,
        POST_INIT

    }

    public static interface DataSet {

        @Nonnull
        public String serialize();

        public static class StringElement implements DataSet {

            private final String str;

            public StringElement(@Nonnull String str) {
                this.str = str;
            }

            @Nonnull
            @Override
            public String serialize() {
                return str;
            }
        }
    }
}
