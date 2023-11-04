package net.brazier_modding.justutilities.mixin.accessors;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemBlockRenderTypes.class)
public interface ItemBlockRenderTypesAccessor {

	@Final
	@Accessor("TYPE_BY_BLOCK")
	static Map<Block, RenderType> justutilities_getTypeByBlock(){ throw new AssertionError(); }

	@Final
	@Accessor("TYPE_BY_FLUID")
	static Map<Fluid, RenderType> justutilities_getTypeByFluid(){ throw new AssertionError(); }
}
