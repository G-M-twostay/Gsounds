package gsound.api;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.math.Vector3;
import gsound.Events.PacketSender;

import java.util.Collection;

public class SoundPlayer extends PacketSender {
    public SoundPlayer(String sound) {
        this(sound, 0);
    }

    public SoundPlayer(String sound, int delay) {
        super.pkt.name = sound;
        super.delay = (short) delay;
    }

    public void setDelay(int delay) {
        super.delay = (short) delay;
    }

    public short getDelay(){return super.delay;}

    public void playAt(int x, int y, int z) {
        playAt(x, y, z, 1, 1);
    }

    public void playAt(int x, int y, int z, float volume, float pitch) {
        pkt.x = x;
        pkt.y = y;
        pkt.z = z;
        pkt.volume = volume;
        pkt.pitch = pitch;
        super.send();
    }

    public void playTo(Collection<Player> players, float volume, float pitch) {
        playTo(players.toArray(new Player[0]), volume, pitch);
    }

    public void playTo(Player[] players, float volume, float pitch) {
        pkt.pitch = pitch;
        pkt.volume = volume;
        Server.broadcastPacket(players, pkt);
    }

    public void setPos(int x, int y, int z) {
        pkt.x = x;
        pkt.y = y;
        pkt.z = z;
    }

    public void setPos(Vector3 v) {
        setPos(v.getFloorX(), v.getFloorY(), v.getFloorZ());
    }

    public void playFor(Player p, float volume, float pitch) {
        setPos(p);
        p.dataPacket(super.pkt);
    }
}
