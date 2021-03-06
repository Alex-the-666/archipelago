package com.github.alexthe666.archipelago.entity.living;

import com.github.alexthe666.archipelago.entity.base.EntityAquaticAnimal;
import com.github.alexthe666.archipelago.entity.living.ai.ArchipelagoAIFindWaterTarget;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

/**
 * Created by Codyr on 04/02/2017.
 */
public class EntitySaltwaterCrocodile extends EntityAquaticAnimal {
    public EntitySaltwaterCrocodile(World world) {
        super(world, 4, 0.5F, 1.5F, 1, 1, 14, 16, 0.1, 0.1);
        this.setSize(0.7F, 0.5F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new ArchipelagoAIFindWaterTarget(this));
        this.tasks.addTask(0, new EntityAIWander(this, 1D));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1D, false));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
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
    public int getMaximumAir() {
        return 1200;
    }

    @Override
    public void onSpawn() {
        this.setVariant(new Random().nextInt(3));
    }



    @Override
    public String getTexture() {
        return "archipelago:textures/models/saltwater_crocodile/saltwater_crocodile_" + this.getVariant();
    }
}

