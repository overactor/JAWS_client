package jaws.business;

public class ClientInitializer {

	private static boolean initialized;

	public static void init() {

		if(initialized) {
			throw new IllegalStateException("ClientInitializer already initialized");
		}

		initialized = true;
	}

	public static void deinit() {

		if(!initialized) {
			throw new IllegalStateException("ClientInitializer not yet initialized");
		}

		initialized = false;
	}
}
