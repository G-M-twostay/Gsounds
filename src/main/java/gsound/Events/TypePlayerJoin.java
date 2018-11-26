package gsound.Events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class TypePlayerJoin extends PacketSender implements Listener {

    public TypePlayerJoin(String name, float v, float p, short delay) {
        super(delay);
        pkt.name = name;
        pkt.volume = v;
        pkt.pitch = p;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        super.level=p.getLevel();
        pkt.x = p.getFloorX();
        pkt.y = p.getFloorY();
        pkt.z = p.getFloorZ();
        send();
    }
}