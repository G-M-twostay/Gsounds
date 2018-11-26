package gsound;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

import java.io.File;
import java.util.HashMap;

public class GSound extends PluginBase {
    private final HashMap<String, Object[]> sounds= new HashMap<>();

    @Override
    public void onLoad() {
        getLogger().info("Loading gsound.GSound by G.M.");
        Config config = new Config(new File(getDataFolder(), "config.yml"), Config.YAML);
        if(config.getKeys(false).size()==0)config.set("OnPlayerJoin",new ConfigSection(){
            {
                put("volume",1);
                put("pitch",1);
                put("name","random.levelup");
                put("delay",8);
            }
        });
        config.save();
        config.reload();
        for(String et:config.getKeys(false)){
            ConfigSection temp=config.getSection(et);
            sounds.put(et,new Object[]{temp.getString("name"),
                    temp.getDouble("volume"),
                    temp.getDouble("pitch"),
                    temp.getInt("delay",10)
            });
        }
        config.save();
    }

    @Override
    public void onEnable() {

        for (String et : sounds.keySet()) {
            Object[]temp=sounds.get(et);
            String name=(String)temp[0];
            float volume=(float)(double)temp[1];
            float pitch=(float)(double)temp[2];
            short delay=(short)((int)temp[3]*20);
            switch (et.toLowerCase()) {
                case "onplayerjoin":
                    getServer().getPluginManager().registerEvents(new gsound.Events.TypePlayerJoin(name,volume,pitch,delay), this);
                    break;
                case "onplayerquit":
                    getServer().getPluginManager().registerEvents(new gsound.Events.TypePlayerQuit(name,volume,pitch,delay), this);
                    break;
                case "onplayerdeath":
                    getServer().getPluginManager().registerEvents(new gsound.Events.TypePlayerDeath(name,volume,pitch,delay), this);
                    break;
                case "onplayerteleport":
                    getServer().getPluginManager().registerEvents(new gsound.Events.TypePlayerTeleport(name,volume,pitch,delay),this);
            }
        }
        getServer().getCommandMap().register("Gsound:playsound",new GPlaySound());
        getLogger().info("Successfully enabled.");
    }

}
