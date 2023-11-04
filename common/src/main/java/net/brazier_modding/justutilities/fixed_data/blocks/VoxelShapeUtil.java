package net.brazier_modding.justutilities.fixed_data.blocks;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.brazier_modding.justutilities.fixed_data.FixedData;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class VoxelShapeUtil extends FixedData {

	private static final Map<ResourceLocation, VoxelShape> VOXEL_SHAPES = new HashMap<>();

	public static VoxelShape getShape(ResourceLocation key){
		return VOXEL_SHAPES.getOrDefault(key, Shapes.block());
	}

	public static void load() {
		VoxelShapeUtil.VOXEL_SHAPES.clear();

		FixedData.load("voxel_shapes/", (dataLocation, pack) -> {
			try(InputStreamReader dataStream = new InputStreamReader(pack.getResource(dataLocation))){
				ResourceLocation keyName = dataLocToKeyName(dataLocation);

				VOXEL_SHAPES.put(keyName, getVoxelShape(dataStream, keyName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	protected static ResourceLocation dataLocToKeyName(ResourceLocation dataLoc){
		String[] splitPath = dataLoc.getPath().split("/");
		return new ResourceLocation(dataLoc.getNamespace(), dataLoc.getPath().substring(dataLoc.getPath().indexOf("/")+1).replace(".json", ""));
	}

	public static VoxelShape getVoxelShape(InputStreamReader dataStream, ResourceLocation name) {
		try (JsonReader reader = new JsonReader(dataStream)) {
			JsonToken token = reader.peek();
			if (token == JsonToken.BEGIN_ARRAY) {
				return new ShapeData(BooleanOp.OR, reader).compile();
			} else if (token == JsonToken.BEGIN_OBJECT) {
				return Shapes.block();
			}
		} catch (Exception e) {
			System.out.println(name);
			e.printStackTrace();
		}
		return Shapes.block();
	}

	public static VoxelShape rotateShape(VoxelShape shape, Direction direction) {
		if(direction == Direction.NORTH) return shape;
		Set<VoxelShape> rotatedShapes = new HashSet<VoxelShape>();

		shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
			y1 = (y1 * 16) - 8; y2 = (y2 * 16) - 8;
			x1 = (x1 * 16) - 8; x2 = (x2 * 16) - 8;
			z1 = (z1 * 16) - 8; z2 = (z2 * 16) - 8;

			if(direction == Direction.EAST)
				rotatedShapes.add(makeBox(8 - z1, y1 + 8, 8 + x1, 8 - z2, y2 + 8, 8 + x2));
			else if(direction == Direction.SOUTH)
				rotatedShapes.add(makeBox(8 - x1, y1 + 8, 8 - z1, 8 - x2, y2 + 8, 8 - z2));
			else if(direction == Direction.WEST)
				rotatedShapes.add(makeBox(8 + z1, y1 + 8, 8 - x1, 8 + z2, y2 + 8, 8 - x2));
			else if(direction == Direction.UP)
				rotatedShapes.add(makeBox(x1 + 8, 8 - z2, 8 + y1, x2 + 8, 8 - z1, 8 + y2));
			else if(direction == Direction.DOWN)
				rotatedShapes.add(makeBox(x1 + 8, 8 + z1, 8 - y2, x2 + 8, 8 + z2, 8 - y1));

		});

		return rotatedShapes.stream().reduce((v1, v2) -> {return Shapes.join(v1, v2, BooleanOp.OR);}).get();
	}

	private static VoxelShape makeBox(double x1, double y1, double z1, double x2, double y2, double z2){
		return Block.box(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x2, x1), Math.max(y2, y1), Math.max(z2, z1));
	}

	private static class ShapeData {
		BooleanOp function;
		List<float[]> boxes = new ArrayList<>();
		List<ShapeData> subShapes = new ArrayList<>();

		public ShapeData(BooleanOp function, JsonReader reader) throws IOException {
			this.function = function;
			reader.beginArray();
			while (reader.hasNext()) {
				JsonToken token = reader.peek();
				if (token == JsonToken.BEGIN_ARRAY) {
					reader.beginArray();
					float[] box = new float[6];
					for (int i = 0; i < 6; i++)
						box[i] = (float) reader.nextDouble() / 16F;
					this.boxes.add(box);
					reader.endArray();
				} else if (token == JsonToken.BEGIN_OBJECT) {
					reader.beginObject();
					BooleanOp function2 = getFuncByName(reader.nextName());
					subShapes.add(new ShapeData(function2, reader));
					reader.endObject();
				} else {
					reader.skipValue();
				}
			}
			reader.endArray();
		}

		public VoxelShape compile() {
			return Stream.concat(
					this.boxes.stream().map(data -> Shapes.box(data[0], data[1], data[2], data[3], data[4], data[5])),
					subShapes.stream().map(ShapeData::compile)
			).reduce((a, b) -> Shapes.join(a, b, function)).orElseGet(Shapes::empty);
		}
	}

	private static BooleanOp getFuncByName(String name) {
		return switch (name) {
			case "FALSE" -> BooleanOp.FALSE;
			case "NOT_OR" -> BooleanOp.NOT_OR;
			case "ONLY_SECOND" -> BooleanOp.ONLY_SECOND;
			case "NOT_FIRST" -> BooleanOp.NOT_FIRST;
			case "ONLY_FIRST" -> BooleanOp.ONLY_FIRST;
			case "NOT_SECOND" -> BooleanOp.NOT_SECOND;
			case "NOT_SAME" -> BooleanOp.NOT_SAME;
			case "NOT_AND" -> BooleanOp.NOT_AND;
			case "AND" -> BooleanOp.AND;
			case "SAME" -> BooleanOp.SAME;
			case "SECOND" -> BooleanOp.SECOND;
			case "CAUSES" -> BooleanOp.CAUSES;
			case "FIRST" -> BooleanOp.FIRST;
			case "CAUSED_BY" -> BooleanOp.CAUSED_BY;
			case "OR" -> BooleanOp.OR;
			case "TRUE" -> BooleanOp.TRUE;
			default -> BooleanOp.OR;
		};
	}
}