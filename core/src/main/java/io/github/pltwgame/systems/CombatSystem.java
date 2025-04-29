package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import io.github.pltwgame.components.CombatComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.TeamComponent;
import io.github.pltwgame.components.FollowComponent;

@All({CombatComponent.class, PositionComponent.class, TeamComponent.class})
public class CombatSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<CombatComponent> cm;
    private ComponentMapper<TeamComponent> tm;
    private ComponentMapper<FollowComponent> fm;

    public CombatSystem(){

    }

    @Override
    protected void process(int entityId) {
        PositionComponent atkPos = pm.get(entityId);
        CombatComponent atkCombat = cm.get(entityId);

        CombatComponent targCombat;

        if(atkCombat.target == -1) {
            findTarget(entityId, atkCombat, atkPos);
        }

        if(atkCombat.target != -1 && isInRange(atkCombat.detectionRange, entityId, atkCombat.target)) {
            targCombat = cm.get(atkCombat.target);

            if(!fm.has(entityId)){
                FollowComponent follow = world.getEntity(entityId).edit().create(FollowComponent.class);
                follow.target = atkCombat.target;
            }
        } else {
            atkCombat.target = -1;
            if(fm.has(entityId)){
                fm.remove(entityId);
            }
        }

    }

    private void findTarget(int entityId, CombatComponent atkCombat, PositionComponent atkPos){
        IntBag entities = getEntityIds();
        int[] ids = entities.getData();

        TeamComponent atkTeam = tm.get(entityId);

        for (int i = 0, s = ids.length; i < s; i++) {
            int id = ids[i];

            TeamComponent targTeam = tm.get(id);

            if (entityId == id || atkTeam.team.equals(targTeam.team)) continue;

            PositionComponent targPos = pm.get(id);

            if (atkCombat.target == -1) {
                if (isInRange(atkCombat.detectionRange, entityId, id)) {
                    atkCombat.target = id;
                    Gdx.app.debug("Target Found", "Attacker: " + entityId + " Target: " + id);
                }
            }
        }
    }

    private boolean isInRange(int radius, int attacker, int target){
        PositionComponent atkPos = pm.get(attacker);
        PositionComponent targPos = pm.get(target);

        Vector2 atkVector = new Vector2(atkPos.x,atkPos.y);
        Vector2 targVector = new Vector2(targPos.x,targPos.y);

        return atkVector.sub(targVector).len() <= radius;
    }


}
