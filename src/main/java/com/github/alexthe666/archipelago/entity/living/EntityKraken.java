package com.github.alexthe666.archipelago.entity.living;

import com.github.alexthe666.archipelago.entity.base.EntityAquaticAnimal;
import com.github.alexthe666.archipelago.entity.living.ai.ArchipelagoAIFindWaterTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Codyr on 05/02/2017.
 */
public class EntityKraken extends EntityAquaticAnimal {

    public EntityKraken(World world) {
        super(world, 3, 25F, 25F, 1, 1, 18, 24, 0, 0);
        this.setSize(0.7F, 0.5F);
        this.suffocates = true;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new ArchipelagoAIFindWaterTarget(this));
        this.tasks.addTask(1, new EntityAILookIdle(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    public int getMaximumAir() {
        return 500;
    }

    @Override
    public boolean isFreeSwimmer() {
        return true;
    }

    @Override
    public double swimSpeed() {
        return 0.025;
    }

    @Override
    public void onSpawn() {
        this.setVariant(0);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        //this.entityDropItem(new ItemStack(Items.FISH, 1, 2), 0);
    }

    @Override
    public String getTexture() {
        return "archipelago:textures/models/kraken/kraken_" + this.getVariant();
    }
}


