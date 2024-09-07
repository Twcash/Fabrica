package aquarion.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.MultiPacker;
import mindustry.type.UnitType;

import static mindustry.core.UI.packer;

public class MechanicalUnitType extends UnitType {
    public TextureRegion undamagedRegion;
    public TextureRegion damagedRegion;
    public TextureRegion veryDamagedRegion;
    public float damageThreshold = 0.95f; // these are percentage based btw%
    public float severeDamageThreshold = 0.3f;

    public MechanicalUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2f3734");
        drawCell = false;
    }
    @Override
    public void load() {
        super.load();
        undamagedRegion = Core.atlas.find(name);
        damagedRegion = Core.atlas.find(name + "-damage" + 1);
        veryDamagedRegion = Core.atlas.find(name + "-damage" + 2);
    }
    public TextureRegion getArmorRegion() {
        return undamagedRegion == null ? (undamagedRegion = new TextureRegion()) : undamagedRegion;
    }

    public TextureRegion getDamagedArmorRegion() {
        return damagedRegion == null ? (damagedRegion = new TextureRegion()) : damagedRegion;
    }

    public TextureRegion getSeverelyDamagedArmorRegion() {
        return veryDamagedRegion == null ? (veryDamagedRegion = new TextureRegion()) : veryDamagedRegion;
    }
    @Override
    public void drawBody(Unit unit) {
        applyColor(unit);
        TextureRegion armorRegion;

        // Determine which set of textures to use based on health thresholds
        if (unit.health < unit.maxHealth * severeDamageThreshold) {
            armorRegion = getSeverelyDamagedArmorRegion();
        } else if (unit.health < unit.maxHealth * damageThreshold) {
            armorRegion = getDamagedArmorRegion();
        } else {
            armorRegion = getArmorRegion();
        }
        Draw.z(Layer.groundUnit);
        Draw.rect(armorRegion, unit.x, unit.y, unit.rotation - 90);
        Draw.z(Layer.groundUnit - .00000011f);
        drawWeapons(unit);
    }
    public void drawOutline(Unit unit){
        Draw.reset();

        if(Core.atlas.isFound(outlineRegion)){
            applyColor(unit);
            applyOutlineColor(unit);
            Draw.z(Layer.groundUnit -.0000001f);
            Draw.rect(outlineRegion, unit.x, unit.y, unit.rotation - 90);
            Draw.reset();
        }
    }

    @Override
    public void killed(Unit unit) {
        super.killed(unit);

        // Check if the unit should transform into the derelict type
        if (unit.health <= 0) {
            // Transform into derelict type by spawning a new unit
        }
    }
}