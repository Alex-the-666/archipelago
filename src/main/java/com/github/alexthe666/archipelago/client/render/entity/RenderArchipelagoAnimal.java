package com.github.alexthe666.archipelago.client.render.entity;

import com.github.alexthe666.archipelago.entity.base.EntityArchipelagoAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderArchipelagoAnimal extends RenderLiving<EntityArchipelagoAnimal> {

    public RenderArchipelagoAnimal(ModelBase model, float shadowSize) {
        super(Minecraft.getMinecraft().getRenderManager(), model, shadowSize);
    }

    @Override
    protected void preRenderCallback(EntityArchipelagoAnimal animal, float partialTickTime) {
        GL11.glScalef(animal.getRenderSize(), animal.getRenderSize(), animal.getRenderSize());
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityArchipelagoAnimal entity) {
        return new ResourceLocation(entity.getTexture() + ".png");
    }
}
