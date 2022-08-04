package miku.protector.mixins;

import com.chaoswither.chaoswither;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.items.ItemChaosGodSword;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

import static com.chaoswither.event.ChaosUpdateEvent.isGod1;
import static com.chaoswither.event.ChaosUpdateEvent.isWitherWorld;

@Mixin(value = ChaosUpdateEvent.class)
public abstract class MixinChaosUpdateEvent {

    /**
     * @author mcst12345
     * @reason Protect
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) {
        if(isWitherWorld(entity.world))return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() instanceof ItemChaosGodSword) {
                    return true;
                }
            }

            if (isGod1(player)) {
                return true;
            }

            Collection<PotionEffect> effects = player.getActivePotionEffects();
            if (effects.size() > 0) {
                for (PotionEffect effect : effects) {
                    if (effect != null) {
                        if (effect.getPotion() == chaoswither.INVINCIBLE) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Inject(at=@At("HEAD"),method = "onEntityJoinWorld", cancellable = true)
    public void onEntityJoinWorld(EntityJoinWorldEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onTick(Lnet/minecraftforge/fml/common/gameevent/TickEvent$WorldTickEvent;)V", cancellable = true)
    public void onTick(TickEvent.WorldTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onServerTick", cancellable = true)
    public void onServerTick(TickEvent.ServerTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onWorldTickEvent", cancellable = true)
    public void onWorldTickEvent(TickEvent.WorldTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onLivingUpdate", cancellable = true)
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event, CallbackInfo ci){
        ci.cancel();
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static void setTimeStop(Minecraft mc, EntityLivingBase player){}
}
