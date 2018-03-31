package frontend;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

import kinesthesis.LeapController;

public class SpellController extends AbstractAppState {
	
	private HorryOtter app;
	
	private Node spellNode;
	private LeapController leapController;
	private WandController wandController;

	private Spell lastSpell;
	private long timeOfLastSpell;
	private final int DEBOUNSE_TIME = 1000;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.app = (HorryOtter) app;
	}

	public SpellController(Node spellNode, LeapController leapController, WandController wandController) {
		this.lastSpell = leapController.getLatestSpell();
		this.leapController = leapController;
		this.spellNode = spellNode;
		this.wandController = wandController;
		timeOfLastSpell = 0;
	}

	@Override
	public void update(float tpf) {
		System.out.println("wks");
		long currentTime = System.currentTimeMillis();

		if (currentTime - timeOfLastSpell < DEBOUNSE_TIME)
			return;

		Spell nextSpell = leapController.getLatestSpell();
		if (nextSpell.getID() == lastSpell.getID())
			return;

		timeOfLastSpell = currentTime;
		switch (nextSpell.getType()) {
		case NULL:
			break;
		case LEVITATE:
			System.out.println("Levitate");
			break;
		case SPARKS:
			System.out.println("Spark");
			castSpark();
			break;
		}
	}

	private void castSpark() {
		Matrix3f angle = wandController.getWandAngle();

		Material unshaded = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		unshaded.setColor("Color", ColorRGBA.Brown);
		Cylinder beamShape = new Cylinder(100, 100, .2f, 100, true);
		Geometry beam = new Geometry("Wand", beamShape);
		beam.setMaterial(unshaded);
		beam.setLocalRotation(angle);
		spellNode.attachChild(beam);

	}

	private void castLevitate() {
		// TODO Auto-generated method stub

	}
}
