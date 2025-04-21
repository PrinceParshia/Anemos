package princ.anemos.client;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import princ.anemos.config.AnemosConfig;

import static princ.anemos.client.KeyMappingRegistry.*;

@Environment(EnvType.CLIENT)
public class Anemosystem {
    public static final AnemosConfig config = ConfigApiJava.registerAndLoadConfig(AnemosConfig::new, RegisterType.CLIENT);
    private static final RandomSource random = RandomSource.create();

    public static double getMinGamma = config.gamma.min.get() / 100.0;
    public static double getMaxGamma = config.gamma.max.get() / 100.0;
    private static int gammaTransitionTime;
    private static double targetGamma;
    private static int elapsedGammaTransitionTime;
    private static boolean execGammaTransition;

    private static OptionInstance<Double> gamma() {
        return Minecraft.getInstance().options.gamma();
    }

    private static final float minFnvScale = 0.0F, maxFnvScale = 1.0F;
    private static int fnvTransitionTime;
    public static float getFnvScale;
    private static float targetFnvScale;
    private static float startFnvScale;
    private static int elapsedFnvTransitionTime;
    private static boolean execFnvTransition;
    private static boolean queueFnvDisable;

    private static int rmbTransitionTime;
    private static int rmbDuration;
    private static boolean execRmbTransition;
    private static boolean targetRmbState;

    public static void execGammaKeyAction() {
        if (gammaKey.consumeClick()) {
            if (config.gamma.transition) {
                if (!execGammaTransition) {
                    targetGamma = (gamma().get() != getMaxGamma) ? getMaxGamma : config.gamma.prev;
                    if (targetGamma == getMaxGamma) config.gamma.prev = gamma().get();
                    gammaTransitionTime = config.gamma.transitionTime;
                    elapsedGammaTransitionTime = 0;
                    execGammaTransition = true;
                } else {
                    targetGamma = (targetGamma != getMaxGamma) ? getMaxGamma : config.gamma.prev;
                    gammaTransitionTime = elapsedGammaTransitionTime;
                    elapsedGammaTransitionTime = 0;
                }
            } else {
                targetGamma = (gamma().get() != getMaxGamma) ? getMaxGamma : config.gamma.prev;
                if (targetGamma == getMaxGamma) config.gamma.prev = gamma().get();
                gamma().set(targetGamma);
                config.save();
            }
        }

        if (config.gamma.transition && execGammaTransition) {
            if (gammaTransitionTime <= 0) {
                execGammaTransition = false;
                config.save();
            } else {
                gamma().set(gamma().get() + ((targetGamma - gamma().get()) / gammaTransitionTime));
                gammaTransitionTime--;
                elapsedGammaTransitionTime++;
            }
        }
    }

    public static void execFnvKeyAction() {
        getFnvScale = config.fakeNightVision.enabled.get() ? maxFnvScale : minFnvScale;

        if (fakeNightVisionKey.consumeClick()) {
            if (config.fakeNightVision.transition) {
                if (!execFnvTransition) {
                    boolean toggleFnv = !config.fakeNightVision.enabled.get();
                    startFnvScale = getFnvScale;
                    targetFnvScale = toggleFnv ? maxFnvScale : minFnvScale;
                    fnvTransitionTime = config.fakeNightVision.transitionTime;
                    elapsedFnvTransitionTime = 0;
                    execFnvTransition = true;

                    if (toggleFnv) {
                        config.fakeNightVision.enabled.validateAndSet(true);
                        queueFnvDisable = false;
                    } else {
                        queueFnvDisable = true;
                    }
                }
            } else {
                config.fakeNightVision.enabled.validateAndSet(!config.fakeNightVision.enabled.get());
                getFnvScale = config.fakeNightVision.enabled.get() ? maxFnvScale : minFnvScale;
                config.save();
            }
        }

        if (config.fakeNightVision.transition && execFnvTransition) {
            if (fnvTransitionTime <= 0) {
                getFnvScale = targetFnvScale;
                execFnvTransition = false;

                if (queueFnvDisable) {
                    config.fakeNightVision.enabled.validateAndSet(false);
                    queueFnvDisable = false;
                }

                config.save();
            } else {
                getFnvScale = Mth.clamp(Mth.lerp((float) elapsedFnvTransitionTime / fnvTransitionTime, startFnvScale, targetFnvScale), minFnvScale, maxFnvScale);
                fnvTransitionTime--;
                elapsedFnvTransitionTime++;
            }
        }
    }

    public static void execRmbKeyAction() {
        if (removeBlindnessKey.consumeClick()) {
            if (config.removeBlindness.transition) {
                if (!execRmbTransition) {
                    execRmbTransition = true;
                    targetRmbState = !config.removeBlindness.enabled.get();
                    rmbTransitionTime = 0;
                    rmbDuration = config.removeBlindness.transitionTime + random.nextInt(10);
                }
            } else {
                targetRmbState = !config.removeBlindness.enabled.get();
                config.removeBlindness.enabled.validateAndSet(!config.removeBlindness.enabled.get());
                config.save();
            }
        }

        if (config.removeBlindness.transition && execRmbTransition) {
            rmbTransitionTime++;

            if (random.nextFloat() < (1.0F - ((float) rmbTransitionTime / rmbDuration))) {
                config.removeBlindness.enabled.validateAndSet(!config.removeBlindness.enabled.get());
            }

            if (rmbTransitionTime >= rmbDuration) {
                config.removeBlindness.enabled.validateAndSet(targetRmbState);
                execRmbTransition = false;
                config.save();
            }
        }
    }
}