package atc.messages;

public enum MessageOpCodes {
	TICK, // It is time to tick. Sent by leader
	STATEMESSAGE, // Contains the GameState. Sent by new membership processes 
	TURN,   // GameControl Command. Turn the given plane direction 
	SET_HEIGHT_GOAL, // Set new Objective Height on give plane 
	NEW_PLANE, // Introduce New Plane on the map. 
	CHAT, // Chat message
}
