package gsound.Events;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.scheduler.NukkitRunnable;

public abstract class PacketSender extends NukkitRunnable{
    protected short delay;
    Level level;
    protected PlaySoundPacket pkt=new PlaySoundPacket();

    PacketSender(short delay){
        this.delay=delay;
    }
    protected PacketSender(){}
    protected void send(){
        Server.getInstance().getScheduler().scheduleDelayedTask(this,delay);
    }
    public void run(){
        level.addChunkPacket(pkt.x>>4,pkt.z>>4,pkt);
    }
}
