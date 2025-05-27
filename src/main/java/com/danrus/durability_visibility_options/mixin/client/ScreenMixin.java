package com.danrus.durability_visibility_options.mixin.client;

import com.danrus.durability_visibility_options.client.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    private void customApplyBlur(Screen i, float v){
        //? if >=1.21.2
        i.applyBlur();
        //? if <1.21.2 >1.20.4
        /*i.applyBlur(v);*/
        return;
    }

    @Redirect(
            method = "renderBackground",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21.2
                    target = "Lnet/minecraft/client/gui/screen/Screen;applyBlur()V"
                    //? if <1.21.2 >1.20.4
                    /*target = "Lnet/minecraft/client/gui/screen/Screen;applyBlur(F)V"*/
                    //? if <=1.20.4
                    /*target = "Lnet/minecraft/client/gui/screen/Screen;renderBackgroundTexture(Lnet/minecraft/client/gui/DrawContext;)V"*/
            )
    )
    //? if >1.20.4 {
    private void applyBlurMixin(Screen instance/*? if <1.21.2 {*//*,float v*//*?}*/)
    //?}
    //? if <=1.20.4
    /*private void renderInGameBackgroundMixin(Screen instance, DrawContext graphics)*/
        {
            //? if >=1.21.2
            float v = 0.0f;
//            if (!ModConfig.isOpen || MinecraftClient.getInstance().player == null) {
//                //? if >1.20.4
//                customApplyBlur(instance, v);
//                //? if <=1.20.4
//                /*instance.renderBackgroundTexture(graphics);*/
//            }
        }
    }
