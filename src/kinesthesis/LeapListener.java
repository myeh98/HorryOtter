package kinesthesis;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

public class LeapListener extends Listener {

	private LeapController controller;

	private boolean keyTap = false;

	public boolean getKeyTap() {
		return keyTap;
	}

	private boolean swipe = false;

	public boolean getSwipe() {
		return swipe;
	}

	public Vector getAngle() {
		return new Vector(pitch, yaw, roll);
	}

	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;

	public float getRoll() {
		return roll;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public LeapListener(LeapController c) {
		this.controller = c;
	}

	public void onFrame(Controller controller) {
		this.controller.onUpdate();
		Frame frame = controller.frame();
		if (!frame.hands().isEmpty()) {
			Hand hand = frame.hands().get(0);
			if (hand.isValid()) {
				pitch = hand.direction().pitch();
				yaw = hand.direction().yaw();
				roll = hand.palmNormal().roll();
			}
		}
		keyTap = false;
		swipe = false;
		for (Gesture gesture : frame.gestures()) {
			switch (gesture.type()) {
			case TYPE_KEY_TAP:
				System.out.println("TAP");
				keyTap = true;
				break;
			case TYPE_SWIPE:
				System.out.println("SWIPE");
				swipe = true;
				break;
			default:
				// Handle unrecognized gestures
				break;
			}
		}
	}
}
