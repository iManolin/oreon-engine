package engine.renderpipeline.data;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL40.GL_PATCH_VERTICES;
import static org.lwjgl.opengl.GL40.glPatchParameteri;
import static org.lwjgl.opengl.GL40.GL_PATCHES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import engine.core.Util;
import engine.core.Vertex;
import engine.models.data.Patch;

public class PatchVAO {

	private int vbo;
	private int vaoId;
	private int size;	
	
	public PatchVAO()
	{
		vbo = glGenBuffers();
		vaoId = glGenVertexArrays();
		size = 0;
	}
	public void addData(Patch patch, int patchsize)
	{
			size = patch.getVertices().length;
			
			glBindVertexArray(vaoId);
			
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBufferAOS(patch.getVertices()), GL_STATIC_DRAW);
			
			glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.BYTES, 0);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.BYTES, 24);
			glPatchParameteri(GL_PATCH_VERTICES, patchsize);
			
			glBindVertexArray(0);
	}
	
	
	public void draw()
	{
			
			glBindVertexArray(vaoId);
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			
			glDrawArrays(GL_PATCHES, 0, size);
			
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);
	}
	
	public void update(Patch patch)
	{	
		glBindVertexArray(vaoId);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBufferAOS(patch.getVertices()), GL_STATIC_DRAW);
		glBindVertexArray(0);
	}
}
