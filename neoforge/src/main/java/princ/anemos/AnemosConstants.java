package princ.anemos;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import princ.anemos.config.AnemosConfig;
import princ.anemos.config.AnemosInternalConfig;

import static princ.anemos.util.MobEffectUtil.*;
import static princ.anemos.util.UnitValueConverter.*;

public class AnemosConstants {
    public static final String NAMESPACE = "anemos";
    public static final String NAME = "Anemos";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final String KEY_CATEGORY = "key.categories." + NAMESPACE;
    public static final String GENERIC_KEY_NAMESPACE = "key." + NAMESPACE;
    public static final String GENERIC_CONFIG_TRANSLATION_PREFIX = "config." + NAMESPACE;
    public static final AnemosConfig config = ConfigApiJava.registerAndLoadConfig(AnemosConfig::new, RegisterType.CLIENT);
    public static final AnemosInternalConfig configInternal = ConfigApiJava.registerAndLoadConfig(AnemosInternalConfig::new, RegisterType.SERVER);

    public static OptionInstance<Double> gamma() {
        return Minecraft.getInstance().options.gamma();
    }

    public static ValidatedFloat fnvScale() {
        return config.fakeNightVision.scale;
    }

    private static int gammaTransitionTime;
    private static double targetGamma;
    private static int elapsedGammaTransitionTime;
    private static boolean execGammaTransition;

    public static void adjustGamma(boolean transition) {
        if (transition) {
            if (!execGammaTransition) {
                targetGamma = gamma().get() != fromDoublePercent(config.gamma.toggleValue.get()) ? fromDoublePercent(config.gamma.toggleValue.get()) : fromDoublePercent(configInternal.gamma.prev.get());
                if (targetGamma == fromDoublePercent(config.gamma.toggleValue.get())) configInternal.gamma.prev.validateAndSet(toDoublePercent(gamma().get()));
                gammaTransitionTime = config.gamma.transitionTime;
                elapsedGammaTransitionTime = 0;
                execGammaTransition = true;
            } else {
                targetGamma = targetGamma != fromDoublePercent(config.gamma.toggleValue.get()) ? fromDoublePercent(config.gamma.toggleValue.get()) : fromDoublePercent(configInternal.gamma.prev.get());
                gammaTransitionTime = elapsedGammaTransitionTime;
                elapsedGammaTransitionTime = 0;
            }
        } else {
            targetGamma = gamma().get() != fromDoublePercent(config.gamma.toggleValue.get()) ? fromDoublePercent(config.gamma.toggleValue.get()) : fromDoublePercent(configInternal.gamma.prev.get());
            if (targetGamma == fromDoublePercent(config.gamma.toggleValue.get())) configInternal.gamma.prev.validateAndSet(toDoublePercent(gamma().get()));
            gamma().set(targetGamma);
            config.save();
            configInternal.save();
        }
    }

    public static void handleGammaTransition() {
        if (config.gamma.transition && execGammaTransition) {
            if (gammaTransitionTime <= 0) {
                execGammaTransition = false;
                config.save();
                configInternal.save();
            } else {
                gamma().set(gamma().get() + ((targetGamma - gamma().get()) / gammaTransitionTime));
                gammaTransitionTime--;
                elapsedGammaTransitionTime++;
            }
        }
    }

    private static boolean wasFakeNightVision;

    public static void adjustFakeNightVision() {
        if (fnvScale().get() == configInternal.fakeNightVision.minScale) {
            config.fakeNightVision.enabled.validateAndSet(false);
        }

        if (!config.fakeNightVision.fog) {
            if (hasEffect(MobEffects.NIGHT_VISION) && config.fakeNightVision.enabled.get()) {
                adjustFakeNightVisionScale(config.fakeNightVision.transition);
                wasFakeNightVision = true;
            }

            if (!hasEffect(MobEffects.NIGHT_VISION) && wasFakeNightVision) {
                adjustFakeNightVisionScale(config.fakeNightVision.transition);
                wasFakeNightVision = false;
            }
        }
    }

    private static int fnvTransitionTime;
    private static float targetFnvScale;
    private static int elapsedFnvTransitionTime;
    private static boolean execFnvTransition;

    public static void adjustFakeNightVisionScale(boolean transition) {
        if (transition) {
            if (!execFnvTransition) {
                if (!config.fakeNightVision.enabled.get()) {
                    targetFnvScale = fromFloatPercent(fnvScale().get());
                    fnvScale().validateAndSet(fromFloatPercent(configInternal.fakeNightVision.minScale));
                    config.fakeNightVision.enabled.validateAndSet(true);
                } else {
                    targetFnvScale = fromFloatPercent(configInternal.fakeNightVision.minScale);
                    configInternal.fakeNightVision.prev.validateAndSet(fnvScale().get());
                }

                fnvTransitionTime = config.fakeNightVision.transitionTime;
                elapsedFnvTransitionTime = 0;
                execFnvTransition = true;
            } else {
                targetFnvScale = targetFnvScale != fromFloatPercent(configInternal.fakeNightVision.minScale) ? fromFloatPercent(configInternal.fakeNightVision.minScale) : fromFloatPercent(configInternal.fakeNightVision.prev.get());
                fnvTransitionTime = elapsedFnvTransitionTime;
                elapsedFnvTransitionTime = 0;
            }
        } else {
            config.fakeNightVision.enabled.validateAndSet(!config.fakeNightVision.enabled.get());
            config.save();
        }
    }

    public static void handleFakeNightVisionScaleTransition() {
        if (config.fakeNightVision.transition && execFnvTransition) {
            if (fnvTransitionTime <= 0) {
                execFnvTransition = false;
                if (targetFnvScale == fromFloatPercent(configInternal.fakeNightVision.minScale)) {
                    fnvScale().validateAndSet(configInternal.fakeNightVision.prev.get());
                }
                config.save();
                configInternal.save();
            } else {
                fnvScale().validateAndSet(toFloatPercent(fromFloatPercent(fnvScale().get()) + ((targetFnvScale - fromFloatPercent(fnvScale().get())) / fnvTransitionTime)));
                fnvTransitionTime--;
                elapsedFnvTransitionTime++;
            }
        }
    }

    private static final RandomSource random = RandomSource.create();

    private static int rmbTransitionTime;
    private static int rmbTransitionDuration;
    private static boolean execRmbTransition;
    private static boolean targetRmbState;

    public static void adjustRemoveBlindness(boolean transition) {
        if (transition) {
            if (!execRmbTransition) {
                targetRmbState = !config.removeBlindness.enabled.get();
                rmbTransitionTime = 0;
                rmbTransitionDuration = config.removeBlindness.transitionTime + random.nextInt(10);
                execRmbTransition = true;
            }
        } else {
            config.removeBlindness.enabled.validateAndSet(!config.removeBlindness.enabled.get());
            config.save();
        }
    }

    public static void handleRemoveBlindnessTransition() {
        if (config.removeBlindness.transition && execRmbTransition) {
            rmbTransitionTime++;

            if (random.nextFloat() < (1.0F - ((float) rmbTransitionTime / rmbTransitionDuration))) {
                config.removeBlindness.enabled.validateAndSet(!config.removeBlindness.enabled.get());
            }

            if (rmbTransitionTime >= rmbTransitionDuration) {
                config.removeBlindness.enabled.validateAndSet(targetRmbState);
                execRmbTransition = false;
                config.save();
            }
        }
    }

    private static int rmdTransitionTime;
    private static int rmdTransitionDuration;
    private static boolean execRmdTransition;
    private static boolean targetRmdState;

    public static void adjustRemoveDarkness(boolean transition) {
        if (transition) {
            if (!execRmdTransition) {
                targetRmdState = !config.removeDarkness.enabled.get();
                rmdTransitionTime = 0;
                rmdTransitionDuration = config.removeDarkness.transitionTime + random.nextInt(10);
                execRmdTransition = true;
            }
        } else {
            config.removeDarkness.enabled.validateAndSet(!config.removeDarkness.enabled.get());
            config.save();
        }
    }

    public static void handleRemoveDarknessTransition() {
        if (config.removeDarkness.transition && execRmdTransition) {
            rmdTransitionTime++;

            if (random.nextFloat() < (1.0F - ((float) rmdTransitionTime / rmdTransitionDuration))) {
                config.removeDarkness.enabled.validateAndSet(!config.removeDarkness.enabled.get());
            }

            if (rmdTransitionTime >= rmdTransitionDuration) {
                config.removeDarkness.enabled.validateAndSet(targetRmdState);
                execRmdTransition = false;
                config.save();
            }
        }
    }
}