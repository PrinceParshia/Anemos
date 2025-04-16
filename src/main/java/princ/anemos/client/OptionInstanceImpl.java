package princ.anemos.client;

import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.OptionInstance.SliderableValueSet;

import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.function.ToDoubleFunction;

import static princ.anemos.Anemosystem.config;

public class OptionInstanceImpl {
    @Environment(EnvType.CLIENT)
    public enum UnitDouble implements SliderableValueSet<Double> {
        INSTANCE;

        @Override
        public Optional<Double> validateValue(Double double_) {
            return double_ >= config.gamma.min && double_ <= config.gamma.max ? Optional.of(double_) : Optional.empty();
        }

        @Override
        public double toSliderValue(Double double_) {
            return double_ / config.gamma.max;
        }

        @Override
        public Double fromSliderValue(double d) {
            return d * config.gamma.max;
        }

        public <R> SliderableValueSet<R> xmap(final DoubleFunction<? extends R> doubleFunction, final ToDoubleFunction<? super R> toDoubleFunction) {
            return new SliderableValueSet<R>() {
                public Optional<R> validateValue(R object) {
                    Objects.requireNonNull(doubleFunction);
                    return UnitDouble.this.validateValue(toDoubleFunction.applyAsDouble(object)).map(doubleFunction::apply);
                }

                public double toSliderValue(R object) {
                    return OptionInstanceImpl.UnitDouble.this.toSliderValue(toDoubleFunction.applyAsDouble(object));
                }

                public R fromSliderValue(double d) {
                    return (R) doubleFunction.apply(OptionInstanceImpl.UnitDouble.this.fromSliderValue(d));
                }

                public Codec<R> codec() {
                    Objects.requireNonNull(toDoubleFunction);
                    return UnitDouble.this.codec().xmap(doubleFunction::apply, toDoubleFunction::applyAsDouble);
                }
            };
        }

        @Override
        public Codec<Double> codec() {
            return Codec.withAlternative(Codec.doubleRange(config.gamma.min, config.gamma.max), Codec.BOOL, (boolean_) -> boolean_ ? config.gamma.max : config.gamma.min);
        }
    }
}
