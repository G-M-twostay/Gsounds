package gsound.Events;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.PlaySoundPacket;

public class TypePlayerTeleport extends PacketSender implements Listener {
    private final PlaySoundPacket pkt = new PlaySoundPacket();
    private final PlaySoundPacket pkt2 = new PlaySoundPacket();

    public TypePlayerTeleport(String name, float v, float p, short delay) {
        super(delay);
        pkt.name = name;
        pkt.volume = v;
        pkt.pitch = p;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerTeleportEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        pkt.x = from.getFloorX();
        pkt.y = from.getFloorY();
        pkt.z = from.getFloorZ();
        super.pkt=pkt;
        send();
        pkt2.x = to.getFloorX();
        pkt2.y = to.getFloorY();
        pkt2.z = to.getFloorZ();
        super.pkt=pkt2;
        send();
    }
}


