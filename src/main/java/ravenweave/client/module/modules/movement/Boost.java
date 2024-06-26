package ravenweave.client.module.modules.movement;

import net.weavemc.api.event.SubscribeEvent;
import ravenweave.client.Raven;
import ravenweave.client.event.TickEvent;
import ravenweave.client.module.Module;
import ravenweave.client.module.setting.impl.DescriptionSetting;
import ravenweave.client.module.setting.impl.SliderSetting;
import ravenweave.client.utils.Utils;

public class Boost extends Module {
    public static SliderSetting multiplier;
    public static SliderSetting time;
    private int i;
    private boolean t;

    public Boost() {
        super("Boost", ModuleCategory.movement);
        this.registerSetting(new DescriptionSetting("20 ticks are in 1 second"));
        this.registerSetting(multiplier = new SliderSetting("Multiplier", 2.0D, 1.0D, 3.0D, 0.05D));
        this.registerSetting(time = new SliderSetting("Time (ticks)", 15.0D, 1.0D, 80.0D, 1.0D));
    }

    public void onEnable() {
        Module timer = Raven.moduleManager.getModuleByClazz(Timer.class);
        if (timer != null && timer.isEnabled()) {
            this.t = true;
            timer.disable();
        }

    }

    public void onDisable() {
        this.i = 0;
        if (Utils.Client.getTimer().timerSpeed != 1.0F) {
            Utils.Client.resetTimer();
        }

        if (this.t) {
            Module timer = Raven.moduleManager.getModuleByClazz(Timer.class);
            if (timer != null)
                timer.enable();
        }

        this.t = false;
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (mc.thePlayer != null) {
            if (this.i == 0) {
                this.i = mc.thePlayer.ticksExisted;
            }

            Utils.Client.getTimer().timerSpeed = (float) multiplier.getInput();
            if ((double) this.i == (double) mc.thePlayer.ticksExisted - time.getInput()) {
                Utils.Client.resetTimer();
                this.disable();
            }
        }
    }
}
