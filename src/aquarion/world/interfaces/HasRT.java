package aquarion.world.interfaces;

import aquarion.world.graphs.RTGraph;
import aquarion.world.meta.RTConfig;
import aquarion.world.meta.RTModule;
import arc.struct.*;
import mindustry.gen.*;
public interface HasRT extends Buildingc {

    default boolean connects(HasRT to) {
        return rTConfig().outputsRT || rTConfig().acceptsRT;
    }



    default HasRT getRTDest(HasRT from) {
        return this;
    }

    default float rotationProduction() {
        return 0f;
    }
    default float rotationConsumption() {
        return rTConfig().rotationConsumption;
    }

    default Seq<HasRT> nextBuilds() {
        return proximity().select(b -> b instanceof HasRT && connects((HasRT) b)).map(b -> ((HasRT) b).getRTDest(this)).removeAll(b -> !connects(b) && !b.connects(this));
    }

    RTModule rotationPower();

    RTConfig rTConfig();

    default RTGraph rTGraph() {
        return rotationPower().graph;
    }	default void onRTupdate() {

    }
    default RTGraph graph() {
        return rotationPower().graph;
    }


}