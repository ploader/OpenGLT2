package renderEngine;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class WindowManager {

	private final static int width = 1200;
	private final static int height = 700;
	private final static String title = "3D Renderer";
	private static long window;
	
	public static long getWindow() {
		return window;
	}

	public static void createWindow() {
		
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}

		GLFW.glfwInitHint(GLFW.GLFW_VERSION_MINOR, 2);
		GLFW.glfwInitHint(GLFW.GLFW_VERSION_MINOR, 3);
//		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,GLFW.GLFW_FALSE);
				
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		if(window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		try(MemoryStack stack = stackPush()){
			IntBuffer pHeight = stack.mallocInt(1);
			IntBuffer pWidth = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0))/2,
					(vidmode.height() - pHeight.get(0))/2
			);
		}
			
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
	}
	
	public static void updateWindow() {
		reshape(width,height);
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
	
	private static void reshape(int w, int h) {
		GL11.glViewport(0, 0, w, h);
	}

	public static void closeWindow() {
		GLFW.glfwDestroyWindow(window);
	}
	
}