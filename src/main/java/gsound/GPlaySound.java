package gsound;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.network.protocol.PlaySoundPacket;

public class GPlaySound extends VanillaCommand {
    public GPlaySound() {
        super("playsound", "Play a sound", "/playsound <sound> [player] [position] [volume] [pitch]");
        commandParameters.clear();
        commandParameters.put("normal", new CommandParameter[]{
                new CommandParameter("sound", CommandParamType.STRING,false),
                new CommandParameter("player", CommandParameter.ARG_TYPE_PLAYER, true),
                new CommandParameter("position", CommandParameter.ARG_TYPE_BLOCK_POS, true),
                new CommandParameter("volume", CommandParamType.FLOAT, true),
                new CommandParameter("pitch", CommandParamType.FLOAT, true)
        });
        this.setPermission("gsound.playsound");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return false;
        if (args.length < 1 || args.length > 7 || (args.length > 2 && args.length < 5)) {
            return false;
        }
        Player p = (Player) sender;
        Player target=args.length>1?sender.getServer().getPlayer(args[1]):null;
        if (args.length == 1) {
            if (sender.isPlayer()) {
                return execute(args[0], p);
            } else {
                return false;
            }
        }
        else if (args.length == 2) {
            if (target == null) {
                sender.sendMessage("Can't find player " + args[1]);
                return true;
            }
            execute(args[0], target);
            return true;
        } else if (args.length < 6) {
            if (target == null) {
                sender.sendMessage("Can't find player " + args[1]);
                return true;
            }
            return execute(args[0], target, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), 1, 1);
        } else {
            float v = args.length >= 6 ? Float.parseFloat(args[5]) : 1;
            float pt = args.length == 7 ? Float.parseFloat(args[6]) : 1;
            if (target == null) {
                p.sendMessage("Can't find player " + args[1]);
                return true;
            }
            execute(args[0], target, Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), pt, v);
            return true;
        }
    }

    private boolean execute(final String sound, @com.sun.istack.internal.NotNull final Player p) {
        Server.broadcastPacket(new Player[]{p}, new PlaySoundPacket() {
            {
                x = p.getFloorX();
                y = p.getFloorY();
                z = p.getFloorZ();
                volume = 1;
                pitch = 1;
                name = sound;
            }
        });
        return true;
    }

    private boolean execute(String sound, @com.sun.istack.internal.NotNull Player p, int x, int y, int z, float pitch, float volume) {
        PlaySoundPacket pkt = new PlaySoundPacket();
        pkt.name = sound;
        pkt.x = x;
        pkt.y = y;
        pkt.z = z;
        pkt.pitch = pitch;
        pkt.volume = volume;
        p.getLevel().addChunkPacket(p.getChunkX(), p.getChunkZ(), pkt);
        return true;
    }
}
