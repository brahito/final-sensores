package brayan_valeria_taller2;

import de.voidplus.leapmotion.Arm;
import de.voidplus.leapmotion.CircleGesture;
import de.voidplus.leapmotion.Finger;
import de.voidplus.leapmotion.Hand;
import de.voidplus.leapmotion.KeyTapGesture;
import de.voidplus.leapmotion.LeapMotion;
import de.voidplus.leapmotion.ScreenTapGesture;
import de.voidplus.leapmotion.SwipeGesture;
import processing.core.PApplet;
import processing.core.PVector;

public class LeapMotionS {

	private PApplet app;
	private LeapMotion leap;
	private boolean grab;
	private float x, y;
	private Gestos gesto;
	private boolean parar = false;
	private boolean accion = true;

	public LeapMotionS(PApplet app) {
		this.app = app;
		this.leap = new LeapMotion(app);
		this.leap.allowGestures();
	}

	public void detener(int espera) {
		if (parar == false) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					float actual = 0;
					boolean vivo = true;
					accion = false;

					while (vivo) {
						try {
							actual += 0.3;
							if (actual >= espera) {
								vivo = false;
								accion = true;
								parar = false;
							}
							Thread.sleep(50);
						} catch (InterruptedException e) {

						}
					}

				}
			}).start();
		}
		parar = true;
	}

	public void pintar() {
		for (Hand hand : leap.getHands()) {
			hand.draw();
			for (Finger finger : hand.getFingers()) {
				finger.draw();
			}
		}
		// if (getAgarro()) {
		// app.fill(255, 0, 0);
		// } else {
		// app.fill(0, 255, 0);
		// }
		// app.ellipse(getHandX(), getHandY(), 50, 50);
		//
	}

	public void procesosEnEjecucion(int time) {
		if (app.frameCount % time == 0) {
			informacion();
		}
	}

	public void leapOnSwipeGesture(SwipeGesture g, int state) {
		int id = g.getId();
		Finger finger = g.getFinger();
		PVector position = g.getPosition();
		PVector positionStart = g.getStartPosition();
		PVector direction = g.getDirection();
		float speed = g.getSpeed();
		long duration = g.getDuration();
		float durationSeconds = g.getDurationInSeconds();

		switch (state) {
		case 1: // Start
			break;
		case 2: // Update
			break;
		case 3: // Stop
			if (accion) {
				// System.out.println("SwipeGesture: " + id);
				if (gesto != null) {
					gesto.swipe();
				}
				detener(1);
			}
			break;
		}
	}

	// ======================================================
	// 2. Circle Gesture

	public void leapOnCircleGesture(CircleGesture g, int state) {
		int id = g.getId();
		Finger finger = g.getFinger();
		PVector positionCenter = g.getCenter();
		float radius = g.getRadius();
		float progress = g.getProgress();
		long duration = g.getDuration();
		float durationSeconds = g.getDurationInSeconds();
		int direction = g.getDirection();

		switch (state) {
		case 1: // Start
			break;
		case 2: // Update
			break;
		case 3: // Stop
			// System.out.println("CircleGesture: " + id);
			if (accion) {
				if (gesto != null) {
					gesto.circleGeneral();
				}
				detener(1);
			}
			break;
		}

		switch (direction) {
		case 0: // Anticlockwise/Left gesture
			if (accion) {
				if (gesto != null) {
					gesto.circleIzquierda();
				}
				detener(1);
			}
			break;
		case 1: // Clockwise/Right gesture
			if (accion) {
				if (gesto != null) {
					gesto.circleDerecha();
				}
				detener(1);
			}
			break;
		}
	}

	// ======================================================
	// 3. Screen Tap Gesture
	public void leapOnScreenTapGesture(ScreenTapGesture g) {
		int id = g.getId();
		Finger finger = g.getFinger();
		PVector position = g.getPosition();
		PVector direction = g.getDirection();
		long duration = g.getDuration();
		float durationSeconds = g.getDurationInSeconds();
		if (accion) {
			// System.out.println("ScreenTapGesture: " + id);
			if (gesto != null) {
				gesto.screenTap();
			}
			detener(1);
		}
	}

	// ======================================================
	// 4. Key Tap Gesture

	public void leapOnKeyTapGesture(KeyTapGesture g) {
		int id = g.getId();
		Finger finger = g.getFinger();
		PVector position = g.getPosition();
		PVector direction = g.getDirection();
		long duration = g.getDuration();
		float durationSeconds = g.getDurationInSeconds();
		if (accion) {
			// System.out.println("KeyTapGesture: " + id);
			if (gesto != null) {
				gesto.keyTap();
			}
			detener(1);
		}
	}

	public void informacion() {
		int fps = leap.getFrameRate();
		for (Hand hand : leap.getHands()) {

			// ==================================================
			// 2. Hand

			int handId = hand.getId();
			PVector handPosition = hand.getPosition();
			PVector handStabilized = hand.getStabilizedPosition();
			PVector handDirection = hand.getDirection();
			PVector handDynamics = hand.getDynamics();
			float handRoll = hand.getRoll();
			float handPitch = hand.getPitch();
			float handYaw = hand.getYaw();
			boolean handIsLeft = hand.isLeft();
			boolean handIsRight = hand.isRight();
			float handGrab = hand.getGrabStrength();
			float handPinch = hand.getPinchStrength();
			float handTime = hand.getTimeVisible();
			PVector spherePosition = hand.getSpherePosition();
			float sphereRadius = hand.getSphereRadius();

			if (handGrab >= 0.9) {
				grab = true;
			} else {
				grab = false;
			}

			// --------------------------------------------------
			// Drawing
			// hand.draw();

			// ==================================================
			// 3. Arm

			if (hand.hasArm()) {
				Arm arm = hand.getArm();
				float armWidth = arm.getWidth();
				PVector armWristPos = arm.getWristPosition();
				PVector armElbowPos = arm.getElbowPosition();
			}

			// ==================================================
			// 4. Finger

			Finger fingerThumb = hand.getThumb();
			// or hand.getFinger("thumb");
			// or hand.getFinger(0);

			Finger fingerIndex = hand.getIndexFinger();
			// or hand.getFinger("index");
			// or hand.getFinger(1);

			Finger fingerMiddle = hand.getMiddleFinger();
			// or hand.getFinger("middle");
			// or hand.getFinger(2);

			Finger fingerRing = hand.getRingFinger();
			// or hand.getFinger("ring");
			// or hand.getFinger(3);

			Finger fingerPink = hand.getPinkyFinger();
			// or hand.getFinger("pinky");
			// or hand.getFinger(4);

			for (Finger finger : hand.getFingers()) {
				// or hand.getOutstretchedFingers();
				// or hand.getOutstretchedFingersByAngle();

				int fingerId = finger.getId();
				PVector fingerPosition = finger.getPosition();
				PVector fingerStabilized = finger.getStabilizedPosition();
				PVector fingerVelocity = finger.getVelocity();
				PVector fingerDirection = finger.getDirection();
				float fingerTime = finger.getTimeVisible();

				// ------------------------------------------------
				// Drawing

				// Drawing:
				// finger.draw(); // Executes drawBones() and drawJoints()
				// finger.drawBones();
				// finger.drawJoints();

				// ------------------------------------------------
				// Selection

				switch (finger.getType()) {
				case 0:
					// System.out.println("thumb");
					break;
				case 1:
					// System.out.println("index");
					break;
				case 2:
					// System.out.println("middle");
					break;
				case 3:
					// System.out.println("ring");
					break;
				case 4:
					// System.out.println("pinky");
					break;
				}

			}
		}
	}

	void pinchStarted() {
		app.println("pinch started");
	}

	void pinchMoved(PVector posIndexTip) {
		app.println("pinch moved: " + posIndexTip);
	}

	void pinchReleased() {
		app.println("pinch released");
	}

	public float getHandX() {

		if (!leap.getHands().isEmpty()) {
			Hand mHand = leap.getHands().get(0);
			PVector mHandPos = mHand.getPosition();
			x = mHandPos.x;
		}
		return x;
	}

	public float getHandY() {

		if (!leap.getHands().isEmpty()) {
			Hand mHand = leap.getHands().get(0);
			PVector mHandPos = mHand.getPosition();
			y = mHandPos.y;
		}
		return y;
	}

	public boolean getAgarro() {
		return grab;
	}

	public void setClassGestos(Gestos g) {
		this.gesto = g;
	}

	public interface Gestos {

		// En horizontal----------------------------------
		public void swipe();

		// En Circulo----------------------------------
		public void circleGeneral();

		// En Circulo----------------------------------
		public void circleDerecha();

		// En Circulo----------------------------------
		public void circleIzquierda();

		// Hacia abajo----------------------------------
		public void screenTap();

		// En sig zag----------------------------------
		public void keyTap();

	}

}