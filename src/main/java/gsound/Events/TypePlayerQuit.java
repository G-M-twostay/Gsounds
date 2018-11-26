package gsound.Events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;

public class TypePlayerQuit extends PacketSender implements Listener {

    public TypePlayerQuit(String name, float v, float p, short delay) {
        super(delay);
        pkt.name = name;
        pkt.volume = v;
        pkt.pitch = p;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        pkt.x=p.getFloorX();
        pkt.y=p.getFloorY();
        pkt.z=p.getFloorZ();
        super.level=p.getLevel();
        send();
    }
}
