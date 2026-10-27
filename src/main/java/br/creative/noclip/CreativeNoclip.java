package br.creative.noclip;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class CreativeNoclip extends JavaPlugin implements Listener {
    private final Set<UUID> enabled = new HashSet<>();

    @Override public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("CreativeNoclip ativado.");
    }

    @Override public void onDisable() {
        for (UUID id : enabled) {
            Player p = getServer().getPlayer(id);
            if (p != null) p.setFlying(false);
        }
        enabled.clear();
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Este comando só pode ser usado por um jogador.");
            return true;
        }
        if (!p.hasPermission("creativenoclip.use")) {
            p.sendMessage("§cVocê não tem permissão para usar isso.");
            return true;
        }
        if (args.length != 1 || !(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))) {
            p.sendMessage("§eUse: §f/nc on §eou §f/nc off");
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            enabled.add(p.getUniqueId());
            p.setGameMode(GameMode.CREATIVE);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.sendMessage("§aCreative Noclip ativado. §7Use §f/nc off §7para desativar.");
        } else {
            enabled.remove(p.getUniqueId());
            p.setFlying(false);
            p.sendMessage("§cCreative Noclip desativado.");
        }
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (!enabled.contains(p.getUniqueId())) return;
        Location to = event.getTo();
        if (to == null) return;
        // If the player's destination intersects a solid block, cancel movement.
        // This is a safe API-only approximation, not true server-side collision bypass.
        Block feet = to.getBlock();
        Block head = to.clone().add(0, 1, 0).getBlock();
        if (isSolid(feet) || isSolid(head)) {
            Location from = event.getFrom();
            if (from.getWorld() == to.getWorld()) {
                event.setTo(from);
            }
        }
    }

    private boolean isSolid(Block b) {
        Material m = b.getType();
        return m.isSolid() && !m.isAir();
    }
}
